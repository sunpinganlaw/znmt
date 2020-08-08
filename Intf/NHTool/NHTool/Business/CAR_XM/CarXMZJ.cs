using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Windows.Forms;
using NHTool.Device.LED;

namespace NHTool.Business.Car_XM
{
    class CarXMZJ : BusinessBase
    {
        /// <summary>
        /// 当前卸煤沟使用状态  默认IDLE, UNLOADING
        /// </summary>
        static string ditchStatus = Commons.DITCH_IDLE;

        static string currentXMDitch = "";

        string strIp = null;
        uint fonSize = 16;
        int width = 64;
        int height = 32;

        public enum modbusPoint_zjXM : int
        {
            groundSense = 1, //卸煤沟地感，经观察，这个点一直没有信号
            groundSense2 = 2, //龙门架地感
            inRadiation = 3,  //卸煤沟红外对射
            lmjRadiation = 4, //龙门架前的红外对射

            lightRedOut = 21, //红灯
            lightGreenOut = 22,//绿灯
            frontGateUpOut = 23, //  道闸抬
            frontGateDownOut = 24  //道闸落
        }


        /// <summary>
        /// 主体程序流转，通过switch+stateEventArgs进行切换
        /// </summary>
        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            ditchStatus = Commons.DITCH_IDLE; 

