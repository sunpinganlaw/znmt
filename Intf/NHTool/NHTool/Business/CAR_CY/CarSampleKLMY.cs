using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using NHTool.Device.RFID;
using NHTool.Common;
using System.Threading;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;

namespace NHTool.Business.CAR_CY
{
    /**   
     * 功能    : 克拉玛依汽车采样接口调度流程
     * 创建人  : yangff 2018-11-20  
     * 修改记录: wz klmy,初始调试和修改 2018-11-30    
     */
    public class CarSampleKLMY : CarSampleBusiness
    {
        public enum modbusPoint_klmycyj : int
        {
            inRadiation = 1,
            outRadiation = 2,
            frontRadiation = 4,
            backRadiation = 5,
            frontGateUpOut = 18,
            frontGateDownOut = 19,
            lightRedOut = 17
        }

        /**
         * 接受Web的直接控制
         * 语法：
         * {"action":"",device"",value:"",value2:""}
         * 
         */
        public override JObject processCarHttpRequestMethod(String httpStr)
        {
            JObject ret = new JObject();
            try
            {
                //前期判断
                JObject dataJson = (JObject)JsonConvert.DeserializeObject(httpStr);
                string action = Commons.getJsonValue(dataJson, "action");
                string deviceCode = Commons.getJsonValue(dataJson, "deviceCode");

                if (deviceCode != null && Commons.getDcValue(ctlConfig, "currentFlowId").Equals(deviceCode))
                {
                    if ("systemReset".Equals(action))
                    {
                        restData();
                    }
                    else if ("errReset".Equals(action))
                    {
                        faultRecoveryHttpInovke(dataJson);
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
                    else if("unload".Equals(action))
                    {
                        unloadHttpInovke(dataJson);
                    }
                    else if("stop".Equals(action))
                    {
                        stopQcCyjHttpInovke(dataJson);
                    }
                    else if ("actionDbInovke".Equals(action))
                    {

                    }
                }
              

                ret.Add(Commons.RES_CODE, "0");
                ret.Add(Commons.RES_MSG, "succ");
                LogTool.WriteLog(typeof(OPCTool), httpStr + ",执行命令成功," + httpStr.ToString());
                return ret;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                ret.Add(Commons.RES_CODE, "1");
                ret.Add(Commons.RES_MSG, "err：" + e.Message);
                LogTool.WriteLog(typeof(OPCTool), httpStr + ",执行命令失败," + httpStr.ToString() + "," + e.Message);
                return ret;
            }
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();

            //------------------------------------------------------------------------

            bool initResult = false;
            int initCount = 0;
            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_klmycyj)))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKLMY), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKLMY), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiVoiceDecice())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKLMY), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKLMY), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

            if (myOPCTool.startOpcServer())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKLMY), "OPC服务初始化失败");
                MessageBox.Show("OPC服务初始化失败");
            }

            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);
            deviceTag = "RC_CYJ" + currentFlowId.Substring(2);

            if (initCount.Equals(5))
            {
                initResult = true;
            }

            return initResult;
        }

        public override void mainProcess()
        {
            myOPCTool = new OPCTool();
            new Thread(new ThreadStart(delegate
            {
                //初始为空闲环节
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                if (initial()) //成功初始化
                {
                    restData();
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: ; IDLE(); break;
                                case Commons.STEP.CAR_GetCarNo: GetCardInfo(); break;
                                case Commons.STEP.CAR_CheckCard: checkCarInfoByCardId(); break;
                                case Commons.STEP.CAR_WaitForParking: waitForParking(); break;
                                case Commons.STEP.CAR_ScanCarLength: startScanCarLengthCommand(); break;
                                case Commons.STEP.CAR_WaitScanCarLength: waitScanCarLength(); break;
                                case Commons.STEP.CAR_SendSampleCommand: sendSampleCommand(); break;
                                case Commons.STEP.CAR_WaitSample: waitSampling(); break;
                                case Commons.STEP.CAR_SampleFinish: sampleFinish(); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_OutFinish: allSampleFlowFinish(); break;
                                default: ; break;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarSampleKLMY), "主循环异常:" + e.Message);
                        }
                    }
                }
            })).Start();
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
};
