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
using NHTool.Device.LED;

namespace NHTool.Business.Car_IN
{
    class CarInHA : CarInBusiness
    {
        string strIp = null;
        uint fonSize = 16;
        int width = 64;
        int height = 32;
       

        public enum modbusPoint_InHA : int
        {
            inRadiation = 4,  //入口红外
            outRadiation = 3, //出口红外

            lightRedOut = 17, //红绿灯
            lightGreenOut = 18,
            frontGateUpOut = 19, //道闸
            frontGateDownOut = 20
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
                LogTool.WriteLog(typeof(CarInHA), httpStr + ",执行命令失败," + jsonIn.ToString() + "," + e.Message);
                return ret;
            }
        }


        int expectDeviceCount = 4;//期望初始化成功N个设备
        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;
            httpDbTool = new HttpDbTool();

            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_InHA)))
            {
                LogTool.WriteLog(typeof(CarInHA), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHA), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarInHA), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHA), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            strIp = ctlConfig["LED_IP"];
            fonSize = Convert.ToUInt32(ctlConfig["LED_FONT_SIZE"]);
            width = Convert.ToInt16(ctlConfig["LED_WIDTH"]);
            height = Convert.ToInt16(ctlConfig["LED_HEIGHT"]);
            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(CarInHA), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHA), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }


            //初始化语音
            try
            {
                InitINBSVoiceDevice();
                initCount++;
            }
            catch
            {
                LogTool.WriteLog(typeof(CarInHA), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }            


            //启动接受控制测点的服务器
            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            //恢复重置设备
            recoverDeviceState();
            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarInHA), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHA), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            deviceTag = "RC";

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
                            LogTool.WriteLog(typeof(CarInHA), e.StackTrace); 
                            LogTool.WriteLog(typeof(CarInHA), "主循环异常:" + e.Message);
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

                //落道闸红灯
                actionFrontGateDown();
                actionLedRed();

                tipMsg("请停车扫卡");
            }

            if (isRadiationBolckOk("进口红外", "inRadiation", 1))  //2->1
            {   
                Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
                if (rfid_result[Commons.RES_CODE].Equals("0"))//成功启动天线读取车卡
                {
                    tipMsg("正在扫卡");
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
            if (stateEventArgs.stepExcuteCount > 15)//30->20
            {
                stateEventArgs.stepExcuteCount = 0;
                resetAndToNextSetp("扫卡结束", Commons.STEP.IDLE);
            }


            if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
            {
                foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                {
                    if ("b2a".Equals(epcStr.Substring(0, 3)) || "ab".Equals(epcStr.Substring(0, 2)))
                    {
                        writeMointorSingle("疑似车卡号", epcStr); 
                    }
                  
                    if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                    {                      
                        //将车卡号都列出来写进去
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
                            
                            tipMsg(stateEventArgs.carNo + "请入厂");
                            idle(1);//2->1
                            stateEventArgs.nextStep = Commons.STEP.CAR_OUT; //到这就出去了
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
            if (isRadiationBolckOk("出口处红外", Commons.outRadiation, 1))//2->1
            {
                tipMsg(stateEventArgs.carNo + "正在入厂");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
                idle(1);//保持一段时间  3->1
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

            //时间稍晚长点，防止车头和车厢的间隙过大对射没信号了误以为入厂了
            if (!isRadiationBolckOk("入厂出口红外", Commons.outRadiation, 2)) //4->1
            {
               // idle(1);
                Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoRC", "");

                //调用入厂after
                JObject retJson = invokeDBRCAfter();
                if ("1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    resetAndToNextSetp(stateEventArgs.carNo + "入厂完成", Commons.STEP.IDLE);
                    // idle(1);
                }
                else
                {
                    resetAndToNextSetp(stateEventArgs.carNo + "入厂失败", Commons.STEP.IDLE);
                    idle(1);            
                    return;
                }
                //清除疑似车卡号中的信息
                writeMointorSingle("疑似车卡号", "等待扫卡"); 

                //落道闸红灯
                actionFrontGateDown();
                actionLedRed();
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
                try
                {

                    actionVoice(Commons.changeCarNo(msg));
                    idle(1);

                    Onbon_LED.SendText(msg, strIp, fonSize, width, height);
                    Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_LEDShow", msg);
                    lastMsg = msg;
                    writeMointorSingle("LED屏", msg);
                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(CarInHA), "发信息到LED异常：" + e.StackTrace);
                    LogTool.WriteLog(typeof(CarInHA), "发信息到LED异常：" + e.Message);
                }
            }
        }


        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.frontGateUpOut)), "True", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.frontGateUpOut)), "False", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_barrel", "0");
        }


        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.frontGateDownOut)), "True", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.frontGateDownOut)), "False", Commons.modbusType.COIL_STATUS);
        }


        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.lightGreenOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.lightRedOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_trafficLights", "0");
        }


        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.lightRedOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_InHA.lightGreenOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_trafficLights", "1");
        }


        //语音
        public override void actionVoice(string text)
        {
            iNBSVoiceDevice.iNBS_BroadCastContent(text);
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

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.rc_before_preprocess", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarInHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarInHA), "invokeDBRCBefore数据库调用失败：" + e.Message);
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
        /// 调用入厂before过程
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBRCAfter()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", ctlConfig["currentFlowId"]);

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.rc_after_process", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarInHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarInHA), "invokeDBRCBefore数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
                return retJson;
            }
            return retJson;
        }

    }
}