            if (initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            //writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            //switch (stateEventArgs.nextStep)
                            //{
                            //    case Commons.STEP.IDLE: carIdleStep(); break;
                            //    case Commons.STEP.CAR_GetCarNo: getCarNoStep(20); break;
                            //    case Commons.STEP.CAR_WAITING_UNLOAD: waitingUnload(); break;
                            //    case Commons.STEP.CAR_FINISH_UNLOAD: finishUnloadCoal(); break;
                            //    default: idle(1); break;
                            //}
                            actionFrontGateDown();
                            idle(1);
                            actionLedGreen();
                            idle(1);
                            actionFrontGateUp();
                            idle(1);
                            actionLedRed();
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarXMZJ), "主循环异常:" + e.StackTrace);
                        }
                    }
                })).Start();
            }
        }



        int expectDeviceCount = 3;//期望初始化成功N个设备
        public override Boolean initial()
        {
            //if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_zjXM)))
            //{
            //    LogTool.WriteLog(typeof(CarXMZJ), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
            //    return true;
            //}
            //else
            //{
            //    LogTool.WriteLog(typeof(CarXMZJ), "亚当模块初始化失败");
            //    MessageBox.Show("亚当模块初始化失败");
            //}


            initiModbusDevice("192.168.90.151", "502",typeof(modbusPoint_zjXM));
            return true;



            bool initResult = false;
            int initCount = 0;
            stateEventArgs.currentChannel = "1";
            httpDbTool = new HttpDbTool();

            currentXMDitch = ctlConfig["XM_DITCH"]; //卸煤ditch,  123456

            strIp = ctlConfig["LED_IP"];
            fonSize = 16;
            width = 64;
            height = 32;
            if (initiLedDevice(strIp))
            {
                LogTool.WriteLog(typeof(CarXMZJ), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }


            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_zjXM)))
            {
                LogTool.WriteLog(typeof(CarXMZJ), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarXMZJ), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }


            //启动接受控制测点的服务器
            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            //恢复重置设备
            recoverDeviceState();

            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarXMZJ), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
            }

            return initResult;
        }




        /// <summary>
        /// 汽车调度，空闲时，持续判断入红外对射被挡住超2秒，则认为汽车开始入厂
        /// </summary>
        public void carIdleStep()
        {
            restData();
            ditchStatus = Commons.DITCH_IDLE;//卸煤沟空闲

            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                LogTool.WriteLog(typeof(CarXMZJ), "进入通道空闲状态，龙门架开始感应车carIdleStep。。。");
                stateEventArgs.currentStep = stateEventArgs.nextStep;

                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoXM_" + currentXMDitch, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelXM_" + currentXMDitch, "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsXM_" + currentXMDitch, "0");

                tipMsg("您辛苦了");
            }


            //空闲时候龙门架下地感有信号  或  龙门架红外对射有信号，则说明有车来了
            if ((ditchStatus.Equals(Commons.DITCH_IDLE) && isGroundSense("龙门架地感", "groundSense2", 3))
                ||
                (ditchStatus.Equals(Commons.DITCH_IDLE) && isRadiationBolckOk("龙门架红外对射", "lmjRadiation", 3))
                ) //prod
            //if (ditchStatus.Equals(Commons.DITCH_IDLE) && isGroundSenseTest()) //test
            {
                
                if (invokeDBCheckDitchActive())//卸煤沟是否启用
                {
                    LogTool.WriteLog(typeof(CarXMZJ), "感应到龙门架有车，且通道激活状态，准备进入扫卡carIdleStep");
                    stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                }
                else
                {
                    LogTool.WriteLog(typeof(CarXMZJ), "感应到龙门架有车，但通道未激活，继续等着carIdleStep。。。");
                    idle(10);
                }
            }
            else 
            {
                idle(5);
            }
        }


        /// <summary>
        /// 用于调试测试，模拟龙门架下的地感总是有信号，省的真的开个车过去了
        /// </summary>
        /// <returns></returns>
        public Boolean isGroundSenseTest()
        {
            return true;
        }
       

        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public void getCarNoStep(int seconds)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                LogTool.WriteLog(typeof(CarXMZJ), "进入天线获取车号环节getCarNoStep");
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //先打开天线
            Dictionary<String, String>  rfid_result = readRfidDevice.read_RFID_Info();
            tipMsg("扫描车卡");

            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (!rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("天线打开异常");
                LogTool.WriteLog(typeof(CarXMZJ), "天线打开异常"); 
                idle(2);
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
                                LogTool.WriteLog(typeof(CarXMZJ), "本次无效车卡：" + epcStr);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                                continue;
                            }
                            
                            writeMointorSingle("卸煤车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;


                            //检查车辆是否合法（该卸煤沟，插队没）
                            JObject retJson = invokeDBCheckXMCar();

                            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode"))) 
                            {
                                stateEventArgs.nextStep = Commons.STEP.CAR_WAITING_UNLOAD;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();

                                
                                actionFrontGateUp();
                                actionLedGreen(); //绿

                                ditchStatus = Commons.DITCH_WAITING; //在卸煤路上
                                stateEventArgs.carNo = getJsonValue(retJson, "carId");
                                LogTool.WriteLog(typeof(CarXMZJ), "天线获取到车号：" + stateEventArgs.carNo + " getCarNoStep");
                                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoXM_" + currentXMDitch, stateEventArgs.carNo);

                                //放行
                                tipMsg(stateEventArgs.carNo + "请卸煤");
                                idle(2);
                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                return;
                            }
                            else if ("1001".Equals(getJsonValue(retJson, "logicRetCode")) 
                                || "1002".Equals(getJsonValue(retJson, "logicRetCode")) 
                                || "1003".Equals(getJsonValue(retJson, "logicRetCode")))
                            {
                                //检测结果失败
                                tipMsg(getJsonValue(retJson, "resMsg"));
                                idle(3);

                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);//将存储过程检测到的废卡的EPC插入错卡列表里                         
                            }
                        }
                    }
                }
            }

            //超时seconds扫不到车卡时，切换到idle
            resetToIdle();
            idle(1);
        }


        /// <summary>
        /// 调用过程检测是否合法车辆
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBCheckXMCar()
        {   
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("toDitch", currentXMDitch);

            JObject retJson = null;
            try
            {
                LogTool.WriteLog(typeof(CarXMZJ), "调用数据库检测车辆是否合法invokeDBCheckXMCar");
                retJson = httpDbTool.invokeProc("pk_car_schedule.xm_before_preprocess", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarXMZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarXMZJ), "invokeDBCheckXMCar数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }
            return retJson;
        }


        /// <summary>
        /// 检测卸煤沟是否有卸煤队列启用
        /// </summary>
        /// <returns></returns>
        public Boolean invokeDBCheckDitchActive()
        {
            Boolean res = false;
            JObject dataJson = new JObject();
            dataJson.Add("ditchNo", currentXMDitch);


            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("check_ditch_active", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarXMZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarXMZJ), "invokeDBCheckDitchActive数据库调用失败：" + e.Message);
                return false;
            }

            

            if ("Y".Equals(getJsonValue(retJson, "active"))) 
            {
                res = true;
            }

            return res;
        }


        public DateTime waitingBeginTime;

        /// <summary>
        /// 卸煤中
        /// </summary>
        public void waitingUnload()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "进入等待去卸煤环节waitingUnload");
                waitingBeginTime = DateTime.Now;
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //等待状态时，感应到车了，就进入正在卸煤状态
            if (ditchStatus.Equals(Commons.DITCH_WAITING) && isRadiationBolckOk("卸煤沟红外", "inRadiation", 3))
            {
                tipMsg("正在卸煤请等待");
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "结束去卸煤状态，准备进入正在卸煤状态（对射被挡住了3秒）waitingUnload");
                ditchStatus = Commons.DITCH_UNLOADING;
                actionLedRed(); //恢复红灯
            }
            else if (ditchStatus.Equals(Commons.DITCH_WAITING) && !isRadiationBolckOk("卸煤沟红外", "inRadiation", 3))
            {
                //如3分钟内不进入卸煤沟，则重新开始
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "等待去卸煤状态（检测3秒对射还没被挡住）waitingUnload");
                TimeSpan interval = DateTime.Now - waitingBeginTime;
                if (interval.Minutes > 3)
                {
                    LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "等待去卸煤超时（3分钟内未挡住对射）waitingUnload");
                    tipMsg("卸煤超时");
                    idle(3);
                    resetToIdle();
                }
                else 
                {
                    LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "继续等待去卸煤状态（卸煤沟对射3秒还没挡住且未超时）waitingUnload");
                    idle(2);
                    return;
                }
            }


            //正在卸煤状态，如果感应消失了，说明卸煤完成车走了，则结束
            if (ditchStatus.Equals(Commons.DITCH_UNLOADING) && !isRadiationBolckOk("卸煤沟红外", "inRadiation", 3))
            {
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "已经无信号2，第二次再次确认waitingUnload");
                idle(3);
                if (!isRadiationBolckOk("卸煤沟红外", "inRadiation", 3)) 
                {
                    LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "已经无信号3，第三次再次确认waitingUnload");
                    idle(3);
                    if (!isRadiationBolckOk("卸煤沟红外", "inRadiation", 3))
                    {
                        LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "卸煤准备进入结束（因卸煤状态且3秒无阻挡信号）waitingUnload");
                        tipMsg("当前车卸煤完成");
                        stateEventArgs.nextStep = Commons.STEP.CAR_FINISH_UNLOAD;
                    }
                }
                
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "正在卸煤状态（3秒检测对射信号被挡住）waitingUnload");
                idle(5);
                return;
            }

        }


        public void finishUnloadCoal()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "进入卸煤结束环节finishUnloadCoal");
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //再次确认信号
            
            if (!isRadiationBolckOk("卸煤沟红外", "inRadiation", 2))
            {
                //调用过程处理卸煤结束,记录卸煤标志 xm_dtm,出队列,发采样码给封装桶
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "卸煤结束环节，车走了（因检测2秒对射已无信号），执行卸煤结束调用数据库finishUnloadCoal");

                //落道闸
                actionFrontGateDown();

                //卸煤结束处理数据
                invokeDBFinishXM();

                //重置
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoXM_" + currentXMDitch, "");

                //重置，准备下辆车
                idle(2);//延时2秒重新开始
                resetToIdle();
            }
            else
            {
                idle(1);
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "卸煤结束环节，车还没走掉（因检测2秒对射还有信号）finishUnloadCoal");
                return;
            }
        }


        //重置
        public void resetToIdle()
        {
            restData();
            actionFrontGateDown();
            actionLedRed();
            stateEventArgs.nextStep = Commons.STEP.IDLE;
        }


        /// <summary>
        /// 调用过程检测是否合法车辆
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBFinishXM()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("toDitch", currentXMDitch);

            JObject retJson = null;
            try
            {
                LogTool.WriteLog(typeof(CarXMZJ), stateEventArgs.carNo + "卸煤结束数据库调用invokeDBFinishXM");
                retJson = httpDbTool.invokeProc("pk_car_schedule.xm_after_process", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarXMZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarXMZJ), "invokeDBFinishXM数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }

            return retJson;
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
        }



        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.frontGateDownOut)), "True", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.frontGateDownOut)), "False", Commons.modbusType.COIL_STATUS);
            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelXM_" + currentXMDitch, "1");
        }


        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.frontGateUpOut)), "True", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.frontGateUpOut)), "False", Commons.modbusType.COIL_STATUS);
            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelXM_" + currentXMDitch, "0");
        }



        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.lightRedOut)), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.lightGreenOut)), "False", Commons.modbusType.COIL_STATUS);
            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsXM_" + currentXMDitch, "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.lightRedOut)), "False", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjXM.lightGreenOut)), "True", Commons.modbusType.COIL_STATUS);
            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsXM_" + currentXMDitch, "1");

        }


        //提示信息，LED+声音
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                try
                {
                    Onbon_LED.SendText(msg, strIp, fonSize, width, height);
                    Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowXM" + currentXMDitch, msg);
                    lastMsg = msg;
                    writeMointorSingle("LED屏", msg);
                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(CarXMZJ), "发信息到LED异常：" + e.StackTrace);
                    LogTool.WriteLog(typeof(CarXMZJ), "发信息到LED异常：" + e.Message);
                }
            }
        }

    }


}
