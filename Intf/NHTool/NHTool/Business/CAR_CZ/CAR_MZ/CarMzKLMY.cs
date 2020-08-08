using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Windows.Forms;
using System.Threading;
using Newtonsoft.Json;

namespace NHTool.Business.CAR_CZ.CAR_MZ
{
    class CarMzKLMY : CarCzBusiness
    {
        public enum modbusPoint_klmyMz : int
        {
            inRadiation = 1,
            outRadiation = 2,
            frontRadiation = 4,
            backRadiation = 5,
            frontGateUpOut = 17,
            frontGateDownOut = 18,
            backGateUpOut = 19,
            backGateDownOut = 20,
            lightRedOut = 16
        }


        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;
            deviceTag = "MZ" + currentFlowId.Substring(2);
            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_klmyMz)))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKLMY), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKLMY), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiVoiceDecice())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKLMY), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKLMY), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

             if (initiSerialPortDevice(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKLMY), "地磅串口初始化失败");
                MessageBox.Show("地磅串口初始化失败");

            }

            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            if (initCount.Equals(5))
            {
                initResult = true;
            }


            return initResult;
        }

        public override void processCarWeightPacketMethod(byte[] packetData)
        {
            int weightData = 0;
            byte DT_STX = 0x02;
            byte DT_ETX = 0x03;
            int parseflag = 0;
            int i = 0;
            byte[] parseData = new byte[32];
            while (i < packetData.Length)
            {
                if (packetData[i].Equals(DT_STX))
                {
                    for (int j = 0; j < 8; j++)
                    {
                        parseData[j] = packetData[i];

                        i++;
                    }
                    parseflag = 1;
                    break;
                }
                i++;
            }


            if (parseflag.Equals(1))
            {
                //weightData = Convert.ToInt32(Commons.fromByteString(parseData, 2, 6));

                if (realDataDictionary.ContainsKey("carWeight"))
                {
                    realDataDictionary["carWeight"] = Convert.ToString(weightData);

                }
                else
                {
                    realDataDictionary.TryAdd("carWeight", Convert.ToString(weightData));
                }

                string LogicTagName = "01#" + deviceTag + "_weight";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, Convert.ToString(weightData));
            }

            lastGetWegihtTime = DateTime.Now;
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

                if (deviceCode != null && currentFlowId.Equals(deviceCode))
                {
                    if ("systemReset".Equals(action))
                    {
                        stateEventArgs.nextStep = Commons.STEP.IDLE;
                    }
                    else if ("actionFrontGateUp".Equals(action))
                    {
                        actionFrontGateUp();
                    }
                    else if ("actionFrontGateDown".Equals(action))
                    {
                        actionFrontGateDown();
                    }
                    else if ("actionLEDGreen".Equals(action))
                    {
                        actionLedGreen();
                    }
                    else if ("actionLEDRed".Equals(action))
                    {
                        actionLedRed();
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
        /**
         * 主体程序流转，通过switch+stateEventArgs进行切换
         * 
         */
        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.nextStep = Commons.STEP.IDLE;

            if (this.initial()) //成功初始化
            {
                actionbackGateDown();
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            LogTool.WriteLog(typeof(CarMzKLMY), "currentStep=" + stateEventArgs.nextStep.ToString());
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(40, "MZ");  break;
                                case Commons.STEP.CAR_ReadyWeight: readyWeightStep(3); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_Weighting: weightingStep(); break;
                                case Commons.STEP.CAR_WeightFinish: weightFinishStep("MZ"); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_OUT: carOutStep(); break;
                                default: break;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            //LogTool.WriteLog(typeof(CarCzBusiness), "主循环异常:" + e.Message);
                        }
                    }
                })).Start();
            }
        }


        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.frontGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }

            string LogicTagName = "01#" + deviceTag + "_barrel";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "0");
        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.frontGateDownOut))
                {

                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }
            string LogicTagName = "01#" + deviceTag + "_barrel";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "1");
        }

        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.lightRedOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }
            string LogicTagName = "01#" + deviceTag + "_trafficLights";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.lightRedOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
            }
            string LogicTagName = "01#" + deviceTag + "_trafficLights";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "1");
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
}
