using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Windows.Forms;

namespace NHTool.Business.Car_OUT
{
    class CarOutZJ : BusinessBase
    {
        public enum modbusPoint_zjOut : int
        {
            inRadiation = 1,  //入口红外
            outRadiation = 2, //出口红外
            lightRedOut = 17, //红灯
            lightGreenOut = 18,//绿灯
            frontGateUpOut = 19, //  道闸抬   先【落】0，再【抬】1，则抬；也就是说先都0 再抬1，则可以抬
            frontGateDownOut = 20  //道闸落   先【抬】0，再【落】1，则落；也就是说先都0 再落1，则可以落
        }


        int expectDeviceCount = 3;//期望初始化成功N个设备
        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;
            stateEventArgs.currentChannel = "1";
            httpDbTool = new HttpDbTool();

            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_zjOut)))
            {
                LogTool.WriteLog(typeof(CarOutZJ), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarOutZJ), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarOutZJ), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarOutZJ), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(CarOutZJ), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarOutZJ), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }


            //启动接受控制测点的服务器
            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            //恢复重置设备
            recoverDeviceState();
            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarOutZJ), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarOutZJ), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
            }

            return initResult;
        }



        /// <summary>
        /// 主体程序流转，通过switch+stateEventArgs进行切换
        /// </summary>
        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.nextStep = Commons.STEP.IDLE;

            if (initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(15); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_OutFinish: carOutStep(); break;
                                default: idle(1); break;
                            }

                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarOutZJ), "主循环异常:" + e.StackTrace);
                            LogTool.WriteLog(typeof(CarOutZJ), "主循环异常:" + e.Message);
                        }
                    }
                })).Start();

            }
        }



        /// <summary>
        /// 汽车调度，空闲时，持续判断入红外对射被挡住超2秒，则认为汽车开始入厂
        /// </summary>
        public void carIdleStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                tipMsg("严禁冲卡违者罚款");
                restData();
            }

            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                tipMsg("扫描车卡");
            }
        }

       
       

        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public void getCarNoStep(int seconds)
        {
            tipMsg("扫描车卡");

            //先打开天线
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();

            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (!rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("天线打开异常");
                idle(3);
                return;
            }

            //天线打开成功，在seconds秒内，每秒试读取卡数据，并调用存储过程校验
            for (int i = 0; i < seconds; i++)
            {
                idle(1);
                if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
                {
                    foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                    {
                        if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                        {
                            //判断卡号是否合法 0000 和ab00开头的是车卡
                            if (epcStr.Length != 24 || (!epcStr.StartsWith("0000") && !epcStr.StartsWith("ab00")))
                            {
                                LogTool.WriteLog(typeof(CarOutZJ), "本次无效车卡：" + epcStr);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                                continue;
                            }

                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;

                            JObject retJson = invokeDBCheckOut();//调用过程检查是否允许出厂

                            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                            {
                                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();
                                stateEventArgs.carNo = getJsonValue(retJson, "carId");

                                //放行
                                actionFrontGateUp();
                                actionLedGreen();
                                idle(1);
                                tipMsg(stateEventArgs.carNo + "允许出厂");
                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoCC", stateEventArgs.carNo);
                                return;
                            }
                            else
                            {
                                tipMsg(getJsonValue(retJson, "logicRetCode"));
                                idle(2);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);//将存储过程检测到的废卡的EPC插入错卡列表里                         
                            }
                        }
                    }
                }
            }

            //超时seconds扫不到车卡时，切换到idle
            tipMsg("未扫到有效车卡");
            resetAndToNextSetp(null, Commons.STEP.IDLE);
            idle(1);
        }

        /// <summary>
        /// 调用过程检测是否允许出厂
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBCheckOut()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", ctlConfig["currentFlowId"]);

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("check_out", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarOutZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarOutZJ), "数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }
            return retJson;
        }



        public void checkCarOutSign()
        {
            if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "正在离开");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
            }
        }



        /// <summary>
        /// 汽车调度，称重离开道闸
        /// </summary>
        public virtual void carOutStep()
        {
            if (!isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoCC", "");
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                recoverDeviceState();
            }
        }



        public void recoverDeviceState()
        {
            actionFrontGateDown();
            actionLedRed();

            restData();

            Commons.putDcValue(ref dictionaryUseInForm, "carId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "cardId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "flowId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "trainNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketQty", "");
            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "0");
            Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "0");
            tipMsg("严禁冲卡违者罚款");
        }


        //提示信息
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowCC", msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                ledShow(msg);
            }
        }


        public void ledShow(string text)
        {
            if (stateEventArgs.currentChannel.Equals("1"))
            {
                ledDevice.sendText_Screen(Commons.pwd, text);
            }
            else if (stateEventArgs.currentChannel.Equals("2"))
            {
                ledDevice2.sendText_Screen(Commons.pwd, text);
            }
        }


        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            //先都置成低电平，再来个高电平；因为有人干预，所以老的方法有时候不能控制
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.frontGateUpOut)), "False", Commons.modbusType.COIL_STATUS); 
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.frontGateDownOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.frontGateDownOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelCC", "1");
        }



        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            //先都置成低电平，再来个高电平；因为有人干预，所以老的方法有时候不能控制
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.frontGateUpOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.frontGateDownOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.frontGateUpOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelCC", "0");
        }



        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.lightGreenOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.lightRedOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsCC", "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.lightRedOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjOut.lightGreenOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsCC", "1");
        }

    }
}
