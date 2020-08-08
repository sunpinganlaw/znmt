using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Business.Car_IN;
using NHTool.Common;
using System.Threading;
using System.Windows.Forms;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using NHTool.Device.LED;

namespace NHTool.Business.CAR_IN
{
    class CarInHM : CarInBusiness
    {
        private DataBaseTool myDataBase = null;
        private string Onbon_Ip = "";
        private uint Onbon_FontSize = 0;

        public enum modbusPoint_hmIn : int
        {
            inRadiation = 4,
            outRadiation = 3,
            frontGateUpOut = 19,
            frontGateDownOut = 20,
            lightRedOut = 17,
            lightGreenOut = 18
        }

        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;
            myDataBase = new DataBaseTool();
            //string resultJsonStr = "";
            //string resultCode = "";
            //string resultMsg = "";
            //myDataBase.rc_before_preProcess("RC1", "ab0000000002018022420615", out resultJsonStr, out resultCode, out resultMsg);

            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_hmIn)))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHM), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHM), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiVoiceDecice())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInHM), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }

            Onbon_Ip = ctlConfig["OnbonLED_IP"];
            Onbon_FontSize = Convert.ToUInt16(ctlConfig["OnbonLED_FontSize"]);
            //httpToolCarControl = new HttpTool();
            //httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);
            initResult = true;
            return initResult;
        }

        public override void carIdleStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                recoverDeviceState();
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                actionFrontGateDown();
                actionLedRed();
                tipMsg("停车到位等待扫卡");
            }
            if (isRadiationBolckOk("进口处红外", "inRadiation", 5))
            {
                tipMsg("开始扫卡请等待");
                Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
                if (rfid_result[Commons.RES_CODE].Equals("0"))//成功启动天线读取车卡
                {
                    stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                }
            }
        }

        public override void getCarNoStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (readRfidDevice.epcTag2AntId.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
            {
                foreach (var epcStr in readRfidDevice.epcTag2AntId)//检查缓存到的所有的EPC卡号
                {
                    string scanEpcTag = epcStr.Key;
                    if (!readRfidDevice.errorEpcTag.Contains(scanEpcTag))//这些EPC卡号不存在已有的错误列表里
                    {
                        writeMointorSingle("车卡号", scanEpcTag);
                        stateEventArgs.cardID = scanEpcTag;
                        if (excuRCProcedure("before"))
                        {
                            stateEventArgs.stepExcuteCount = 0;
                            readRfidDevice.stop_read_RFID_Info();
                            readRfidDevice.clearEcpTagList();
                            GKJsonSend(stateEventArgs.carNo);
                            Commons.putDcValue(ref dictionaryUseInForm, "carId", stateEventArgs.carNo);
                            Commons.putDcValue(ref dictionaryUseInForm, "cardId", stateEventArgs.cardID);
                            Commons.putDcValue(ref dictionaryUseInForm, "flowId", currentFlowId);
                            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "1");
                            stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
                            return;
                        }
                        else
                        {
                            readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, scanEpcTag);//将存储过程检测到的废卡的EPC插入错卡列表里                         
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
                    resetAndToNextSetp(null, Commons.STEP.IDLE);
                }
            }

        }

        public override void CheckCardIdInfo()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                actionFrontGateUp();
                actionLedGreen();
                tipMsg(stateEventArgs.carNo + "        " + stateEventArgs.mineName + "   进" + stateEventArgs.carNo);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
            }
        }

        public override void checkCarOutSign()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "正在离开入厂区域");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
                idle(20);
            }
        }

        public override void carOutStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (!isRadiationBolckOk("入厂-出口处红外对射", Commons.outRadiation, 2))
            {
                resetAndToNextSetp(stateEventArgs.carNo + "完成入厂", Commons.STEP.IDLE);
                idle(8);//为安全起见，再等待一段时间再落道闸，本身道闸下落也比较慢
                recoverDeviceState();
            }
        }

        public override bool excuRCProcedure(string prodType)
        {
            bool isSccCheckCard = false;
            if (prodType.Equals("before"))
            {
                string resultJsonStr = "";
                string resultCode = "";
                string resultMsg = "";
                myDataBase.rc_before_preProcess(Commons.getDcValue(ctlConfig, "currentFlowId"), stateEventArgs.cardID, out resultJsonStr, out resultCode, out resultMsg);

                stateEventArgs.actionResultCode = resultCode;
                stateEventArgs.actionResultMsg = resultMsg;
                if (stateEventArgs.actionResultCode.Equals("1000"))
                {
                    JObject retJObject = (JObject)JsonConvert.DeserializeObject(resultJsonStr);
                    stateEventArgs.carNo = getJsonValue(retJObject, "carId");
                    stateEventArgs.cyjNo = getJsonValue(retJObject, "cyjNo");
                    stateEventArgs.venName = getJsonValue(retJObject, "venName");
                    stateEventArgs.mineName = getJsonValue(retJObject, "mineName");
                    isSccCheckCard = true;
                }
                else
                {

                }
            }
            else if (prodType.Equals("after"))
            {
                string resultCode = "";
                string resultMsg = "";
                myDataBase.rc_alter_Process(Commons.getDcValue(ctlConfig, "currentFlowId"), stateEventArgs.cardID, stateEventArgs.cyjNo, out resultCode, out resultMsg);

                stateEventArgs.actionResultCode = resultCode;
                stateEventArgs.actionResultMsg = resultMsg;
                if (stateEventArgs.actionResultCode.Equals("1000"))
                {
                    isSccCheckCard = true;
                }
                else
                {

                }
            }
            return isSccCheckCard;
        }
        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            modbus.setCommand(((int)modbusPoint_hmIn.frontGateDownOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_hmIn.frontGateUpOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            string LogicTagName = "01#" + deviceTag + "_barrel";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "0");
        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            modbus.setCommand(((int)modbusPoint_hmIn.frontGateUpOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_hmIn.frontGateDownOut).ToString(), "True", Commons.modbusType.COIL_STATUS);

            string LogicTagName = "01#" + deviceTag + "_barrel";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "1");
        }

        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(((int)modbusPoint_hmIn.lightRedOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(((int)modbusPoint_hmIn.lightGreenOut).ToString(), "False", Commons.modbusType.COIL_STATUS);

            string LogicTagName = "01#" + deviceTag + "_trafficLights";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(((int)modbusPoint_hmIn.lightGreenOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(((int)modbusPoint_hmIn.lightRedOut).ToString(), "False", Commons.modbusType.COIL_STATUS);

            string LogicTagName = "01#" + deviceTag + "_trafficLights";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "1");
        }

        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                string LogicTagName = "01#" + deviceTag + "_LEDShow";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                actiomLedShow(msg);
                LogTool.WriteLog(typeof(BusinessBase), "tipMsg=" + msg);
            }
        }

        public override void actiomLedShow(string text)
        {
            Onbon_LED.SendText(text,Onbon_Ip,Onbon_FontSize);
        }

        public override void recoverDeviceState()
        {
            actionFrontGateDown();
            actionLedRed();
            stateEventArgs.carNo = "";
            stateEventArgs.cyjNo = "";
            stateEventArgs.venName = "";
            stateEventArgs.mineName = "";
            GKJsonSend(stateEventArgs.carNo);
            Commons.putDcValue(ref dictionaryUseInForm, "carId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "cardId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "flowId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "trainNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketQty", "");
            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "0");
            Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "0");
            writeMointorSingle("车卡号", "");
        }

        public override void modbusDeviceDataHandler(Dictionary<string, string> resultDictionary)
        {
            if (resultDictionary.Count > 2)
            {
                foreach (var item in resultDictionary)
                {
                    //pubKey = Enum.GetName(typeof(Commons.modbusPoint), int.Parse(item.Key));
                    string pubKey = Enum.GetName(modbusPointAddress, int.Parse(item.Key));

                    if (pubKey != null)
                    {
                        string Value = "";
                        if (item.Value.Equals("True") || item.Value.Equals("False"))
                        {
                            Value = Commons.signalON.ToString();
                        }
                        if (item.Value.Equals("False") || item.Value.Equals("false"))
                        {
                            Value = Commons.signalOFF.ToString();

                        }
                        if (realDataDictionary.ContainsKey(pubKey))
                        {
                            realDataDictionary[pubKey] = Value;
                        }
                        else
                        {
                            realDataDictionary.TryAdd(pubKey, Value);
                        }

                        if (pubKey.Equals("inRadiation"))
                        {
                            string LogicTagName = "01#" + deviceTag + "_infraredIn";
                            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, realDataDictionary[pubKey]);
                        }
                        if (pubKey.Equals("outRadiation"))
                        {
                            string LogicTagName = "01#" + deviceTag + "_infraredOut";
                            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, realDataDictionary[pubKey]);
                        }
                    }
                }
            }
        }

        public void GKJsonSend(string carId)
        {
            JObject sendJson = new JObject();

            sendJson.Add("COMMAND", "RTDATA");
            sendJson.Add("DEVICE", "CC1DB001");
            sendJson.Add("VALUE", "1");
            sendJson.Add("CP", carId);

            string JsonShowText = sendJson.ToString().Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "");

            if (tcpclient != null)
            {
                tcpclient.HostAddress = "192.168.1.13";
                tcpclient.Port = 2012;
                if (tcpclient.IsConnected)
                {
                    tcpclient.disconnect();
                    System.Threading.Thread.Sleep(50);
                    tcpclient.ConnectToServer();
                    tcpclient.SendMessage(JsonShowText);
                    tcpclient.disconnect();
                }
                else
                {
                    tcpclient.ConnectToServer();
                    tcpclient.SendMessage(JsonShowText);
                    tcpclient.disconnect();

                }
            }
        }

        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            if (this.initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (!IsMainThreadRunStop1)
                    {
                        try
                        {
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(); break;
                                case Commons.STEP.CAR_CheckCard: CheckCardIdInfo(); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_OutFinish: carOutStep(); break;
                                default: idle(1); break;
                            }
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarInBusiness), "主循环异常:" + e.Message);
                        }
                        idle(1);
                    }
                })).Start();
            }
        }
    }
}
