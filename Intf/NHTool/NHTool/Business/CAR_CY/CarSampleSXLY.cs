using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.Windows.Forms;
using System.Threading;

namespace NHTool.Business.CAR_CY
{
    class CarSampleSXLY : CarSampleBusiness
    {
        private int showCount = 0;
        private string m_BigLedSendFlag = "0";

        public CarSampleSXLY()
        {
            m_BigLedSendFlag = ctlConfig["BigLedSendFlag"];
        }

        public enum modbusPoint_sxlycyj : int
        {
            inRadiation = 3,
            outRadiation = 4,
            frontGateUpOut = 19,
            frontGateDownOut = 20,
            lightRedOut = 17,
            lightGreenOut = 18
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
                    //系统初始化  businessBase.InitStartCyj();
                    //系统复位    businessBase.resetProcess();
                    if ("sysReset".Equals(action))
                    {
                        resetProcess();
                    }
                    else if ("sysInit".Equals(action))
                    {
                        InitStartCyj();
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
                    else if ("unload".Equals(action))
                    {
                        unloadHttpInovke(dataJson);
                    }
                    else if ("stop".Equals(action))
                    {
                        stopQcCyjHttpInovke(dataJson);
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
                return ret;
            }
        }

        public override JObject unloadHttpInovke(JObject paramIn)
        {
            JObject httpInovkeRet = new JObject();
            //检查是否就绪状态
            if (!myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus_XM").Equals("1"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "系统不在就绪状态！");
                return httpInovkeRet;
            }

            string sampleCode = Commons.getJsonValue(paramIn, "value");
            string opId = Commons.getJsonValue(paramIn, "opId");
            string action = Commons.getJsonValue(paramIn, "action");
            string deviceCode = Commons.getJsonValue(paramIn, "deviceCode");

            if (Commons.isNullStr(sampleCode) || Commons.isNullStr(opId) || Commons.isNullStr(deviceCode))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "请求参数不完整！");
                return httpInovkeRet;
            }

            if (!deviceCode.Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "命令下发的设备不对，目标为：" + deviceCode + "当前设备为:" + Commons.getDcValue(ctlConfig, "currentFlowId") + "！");
                return httpInovkeRet;
            }


