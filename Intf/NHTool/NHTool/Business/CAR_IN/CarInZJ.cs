using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using System.Threading;
using NHTool.Device.RFID;
using System.Windows.Forms;
using Newtonsoft.Json.Linq;
using NHTool.Forms.SelectMineMachine;
using Newtonsoft.Json;
namespace NHTool.Business.Car_IN
{
    class CarInZJ : CarInBusiness
    {

        public enum modbusPoint_zjIn : int
        {
            inRadiation = 1,  //入口红外
            outRadiation = 2, //出口红外
            lightRedOut = 17, //红绿灯
            lightGreenOut = 18,
            frontGateUpOut = 19, //道闸
            frontGateDownOut = 20
        }


        /// <summary>
        /// 调用入厂before过程
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBRCBefore()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", ctlConfig["currentFlowId"]);
            dataJson.Add("mineCardId", "e2005127870a00880310f244");

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.rc_before_preprocess", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarInZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarInZJ), "invokeDBRCBefore数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
                return retJson;
            }
            return retJson;
        }

        /// <summary>
        /// 检查剩余车位
        /// </summary>
        /// <returns>剩余能进去的车数量</returns>
        public String qryAvailableCarsCnt()
        {
            JObject dataJson = new JObject();

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("qry_available_cars_cnt", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarInZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarInZJ), "qryAvailableCarsCnt数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "异常");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }

            return Commons.getJsonValue(retJson, "logicRetMsg");
        }


        public override JObject processCarHttpRequestMethod(String httpStr)
        {
            JObject ret = new JObject();
            JObject jsonIn = new JObject();
            try
            {
                //前期判断
                JObject dataJson = (JObject)JsonConvert.DeserializeObject(httpStr);
                string action = Commons.getJsonValue(dataJson, "action");
                string deviceCode = Commons.getJsonValue(dataJson, "deviceCode");

                if (deviceCode != null && deviceCode.StartsWith("RC"))
                {
                    string ChannelNum = deviceCode.Substring(2);
                    if ("systemReset".Equals(action))
                    {
                        stateEventArgs.nextStep = Commons.STEP.IDLE;
                    }
                    else if ("actionFrontGateUp".Equals(action))
                    {
                        deviceAction(ChannelNum,"GateUp");
                    }
                    else if ("actionFrontGateDown".Equals(action))
                    {
                        deviceAction(ChannelNum, "GateDown");
                    }
                    else if ("actionLEDGreen".Equals(action))
                    {
                        deviceAction(ChannelNum, "LedGreen");
                    }
                    else if ("actionLEDRed".Equals(action))
                    {
                        deviceAction(ChannelNum, "LedRed");
                    }
                    else if ("actionDbInovke".Equals(action))
                    {

                    }
                }

                ret.Add(Commons.RES_CODE, "0");
                ret.Add(Commons.RES_MSG, "succ");

                return ret;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                ret.Add(Commons.RES_CODE, "1");
                ret.Add(Commons.RES_MSG, "err：" + e.Message);
                LogTool.WriteLog(typeof(CarInZJ), httpStr + ",执行命令失败," + jsonIn.ToString() + "," + e.Message);
                return ret;
            }
        }


        int expectDeviceCount = 3;//期望初始化成功N个设备
        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;
            httpDbTool = new HttpDbTool();

            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_zjIn)))
            {
                LogTool.WriteLog(typeof(CarInZJ), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInZJ), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarInZJ), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInZJ), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

           if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(CarInZJ), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInZJ), "LED初始化失败");
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
                LogTool.WriteLog(typeof(CarInZJ), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarInZJ), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
            }

            return initResult;
        }

        
        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.currentChannel = "1";
            stateEventArgs.nextStep = Commons.STEP.IDLE;

            if (this.initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_OutFinish: carOutStep(); break;
                                default: idle(1); break;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarInZJ), e.StackTrace); 
                            LogTool.WriteLog(typeof(CarInZJ), "主循环异常:" + e.Message);
                        }
                        
                         
                    }
                })).Start();
            }
        }


        public override void carIdleStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //recoverDeviceState();
            
            //判断厂内车辆
            string availcnt = qryAvailableCarsCnt();
            if (availcnt.Equals("0"))
            {
                tipMsg("厂内车辆已满");
                idle(15);
                return;
            }
            else 
            {
                tipMsg("剩余车位：" + availcnt);
                idle(10);
            }


            if (isRadiationBolckOk("进口红外", "inRadiation", 2))
            {   
                Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
                if (rfid_result[Commons.RES_CODE].Equals("0"))//成功启动天线读取车卡
                {
                    tipMsg("正在扫描卡片");
                    stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                }
            }

        }

        /// <summary>
        /// 入厂天线扫卡
        /// </summary>
        public override void getCarNoStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            stateEventArgs.stepExcuteCount++;
            if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
            {
                foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                {
                    //string scanEpcTag = epcStr.Key;
                    if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                    {
                        //判断卡号是否合法 0000 和ab00开头的是车卡
                        if (epcStr.Length != 24 || (!epcStr.StartsWith("0000") && !epcStr.StartsWith("ab00")))
                        {
                            LogTool.WriteLog(typeof(CarInZJ), "本次无效车卡：" + epcStr);
                            readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                            continue;
                        }

                        writeMointorSingle("车卡号", epcStr);
                        stateEventArgs.cardID = epcStr;

                        //入厂before检测
                        JObject retJson = invokeDBRCBefore();

                        if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                        {
                            stateEventArgs.carNo = getJsonValue(retJson, "carId");//从返回结果里获取车牌号
                            stateEventArgs.stepExcuteCount = 0;

                            readRfidDevice.stop_read_RFID_Info();//停止读
                            readRfidDevice.clearEcpTagList();//清除列表里的数据

                            Commons.putDcValue(ref dictionaryUseInForm, "carId", stateEventArgs.carNo);
                            Commons.putDcValue(ref dictionaryUseInForm, "cardId", stateEventArgs.cardID);
                            Commons.putDcValue(ref dictionaryUseInForm, "flowId", ctlConfig["currentFlowId"]);
                            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "1");

                            Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoRC", stateEventArgs.carNo);
                            
                            //允许动作,先抬杆，后写LED，防止看到字就往里冲
                            actionFrontGateUp();
                            actionLedGreen();
                            idle(1);
                            tipMsg(stateEventArgs.carNo + "请入厂");
                            stateEventArgs.nextStep = Commons.STEP.CAR_OUT; //织金到这就出去了
                            return;
                        }
                        else
                        {
                            tipMsg(getJsonValue(retJson, Commons.RES_MSG));
                            readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);//将存储过程检测到的废卡的EPC插入错卡列表里                         
                            return;
                        }
                    }
                }
            }
            else
            {//扫不到车卡的情形
                if (stateEventArgs.stepExcuteCount > 90)
                {
                    stateEventArgs.stepExcuteCount = 0;
                    resetAndToNextSetp("未扫到车卡", Commons.STEP.IDLE);
                    //落道闸红灯
                    actionFrontGateDown();
                    actionLedRed();
                }
            }
            idle(1);
        }


        /// <summary>
        /// 入厂过程处理
        /// </summary>
        public override void checkCarOutSign()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //出去有对射信号
            if (isRadiationBolckOk("出口处红外", Commons.outRadiation, 2))
            {
                tipMsg(stateEventArgs.carNo + "正在入厂");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
                idle(3);//保持一段时间
            }
        }


        /// <summary>
        /// 入厂结束
        /// </summary>
        public override void carOutStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }


            if (!isRadiationBolckOk("入厂出口红外", Commons.outRadiation, 2))
            {
                resetAndToNextSetp(stateEventArgs.carNo + "完成入厂", Commons.STEP.IDLE);
                idle(1);
                Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoRC", "");

                //落道闸红灯
                actionFrontGateDown();
                actionLedRed();

                //显示下剩余车位
                //判断厂内车辆
                string availcnt = qryAvailableCarsCnt();
                if (availcnt.Equals("0"))
                {
                    tipMsg("厂内车辆已满");
                }
                else 
                {
                    tipMsg("厂内剩余车位" + availcnt);
                    idle(2);
                }
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


        public override void recoverDeviceState()
        {
            actionFrontGateDown();
            actionLedRed();

            Commons.putDcValue(ref dictionaryUseInForm, "carId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "cardId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "flowId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "trainNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketQty", "");
            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "0");
            Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "0");
        }


        //提示信息，LED+声音
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowRC", msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                ledShow(msg);
            }
        }


        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            //先都置成低电平，再来个高电平，织金的亚当模块调试工具中试出来的
            modbus.setCommand("19", "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand("20", "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand("20", "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelRC", "1");
        }



        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            //先都置成低电平，再来个高电平，织金的亚当模块调试工具中试出来的
            modbus.setCommand("19", "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand("20", "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand("19", "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelRC", "0");
        }

        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand("17", "True", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand("18", "False", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsRC", "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand("17", "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand("18", "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsRC", "1");
        }
    }
}
