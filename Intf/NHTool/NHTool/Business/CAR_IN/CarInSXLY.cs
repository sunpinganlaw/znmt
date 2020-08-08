using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Business.Car_IN;
using NHTool.Common;
using System.Threading;
using System.Windows.Forms;
using Newtonsoft.Json.Linq;


/*********************************************
 ******** 山西铝业入厂刷卡接口****************
 ***********create by dafeige   20190529******
 ********************************************/

namespace NHTool.Business.CAR_IN
{
    class CarInSXLY : CarInBusiness
    {
        private int showCount = 0;
        private string m_BigLedSendFlag = "0";
        private int m_bigLedShowInterval = 30;
        private string coalTypeName = "";
        private string planId = "";
        private string UHFReadStepType = "IDEL";

        public CarInSXLY()
        {
            m_BigLedSendFlag = ctlConfig["BigLedSendFlag"];
            m_bigLedShowInterval = Convert.ToInt32(ctlConfig["BigLedShowInterval"]);
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();

            InitINBSVoiceDevice();
            InitMc3000LedDevice();
            initHFRfidReader();
            InitSxlyBigLED();

            return true;
        }

        public override void recoverDeviceState()
        {
            stateEventArgs.stepExcuteCount = 0;
            stateEventArgs.cardID = "";
            stateEventArgs.carNo = "";
            stateEventArgs.recordNo = "";
            stateEventArgs.actionResultMsg = "";
            stateEventArgs.cyjNo = "";
            planId = "";
            coalTypeName = "";
            writeMointorSingle("carId", "");
            writeMointorSingle("车牌号", "");
            writeMointorSingle("recordNO", "");
            cardHFReader.action_flag = "IDLE";
            stateEventArgs.nextStep = Commons.STEP.IDLE;
        }

