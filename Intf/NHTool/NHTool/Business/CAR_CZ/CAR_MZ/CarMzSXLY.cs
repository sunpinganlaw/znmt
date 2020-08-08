using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using System.Windows.Forms;
using System.IO.Ports;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.Threading;
using NHTool.Device.BY17NCReader;

/*********************************************
 ******** 山西铝业重磅接口********************
 ***********create by dafeige   20190426******
 ********************************************/
namespace NHTool.Business.CAR_CZ.CAR_MZ
{
    class CarMzSXLY : CarCzBusiness
    {
        private int radomNum = 1000;
        private string szs_HttpUrl = "";
        private string szs_HeartBeatUrl = "";
        public CarMzSXLY()
        {
            szs_HttpUrl = ctlConfig["SzsHttpUrl"];
            szs_HeartBeatUrl = ctlConfig["SzsHeartBeatUrl"];
        }

        public enum modbusPoint_SXLYMz : int
        {
            inRadiation = 3,
            outRadiation = 4,
            frontGateUpOut = 21,
            frontGateDownOut = 22,
            backGateUpOut = 19,
            backGateDownOut = 20,
            lightRedOut = 17,
            lightGreenOut = 18
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();
            if (!initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_SXLYMz)))
            {
                LogTool.WriteLog(typeof(CarMzSXLY), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (!initiSerialPortDevice(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]))
            {
                LogTool.WriteLog(typeof(CarMzSXLY), "地磅串口初始化失败");
                MessageBox.Show("地磅串口初始化失败");
            }

            InitINBSVoiceDevice();
            InitMc3000LedDevice();
            initHFRfidReader();

            //httpToolCarControl = new HttpTool();
            //httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            deviceTag = "MZ" + currentFlowId.Substring(2);
            writeMointorSingle("deviceName", deviceName);
            return true;
        }

