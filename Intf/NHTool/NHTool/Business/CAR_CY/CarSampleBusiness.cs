using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using System.Windows.Forms;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.Text.RegularExpressions;

namespace NHTool.Business.CAR_CY
{
    public abstract class CarSampleBusiness : BusinessBase
    {
        public void IDLE()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", "");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_samplePoints", "0");
                getCoordinateListParam("");
                stateEventArgs.currentChannel = "1";
                tipMsg("欢迎光临可以采样");
                restData();
            }

            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                tipMsg("汽车驶入采样区域开始扫卡");
            }
        }

        public void GetCardInfo()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
          
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
            if (rfid_result[Commons.RES_CODE].Equals("0"))//成功启动天线读取车卡
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
            }
        }

        public void checkCarInfoByCardId()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
            {
                foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                {
                    if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                    {
                        writeMointorSingle("车卡号", epcStr);
                        stateEventArgs.cardID = epcStr;
                        Boolean exeBefore = excuCYProcedure("before");

                        if (exeBefore)
                        {
                            stateEventArgs.nextStep = Commons.STEP.CAR_WaitForParking;
                            stateEventArgs.stepExcuteCount = 0;
                            readRfidDevice.stop_read_RFID_Info();
                            readRfidDevice.clearEcpTagList();

                            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", stateEventArgs.carNo);
                            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_samplePoints", stateEventArgs.sampleCfgPonits);
                            getCoordinateListParam(stateEventArgs.sampleCoordNumList);

                            actionFrontGateDown();
                            actionLedRed();
                            writeMointorSingle("车牌号", stateEventArgs.carNo);
                            tipMsg(stateEventArgs.carNo+"扫卡成功");
                            return;
                        }
                        else
                        {
                            tipMsg(stateEventArgs.actionResultMsg);
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
                    stateEventArgs.nextStep = Commons.STEP.IDLE;
                }
            }
        }

        public void waitForParking()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 20)
            {
                if (Commons.getDcValue(realDataDictionary, "inRadiation").Equals(Commons.signalOFF) && Commons.getDcValue(realDataDictionary, "outRadiation").Equals(Commons.signalOFF))
                {
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.nextStep = Commons.STEP.CAR_ScanCarLength;
                }
            }
        }

        public string getLogicDeviceID()
        {
            string logicDeviceID = "";
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                logicDeviceID = "RC_CYJ1";
            }
            else if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                logicDeviceID = "RC_CYJ2";
            }
            return logicDeviceID;
        }


        public string getMachinCode()
        {
            string machin_code = "";
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                machin_code = "RC_CYJ1";
            }
            else if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                machin_code = "RC_CYJ2";
            }
            return machin_code;
        }

        public void startScanCarLengthCommand()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("3"))
            {
                tipMsg("采样机开始检车");
                /*****以下代码调用存储过程发送启动检车命令************/
                JObject dataJson = new JObject();
                dataJson.Add("machin_code", getMachinCode());
                dataJson.Add("machin_type", "1");
                dataJson.Add("record_no", stateEventArgs.recordNo);
                dataJson.Add("command_code", "7");
                dataJson.Add("sample_code", stateEventArgs.sampleCode);
                dataJson.Add("data_status", "0");
                dataJson.Add("op_code", "admin");

                String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

                LogTool.WriteLog(typeof(CarSampleKLMY), "检车命令:" + opcCommand);
                JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);

                if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
                {
                    tipMsg("发送检车命令失败");
                }
                else
                {
                    writeMointorSingle("检车命令", stateEventArgs.carNo + "已发送");
                }
                stateEventArgs.nextStep =  Commons.STEP.CAR_SendSampleCommand ;//Commons.STEP.CAR_WaitScanCarLength;
                stateEventArgs.sampleStartTime = System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            }
        }

        public void waitScanCarLength()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("4"))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_SendSampleCommand;
                stateEventArgs.stepExcuteCount = 0;
            }
        }

        public void sendSampleCommand()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("5"))//检测到采样机检车完成信号
            {
                /*****以下代码调用存储过程发送启动采样命令************/
                JObject dataJson = new JObject();
                dataJson.Add("machin_code", getMachinCode());
                dataJson.Add("machin_type", "1");
                dataJson.Add("record_no", stateEventArgs.recordNo);
                dataJson.Add("command_code", "1");
                dataJson.Add("sampleCfgPonits", stateEventArgs.sampleCfgPonits);
                dataJson.Add("sample_code", stateEventArgs.sampleCode);
                dataJson.Add("big_water", stateEventArgs.bigWater);
                dataJson.Add("sampleCoordList", stateEventArgs.sampleCoordList);
                dataJson.Add("suofenInterval", stateEventArgs.SuofenInterval);
                dataJson.Add("suofenCnt", stateEventArgs.SuofenCnt);
                dataJson.Add("data_status", "0");
                dataJson.Add("op_code", "admin");

                String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);
                LogTool.WriteLog(typeof(CarSampleKLMY), "采样命令:" + opcCommand);
                JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);
                if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
                {
                    tipMsg("启动采样机失败");//!!这个提示看不到，时间短
                }
                else
                {
                    writeMointorSingle("采样命令", stateEventArgs.carNo + "已发送");
                }

                stateEventArgs.nextStep = Commons.STEP.CAR_WaitSample;
                tipMsg("检车完成熄火下车开始采样");
                
                stateEventArgs.stepExcuteCount = 0;
            }
            //因为检车时间不可靠，改为plc程序给的测点判断。
            else if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("6"))//检测到采样机检车失败信号
            {
                tipMsg("检车失败请处理");
                //出现检车超时的情形
                /*if (stateEventArgs.stepExcuteCount >= 40)
                {
                    tipMsg("检车失败!");
                }
                else if (stateEventArgs.stepExcuteCount >= 60)//重新发送检车命令
                {
                    stateEventArgs.nextStep = Commons.STEP.CAR_WaitForParking;//重新等待汽车停车到位
                    stateEventArgs.stepExcuteCount = 0;
                }
                */
            }
        }

        public void waitSampling()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("1"))
            {
                //tipMsg("采样机启动采样"); !! 异常，到不了下面步骤
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

        public void sampleFinish()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 60)//启动采样1分钟之后再检测采样完成信号
            {
                if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("7"))
                {                  
                    stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.sampleEndTime = System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");

                    if (excuCYProcedure("after"))
                    {
                        tipMsg(stateEventArgs.carNo + "采样结束汽车离开");
                        actionFrontGateUp();
                        actionLedGreen();
                    }
                    else
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

        public void checkCarOutSign()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "正在离开采样区域");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
            }
        }


        public void allSampleFlowFinish()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (!isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "离开采样区域");
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                clearDebugInfo();
            }
        }

        public void clearDebugInfo(){
                writeMointorSingle("exe before", "--");
                writeMointorSingle("exe after", "--");
                writeMointorSingle("检车命令", "--");
                writeMointorSingle("采样命令", "--");
                writeMointorSingle("车牌号", "--");
                writeMointorSingle("车卡号", "--");
        }

        public bool excuCYProcedure(string prodType)
        {
            bool isSccCheckCard = false;
            JObject dataJson = new JObject();
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                dataJson.Add("cyjNo", "1");
            }
            if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                dataJson.Add("cyjNo", "2");
            }
            if (prodType.Equals("before"))
            {
                dataJson.Add("cardId", stateEventArgs.cardID);
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                JObject retJson = httpDbTool.invokeProc("pk_car_schedule.cy_before_preProcess", dataJson);
                if (retJson != null && dataJson != null)
                {
         
                    LogTool.WriteLog(typeof(CarSampleKLMY),"exe before require param\n");
                    LogTool.WriteLog(typeof(CarSampleKLMY), dataJson.ToString() + "\n");

                    LogTool.WriteLog(typeof(CarSampleKLMY), "exe before ret(" + stateEventArgs.carNo + "," + stateEventArgs.cardID + ")\n");
                    LogTool.WriteLog(typeof(CarSampleKLMY), retJson.ToString() + "\n");

                }
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    stateEventArgs.carNo = getJsonValue(retJson, "carId");
                    stateEventArgs.sampleCode = getJsonValue(retJson, "sampleCode");
                    stateEventArgs.recordNo = getJsonValue(retJson, "recordNo");
                    stateEventArgs.sampleType = getJsonValue(retJson, "sampleType"); //来煤预报里面的，后面存过补送，klmy没有用到
                    stateEventArgs.sampleCfgPonits = getJsonValue(retJson, "sampleCfgPoints");
                    stateEventArgs.batchChgFlag = getJsonValue(retJson, "batchChgFlag");
                    stateEventArgs.sampleCoordNumList = getJsonValue(retJson, "sampleCoordNumList");
                    stateEventArgs.sampleCoordList = (JArray)retJson["sampleCoordList"];//getJsonValue(retJson, "sampleCoordList");
                    stateEventArgs.bigWater = getJsonValue(retJson, "bigWater");
                    stateEventArgs.SuofenInterval = getJsonValue(retJson, "SuofenInterval");
                    stateEventArgs.SuofenCnt = getJsonValue(retJson, "SuofenCnt");
                    //stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
                    isSccCheckCard = true;
                    writeMointorSingle("exe before","succ");

                }
                else
                {
                    if ("2000".Equals(getJsonValue(retJson, "logicRetCode")))
                    {//无须采样 
                        stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                    }
                    else
                    {
                        clearDebugInfo();
                    }
                    stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
                    writeMointorSingle("exe before", getJsonValue(retJson, "logicRetMsg"));
                }
            }
            else if (prodType.Equals("after"))
            {
                dataJson.Add("cardId", stateEventArgs.cardID);
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("recordNo", stateEventArgs.recordNo);
                dataJson.Add("machinCode", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("sampleCode", stateEventArgs.sampleCode);
                dataJson.Add("sampleType", stateEventArgs.sampleType);
                dataJson.Add("samplePoints", stateEventArgs.sampleCfgPonits);
                dataJson.Add("sampleCoordNumList", stateEventArgs.sampleCoordNumList);
                dataJson.Add("sampleCoordList", stateEventArgs.sampleCoordList);
                dataJson.Add("startTime", stateEventArgs.sampleStartTime);
                dataJson.Add("endTime", stateEventArgs.sampleEndTime);
                JObject retJson = httpDbTool.invokeProc("pk_car_schedule.cy_after_process", dataJson);
                if (retJson != null && dataJson != null)
                {
                    LogTool.WriteLog(typeof(CarSampleKLMY),"exe after require param\n");
                    LogTool.WriteLog(typeof(CarSampleKLMY), dataJson.ToString() + "\n");

                    LogTool.WriteLog(typeof(CarSampleKLMY), "exe after ret(" + stateEventArgs.carNo + "," + stateEventArgs.cardID + ")\n");
                    LogTool.WriteLog(typeof(CarSampleKLMY), retJson.ToString() + "\n");
      
                }
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    isSccCheckCard = true;
                    writeMointorSingle("exe after", "succ");
                }
                else
                {
                    stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
                    writeMointorSingle("exe after", getJsonValue(retJson, "logicRetMsg"));
                }
            }
            return isSccCheckCard;
        }


        public void faultRecovery()
        {
            //检查是否故障状态
            if (!myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("2"))
            {
                MessageBox.Show("系统不在故障状态！");
                return;
            }

            //下发200 故障复位命令
            JObject dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", stateEventArgs.recordNo);
            dataJson.Add("command_code", "8");
            dataJson.Add("sample_code", stateEventArgs.sampleCode);
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "admin");

            String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "故障复位:" + opcCommand);
            JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                MessageBox.Show("发送故障复位命令失败");
                return;
            }

            //延迟2秒
            idle(2);

            //下发300命令
            dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", stateEventArgs.recordNo);
            dataJson.Add("command_code", "9");
            dataJson.Add("sample_code", stateEventArgs.sampleCode);
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "1");

            opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "设备复位命令:" + opcCommand);
            ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                MessageBox.Show("发送设备复位失败");
                return;
            }


            //延迟2秒
            idle(2);

            //下发0命令,恢复运行
            dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", stateEventArgs.recordNo);
            dataJson.Add("command_code", "10");
            dataJson.Add("sample_code", stateEventArgs.sampleCode);
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "1");

            opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "恢复运行命令:" + opcCommand);
            ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                MessageBox.Show("发送恢复运行命令失败");
                return;
            }
            MessageBox.Show("发送故障复位，设备复位，恢复运行，成功，等待采样机就绪再重新检车！");
        }

        public JObject faultRecoveryHttpInovke(JObject paramIn)
        {
            JObject httpInovkeRet = new JObject();
            //检查是否故障状态
            if (!myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("2"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "系统不在故障状态！");
                return httpInovkeRet;
            }

            string opId = Commons.getJsonValue(paramIn, "opId");
            string deviceCode = Commons.getJsonValue(paramIn, "deviceCode");

            if (Commons.isNullStr(opId) || Commons.isNullStr(deviceCode))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "请求参数不完整！");
                return httpInovkeRet;
            }

            if (!deviceCode.Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "命令下发的设备不对，目标为：" + deviceCode + "当前设备为:" + Commons.getDcValue(ctlConfig, "currentFlowId") + "！");
                return httpInovkeRet;
            }

            //下发200 故障复位命令
            JObject dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", "");
            dataJson.Add("command_code", "8");
            dataJson.Add("sample_code", "");
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", opId);

            String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "故障复位:" + opcCommand);
            JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "发送故障复位命令失败！");
                return httpInovkeRet;
            }

            //延迟2秒
            idle(2);

            //下发300命令
            dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", "");
            dataJson.Add("command_code", "9");
            dataJson.Add("sample_code", "");
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "1");

            opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "设备复位命令:" + opcCommand);
            ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "发送设备复位失败！");
                return httpInovkeRet;
            }


            //延迟2秒
            idle(2);

            //下发0命令,恢复运行
            dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", "");
            dataJson.Add("command_code", "10");
            dataJson.Add("sample_code", "");
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "1");

            opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "恢复运行命令:" + opcCommand);
            ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "发送恢复运行命令失败！");
                return httpInovkeRet;
            }

            Commons.putJsonResCodeMsg(ref httpInovkeRet, "0", "执行成功！");
            return httpInovkeRet;
        }

        public JObject unloadHttpInovke(JObject paramIn)
        {
            JObject httpInovkeRet = new JObject();
            //检查是否就绪状态
            if (!myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("3"))
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

            //卸料
            JObject dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", "");
            dataJson.Add("command_code", "4");
            dataJson.Add("sample_code", sampleCode);
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", opId);

            String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "执行卸料命令失败:" + opcCommand);
            JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "执行卸料命令失败！");
                return httpInovkeRet;
            }

            Commons.putJsonResCodeMsg(ref httpInovkeRet, "0", "执行成功！");
            return httpInovkeRet;
        }

        public JObject stopQcCyjHttpInovke(JObject paramIn)
        {
            JObject httpInovkeRet = new JObject();

            string opId = Commons.getJsonValue(paramIn, "opId");
            string deviceCode = Commons.getJsonValue(paramIn, "deviceCode");

            if (Commons.isNullStr(opId) || Commons.isNullStr(deviceCode))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "请求参数不完整！");
                return httpInovkeRet;
            }

            if (!deviceCode.Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "命令下发的设备不对，目标为：" + deviceCode + "当前设备为:" + Commons.getDcValue(ctlConfig, "currentFlowId") + "！");
                return httpInovkeRet;
            }

            //停止
            JObject dataJson = new JObject();
            dataJson.Add("machin_code", getMachinCode());
            dataJson.Add("machin_type", "1");
            dataJson.Add("command_code", "2");
            dataJson.Add("op_code", opId);

            String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKLMY), "执行停止命令失败:" + opcCommand);
            JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                Commons.putJsonResCodeMsg(ref httpInovkeRet, "1", "执行卸停止令失败！");
                return httpInovkeRet;
            }

            Commons.putJsonResCodeMsg(ref httpInovkeRet, "0", "执行成功！");
            return httpInovkeRet;
        }

        public void getCoordinateListParam(string sampleCoordNumList)
        {
            if (!sampleCoordNumList.Equals(""))
            {
                  string[] coordNum = sampleCoordNumList.Split(',');
                  if (coordNum.Length > 0)
                  {
                      List<Int32> tempCoordNumList = new List<Int32>();
                      foreach (var item in coordNum)
                      {
                          tempCoordNumList.Add(Convert.ToInt32(item));
                      }
                      for (int i = 1; i < 19; i++)
                      {
                          string coordTagName = "01#" + deviceTag + "_" + "POINT" + "_" + Convert.ToString(i);
                          if (tempCoordNumList.Contains(i))
                          {
                              Commons.putDictionaryData(ref realDataDictionary, coordTagName, "1");
                          }
                          else
                          {
                              Commons.putDictionaryData(ref realDataDictionary, coordTagName, "0");
                          }
                      }
                  }
            }
            else 
            {
                for (int i = 1; i < 19; i++)
                {
                    string coordTagName = "01#" + deviceTag + "_" + "POINT" + "_" + Convert.ToString(i);
                    Commons.putDictionaryData(ref realDataDictionary, coordTagName, "0");
                }
            }
        }
    }
}