        public override void carIdleStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                recoverDeviceState();
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                tipMsg("停车刷卡分配采样");
            }
            stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
        }

        public override void getCarNoStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                cardHFReader.action_flag = "found_card";
                UHFReadStepType = "found_card";
                tipMsg("停车刷卡分配采样");
                writeMointorSingle(deviceName + "运行环节", "获取车卡信息");
            }

            getRFIDCardID();
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 200)
            {
                //超时seconds扫不到车卡时，切换到idle
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                stateEventArgs.stepExcuteCount = 0;
                return;
            }
        }

        public void checkCarInfoByCardId()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (CheckCarInBeforeStep())
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
            }
            else
            {
                tipMsg(stateEventArgs.actionResultMsg);
                idle(5);
                stateEventArgs.nextStep = Commons.STEP.IDLE;
            }
        }

        public override void checkCarOutSign()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 30)
            {
                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.nextStep = Commons.STEP.IDLE;
            }
        }

        public bool CheckCarInBeforeStep()
        {
            Boolean isSccInvokeDb = false;
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.carNo));
            dataJson.Add("recordNo", Convert.ToString(stateEventArgs.recordNo));
            dataJson.Add("coalTypeNo", coalTypeName);
            dataJson.Add("PlanId", planId);
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.rc_before_preProcess", dataJson);

            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                stateEventArgs.cyjNo = getJsonValue(retJson, "cyjNo");
                string indicateMsg = stateEventArgs.carNo + "进#" + stateEventArgs.cyjNo + "采样";
                tipMsg(indicateMsg);
                LogTool.WriteLog(typeof(CarInSXLY), indicateMsg);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                isSccInvokeDb = true;
            }
            else
            {
                stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
            }
            return isSccInvokeDb;
        }

        public void testCheckCarInBeforeStep()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.carNo));
            dataJson.Add("recordNo", Convert.ToString(stateEventArgs.recordNo));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.rc_before_process", dataJson);

            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                stateEventArgs.cyjNo = getJsonValue(retJson, "cyjNo");
                string indicateMsg = stateEventArgs.carNo + "进#" + stateEventArgs.cyjNo + "采样";
                tipMsg(indicateMsg);
                LogTool.WriteLog(typeof(CarInSXLY), indicateMsg);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
            }
        }

        public override void processRfid4UHF(string callBackInfo, string stepfunction)
        {
            if (stepfunction.Equals("found_card"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    Byte[] RetByte = strToByteList(callBackInfo, 20);
                    string readCarId = AskiiToStr(RetByte, 4, 8);
                    stateEventArgs.carNo = callBackInfo;
                    writeMointorSingle("carId", stateEventArgs.carNo);
                    writeMointorSingle("车牌号", stateEventArgs.carNo);
                    UHFReadStepType = "found_recordno";
                }
            }
            else if (stepfunction.Equals("found_recordno"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    stateEventArgs.recordNo = callBackInfo;
                    writeMointorSingle("recordNO", stateEventArgs.recordNo);
                    UHFReadStepType = "found_planid";
                }
            }
            else if (stepfunction.Equals("found_planid"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    planId = callBackInfo;
                    writeMointorSingle("planId", planId);
                    UHFReadStepType = "found_coalno";
                }
            }
            else if (stepfunction.Equals("found_coalno"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    coalTypeName = callBackInfo;
                    writeMointorSingle("coalNo", coalTypeName);
                    stateEventArgs.stepExcuteCount = 0;
                    UHFReadStepType = "IDLE";
                    stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
                }
            }
        }

        public override void processHFCardID(string cardId, int stepfunction)
        {
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
                    cardHFReader.action_flag = "found_planid";
                }
            }
            else if (cardHFReader.action_flag.Equals("found_planid"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    planId = cardId;
                    writeMointorSingle("planId", planId);
                    cardHFReader.action_flag = "found_coalno";
                }
            }
            else if (cardHFReader.action_flag.Equals("found_coalno"))
            {
                if (stateEventArgs.currentStep.Equals(Commons.STEP.CAR_GetCarNo))
                {
                    coalTypeName = cardId;
                    writeMointorSingle("coalNo", coalTypeName);
                    stateEventArgs.stepExcuteCount = 0;
                    cardHFReader.action_flag = "IDLE";
                    stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
                }
            }
        }

        public override void actiomLedShow(string text)
        {
            mc3000LedDevice.sendContentToLed(text);
            m_SxlyBigLED.sendNormalStrToLed(text);
        }

        public override void actionVoice(string text)
        {
            iNBSVoiceDevice.iNBS_BroadCastContent(text);
        }

        public void getRFIDCardID()
        {
            cardHFReader.funfoundCard();
        }

        public void getRFIDUHFCardID()
        {
            if (UHFReadStepType.Equals("found_card"))
            {
                m_SxlyRfidUHF.ReadSelectedUsedData(m_SxlyRfidUHF.areaTypeList["EPC"].ToString(), "0", "6", "found_card");
            }
            else if (UHFReadStepType.Equals("found_recordno"))
            {
                m_SxlyRfidUHF.ReadSelectedUsedData(m_SxlyRfidUHF.areaTypeList["用户区"].ToString(), "2", "6", "found_recordno");
            }
            else if (UHFReadStepType.Equals("found_planid"))
            {
                m_SxlyRfidUHF.ReadSelectedUsedData(m_SxlyRfidUHF.areaTypeList["用户区"].ToString(), "2", "3", "found_planid");
            }
            else if (UHFReadStepType.Equals("found_coalno"))
            {
                m_SxlyRfidUHF.ReadSelectedUsedData(m_SxlyRfidUHF.areaTypeList["用户区"].ToString(), "14", "2", "found_coalno");
            }
            cardHFReader.funfoundCard();
        }

        public byte[] strToByteList(string str, int retLen)
        {
            int count = 0;
            byte[] ret = new byte[retLen];
            string[] tempStrList = (str.Trim(' ')).Split(' ');
            for (int i = 0; i < tempStrList.Length; i++)
            {
                if (!tempStrList[i].Equals(""))
                {
                    ret[count] = Convert.ToByte(tempStrList[i], 16);
                    count++;
                }
            }
            return ret;
        }

        public string AskiiToStr(Byte[] Data, int startPos, int len)
        {
            string name = "";
            for (int j = startPos; j < (startPos + len); j++)
            {
                if (Data[j] > 1 && Data[j] < 127)//是ASCII码，直接转
                {
                    name += ASCIIEncoding.Default.GetString(Data, j, 1);
                }
                else
                {
                    byte[] tmp = new byte[2];
                    tmp[0] = Data[j];
                    tmp[1] = Data[j + 1];
                    name += Encoding.Default.GetString(tmp, 0, 2);
                    j = j + 1;
                }
            }
            return name;
        }


        public void getQueueListCarId()
        {
            if (m_BigLedSendFlag.Equals("1"))
            {
                showCount++;
                int modValue = showCount % m_bigLedShowInterval;
                if (modValue.Equals(0))
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
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(); break;
                                case Commons.STEP.CAR_CheckCard: checkCarInfoByCardId(); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
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