        public override void comPort_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            try
            {
                SerialPort comPort = (SerialPort)sender;
                byte[] packetData = null;
                while (comPort.BytesToRead > 0)
                {
                    packetData = new byte[comPort.ReadBufferSize + 1];
                    int count = comPort.Read(packetData, 0, comPort.ReadBufferSize);
                }
                Thread.Sleep(500);
                int weightData = 0;
                byte DT_STX = 0x2B;
                byte DT_ETX = 0x6B;
                int parseflag = 0;
                int i = 0;
                byte[] parseData = new byte[32];
                while (i < packetData.Length)
                {
                    if (packetData[i].Equals(DT_STX))
                    {
                        for (int j = 0; j < 20; j++)
                        {
                            parseData[j] = packetData[i];
                            if (packetData[i].Equals(DT_ETX))
                            {
                                parseData[j] = packetData[i];
                                parseflag = 1;
                                break;
                            }
                            i++;
                        }
                        if (parseflag.Equals(1))
                        {
                            weightData = Convert.ToInt32(RfidReadrHF.fromByteString(parseData, 2, 6));

                            if (realDataDictionary.ContainsKey("carWeight"))
                            {
                                realDataDictionary["carWeight"] = Convert.ToString(weightData);

                            }
                            else
                            {
                                realDataDictionary.TryAdd("carWeight", Convert.ToString(weightData));
                            }

                            string LogicTagName = deviceTag + "地磅串口状态";
                            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "信号正常");

                            lastGetWegihtTime = DateTime.Now;

                            break;
                        }
                        i++;
                    }
                    i++;
                }
            }
            catch (Exception ex)
            {
            }
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
                    else if ("actionBackGateUp".Equals(action))
                    {
                        actionBackGateUp();
                    }
                    else if ("actionBackGateDown".Equals(action))
                    {
                        actionbackGateDown();
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
                return ret;
            }
            catch (Exception e)
            {
                ret.Add(Commons.RES_CODE, "1");
                ret.Add(Commons.RES_MSG, "err：" + e.Message);
                return ret;
            }
        }

        public override void processHFCardID(string cardId, int stepfunction)
        {
            string LogicTagName = deviceTag + "IC卡读卡器";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "信号正常");
            if (cardHFReader.action_flag.Equals("found_card"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    stateEventArgs.carNo = cardId;
                    writeMointorSingle("carId", stateEventArgs.carNo);
                    writeMointorSingle("车牌号", stateEventArgs.carNo);
                    cardHFReader.action_flag = "found_recordno";
                }
            }
            else if (cardHFReader.action_flag.Equals("found_recordno"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    stateEventArgs.recordNo = cardId;
                    writeMointorSingle("recordNO", stateEventArgs.recordNo);
                    if (getHttpJsonData(stateEventArgs.recordNo, Convert.ToString(stateEventArgs.carWeight)))
                    {
                        cardHFReader.action_flag = "write_sampleId";
                    }
                }
            }
            else if (cardHFReader.action_flag.Equals("write_sampleId"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    if (cardId.Equals("write_sampleId_ok"))
                    {
                        cardHFReader.action_flag = "write_weight";
                    }
                    else
                    {
                        tipMsg("刷卡失败请重新刷");
                    }
                }
                else
                {
                    tipMsg("等待称重禁止刷卡");
                }
            }
            else if (cardHFReader.action_flag.Equals("write_weight"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    if (cardId.Equals("write_weight_ok"))
                    {
                        cardHFReader.action_flag = "write_time";
                    }
                    else
                    {
                        tipMsg("刷卡失败请重新刷");
                    }
                }
                else
                {
                    tipMsg("等待称重禁止刷卡");
                }
            }
            else if (cardHFReader.action_flag.Equals("write_time"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    if (cardId.Equals("write_time_ok"))
                    {
                        tipMsg(stateEventArgs.carNo + " " + Convert.ToString(cardHFReader.m_szs_weight1));
                        cardHFReader.action_flag = "IDEL";
                        stateEventArgs.nextStep = Commons.STEP.CAR_WeightFinish;
                    }
                    else
                    {
                        tipMsg("刷卡失败请重新刷");
                    }
                }
                else
                {
                    tipMsg("等待称重禁止刷卡");
                }
            }
        }

        public override void modbusDeviceDataHandler(Dictionary<string, string> resultDictionary)
        {
            if (resultDictionary.Count > 2)
            {
                foreach (var item in resultDictionary)
                {
                    //pubKey = Enum.GetName(typeof(Commons.modbusPoint), int.Parse(item.Key));
                    try
                    {
                        string pubKey = Enum.GetName(modbusPointAddress, int.Parse(item.Key));
                        string linestate = "未被挡住";
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

                            if(Value.Equals("1"))
                            {
                                linestate = "被挡住";
                            }
                            else if(Value.Equals("0"))
                            {
                                linestate = "未被挡住";
                            }

                            if (pubKey.Equals("inRadiation"))
                            {
                                string LogicTagName = deviceName + "入口对射";
                                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, linestate);
                            }
                            if (pubKey.Equals("outRadiation"))
                            {
                                string LogicTagName = deviceName + "出口对射";
                                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, linestate);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        string errStr = ex.ToString();
                        LogTool.WriteLog(typeof(CarMzSXLY), errStr);
                    }
                }
            }
        }

        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            modbus.setCommand(((int)modbusPoint_SXLYMz.frontGateDownOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_SXLYMz.frontGateUpOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            string LogicTagName = deviceName + "出口道闸";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "抬起");
        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            modbus.setCommand(((int)modbusPoint_SXLYMz.frontGateUpOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_SXLYMz.frontGateDownOut).ToString(), "True", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "出口道闸";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "落下");
        }

        public override void actionbackGateDown()
        {
            modbus.setCommand(((int)modbusPoint_SXLYMz.backGateUpOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_SXLYMz.backGateDownOut).ToString(), "True", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "入口道闸";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "落下");
        }

        public override void actionBackGateUp()
        {
            modbus.setCommand(((int)modbusPoint_SXLYMz.backGateDownOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_SXLYMz.backGateUpOut).ToString(), "True", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "入口道闸";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "抬起");
        }

        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(((int)modbusPoint_SXLYMz.lightRedOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(((int)modbusPoint_SXLYMz.lightGreenOut).ToString(), "False", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "红绿灯";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName,"红灯亮");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(((int)modbusPoint_SXLYMz.lightGreenOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(((int)modbusPoint_SXLYMz.lightRedOut).ToString(), "False", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "红绿灯";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "绿灯亮");
        }

        public override void actiomLedShow(string text)
        {
            mc3000LedDevice.sendContentToLed(text);
            string LogicTagName = deviceName + "信息提示";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, text);
        }

        public override void actionVoice(string text)
        {
            iNBSVoiceDevice.iNBS_BroadCastContent(text);
        }

        public override void carIdleStep()
        {
            //2)获取通道状态，持续2秒, 但目前都改为根据重量，不判断红外对射了 
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("地磅空闲可以称重");
                writeMointorSingle(deviceName + "运行环节", "地磅空闲");
                restData();
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (getWeigth() > WEIGHT)
            {
                tipMsg("汽车驶入衡器开始扫卡");
                stateEventArgs.nextStep = Commons.STEP.CAR_ReadyWeight;
            }
            else
            {
                stateEventArgs.nextStep = Commons.STEP.IDLE;
            }
        }

        public override void getCarNoStep(int seconds, string czType)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                cardHFReader.action_flag = "found_card";
                actionbackGateDown();
                writeMointorSingle(deviceName + "运行环节", "获取卡号信息");
                tipMsg("称重完成刷卡抬杆");
            }

            if (getWeigth() < WEIGHT)
            {
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                return;
            }

            getRFIDCardID();
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > seconds)
            {
                //超时seconds扫不到车卡时，切换到idle
                tipMsg("等待扫卡超时");
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                return;
            }
        }

        //重置对象
        public override void restData()
        {
            stateEventArgs.carWeight = 0;
            stateEventArgs.carNo = "";
            stateEventArgs.cardID = "";
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            stateEventArgs.recordNo = "";
            stateEventArgs.sampleCode = "";
            stateEventArgs.sampleType = "";
            stateEventArgs.carType = "";
            stateEventArgs.sampleCfgPonits = "";
            stateEventArgs.batchChgFlag = "";
            stateEventArgs.sampleStartTime = "";
            stateEventArgs.sampleCoordList = new JArray();
            stateEventArgs.stepExcuteCount = 0;
            cardHFReader.read_function = 1;
            cardHFReader.action_flag = "IDEL";
            cardHFReader.m_szs_weight1 = "0";
            writeMointorSingle("车牌号", "");
            writeMointorSingle("carId", "");
            writeMointorSingle("recordNO", "");
            actionFrontGateDown();
            actionBackGateUp();
            actionLedRed();
        }

        public Boolean getHttpJsonData(string tickNo, string weightData)
        {
            Boolean excuRes = false;
            string httpCallBackStr = "";
            radomNum = radomNum + 1;
            if (radomNum > 10000)
            {
                radomNum = 1001;
            }

            string floatWeightData = String.Format("{0:N3}", (float)Convert.ToInt32(weightData) / 1000);
            JObject sendJson = new JObject();
            sendJson.Add("request_id", radomNum.ToString());
            sendJson.Add("device_id", cardHFReader.m_Device_id);
            sendJson.Add("ticket_id", tickNo);
            sendJson.Add("weight", floatWeightData);
            sendJson.Add("action_owner", "admin");
            sendJson.Add("ic_card_no", cardHFReader.ic_code);

            try
            {
                HttpTool httpToolSend = new HttpTool();
                LogTool.WriteLog(typeof(CarMzSXLY), "before cz:" + Convert.ToString(sendJson));
                //httpCallBackStr = httpToolSend.sendHttpForSZSMsg(Convert.ToString(sendJson), szs_HttpUrl);
                LogTool.WriteLog(typeof(CarMzSXLY), "after cz:" +"tickNo=" + tickNo +",weight="+floatWeightData+","+ httpCallBackStr);
                if (!httpCallBackStr.Equals(""))
                {
                    httpCallBackStr = httpCallBackStr.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "").Replace("\\", "");
                    JObject retJObject = (JObject)JsonConvert.DeserializeObject(httpCallBackStr);
                    string retCode = Commons.getJsonValue(retJObject, "result_code");
                    if (retCode.Equals("1000"))
                    {
                        JObject tempJson = (JObject)retJObject["result_data"];
                        cardHFReader.m_szs_sampleId = Commons.getJsonValue(tempJson, "sample_id");
                        cardHFReader.m_szs_weight1 = Commons.getJsonValue(tempJson, "weight1");
                        LogTool.WriteLog(typeof(CarMzSXLY), httpCallBackStr);
                        excuRes = true;
                    }
                    else
                    {
                        string retMsg = Commons.getJsonValue(retJObject, "result_info");
                        tipMsg(retMsg);
                        LogTool.WriteLog(typeof(CarMzSXLY), "上自所更新重量失败:" + retMsg);
                    }
                }
                else
                {
                    tipMsg("车卡异常找人处理");
                    LogTool.WriteLog(typeof(CarMzSXLY), stateEventArgs.carNo + "车卡信息异常大宗系统无返馈信息");
                }
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzSXLY), "after cz: tickNo=" + tickNo +",weight="+floatWeightData+","+e.Message);
                return false;
            }
            return excuRes;
        }

        public override void weightFinishStep(String czType)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                writeMointorSingle(deviceName + "运行环节", "计量完成");
                actionFrontGateUp();//抬道闸
                actionLedGreen();
            }
            else
            {
                idle(5);
            }
        }

        public void getRFIDCardID()
        {
            cardHFReader.funfoundCard();
        }

        public override void resetProcess()
        {
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            IsMainThreadRunStop1 = false;
            writeMointorSingle(deviceName + "系统状态", "设备运行");
        }

        public Boolean getDZWLCommand()
        {
            Boolean excuRes = false;
            string httpCallBackStr = "";
            string actionTag = "";
            string resultType = "";
            JObject sendJson = new JObject();
            sendJson.Add("device_id", deviceName);
            try
            {
                HttpTool httpToolSend = new HttpTool();
                //httpCallBackStr = httpToolSend.sendHttpForSZSMsg(Convert.ToString(sendJson), szs_HeartBeatUrl);
                if (!httpCallBackStr.Equals(""))
                {
                    httpCallBackStr = httpCallBackStr.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "").Replace("\\", "");
                    JObject retJObject = (JObject)JsonConvert.DeserializeObject(httpCallBackStr);
                    string retCode = Commons.getJsonValue(retJObject, "result_code");
                    if (retCode.Equals("1000"))
                    {
                        resultType = Commons.getJsonValue(retJObject, "result_info");
                        if (resultType.Equals("cmd"))
                        {
                            JObject tempJson = (JObject)retJObject["result_data"];
                            actionTag = Commons.getJsonValue(tempJson, "action");
                            if (actionTag.Equals("taigan"))
                            {
                                actionFrontGateUp();
                                actionBackGateUp();
                            }
                            else if (actionTag.Equals("luogan"))
                            {
                                actionFrontGateDown();
                                actionbackGateDown();
                            }
                            LogTool.WriteLog(typeof(CarMzSXLY), "接收到大宗物料控制命令：" + actionTag);
                        }
                        else if (resultType.Equals("Ok"))
                        {

                        }
                        excuRes = true;
                    }
                }
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzSXLY), "发送大宗物料数据心跳失败；" + e.Message);
                excuRes = false;
            }
            return excuRes;
        }

        public override void stopSysRun()
        {
            IsMainThreadRunStop1 = true;
            writeMointorSingle(deviceName + "系统状态", "设备停用");
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
                            writeMointorSingle("stepExcuteCount", Convert.ToString(stateEventArgs.stepExcuteCount));
                            writeMointorSingle(deviceName + "系统状态", "设备运行");
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_ReadyWeight: readyWeightStep(3); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_Weighting: weightingStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(600, "MZ"); break;
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
    }
}
