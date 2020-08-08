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
using System.IO.Ports;
using NHTool.Device.LED;

/*
 * 1.怀安的三个磅不区分重衡轻衡，都可以称重称轻，都可以双向
 * 
 * 2.所以比较特殊，这一个类支持三个，程序内部按123#来标识
 *     出厂入厂那个磅编号为       1#
 *     从1号采样机挪出来的重磅为  2#
 *     主要用于搬倒煤的为         3#
 *   
 * 3.根据车卡类型来区分是搬倒煤还是入厂煤
 * 
 * 4.第一次上衡的车（无毛重）是重车，有毛重没皮重的是轻车
 */
namespace NHTool.Business.CAR_CZ.CAR_PZ
{
    class CarWeightHA : CarCzBusiness
    {
        public string channelNo;
        public string frontNo = ""; //1,2； 当前哪个是在前面：从1进来，2是前面；从2进来，1是前面

        string strIp = null;
        uint fonSize = 16;
        int width = 64;
        int height = 32;

        string strIp2 = null;
        uint fonSize2 = 16;
        int width2 = 64;
        int height2 = 32;


        //怀安 - 地磅的亚当模块配置
        public enum modbusPoint_weightHA : int
        {
            radiation1 = 3, // 北边红外对射 3   东
            radiation2 = 4, //南边红外对射 4   西

            lightRedOut1 = 17, //北红灯  DO0  东红灯
            lightGreenOut1 = 18, //北绿灯  DO1 
            frontGateUpOut1 = 19, //北道闸抬  DO2
            frontGateDownOut1 = 20, //北道闸落 DO3

            frontGateUpOut2 = 21, //南道闸抬 DO4  西
            frontGateDownOut2 = 22, //南道闸落 DO5
            lightRedOut2 = 23, //南红灯 DO6
            lightGreenOut2 = 24 //南绿灯 DO7
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();

            bool initResult = false;
            int initCount = 0;
 