            if (!sendXMCommand(sampleCode))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "执行卸料命令失败！");
                return httpInovkeRet;
            }

            Commons.putJsonResCodeMsg(ref httpInovkeRet, "0", "执行成功！");
            return httpInovkeRet;
        }

        public Boolean sendXMCommand(string sampleCode)
        {
            bool excuResult = false;
            JObject ret = new JObject();
            JArray sendJo = new JArray();

            string cyjCode = getLogicDeviceID();
            int BarrelCount = 0;
            for (int i = 1; i <= 8; i++)
            {
                string barrelName = "BarrelCode" + Convert.ToString(i);
                if (myOPCTool.getPointValue("01", getLogicDeviceID(), barrelName).Equals(sampleCode))
                {
                    BarrelCount++;
                    string xmBarrelCode = "XMBarrelCode" + Convert.ToString(BarrelCount);
                    JObject uu = new JObject();
                    uu.Add("device", cyjCode);
                    uu.Add("point", xmBarrelCode);
                    uu.Add("value", Convert.ToString(i));
                    sendJo.Add(uu);
                }
            }

            JObject zz = new JObject();
            zz.Add("device", cyjCode);
            zz.Add("point", "XMBarrelNum");
            zz.Add("value", Convert.ToString(BarrelCount));
            sendJo.Add(zz);

            JObject yy = new JObject();
            yy.Add("device", cyjCode);
            yy.Add("point", "SampleCodeXM");
            yy.Add("value", sampleCode);
            sendJo.Add(yy);

            JObject ss = new JObject();
            ss.Add("device", cyjCode);
            ss.Add("point", "CommandCodeXM");
            ss.Add("value", "1");
            sendJo.Add(ss);

            ret = myOPCTool.sendcommandTest(sendJo);
            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                excuResult = true;
            }
            return excuResult;
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();
            if (!initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_sxlycyj)))
            {
                LogTool.WriteLog(typeof(CarSampleSXLY), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (!myOPCTool.startOpcServer())
            {
                LogTool.WriteLog(typeof(CarSampleSXLY), "OPC服务初始化失败");
                MessageBox.Show("OPC服务初始化失败");
            }

            InitINBSVoiceDevice();
            InitMc3000LedDevice();
            initHFRfidReader();
            InitSxlyBigLED();

            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            deviceTag = "RC_CYJ" + currentFlowId.Substring(2);
            writeMointorSingle("deviceName", deviceName);
            return true;
        }

        public override void modbusDeviceDataHandler(Dictionary<string, string> resultDictionary)
        {
            if (resultDictionary.Count > 2)
            {
                foreach (var item in resultDictionary)
                {
                    //pubKey = Enum.GetName(typeof(Commons.modbusPoint), int.Parse(item.Key));
                    string linestate = "未被挡住";
                    try
                    {
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

                            if (Value.Equals("1"))
                            {
                                linestate = "被挡住";
                            }
                            else if (Value.Equals("0"))
                            {
                                linestate = "未被挡住";
                            }

                            if (pubKey.Equals("inRadiation"))
                            {
                                string LogicTagName = deviceName + "入口对射";
                                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, linestate);

                                string showTagName = "01#" + deviceTag + "_infraredIn";
                                Commons.putDictionaryData(ref realDataDictionary, showTagName, realDataDictionary[pubKey]);
                            }
                            if (pubKey.Equals("outRadiation"))
                            {
                                string LogicTagName = deviceName + "出口对射";
                                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, linestate);

                                string showTagName = "01#" + deviceTag + "_infraredOut";
                                Commons.putDictionaryData(ref realDataDictionary, showTagName, realDataDictionary[pubKey]);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        string errStr = ex.ToString();
                        LogTool.WriteLog(typeof(CarSampleSXLY), errStr);
                    }
                }
            }
        }

        public override void GetCardInfo()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                cardHFReader.action_flag = "found_card";
                tipMsg("停车熄火扫描车卡");
                writeMointorSingle(deviceName + "运行环节", "获取车卡");
            }

            stateEventArgs.stepExcuteCount++;
            getRFIDCardID();
            if (stateEventArgs.stepExcuteCount > 300)
            {
                //超时seconds扫不到车卡时，切换到idle
                tipMsg("等待扫卡超时");
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                stateEventArgs.stepExcuteCount = 0;
                return;
            }
        }

        public override void waitForParking()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                writeMointorSingle(deviceName + "运行环节", "等待停车到位");
            }
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 10)
            {
                if (Commons.getDcValue(realDataDictionary, "inRadiation").Equals(Commons.signalOFF) && Commons.getDcValue(realDataDictionary, "outRadiation").Equals(Commons.signalOFF))
                {
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.nextStep = Commons.STEP.CAR_SendSampleCommand;
                }
                else
                {
                    int countNum = stateEventArgs.stepExcuteCount % 5;
                    if (countNum.Equals(0))
                    {
                        tipMsg("停车不到位");
                    }
                }
            }
        }

        public override void sendSampleCommand()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                writeMointorSingle(deviceName + "运行环节", "发送采样命令");
            }
            stateEventArgs.stepExcuteCount++;
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("1"))//检测到采样机检车完成信号
            {
                stateEventArgs.stepExcuteCount = 0;
                sendStartCYCommand();
                stateEventArgs.nextStep = Commons.STEP.CAR_WaitSample;
            }
            else
            {
                if (stateEventArgs.stepExcuteCount.Equals(8))
                {
                    tipMsg("采样机未就绪");
                }
            }
        }

        public override void waitSampling()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                writeMointorSingle(deviceName + "运行环节", "等待采样机启动");
            }
            stateEventArgs.stepExcuteCount++;
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus_Run").Equals("1"))
            {
                tipMsg("采样机启动采样");
                stateEventArgs.sampleStartTime = System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                stateEventArgs.nextStep = Commons.STEP.CAR_SampleFinish;//!!是切换CAR_SampleFinish
                stateEventArgs.stepExcuteCount = 0;
            }
            else
            {
                if (stateEventArgs.stepExcuteCount > 30)
                {
                    tipMsg("机器故障无法启动");//!!一直循环，等待复位
                }
            }
        }

        public override void sampleFinish()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                writeMointorSingle(deviceName + "运行环节", "采样机启动");
            }
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 30)//启动采样1分钟之后再检测采样完成信号
            {
                if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus_Finish").Equals("1"))
                {
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.sampleEndTime = System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                    stateEventArgs.nextStep = Commons.STEP.CAR_SampleSwipe;
                    if (!updateSampleResult())
                    {
                        tipMsg(stateEventArgs.actionResultMsg);
                    }
                }
                else
                {
                    if (stateEventArgs.stepExcuteCount > 400)
                    {
                        tipMsg("采样超时请检查");//!!一直循环，等待复位
                    }
                }
            }
        }

        public void sampleWaitSwipe()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                cardHFReader.action_flag = "write_sample_name";
                tipMsg("采样完成刷卡抬杆");
                writeMointorSingle(deviceName + "运行环节", "采样完成");
            }
            getRFIDCardID();
        }

        public Boolean updateSampleResult()
        {
            bool excuRes = false;
            if (excuCYProcedure("after"))
            {
                excuRes = true;
            }
            return excuRes;
        }

        public override void processHFCardID(string cardId, int stepfunction)
        {
            if (cardHFReader.action_flag.Equals("found_card"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    stateEventArgs.carNo = cardId;
                    stateEventArgs.cardID = stateEventArgs.carNo;
                    writeMointorSingle("carId", stateEventArgs.carNo);
                    Boolean exeBefore = excuCYProcedure("before");
                    if (exeBefore)
                    {
                        writeMointorSingle("车牌号", stateEventArgs.carNo);
                        Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", stateEventArgs.carNo);
                        Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_samplePoints", stateEventArgs.sampleCfgPonits);

                        tipMsg(stateEventArgs.carNo + "扫卡成功");
                        stateEventArgs.nextStep = Commons.STEP.CAR_WaitForParking;
                        stateEventArgs.stepExcuteCount = 0;
                        cardHFReader.action_flag = "IDEL";
                    }
                    else
                    {
                        tipMsg(stateEventArgs.actionResultMsg);
                    }
                }
                else
                {
                    tipMsg("流程错误禁止刷卡");
                }
            }
            else if (cardHFReader.action_flag.Equals("write_sample_name"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_SampleSwipe))
                {
                    if (cardId.Equals("write_sampleName_ok"))
                    {
                        stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                        tipMsg(stateEventArgs.carNo + "采样结束");
                        cardHFReader.action_flag = "IDEL";
                    }
                    else
                    {
                        tipMsg("刷卡失败请重新刷");
                    }
                }
                else
                {
                    tipMsg("流程错误禁止刷卡");
                }
            }
        }

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
            stateEventArgs.sampleEndTime = "";
            stateEventArgs.sampleCoordList = new JArray();
            stateEventArgs.stepExcuteCount = 0;
            cardHFReader.read_function = 1;
            cardHFReader.action_flag = "IDEL";
            writeMointorSingle("carId", "");
            actionFrontGateDown();
            actionLedRed();
        }

        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public override void actionFrontGateUp()
        {
            modbus.setCommand(((int)modbusPoint_sxlycyj.frontGateDownOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_sxlycyj.frontGateUpOut).ToString(), "True", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "出口道闸";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "抬起");

            string showTagName = "01#" + deviceTag + "_barrel";
            Commons.putDictionaryData(ref realDataDictionary, showTagName, "0");
        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            modbus.setCommand(((int)modbusPoint_sxlycyj.frontGateUpOut).ToString(), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(((int)modbusPoint_sxlycyj.frontGateDownOut).ToString(), "True", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "出口道闸";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "落下");

            string showTagName = "01#" + deviceTag + "_barrel";
            Commons.putDictionaryData(ref realDataDictionary, showTagName, "1");
        }

        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(((int)modbusPoint_sxlycyj.lightRedOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(((int)modbusPoint_sxlycyj.lightGreenOut).ToString(), "False", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "红绿灯";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "红灯亮");

            string showTagName = "01#" + deviceTag + "_trafficLights";
            Commons.putDictionaryData(ref realDataDictionary, showTagName, "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(((int)modbusPoint_sxlycyj.lightGreenOut).ToString(), "True", Commons.modbusType.COIL_STATUS);
            modbus.setCommand(((int)modbusPoint_sxlycyj.lightRedOut).ToString(), "False", Commons.modbusType.COIL_STATUS);

            string LogicTagName = deviceName + "红绿灯";
            Commons.putDictionaryData(ref realDataDictionary, LogicTagName, "绿灯亮");

            string showTagName = "01#" + deviceTag + "_trafficLights";
            Commons.putDictionaryData(ref realDataDictionary, showTagName, "1");
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

        public void sendStartCYCommand()
        {
            getCoordinateListParam(stateEventArgs.sampleCoordNumList);

            JObject dataJson = new JObject();
            dataJson.Add("machin_code", getLogicDeviceID());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", stateEventArgs.recordNo);
            dataJson.Add("command_code", "1");
            dataJson.Add("sampleCfgPonits", stateEventArgs.sampleCfgPonits);
            dataJson.Add("sample_code", stateEventArgs.sampleCode);
            dataJson.Add("big_water", stateEventArgs.bigWater);
            dataJson.Add("sampleType", stateEventArgs.sampleType);
            dataJson.Add("sampleCoordList", sortCoordList(stateEventArgs.sampleCoordList));
            dataJson.Add("suofenInterval", stateEventArgs.SuofenInterval);
            dataJson.Add("suofenCnt", stateEventArgs.SuofenCnt);
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "admin");

            String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendQCCYJCmd", dataJson);
            LogTool.WriteLog(typeof(CarSampleSXLY), "采样命令:" + opcCommand);
            JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);
            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                tipMsg("启动采样机失败");//!!这个提示看不到，时间短
            }
            else
            {
                writeMointorSingle("采样命令", stateEventArgs.carNo + "已发送");
            }
        }

        public override string getLogicDeviceID()
        {
            string logicDeviceID = "";
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                logicDeviceID = "CYJ1";
            }
            else if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                logicDeviceID = "CYJ2";
            }
            return logicDeviceID;
        }

        public void getQueueListCarId()
        {
            if (m_BigLedSendFlag.Equals("1"))
            {
                showCount++;
                if (showCount % 35 == 0)
                {
                    JObject dataJson = new JObject();
                    JObject ret = httpDbTool.invokeQry("car.qryQueueListCarId", dataJson);
                    String resCode = (String)ret[Commons.RES_CODE];
                    JArray queueList = new JArray();
                    if ("0".Equals(resCode))
                    {
                        if (ret["rows"] != null)
                        {
                            queueList = (JArray)ret["rows"];
                            m_SxlyBigLED.clearShowStrList();
                            foreach (var item in queueList)
                            {
                                JObject perRowObj = (JObject)item;
                                string showStr = Commons.getJsonValue(perRowObj, "car_id") + Commons.getJsonValue(perRowObj, "queue_no");
                                m_SxlyBigLED.setShowString(showStr);
                            }
                            m_SxlyBigLED.sendStrToLed();
                        }
                    }

                }
            }
        }

        public override void resetProcess()
        {
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            RecoverCommand(getLogicDeviceID());
        }

        public override void InitStartCyj()
        {
            string dveviceNo = getLogicDeviceID();
            JArray param = new JArray();
            JObject uu = new JObject();
            uu.Add("device", dveviceNo);
            uu.Add("point", "CommandInitStart");
            uu.Add("value", "1");
            param.Add(uu);
            JObject ret = myOPCTool.sendcommandTest(param);
            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {

            }
            else
            {

            }
            Thread.Sleep(3500);

            JArray param1 = new JArray();
            JObject kk = new JObject();
            kk.Add("device", dveviceNo);
            kk.Add("point", "CommandInitStart");
            kk.Add("value", "0");
            param1.Add(kk);
            ret = myOPCTool.sendcommandTest(param1);
            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {

            }
            else
            {

            }
        }

        public void getRFIDCardID()
        {
            cardHFReader.funfoundCard();
        }

        public void RecoverCommand(string dveviceNo)
        {
            JArray param = new JArray();
            JObject uu = new JObject();
            uu.Add("device", dveviceNo);
            uu.Add("point", "RecoverDevice");
            uu.Add("value", "1");
            param.Add(uu);
            JObject ret = myOPCTool.sendcommandTest(param);
            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {

            }
            else
            {

            }
            Thread.Sleep(3500);

            JArray param1 = new JArray();
            JObject kk = new JObject();
            kk.Add("device", dveviceNo);
            kk.Add("point", "RecoverDevice");
            kk.Add("value", "0");
            param1.Add(kk);
            ret = myOPCTool.sendcommandTest(param1);
            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {

            }
            else
            {

            }
        }

        public JArray sortCoordList(JArray OrgCoordinateList)
        {
            JArray retJarry = new JArray();
            List<int> x_pos_list = new List<int>();

            foreach (var item in OrgCoordinateList)
            {
                JObject obj = (JObject)item;
                x_pos_list.Add(Convert.ToInt32(obj["cy_x"]));
            }
            x_pos_list.Sort();
            foreach (var item in x_pos_list)
            {
                foreach (var elem in OrgCoordinateList)
                {
                    JObject thisObj = (JObject)elem;
                    if (Convert.ToInt32(thisObj["cy_x"]).Equals(item))
                    {
                        retJarry.Add(elem);
                        break;
                    }
                }
            }
            return retJarry;
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
                    while (!IsMainThreadRunStop1)
                    {
                        try
                        {
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            getQueueListCarId();
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE:IDLE(); break;
                                case Commons.STEP.CAR_GetCarNo: GetCardInfo(); break;
                                case Commons.STEP.CAR_WaitForParking: waitForParking(); break;
                                case Commons.STEP.CAR_SendSampleCommand: sendSampleCommand(); break;
                                case Commons.STEP.CAR_WaitSample: waitSampling(); break;
                                case Commons.STEP.CAR_SampleFinish: sampleFinish(); break;
                                case Commons.STEP.CAR_SampleSwipe: sampleWaitSwipe(); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_OutFinish: allSampleFlowFinish(); break;
                                default: ; break;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarSampleSXLY), "主循环异常:" + e.Message);
                        }
                    }
                }
            })).Start();
        }
    }
}
