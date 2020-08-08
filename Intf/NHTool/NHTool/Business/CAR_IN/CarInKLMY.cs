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
    class CarInKLMY : CarInBusiness
    {
        /*********************************
         * 1号通道红绿灯 DO1
         * 1号通道道闸升
         * m_ADAM.SendData(DO2,ON);
		   m_ADAM.SendData(DO2,OFF);
         * 1号通道道闸落
         * m_ADAM.SendData(DO3,ON);
		   m_ADAM.SendData(DO3,OFF);
         * 2号通道红绿灯 DO5
         * 2号通道道闸升
         * m_ADAM.SendData(DO6,ON);
		   m_ADAM.SendData(DO6,OFF);
         * 2号通道道闸落
         * m_ADAM.SendData(DO7,ON);
		   m_ADAM.SendData(DO7,OFF);
         **************************************/
        private DataBaseTool myDataBase = null; 
        public enum modbusPoint_klmyIn : int
        {
            channel1LedRed = 17,
            channel1GateUp = 18,
            channel1GateDown = 19,

            channel2LedRed = 21,
            channel2GateUp = 22,
            channel2GateDown = 23,
        }

        public override bool checkRCBeforeCardId(string epcId,out string carId)
        {
            bool qryResult = false;
            carId = "1";
            if (Commons.getDcValue(ctlConfig, "DataBaseUsed").Equals("0"))
            {
                JObject dataJson = new JObject();
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                JObject retJson = httpDbTool.invokeProc("pk_car_schedule.check_car_id", dataJson);
                //调用校验存储过程成功
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    carId = getJsonValue(retJson, "carId");
                    qryResult = true;
                }
            }
            else if (Commons.getDcValue(ctlConfig, "DataBaseUsed").Equals("1"))
            {
                if (myDataBase.qryCarNoByCardId(epcId, out carId))
                {
                    qryResult = true;
                }
            }
            return qryResult;
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
                LogTool.WriteLog(typeof(OPCTool), httpStr + ",执行命令成功," + jsonIn.ToString());
                return ret;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                ret.Add(Commons.RES_CODE, "1");
                ret.Add(Commons.RES_MSG, "err：" + e.Message);
                LogTool.WriteLog(typeof(OPCTool), httpStr + ",执行命令失败," + jsonIn.ToString() + "," + e.Message);
                return ret;
            }
        }

        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;
            myDataBase = new DataBaseTool();
            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_klmyIn)))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInKLMY), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInKLMY), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiVoiceDecice())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInKLMY), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }

           if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInKLMY), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }
           
            if (initiLedDevice2(ctlConfig["LED_IP2"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarInKLMY), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            recoverDeviceState();
            if (initCount.Equals(5))
            {
                initResult = true;
            }

            return initResult;
        }

        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.currentChannel = "1";
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            stateEventArgs.currentChannel = "1";
            if (this.initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            //writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            LogTool.WriteLog(typeof(CarInBusiness), "currentStep=" + stateEventArgs.nextStep.ToString());
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(); break;
                                case Commons.STEP.CAR_WaitSelectStep: carWaitSelectStep();break;
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

        public override void deviceAction(string ChannelNum, string actionType)
        {
            if(actionType.Equals("LedRed"))
            {
                var enums = Enum.GetValues(modbusPointAddress);
                string actionName = "channel" + ChannelNum + "LedRed";
                foreach (var item in enums)
                {
                    if (item.ToString().Equals(actionName))
                    {
                        modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                    }
                }
                string LogicTagName=  "01#RC" + ChannelNum + "_trafficLights";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "0");
            }
            else if (actionType.Equals("LedGreen"))
            {
                var enums = Enum.GetValues(modbusPointAddress);
                string actionName = "channel" + ChannelNum + "LedRed";
                foreach (var item in enums)
                {
                    if (item.ToString().Equals(actionName))
                    {
                        modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    }
                }
                string LogicTagName = "01#RC" + ChannelNum + "_trafficLights";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "1");
            }
            else 
            {
                var enums = Enum.GetValues(modbusPointAddress);
                string actionName = "channel" + ChannelNum + actionType;
                foreach (var item in enums)
                {
                    if (item.ToString().Equals(actionName))
                    {
                        modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                        idle(1);
                        modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                    }
                }

                if (actionType.Equals("GateUp"))
                {
                    string LogicTagName = "01#RC" + ChannelNum + "_barrel";
                    Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "0");
                }
                else if (actionType.Equals("GateDown"))
                {
                    string LogicTagName = "01#RC" + ChannelNum + "_barrel";
                    Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "1");
                }
            }
        }

        public override void actiomLedShow(string text)
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
            deviceAction("1", "LedRed");
            deviceAction("2", "LedRed");
            Commons.putDcValue(ref dictionaryUseInForm, "carId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "cardId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "flowId", "");
            Commons.putDcValue(ref dictionaryUseInForm, "trainNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketNo", "");
            Commons.putDcValue(ref dictionaryUseInForm, "ticketQty", "");
            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "0");
            Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "0");
            //deviceAction("1", "GateDown");
            //deviceAction("2", "GateDown");
        }
    }
}