            int expectDeviceCount = 8;//期望初始化成功N个设备
 

            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_weightHA)))
            {
                LogTool.WriteLog(typeof(CarWeightHA), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }


            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarWeightHA), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }


            if (initiRfidDevice2(ctlConfig["RFID_IP2"], ctlConfig["RFID_PORT2"], ctlConfig["RFID_ANT_PORT2"]))
            {
                LogTool.WriteLog(typeof(CarWeightHA), "天线2初始化成功【IP2：" + ctlConfig["RFID_IP2"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "RFID2初始化失败");
                MessageBox.Show("RFID2初始化失败");
            }


            strIp = ctlConfig["LED_IP"];
            fonSize = Convert.ToUInt32(ctlConfig["LED_FONT_SIZE"]);
            width = Convert.ToInt16(ctlConfig["LED_WIDTH"]);
            height = Convert.ToInt16(ctlConfig["LED_HEIGHT"]);
            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(CarWeightHA), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }


            strIp2 = ctlConfig["LED_IP2"];
            fonSize2 = Convert.ToUInt32(ctlConfig["LED_FONT_SIZE2"]);
            width2 = Convert.ToInt16(ctlConfig["LED_WIDTH2"]);
            height2 = Convert.ToInt16(ctlConfig["LED_HEIGHT2"]);
            if (initiLedDevice2(ctlConfig["LED_IP2"]))
            {
                LogTool.WriteLog(typeof(CarWeightHA), "LED2初始化成功【IP：" + ctlConfig["LED_IP2"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "LED2初始化失败");
                MessageBox.Show("LED2初始化失败");
            }

 
            //初始化语音
            try
            {
                InitINBSVoiceDevice();
                initCount++;
            }
            catch
            {
                LogTool.WriteLog(typeof(CarWeightHA), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }


            try
            {
                InitINBSVoiceDevice2();
                initCount++;
            }
            catch
            {
                LogTool.WriteLog(typeof(CarWeightHA), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }


            //初始化地磅串口
 
            if (initiSerialPortDevice(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]))
            {
                LogTool.WriteLog(typeof(CarWeightHA), "地磅串口初始化成功！");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "地磅串口初始化失败");
                MessageBox.Show("地磅串口初始化失败");
            }


            //启动接受控制测点的服务器
            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            //测点皮重PZ1, PZ2
            channelNo = currentFlowId.Substring(2);
            deviceTag = "CZ" + channelNo;
            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarWeightHA), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarWeightHA), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
            }

            return initResult;

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

                if (deviceCode != null && Commons.getDcValue(ctlConfig, "currentFlowId").Equals(deviceCode))
                {
                    if ("systemReset".Equals(action))
                    {
                        stateEventArgs.currentStep = Commons.STEP.IDLE;
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


        //解析处理地磅串口数据
        public override void comPort_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            SerialPort comPort = (SerialPort)sender;
            byte[] packetData = null;
            while (comPort.BytesToRead > 0)
            {
                packetData = new byte[comPort.ReadBufferSize + 1];
                int count = comPort.Read(packetData, 0, comPort.ReadBufferSize);
            }

            int weightData = 0;
            byte DT_STX = 0x02;
            int parseflag = 0;
            int i = 0;
            byte[] parseData = new byte[32];

            while (i < packetData.Length)
            {
                if (packetData[i].Equals(DT_STX))
                {
                    for (int j = 0; j < 12; j++)
                    {
                        parseData[j] = packetData[i];
                        if (j == 11)
                        {
                            parseflag = 1;
                        }
                        i++;
                    }

                    if (parseflag == 1)
                    {
                        break;
                    }
                }
                i++;
            }



            if (parseflag == 1)
            {
                //单位是千克
                weightData = Convert.ToInt32(fromByteString(parseData, 2, 8));

                //放入内存库
                if (realDataDictionary.ContainsKey("carWeight"))
                {
                    realDataDictionary["carWeight"] = Convert.ToString(weightData);
                }
                else
                {
                    realDataDictionary.TryAdd("carWeight", Convert.ToString(weightData));
                }

                lastGetWegihtTime = DateTime.Now;
            }
        }


        public static string fromByteString(Byte[] dataArray, int startAdd, int dataLength)
        {
            string railStr = "";
            for (int i = 0; i < dataLength; i++)
            {

                railStr += Convert.ToString(dataArray[startAdd + i] & 0x0f);
            }
            return railStr;
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
                actionLedGreen();

                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());

                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_ReadyWeight: readyWeightStep(3); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(40); break;
                                case Commons.STEP.CAR_Weighting: weightingStep(); break;
                                case Commons.STEP.CAR_WeightFinish: weightFinishStep(); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_OUT: carOutStep(); break;
                                default: break;
                            }
                            idle(1);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarCzBusiness), "主循环异常:" + e.StackTrace);
                            LogTool.WriteLog(typeof(CarCzBusiness), "主循环异常:" + e.Message);
                        }
                    }
                })).Start();
            }
        }


        /// <summary>
        /// 汽车调度，空闲时，持续判断 入红外对射被挡住超2秒，则认为汽车开始入厂
        /// </summary>
        public override void carIdleStep()
        {
            //
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;

                //重置数据，为扫卡称重做准备
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelPZ_" + channelNo, "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsPZ_" + channelNo, "0");
                stateEventArgs.currentChannel = channelNo;


                stateEventArgs.stepExcuteCount = 0;
                stateEventArgs.weightType = "";

              
                actionLedGreen();

                frontNo = "1";
 
                actionFrontGateUp();//默认是抬道闸的
                tipMsg("等待上磅称重");
 
                idle(1);
                frontNo = "2";
 
                actionFrontGateUp();//默认是抬道闸的
                tipMsg("等待上磅称重");
 

                frontNo = "";
            }


            //暂时先串行处理，后面改成两个对射并行处理
            if ("".Equals(frontNo) && isRadiationBolckOk("", "radiation1", 2))
            {
                frontNo = "2";
            }

            if ("".Equals(frontNo) && isRadiationBolckOk("", "radiation2", 2))
            {
                frontNo = "1";
            }


            //有车进来了，开始抓重量
            if (frontNo != "")
            {
                if (getWeigth() > WEIGHT)
                {
                    //有重量了，就落道闸
                  // actionFrontGateDown();//落前方道闸往后挪
                   
                    actionLedRed();

                    tipMsg("停车扫卡请勿下车");
                    stateEventArgs.nextStep = Commons.STEP.CAR_ReadyWeight;
                }
                else
                {
                    if (stateEventArgs.stepExcuteCount > 40)
                    {
                        restData();
                        stateEventArgs.nextStep = Commons.STEP.IDLE;
                        stateEventArgs.currentStep = Commons.STEP.CAR_Finish;//用于结束
                        tipMsg("等待超时退出再进");
                        frontNo = "";
                        stateEventArgs.stepExcuteCount = 0;
                        LogTool.WriteLog(typeof(CarWeightHA),stateEventArgs.cardID);
                        LogTool.WriteLog(typeof(CarWeightHA), stateEventArgs.carWeight.ToString());

                    }

                    stateEventArgs.stepExcuteCount++;
                    tipMsg("正在上磅");
                    idle(3);

                }
            }

        }


        /// <summary>
        /// 汽车调度，准备称重,持续2秒，重量超过Commons.WEIGHT，表示车上榜
        /// </summary>
        /// <param name="seconds">持续时间(秒)</param>
        public override void readyWeightStep(int seconds)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation1"))
               || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation2")))
            {
                tipMsg("不在称重区域内");
                return;
            }
            else {
                //两边都没挡住
                actionFrontGateDown();//落前方道闸
            
            }


            Boolean isBeginWeigth = true;
            writeMointorSingle("车卡号", stateEventArgs.carNo);
            for (int i = 0; i < seconds; i++)
            {
                if (getWeigth() < WEIGHT)
                {
                    isBeginWeigth = false;
                    //重量小于阀值  直接初始化
                    stateEventArgs.nextStep = Commons.STEP.IDLE;
                    break;
                }
                idle(1);
            }

            if (isBeginWeigth)
            {
                
                tipMsg("准备称重");
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
            }
        }



        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public void getCarNoStep(int seconds)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("扫描车卡");
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //选择一个天线打开
            ReadRfidDevice tmpReadRfidDevice = null;
            if ("1".Equals(frontNo))
            {
                tmpReadRfidDevice = readRfidDevice;
            }
            else if ("2".Equals(frontNo))
            {
                tmpReadRfidDevice = readRfidDevice2;
            }
            Dictionary<String, String> rfid_result = tmpReadRfidDevice.read_RFID_Info();


            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (rfid_result == null || !rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("天线打开异常");
                idle(3);
                return;
            }

            //天线打开成功，在seconds秒内，每秒试读取卡数据，并调用存储过程校验
            for (int i = 0; i < seconds; i++)
            {
                if (getWeigth() < WEIGHT)
                {
                    resetAndToNextSetp(null, Commons.STEP.IDLE);
                    return;
                }

                idle(1);
                if (tmpReadRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
                {
                    foreach (var epcStr in tmpReadRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                    {
                        if (!tmpReadRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                        {
                            //判断卡号是否合法
                            /*
                            if (epcStr.Length != 24 || (!epcStr.StartsWith("B2A9")))
                            {
                                LogTool.WriteLog(typeof(CarWeightHA), "本次无效车卡：" + epcStr);
                                tmpReadRfidDevice.InsertEpcTagList(ref tmpReadRfidDevice.errorEpcTag, epcStr);
                                continue;
                            }
                             * */

                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;

                            //调用weight before
                            Boolean invokeRet = invokeDbWeightBefore();

                            if (invokeRet)
                            {
                                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                                tmpReadRfidDevice.stop_read_RFID_Info();
                                tmpReadRfidDevice.clearEcpTagList();
                                stateEventArgs.stepExcuteCount = 0;
                                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoPZ_" + channelNo, stateEventArgs.carNo);

                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                tipMsg(stateEventArgs.carNo);
                                idle(3);//等待停车称重
                                return;
                            }
                            else
                            {
                                //扫到其他卡，异常，不提示 ，继续扫卡
                                tipMsg(stateEventArgs.actionResultMsg);
                                idle(1);

                                //暂时将错误的卡的EPC插入错卡列表里
                                tmpReadRfidDevice.InsertEpcTagList(ref tmpReadRfidDevice.errorEpcTag, epcStr);
                            }
                        }
                    }
                }
            }

            //超时seconds扫不到车卡时，切换到idle
            tipMsg("未扫到有效车卡");
            resetAndToNextSetp(null, Commons.STEP.IDLE);
            idle(1);
        }


        /// <summary>
        /// 汽车调度，检查是否满足称重条件
        ///如果车卡异常，还需要将异常的车卡写入到errlist中，方便进行排错
        /// </summary>
        public override void weightingStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }


            stateEventArgs.stepExcuteCount++;
            if (stateEventArgs.stepExcuteCount > 10)
            {
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                stateEventArgs.stepExcuteCount = 0;
                tipMsg("重新开始");
                return;
            }

            //重量不达标
            if (getWeigth() < WEIGHT)
            {
                return;
            }

            if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation1"))
                || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation2")))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                tipMsg("车辆位置不正确");
                idle(3);
                return;
            }
            
            actionBackGateDown();//落后方道闸

            //汽车在磅秤上,连续10秒的重量平均值
            tipMsg("正在称重保持稳定");
            Boolean isSucc = true;
            int beforeWeight = getWeigth();
            for (int i = 0; i < 5; i++)
            {
                idle(1);
                int nowWeigth = getWeigth();
                //判断两边红外对射是否没有挡住,且重量变化在范围内
                if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation1"))
                        && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation2"))
                        && (Math.Abs(nowWeigth - beforeWeight) < MIN_CHANGE_WEIGHT)
                        && beforeWeight > 0 && nowWeigth > 0)
                {
                    LogTool.WriteLog(typeof(CarCzBusiness), "nowWeigth - beforeWeight =" + Math.Abs(nowWeigth - beforeWeight));
                    beforeWeight = nowWeigth;
                }
                else
                {
                    isSucc = false;
                    break;
                }
            }

            if (isSucc)
            {
                writeMointorSingle("resultWegiht", stateEventArgs.carNo);
                stateEventArgs.carWeight = beforeWeight;
                stateEventArgs.nextStep = Commons.STEP.CAR_WeightFinish;
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, Convert.ToString(beforeWeight));
               // tipMsg("重量" + beforeWeight);
                idle(3);

                //记录数据到本地数据库xieyt
                recordToLocalDB(stateEventArgs.cardID, stateEventArgs.carNo, Convert.ToString(beforeWeight));
            }
            else
            {
                tipMsg(stateEventArgs.carNo + "重量不稳");
                idle(2);
                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
            }
        }


        /// <summary>
        /// /汽车调度，称重或回皮结束
        /// </summary>
        /// <param name="czType"></param>
        public void weightFinishStep()
        {
            //调用存储过程 
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            JObject retJson = new JObject();
            JObject dataJson = new JObject();

            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
            dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));
            dataJson.Add("weightType", Convert.ToString(stateEventArgs.weightType));

            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.weight_after_process", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarWeightHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarWeightHA), "数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_pzQty", Convert.ToString(stateEventArgs.carWeight));
            double netQty = Convert.ToDouble(stateEventArgs.carMzWeight) - Convert.ToDouble(stateEventArgs.carWeight) - Convert.ToDouble(stateEventArgs.carKdWeight);
            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_netQty", Convert.ToString(netQty));


            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                tipMsg("称重完成"+ stateEventArgs.carWeight);

                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightMZ_" + channelNo, stateEventArgs.carMzWeight);
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, Convert.ToString(stateEventArgs.carWeight));
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightKD_" + channelNo, stateEventArgs.carKdWeight);
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightJZ_" + channelNo, Convert.ToString(netQty));

                actionFrontGateUp();//抬前方道闸
                
                actionLedGreen();
                idle(1);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                return;
            }
            else
            {
                tipMsg(stateEventArgs.carNo + getJsonValue(retJson, "logicRetMsg"));
                idle(2);
                frontNo = "";
                resetAndToNextSetp(null, Commons.STEP.IDLE); //从头开始重新来
            }
        }


        /// <summary>
        /// 汽车调度，称重离开道闸
        /// </summary>
        public override void carOutStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }


            tipMsg("过磅结束请驶离");
            idle(2);
            //if (getWeigth() < WEIGHT && !isRadiationBolckOk("", "radiation1", 4) && !isRadiationBolckOk("", "radiation2",4))
            //如果两边的对射都没被挡住 并且重量小于 
       if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation1"))
                        && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation2"))&&getWeigth() < WEIGHT )                
     
            {
               
                //stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                resetAndToNextSetp("离开完成", Commons.STEP.IDLE);

                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightMZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightKD_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightJZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoPZ_" + channelNo, "");
                //actionBackGateUp();//抬后方道闸
                idle(4);
            }
        }

        //抬前方道闸
        public override void actionFrontGateUp()
        {
            if ("1".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "False", Commons.modbusType.COIL_STATUS);
            }
            else if ("2".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "False", Commons.modbusType.COIL_STATUS);
            }
              //页面控制的时候没有frontno两边都抬
            else {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "False", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "False", Commons.modbusType.COIL_STATUS);      
            }
        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            if ("1".Equals(frontNo) )
            {
                if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation1")) && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation2")))
                {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "False", Commons.modbusType.COIL_STATUS);
                }
               }
            else if ("2".Equals(frontNo) )
            {
                if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation2")) && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation1")))
                {
                    modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "False", Commons.modbusType.COIL_STATUS);
                }
                 }
            //页面控制的时候没有frontno两边都落下
            else
            {
                if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation2")) || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation1")))
                {
                    return;
                }
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "False", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "False", Commons.modbusType.COIL_STATUS);
  
            }
        }

        //抬后方道闸
        public override void actionBackGateUp()
        {
            if ("2".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "False", Commons.modbusType.COIL_STATUS);
            }
            else if ("1".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "False", Commons.modbusType.COIL_STATUS);
            }
            //页面控制的时候没有frontno两边都抬
            else
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut1)), "False", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateUpOut2)), "False", Commons.modbusType.COIL_STATUS);         
            }
        }

        /// <summary>
        ///  落下车辆后方道闸
        /// </summary>
        public  void actionBackGateDown()
        {
            if ("2".Equals(frontNo) )
            {
                if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation2")) && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation1")))
                {
                    modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "False", Commons.modbusType.COIL_STATUS);
                }
                 }
            else if ("1".Equals(frontNo))
            {
                if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation2")) && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "radiation1")))
                {
                    modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "False", Commons.modbusType.COIL_STATUS);
                }
               }
            //页面控制的时候没有frontno两边都落下
            else
            {
                if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation2")) || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "radiation1")))
                {
                    return;
                }

                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut1)), "False", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "True", Commons.modbusType.COIL_STATUS);
                idle(1);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.frontGateDownOut2)), "False", Commons.modbusType.COIL_STATUS);
     
            }
        }

        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            if ("1".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightGreenOut1)), "False", Commons.modbusType.COIL_STATUS);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightRedOut1)), "True", Commons.modbusType.COIL_STATUS);
            }
            else if ("2".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightGreenOut2)), "False", Commons.modbusType.COIL_STATUS);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightRedOut2)), "True", Commons.modbusType.COIL_STATUS);
            }
        }


        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            if ("1".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightRedOut1)), "False", Commons.modbusType.COIL_STATUS);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightGreenOut1)), "True", Commons.modbusType.COIL_STATUS);
            }
            else if ("2".Equals(frontNo))
            {
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightRedOut2)), "False", Commons.modbusType.COIL_STATUS);
                modbus.setCommand(Convert.ToString((int)(modbusPoint_weightHA.lightGreenOut2)), "True", Commons.modbusType.COIL_STATUS);
            }

        }


        /// <summary>
        /// 委托处理亚当模块中获取的数据的事件
        /// </summary>
        /// <param name="resultDictionary">从亚当模块获取的各个点的数据</param>
        public override void modbusDeviceDataHandler(Dictionary<string, string> resultDictionary)
        {
            if (resultDictionary.Count > 2)
            {
                foreach (var item in resultDictionary)
                {
                    string pubKey = Enum.GetName(modbusPointAddress, int.Parse(item.Key));

                    if (pubKey != null)
                    {
                        string Value = "";
                        if (item.Value.Equals("True") || item.Value.Equals("true"))
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

                    //转换成需要发送到前台的测点
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

 
        //语音
        public override void actionVoice(string text)
        {
            iNBSVoiceDevice.iNBS_BroadCastContent(text);
        }

        public void actionVoice2(string text)
        {
            iNBSVoiceDevice2.iNBS_BroadCastContent(text);
        }

        /// <summary> 
 
        /// 提示信息
        /// </summary>
        /// <param name="msg"></param>
        public override void tipMsg(string msg)
        {
            string tmpMsg = msg;
            if ("1".Equals(frontNo))
            {
                tmpMsg = tmpMsg + " ";
            }


            if (!lastMsg.Equals(tmpMsg))
            {
                try
                {
                    //根据当前哪边是前面，选择对应的前面LED
                    if ("1".Equals(frontNo))
                    {
 
                        actionVoice(Commons.changeCarNo(tmpMsg));

                        idle(1);
 
 
                        actionVoice2(Commons.changeCarNo(tmpMsg));
                        idle(1);
 
                        Onbon_LED.SendText(tmpMsg, strIp2, fonSize2, width2, height2);
                    }

                    Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_LEDShow", tmpMsg);
                    lastMsg = tmpMsg;
                    writeMointorSingle("LED屏", tmpMsg);
                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(CarWeightHA), "发信息到LED异常：" + e.StackTrace);
                    LogTool.WriteLog(typeof(CarWeightHA), "发信息到LED异常：" + e.Message);
                }
            }
        }


        //调用过磅前过程，校验过磅类型和是否有入厂记录等
        public Boolean invokeDbWeightBefore()
        {
            Boolean ret = false;
            JObject retJson = new JObject();


            //调用存储过程，查询车卡，返回车牌号
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));


            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.weight_before_preProcess", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarWeightHA), e.StackTrace);
                LogTool.WriteLog(typeof(CarWeightHA), "invokeDbCzBefore-PZ数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }


            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                stateEventArgs.carNo = getJsonValue(retJson, "carId");
                stateEventArgs.weightType = getJsonValue(retJson, "weightType"); //CZ称重  HP回皮

                ret = true;
            }
            else
            {
                stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
                //提示
                tipMsg(stateEventArgs.actionResultMsg);
                idle(2);
            }

            return ret;
        }



    }


}
