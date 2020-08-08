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
using System.Xml;
using System.IO;
using System.Media;


namespace NHTool.Business.CAR_CY
{
    public class CarSampleKC : BusinessBase
    {
        private DataBaseTool dataBaseTool = null;
        public enum modbusPoint_kc : int
        {
            inRadiation = 1,
            outRadiation = 3,
            frontRadiation = 2,
            backRadiation = 5,
            frontGateUpOut = 18,
            frontGateDownOut = 19,
            backGateUpOut = 22,
            backGateDownOut = 20,
            lightRedOut = 17
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
                    idle(2);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);

                }
            }

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
                    idle(2);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);

                }
            }
        }



        /// <summary>
        /// 抬起车辆后方道闸
        /// </summary>
        public override void actionBackGateUp()
        {
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.backGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);

                }
                if (item.ToString().Equals(Commons.backGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);

                }
            }

        }

        /// <summary>
        ///  落下车辆后方道闸
        /// </summary>
        public override void actionbackGateDown()
        {

            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.backGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);

                }
                if (item.ToString().Equals(Commons.backGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);

                }
            }

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
        }


        public override Boolean initial()
        {
            dataBaseTool = new DataBaseTool();
            bool initResult = false;
            int initCount = 0;
            //base.initial();
            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_kc)))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKC), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKC), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiVoiceDecice())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKC), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKC), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

            if (myOPCTool.startOpcServer())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarSampleKC), "OPC服务初始化失败");
                MessageBox.Show("OPC服务初始化失败");
            }

            if (initCount.Equals(5))
            {
                initResult = true;
            }

            return initResult;
        }



        // <summary>
        /// 播放语音
        /// </summary>
        /// <param name="text">语音文本</param>
        public override void actionVoice(string text)
        {
            string wavFiname = "";
            if (text.StartsWith("欢迎光临可以采样"))
            {

                wavFiname = "欢迎光临停车刷卡.wav";
            }

            if (text.StartsWith("汽车驶入开始扫卡"))
            {

                wavFiname = "汽车驶入采样区域.wav";
            }
            if (text.StartsWith("正在扫描车卡"))
            {

                wavFiname = "正在检查卡片.wav";
            }
            if (text.StartsWith("采样机开始检车"))
            {

                wavFiname = "采样机启动.wav";
            }
            if (text.StartsWith("熄火下车开始采样"))
            {

                wavFiname = "熄火下车等待采样.wav";
            }

            if (text.EndsWith("采样结束"))
            {

                wavFiname = "采样完毕.wav";
            }
            if (text.EndsWith("正在离开"))
            {

                wavFiname = "正在离开采样区域.wav";
            }

            if (!wavFiname.Equals(""))
            {
                string rootPath = Directory.GetCurrentDirectory() + "\\wav\\";
                SoundPlayer s = new SoundPlayer(rootPath + wavFiname);
                s.Play();

            }
            else
            {

                base.actionVoice(text);
            }


        }


        /// <summary>
        /// 库车LED有问题，覆写基类的初始化方式
        /// </summary>
        /// <param name="ip"></param>
        /// <returns></returns>
        public override bool initiLedDevice(string ip)
        {
            if (tcpclient!=null)
            {

                return true;

            }
            else
            {
                return false;

            }
        }




        /// <summary>
        /// 库车LED有问题，覆写基类的初始化方式
        /// </summary>
        /// <param name="text">发送语音</param>
        public override void actiomLedShow(string text)
        {
            JObject ledJson = new JObject();

            ledJson.Add("IP", ctlConfig["LED_IP"]);
            ledJson.Add("TEXTSHOW", text);

            string ledShowText = ledJson.ToString().Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "");

             if (tcpclient!=null)
             {
                 tcpclient.HostAddress = "192.168.1.13";
                 tcpclient.Port = 6001;
                 if(tcpclient.IsConnected)
                 {
                     tcpclient.disconnect();
                     System.Threading.Thread.Sleep(50);
                     tcpclient.ConnectToServer();
                     tcpclient.SendMessage(ledShowText);
                     tcpclient.disconnect();
                 

                 }
                 else
                 {
                     tcpclient.ConnectToServer();
                     tcpclient.SendMessage(ledShowText);
                     tcpclient.disconnect();
                   
                 }

             }

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
                            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
                            {
                                stateEventArgs.currentStep = stateEventArgs.nextStep;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarSampleKC), "主循环异常:" + e.Message);
                        }
                    }
                }
            })).Start();
        }

        /**
         * 接受对客户段直接控制
         * 语法：
         * {"action":"",value:"",value2:""}
         * 
         */
        public JObject processCarHttpRequestMethod(String httpStr)
        {
            JObject ret = new JObject();
            JObject jsonIn = new JObject();
            try
            {
                //前期判断
                jsonIn = (JObject)JsonConvert.DeserializeObject(httpStr);
                JObject baseInfo = (JObject)jsonIn["baseInfo"];



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

        public void IDLE()
        {
            restData();

            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("欢迎光临可以采样");
                restData();
            }

            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                tipMsg("汽车驶入开始扫卡");
            }
        }

        public void GetCardInfo()
        {
            tipMsg("正在扫描车卡");
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
            if (rfid_result[Commons.RES_CODE].Equals("0"))//成功启动天线读取车卡
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_CheckCard;
            }
        }

        public void checkCarInfoByCardId()
        {
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

                            actionFrontGateDown();
                            actionLedRed();
                            writeMointorSingle("车牌号", stateEventArgs.carNo);
                            tipMsg(stateEventArgs.carNo + "扫卡成功");
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
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 20)
            {
                //&& Commons.getDcValue(realDataDictionary, "outRadiation").Equals(Commons.signalOFF)
                if (Commons.getDcValue(realDataDictionary, "inRadiation").Equals(Commons.signalOFF) )
                {
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.nextStep = Commons.STEP.CAR_ScanCarLength;
                }
            }
        }

        private string getLogicDeviceID()
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


        private string getMachinCode()
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
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("3"))
            {
                stateEventArgs.stepExcuteCount++;
                if (stateEventArgs.stepExcuteCount < 3)
                {

                    return;

                }
                else
                {

                    stateEventArgs.stepExcuteCount = 0;
                }
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

                String opcCommand = "";
                string v_resCode = "";
                string v_resMsg = "";
                //String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

                dataBaseTool.sendWTQCCYJCmd(Convert.ToString(dataJson), out opcCommand, out v_resCode, out v_resMsg);

                if (v_resMsg != "err" && v_resCode == "0" && opcCommand.Length>2)
                {

                    LogTool.WriteLog(typeof(CarSampleKLMY), "检车命令:" + opcCommand.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));
                    JObject ret = myOPCTool.processHttpRequestMethod(opcCommand.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));

                    if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
                    {
                        tipMsg("发送检车命令失败");
                    }
                    else
                    {
                        writeMointorSingle("检车命令", stateEventArgs.carNo + "已发送");
                    }
                    stateEventArgs.nextStep = Commons.STEP.CAR_SendSampleCommand;//Commons.STEP.CAR_WaitScanCarLength;
                    stateEventArgs.sampleStartTime = System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                }
                else
                {

                    LogTool.WriteLogInfo(typeof(CarSampleKC), v_resMsg + "--cased by " + Convert.ToString(dataJson));

                }
                
            }
        }

        public void waitScanCarLength()
        {
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("4"))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_SendSampleCommand;
                stateEventArgs.stepExcuteCount = 0;
            }
        }

        public void sendSampleCommand()
        {
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

                String opcCommand = "";
                string v_resCode = "";
                string v_resMsg = "";


               
                dataBaseTool.sendWTQCCYJCmd(Convert.ToString(dataJson), out opcCommand, out v_resCode, out v_resMsg);

                if (v_resMsg != "err" && v_resCode == "0" && opcCommand.Length > 2)
                {
                    LogTool.WriteLog(typeof(CarSampleKC), "采样命令:" + opcCommand.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));
                    JObject ret = myOPCTool.processHttpRequestMethod(opcCommand.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));
                    if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
                    {
                        tipMsg("启动采样机失败");//!!这个提示看不到，时间短
                    }
                    else
                    {
                        writeMointorSingle("采样命令", stateEventArgs.carNo + "已发送");
                    }

                    stateEventArgs.nextStep = Commons.STEP.CAR_WaitSample;
                    tipMsg("熄火下车开始采样");
                    stateEventArgs.stepExcuteCount = 0;

                }
                
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
            stateEventArgs.stepExcuteCount++;
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("1"))
            {
                //tipMsg("采样机启动采样"); !! 异常，到不了下面步骤
                stateEventArgs.nextStep = Commons.STEP.CAR_SampleFinish;//!!是切换CAR_SampleFinish
                stateEventArgs.stepExcuteCount = 0;
            }
            else
            {
                if (stateEventArgs.stepExcuteCount > 120)
                {
                    tipMsg("机器故障无法启动");//!!一直循环，等待复位
                }
            }
        }

        public void sampleFinish()
        {
            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 150)//启动采样1分钟之后再检测采样完成信号
            {
                if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("7"))
                {
                    stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                    stateEventArgs.stepExcuteCount = 0;
                    stateEventArgs.sampleEndTime = System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");

                    if (excuCYProcedure("after"))
                    {
                        tipMsg(stateEventArgs.carNo + "采样结束");
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
                    if (stateEventArgs.stepExcuteCount > 460)
                    {
                        tipMsg("采样超时请检查");//!!一直循环，等待复位
                    }
                }
            }

        }

        public void checkCarOutSign()
        {
            if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "正在离开");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
            }
        }


        public void allSampleFlowFinish()
        {
            if (!isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "已经离开");
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                clearDebugInfo();
            }
        }

        public void clearDebugInfo()
        {
            
            writeMointorSingle("exe before", "--");
            writeMointorSingle("exe after", "--");
            writeMointorSingle("检车命令", "--");
            writeMointorSingle("采样命令", "--");
            writeMointorSingle("车牌号", "--");
            writeMointorSingle("车卡号", "--");
        }


        public override void resetProcess()
        {
            string key = "";
            Dictionary<string, string> tmpCtlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            string   currentFlowId = Commons.getDcValue(tmpCtlConfig, "currentFlowId");

            if (currentFlowId == "CY1")
            {

                key=  "01" + "#" + "RC_CYJ1" + "_" + "CommandCode";

            }
            else if (currentFlowId == "CY2")
            {

                key = "01" + "#" + "RC_CYJ2" + "_" + "CommandCode";

            }
            if (key.Length > 0)
            {

                myOPCTool.setValue(key, "0");

            }
           
      
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
                string v_attachInfo = "";
                string v_resCode = "";
                string v_resMsg = "";
                string cardID = stateEventArgs.cardID;
                dataBaseTool.cy_before_preProcess(Commons.getDcValue(ctlConfig, "currentFlowId"), cardID, out v_attachInfo, out v_resCode, out v_resMsg);
               
              
                LogTool.WriteLog(typeof(CarSampleKC), v_attachInfo);

               
                if (v_resMsg != "err" && "1000".Equals(v_resCode))
                 {
                     XmlDocument document = new XmlDocument();
                     document.LoadXml(v_attachInfo);
                     XmlNode xNode = document.SelectSingleNode("root/recordNo");
                     stateEventArgs.recordNo = xNode.InnerText.ToString();

                     xNode = document.SelectSingleNode("root/carId");
                     stateEventArgs.carNo = xNode.InnerText.ToString();

                     xNode = document.SelectSingleNode("root/sampleCode");
                     stateEventArgs.sampleCode = xNode.InnerText.ToString();

                     xNode = document.SelectSingleNode("root/samplePoints");
                     stateEventArgs.sampleCfgPonits = xNode.InnerText.ToString();

                     xNode = document.SelectSingleNode("root/batchChgFlag");
                     stateEventArgs.batchChgFlag = xNode.InnerText.ToString();


                     xNode = document.SelectSingleNode("root/CoordNumList");
                     stateEventArgs.sampleCoordNumList = xNode.InnerText.ToString();

                     xNode = document.SelectSingleNode("root/CoordList");//取is_success节点的值
                     string CoordListStr = xNode.InnerText.ToString().Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "");

                     JArray sampleCoordListOrginal = new JArray();
                     sampleCoordListOrginal = JArray.Parse(CoordListStr);
                     List<CoordList> CoordList=  sampleCoordListOrginal.ToObject<List<CoordList>>();

                     CoordList = CoordList.OrderBy(o => o.cy_x_int).ToList();//升序
                  
                 

                     JsonSerializer serializer = new JsonSerializer();
                     StringWriter sw = new StringWriter();
                     serializer.Serialize(new JsonTextWriter(sw), CoordList);
                     string result = sw.GetStringBuilder().ToString();
                     JArray datArray = JArray.Parse(result);


                     stateEventArgs.sampleCoordList = datArray;//----排序后的坐标

                     isSccCheckCard = true;
                     writeMointorSingle("exe before", "succ");
                 }
                 else
                 {
                     if (v_resMsg != "err" && "2000".Equals(v_resCode))
                     {//无须采样 
                         stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                     }
                     else
                     {
                         clearDebugInfo();
                     }
                     stateEventArgs.actionResultMsg =v_resMsg;
                     writeMointorSingle("exe before", v_resMsg);

                 }
                
               
               
            }
            else if (prodType.Equals("after"))
            {
                string v_resCode = "";
                string v_resMsg = "";
                int  sampleCfgPonits=0;
                XmlDocument doc = new XmlDocument();
                XmlDeclaration dec = doc.CreateXmlDeclaration("1.0", "GB2312", null);
                doc.AppendChild(dec);
                //创建一个根节点（一级）
                XmlElement root = doc.CreateElement("root");
                doc.AppendChild(root);

                XmlElement recordNo = doc.CreateElement("recordNo");
                recordNo.InnerText =  stateEventArgs.recordNo;
                root.AppendChild(recordNo);

                XmlElement machinCode = doc.CreateElement("machinCode");
                machinCode.InnerText = Commons.getDcValue(ctlConfig, "currentFlowId");
                root.AppendChild(machinCode);

                XmlElement sampleCode = doc.CreateElement("sampleCode");
                sampleCode.InnerText = stateEventArgs.sampleCode;
                root.AppendChild(sampleCode);


                XmlElement sampleType = doc.CreateElement("sampleType");
                sampleType.InnerText = stateEventArgs.sampleType;
                root.AppendChild(sampleType);

                XmlElement samplePoints = doc.CreateElement("samplePoints");
                samplePoints.InnerText = stateEventArgs.sampleCfgPonits;
                root.AppendChild(samplePoints);

                XmlElement sampleWeight = doc.CreateElement("sampleWeight");
                sampleWeight.InnerText = "0";
                root.AppendChild(sampleWeight);

                XmlElement SFNum = doc.CreateElement("SFNum");
                SFNum.InnerText = "3";
                root.AppendChild(SFNum);

                int.TryParse(stateEventArgs.sampleCfgPonits,out  sampleCfgPonits);
                XmlElement coordinate1 = doc.CreateElement("coordinate1");
                coordinate1.InnerText = "";
              

                XmlElement coordinate2 = doc.CreateElement("coordinate2");
                coordinate2.InnerText = "";

                XmlElement coordinate3 = doc.CreateElement("coordinate3");
                coordinate3.InnerText = "";

                if(sampleCfgPonits>0)
                {
                    string[] coordinatArry = stateEventArgs.sampleCoordNumList.Split(',');
                    for(int i=0;i<coordinatArry.Length;i++)
                    {
                        if(i==0)
                        {
                            coordinate1.InnerText = coordinatArry[0];

                        }
                        if (i == 1)
                        {
                            coordinate2.InnerText = coordinatArry[1];

                        }
                        if (i == 2)
                        {
                            coordinate3.InnerText = coordinatArry[2];

                        }

                    }

                }

                root.AppendChild(coordinate1);
                root.AppendChild(coordinate2);
                root.AppendChild(coordinate3);

                XmlElement startTime = doc.CreateElement("startTime");
                startTime.InnerText = stateEventArgs.sampleStartTime;
                root.AppendChild(startTime);

                XmlElement endTime = doc.CreateElement("endTime");
                endTime.InnerText = stateEventArgs.sampleEndTime;
                root.AppendChild(endTime);

                dataBaseTool.cy_after_process(Commons.getDcValue(ctlConfig, "currentFlowId"), stateEventArgs.cardID, ConvertXmlToString(doc), out v_resCode, out v_resMsg);



                
                if (v_resMsg!="err"&&"1000".Equals(v_resCode) )
                {
                    isSccCheckCard = true;
                    writeMointorSingle("exe after", "succ");
                }
                else
                {
                    stateEventArgs.actionResultMsg = v_resMsg;
                    writeMointorSingle("exe after", v_resMsg);
                }

            }
            return isSccCheckCard;
        }

        public class CoordList:IComparable<CoordList>
        {
            public string cy_x { get; set; }
            public string cy_y { get; set; }
            public string cy_h { get; set; }

            public int cy_x_int { get; set; }

             public CoordList(string cy_x ,string cy_y,string cy_h)
           {
               this.cy_x = cy_x;
               this.cy_y = cy_y;
               this.cy_h = cy_h;
               this.cy_x_int =int.Parse(cy_x);
           }


             //重写的CompareTo方法，根据Id排序
             public  int CompareTo(CoordList other)
                {
                    if (null == other)
                    {
                        return 1;//空值比较大，返回1
                    }
                    return this.cy_x_int.CompareTo(other.cy_x_int);//升序
         
                }


        }

       /// <summary>
        /// 将XmlDocument转化为string
        /// </summary>
        /// <param name="xmlDoc"></param>
        /// <returns></returns>
        public string ConvertXmlToString(XmlDocument xmlDoc)
        {
            MemoryStream stream = new MemoryStream();
            XmlTextWriter writer = new XmlTextWriter(stream, null);
            writer.Formatting = System.Xml.Formatting.Indented;
            xmlDoc.Save(writer); 
	        StreamReader sr = new StreamReader(stream, System.Text.Encoding.UTF8);
            stream.Position = 0;
            string xmlString = sr.ReadToEnd();
            sr.Close();
            stream.Close(); 
	       return xmlString;
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

            LogTool.WriteLog(typeof(CarSampleKC), "故障复位:" + opcCommand);
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
            dataJson.Add("op_code", "admin");

            opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKC), "设备复位命令:" + opcCommand);
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
            dataJson.Add("op_code", "admin");

            opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);

            LogTool.WriteLog(typeof(CarSampleKC), "恢复运行命令:" + opcCommand);
            ret = myOPCTool.processHttpRequestMethod(opcCommand);

            if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
            {
                MessageBox.Show("发送恢复运行命令失败");
                return;
            }
            MessageBox.Show("发送故障复位，设备复位，恢复运行，成功，等待采样机就绪再重新检车！");
        }
    }
}
