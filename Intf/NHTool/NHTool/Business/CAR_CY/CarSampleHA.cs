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
using NHTool.Device.LED;
using NHTool.Device.Radio;

namespace NHTool.Business.CAR_CY
{
    /**   
     * 功能    : 怀安汽车采样接口调度流程 
     */
    public class CarSampleHA : CarSampleBusiness
    {
        string strIp = null;
        uint fonSize = 16;
        int width = 64;
        int height = 32;

        public enum modbusPoint_HACYJ : int
        {
            outRadiation = 3,  //出口红外
            inRadiation = 4, //入口红外

            lightRedOut = 17, //红
            lightGreenOut = 18, //绿
            frontGateUpOut = 19, //抬道闸
            frontGateDownOut = 20 //落道闸
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
                    else if ("readCard4cy".Equals(action))
                    {
                        //采样重新读卡  直接进入第二步
                        stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;

                    }
                    else if ("softReset".Equals(action))
                    {
                        //初始化 
                        stateEventArgs.nextStep = Commons.STEP.IDLE;
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

        int expectDeviceCount = 5;//期望初始化成功N个设备
        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();
            bool initResult = false;
            int initCount = 0;

            //------------------------------------------------------------------------


            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_HACYJ)))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleHA), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }


            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleHA), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }


            //初始化语音
            try
            {
                InitINBSVoiceDevice();
                initCount++;
            }
            catch
            {
                LogTool.WriteLog(typeof(CarSampleHA), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");

            }



            strIp = ctlConfig["LED_IP"];
            fonSize = Convert.ToUInt32(ctlConfig["LED_FONT_SIZE"]);
            width = Convert.ToInt16(ctlConfig["LED_WIDTH"]);
            height = Convert.ToInt16(ctlConfig["LED_HEIGHT"]);
            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleHA), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }


            if (myOPCTool.startOpcServer())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleHA), "OPC服务初始化失败");
                MessageBox.Show("OPC服务初始化失败");
            }


            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);
            // deviceTag = "RC_CYJ" + currentFlowId.Substring(2);  20190808
            deviceTag = "CY0" + currentFlowId.Substring(2);
            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarSampleHA), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleHA), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
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
                                case Commons.STEP.IDLE: IDLE(); break;
                                case Commons.STEP.CAR_CheckCard: checkCarInfoByCardId(); break;
                                case Commons.STEP.CAR_WaitForParking: waitForParking(); break;
                                case Commons.STEP.CAR_SendSampleCommand: sendSampleCommand(); break;
                                case Commons.STEP.CAR_WaitSample: waitSampling(); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_SampleFinish: sampleFinish(); break;
                                default: ; break;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarSampleHA), "主循环异常:" + e.Message);
                        }

                    }
                }
            })).Start();
        }



        public new void IDLE()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;

                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", "");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_samplePoints", "0");

                //默认放道闸，20190709会议确定（和老樊以及运行部门的参加）
                actionFrontGateDown();
                actionLedRed();

                stateEventArgs.currentChannel = "1";
                tipMsg("等待来车采样");
                restData();
            }


            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
                tipMsg("请靠前停车扫卡");
                idle(2);
            }
            else
            {
                idle(1); //没车歇会
            }
        }



        public new void checkCarInfoByCardId()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            //判断车位是否全部进来 20191008
            if (Commons.getDcValue(realDataDictionary, "inRadiation").Equals(Commons.signalON)||
               Commons.getDcValue(realDataDictionary, "outRadiation").Equals(Commons.signalON))
            {
                tipMsg("请开到停止位");
                idle(1);
                return;
            }
            
            //打开天线
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
            idle(1);

            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (!rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("打开天线异常");
                idle(2);
                return;
            }


            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 15) //1 ->2 秒    15-》10
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                stateEventArgs.stepExcuteCount = 0;
                actionFrontGateUp();
                actionLedGreen();
                tipMsg("非采样车请驶离");
                return;
            }


            //校验5次车卡，看该车是否需要采样
            if (isNeedSample(10))   //40
            {
                tipMsg(stateEventArgs.carNo + "扫卡成功");
                //调用过程 cy_before
                if (invokeCYBefore())
                {
                    stateEventArgs.nextStep = Commons.STEP.CAR_WaitForParking;
                    stateEventArgs.stepExcuteCount = 0;

                    //重置天线
                    readRfidDevice.stop_read_RFID_Info();
                    readRfidDevice.clearEcpTagList();
                    Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", stateEventArgs.carNo);
                    Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_samplePoints", stateEventArgs.sampleCfgPonits);

                    //落杆
                    actionFrontGateDown();
                    actionLedRed();

                    writeMointorSingle("车牌号", stateEventArgs.carNo);
                    return;
                }
                else
                {
                    //cy_before 调用失败
                    tipMsg(stateEventArgs.actionResultMsg);
                    idle(2);
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.nextStep = Commons.STEP.IDLE;
                }
            }
            else
            {
                tipMsg("非采样车请驶离"); //满足条件：1.该入厂车无采样机记录  2.分配在该采样机  3.运煤的车
                idle(2);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                stateEventArgs.stepExcuteCount = 0;
                actionFrontGateUp();
                actionLedGreen();
            }

        }


        /// <summary>
        /// 等待车辆停好（前后对射都没有被挡住）
        /// </summary>
        public new void waitForParking()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            stateEventArgs.stepExcuteCount++;

            if (Commons.getDcValue(realDataDictionary, "inRadiation").Equals(Commons.signalOFF) &&
                Commons.getDcValue(realDataDictionary, "outRadiation").Equals(Commons.signalOFF))
            {
                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.nextStep = Commons.STEP.CAR_SendSampleCommand;
            }
            else
            {
                tipMsg("车位不正确");
                idle(2);
            }

            if (stateEventArgs.stepExcuteCount > 60)
            {
                tipMsg("停车失败重新驶入");
                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.nextStep = Commons.STEP.IDLE;
            }
        }


        /// <summary>
        /// 发送采样命令
        /// </summary>
        public new void sendSampleCommand()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            stateEventArgs.stepExcuteCount++;

            //采样机就绪 0停机  1正在采  2故障  3就绪  7采样完成  8急停
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("3"))//检测到采样机检车完成信号
            {
                tipMsg("熄火下车开始采样");
                idle(2);
                tipMsg("发送采样命令");
                idle(1);
                if (invokeSendCYCMD())
                {
                    //给封装桶写码 TODO 

                    tipMsg(stateEventArgs.carNo + "等待采样");
                    stateEventArgs.nextStep = Commons.STEP.CAR_WaitSample;
                    stateEventArgs.stepExcuteCount = 0;
                }
                else
                {
                    LogTool.WriteLog(typeof(CarSampleHA), "发送采样命令失败");
                    tipMsg("发送采样命令失败");
                }
            }
            else
            {
                tipMsg("设备未就绪请稍候");
                idle(2);
            }

            //多次尝试未成功
            if (stateEventArgs.stepExcuteCount > 20)
            {
                tipMsg("启动采样机失败");
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                stateEventArgs.stepExcuteCount = 0;
            }
        }


        /// <summary>
        /// 等待采样过程
        /// </summary>
        public new void waitSampling()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            stateEventArgs.stepExcuteCount++;

            //一分钟没反应就告警
            if (stateEventArgs.stepExcuteCount > 60 && myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("3"))
            {
                tipMsg("启动采样机超时");
                idle(4);
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                stateEventArgs.stepExcuteCount = 0;
            }


            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("1")) //正在采样 
            {
                tipMsg(stateEventArgs.carNo + "正在采样");
                idle(1);
            }
            else if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("7")) //采样完成（这个状态需要让西玛持续时间长点）
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                stateEventArgs.stepExcuteCount = 0;

                //采样完成
                if (invokeCYAfter())
                {
                    tipMsg("采样完成请离开");
                    actionFrontGateUp();
                    actionLedGreen();
                }
                else
                {
                    tipMsg(stateEventArgs.actionResultMsg);
                }
            }
            else if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("2") || //跟西玛刘工沟通了，如果是故障（2）或急停（8）了，说明采样失败了
                myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("8"))
            {
                tipMsg(stateEventArgs.carNo + "采样失败"); //采样失败，就先一直显示，等待复位
                idle(5);
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                stateEventArgs.stepExcuteCount = 0;
            }


            //超时提醒
            if (stateEventArgs.stepExcuteCount > 600)
            {
                tipMsg("采样超时请检查");//!!一直循环，等待复位
                idle(3);
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                stateEventArgs.stepExcuteCount = 0;
            }
        }


        /// <summary>
        /// 汽车驶离
        /// </summary>
        public new void checkCarOutSign()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "正在离开");
                stateEventArgs.nextStep = Commons.STEP.CAR_SampleFinish;
            }
        }


        /// <summary>
        /// 离开，结束
        /// </summary>
        public new void sampleFinish()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //没信号了，车走了；
            //多停留几秒，因为车头和车厢中间有较宽的空隙，防止误判落下道闸
            if (!isRadiationBolckOk("出口处红外", "outRadiation", 4))
            {
                stateEventArgs.nextStep = Commons.STEP.IDLE;
            }
        }



        /// <summary>
        /// 调用数据库校验该车是否需要采样
        /// </summary>
        /// <returns></returns>
        public Boolean isNeedSample(int checkCnt)
        {
            Boolean result = false;
            JObject dataJson = new JObject();
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));

            //对读到的所有卡都调用判断下
            for (int i = 1; i <= checkCnt; i++)
            {
                if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
                {
                    foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                    {
                        if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                        {
                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;


                            dataJson.Remove("cardId");//先移出
                            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                            JObject retJson;
                            try
                            {
                                retJson = httpDbTool.invokeProc("check_car_card", dataJson);
                                if ("Y".Equals(getJsonValue(retJson, "isNeedSample")))
                                {
                                    //找到需要采样的了，就返回
                                    //重置天线
                                    readRfidDevice.stop_read_RFID_Info();
                                    readRfidDevice.clearEcpTagList();

                                    //直接返回需要采样,后面也不用再判了
                                    return true;
                                }
                                else
                                {
                                    //将存储过程检测到的废卡的EPC插入错卡列表里
                                    readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                                }
                            }
                            catch (Exception e)
                            {
                                LogTool.WriteLog(typeof(CarSampleHA), e.StackTrace);
                                LogTool.WriteLog(typeof(CarSampleHA), "isNeedSample数据库调用失败：" + e.Message);
                                continue;
                            }
                        }
                    }
                }

                idle(1);
            }


            return result;
        }



        /// <summary>
        /// 调用采样before
        /// </summary>
        /// <returns></returns>
        public Boolean invokeCYBefore()
        {
            Boolean result = false;
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.cy_before_preprocess", dataJson);

                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    result = true;
                    stateEventArgs.carNo = getJsonValue(retJson, "carNo");
                    stateEventArgs.sampleCfgPonits = getJsonValue(retJson, "samplePoints");
                }
                else
                {
                    stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
                    tipMsg(stateEventArgs.carNo + "核对信息");
                    idle(4);
                    stateEventArgs.nextStep = Commons.STEP.IDLE;
                }
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarSampleHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarSampleHA), "invokeCYBefore数据库调用失败：" + e.Message);
                tipMsg("采样校验异常");
            }
            return result;
        }


        /// <summary>
        /// 发送采样命令
        /// </summary>
        /// <returns></returns>
        public Boolean invokeSendCYCMD()
        {
            Boolean result = false;
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.cy_cmd_process", dataJson);
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    result = true;
                }
                else
                {
                    tipMsg(getJsonValue(retJson, "logicRetMsg"));
                }
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarSampleHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarSampleHA), "invokeSendCYCMD数据库调用失败：" + e.Message);
                tipMsg("发采样命令异常");
            }
            return result;
        }


        /// <summary>
        /// 调用采样after
        /// </summary>
        /// <returns></returns>
        public Boolean invokeCYAfter()
        {
            Boolean result = false;
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));

            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.cy_after_process", dataJson);
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    result = true;
                }
                else
                {
                    tipMsg(getJsonValue(retJson, "logicRetMsg"));
                }
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarSampleHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarSampleHA), "invokeCYAfter数据库调用失败：" + e.Message);
                tipMsg("采样结束异常");
            }
            return result;
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
            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_barrel", "0");
        }


        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            //如果出口方向的红外被遮挡  不落杆
            if (Commons.getDcValue(realDataDictionary, "outRadiation").Equals(Commons.signalON)) {
                return; 
            }  

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
            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_barrel", "1");
        }


        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_HACYJ.lightGreenOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_HACYJ.lightRedOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_trafficLights", "0");
        }


        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_HACYJ.lightRedOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_HACYJ.lightGreenOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_trafficLights", "1");
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

                    if (pubKey != null && pubKey.Equals("inRadiation"))
                    {
                        string LogicTagName = "01#" + deviceTag + "_infraredIn";
                        Commons.putDictionaryData(ref realDataDictionary, LogicTagName, realDataDictionary[pubKey]);
                    }

                    if (pubKey != null && pubKey.Equals("outRadiation"))
                    {
                        string LogicTagName = "01#" + deviceTag + "_infraredOut";
                        Commons.putDictionaryData(ref realDataDictionary, LogicTagName, realDataDictionary[pubKey]);
                    }
                }
            }
        }


        //提示信息，LED+声音
        /*
        public virtual void tipMsg(string msg)
        {
            LogTool.WriteLog(typeof(CarSampleHA), "tipMsg=" + msg);
            if (!lastMsg.Equals(msg))
            {
                string LogicTagName = "01#" + deviceTag + "_LEDShow";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                actiomLedShow(msg);
                actionVoice(msg);
            }
        }
        */


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
                    LogTool.WriteLog(typeof(CarSampleHA), "发信息到LED异常：" + e.StackTrace);
                    LogTool.WriteLog(typeof(CarSampleHA), "发信息到LED异常：" + e.Message);
                }
            }
        }
 

        //语音
        public override void actionVoice(string text)
        {
            iNBSVoiceDevice.iNBS_BroadCastContent(text);
        }
 
    }
}
