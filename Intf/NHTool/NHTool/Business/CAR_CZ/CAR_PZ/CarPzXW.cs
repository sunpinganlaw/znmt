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

namespace NHTool.Business.CAR_CZ.CAR_PZ
{
    class CarPzXW : CarCzBusiness
    {
        public string channelNo;
        private DataBaseTool dataBaseTool = null;
        string v_resCode = "";
        string v_resMsg = "";
        string v_carId = "";
        string v_mzQty = "";
        string v_kdQty = "";
        public static int WEIGHT_LINE = 2000;//正式2000， 调试30
        

        //回皮的点，需要现场确认
        public enum modbusPoint_xwPz : int
        {
            inRadiation = 4,  //入口红外
            outRadiation = 3, //出口红外

            lightRedOut = 17, //红绿灯
            lightGreenOut = 18,

            frontGateUpOut = 19, //前道闸抬
            frontGateDownOut = 20,//前道闸落

            backGateUpOut = 21, //后道闸抬（常开）
            backGateDownOut = 22 //后道闸落
        }

        public override Boolean initial()
        {

            //初始化c#中调用数据库的工具类实例
            dataBaseTool = new DataBaseTool();

            bool initResult = false;
            int initCount = 0;
            int expectDeviceCount = 5;//期望初始化成功N个设备


            if (initiVoiceDecice())
            {
                LogTool.WriteLog(typeof(CarPzXW), "声音模块初始化成功");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarPzXW), "声音模块初始化失败");
            }

            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_xwPz)))
            {
                LogTool.WriteLog(typeof(CarPzXW), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"]+"】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarPzXW), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }


            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarPzXW), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarPzXW), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(CarPzXW), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarPzXW), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

            
            if (initiSerialPortDevice(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]))
            {
                LogTool.WriteLog(typeof(CarPzXW), "地磅串口初始化成功！");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarPzXW), "地磅串口初始化失败");
                MessageBox.Show("地磅串口初始化失败");
            }

            //测点皮重PZ1, PZ2
            channelNo = currentFlowId.Substring(2);
            deviceTag = "HP" + channelNo;
            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarPzXW), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarPzXW), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
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

            Thread.Sleep(200);//稍微缓下，否则会截个一半数据
            while (comPort.BytesToRead > 0)
            {
                packetData = new byte[comPort.ReadBufferSize + 1];
                int count = comPort.Read(packetData, 0, comPort.ReadBufferSize);
            }

            int weightData = 0;
            byte DT_STX = 0x0D;
            int parseflag = 0;
            int i = 0;
            byte[] parseData = new byte[32];

            while (i < packetData.Length)
            {
                if (packetData[i].Equals(DT_STX))
                {
                    for (int j = 0; j < 17; j++)
                    {
                        parseData[j] = packetData[i];
                        if (j == 16) 
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
                weightData = Convert.ToInt32(fromByteString(parseData, 6, 5));
                LogTool.WriteLog(typeof(CarPzXW), "解析地磅重量：" + weightData);

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
                actionFrontGateDown();
                actionBackGateUp();
                actionLedRed();

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
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(40, "PZ"); break;
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
            //2)获取通道状态，持续2秒, 但目前都改为根据重量，不判断红外对射了 
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                writeMointorSingle("皮重(千克)", "0");

                //初始化
                tipMsg("一车一杆严禁冲卡");
                idle(1);
                actionFrontGateDown();
                actionBackGateUp();
                actionLedRed();

                //重置数据，为扫卡称重做准备
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelPZ_" + channelNo, "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsPZ_" + channelNo, "0");
                stateEventArgs.currentChannel = channelNo;
            }

            if (getWeigth() > WEIGHT_LINE)
            {
                tipMsg("车辆进入计量区域");
                idle(3);
                stateEventArgs.nextStep = Commons.STEP.CAR_ReadyWeight;
            }
            else
            {
                restData();
                stateEventArgs.nextStep = Commons.STEP.IDLE;
                idle(5);
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

            Boolean isBeginWeigth = true;
            writeMointorSingle("车牌号", stateEventArgs.carNo);
            for (int i = 0; i < seconds; i++)
            {
                idle(1);
                if (getWeigth() < WEIGHT_LINE)
                {
                    isBeginWeigth = false;
                    break;
                }
            }

            if (isBeginWeigth)
            {
                tipMsg("请停车称重");
                idle(2);
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
            }
        }



        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public override void getCarNoStep(int seconds, string czType)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("扫描车卡信息");
                idle(2);
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            //先打开天线
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();

            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (!rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("天线打开异常");
                idle(3);
                return;
            }

            //天线打开成功，在seconds秒内，每秒试读取卡数据，并调用存储过程校验
            for (int i = 0; i < seconds; i++)
            {
                if (getWeigth() < WEIGHT_LINE)
                {
                    resetAndToNextSetp(null, Commons.STEP.IDLE);
                    return;
                }

                idle(1);
                if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
                {
                    foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                    {
                        if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                        {
                            //判断卡号是否合法 0000 和ab00开头的是车卡
                            if (epcStr.Length != 24 || !epcStr.StartsWith("ab00"))
                            {
                                LogTool.WriteLog(typeof(CarPzXW), "本次无效车卡：" + epcStr);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                                continue;
                            }

                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;

                            try
                            {
                                
                                dataBaseTool.hp_before_preProcess_xw(Commons.getDcValue(ctlConfig, "currentFlowId"), stateEventArgs.cardID, out v_mzQty, out v_kdQty, out v_carId, out v_resCode, out v_resMsg);
                            }
                            catch (Exception e)
                            {
                                LogTool.WriteLog(typeof(CarPzXW), e.StackTrace);
                                LogTool.WriteLog(typeof(CarPzXW), "数据库调用失败：" + e.Message);
                                tipMsg("数据库调用失败");
                                idle(2);
                                resetAndToNextSetp(null, Commons.STEP.IDLE);
                                return;
                            }


                            if ("1000".Equals(v_resCode))
                            {
                                stateEventArgs.carNo = v_carId;
                                tipMsg(stateEventArgs.carNo + "准备计量");
                                idle(2);
                                actionbackGateDown();                             
                                
                                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();

                                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoPZ_" + channelNo, stateEventArgs.carNo);
                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                
                                return;
                            }
                            else
                            {
                                //扫到其他卡，异常，不提示 ，继续扫卡
                                tipMsg(v_resMsg);
                                idle(1);

                                //暂时将错误的卡的EPC插入错卡列表里
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
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

            if (getWeigth() < WEIGHT_LINE)
            {
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                return;
            }


            //判断红外对射，分4轴车和6轴车（0:4轴  1:6轴）
            if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "inRadiation"))
                || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "outRadiation")))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                tipMsg("车辆位置不正确");
                idle(2);
                return;
            }


            //汽车在磅秤上,连续10秒的重量平均值
            Boolean isSucc = true;
            int beforeWeight = getWeigth();
            for (int i = 0; i < 3; i++)
            {
                idle(1);
                int nowWeigth = getWeigth();
                //判断两边红外对射是否没有挡住,且重量变化在范围内
                if (Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "inRadiation"))
                        && Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "outRadiation"))
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

                
            try
            {
                dataBaseTool.hp_after_process_xw(Commons.getDcValue(ctlConfig, "currentFlowId"), stateEventArgs.cardID, Convert.ToString(stateEventArgs.carWeight), out v_resCode, out v_resMsg);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarPzXW), e.StackTrace);
                LogTool.WriteLog(typeof(CarPzXW), "数据库调用失败：" + e.Message);
                tipMsg("数据库调用失败");
                idle(2);
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                return;
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_pzQty", Convert.ToString(stateEventArgs.carWeight));
            double netQty = Convert.ToDouble(stateEventArgs.carMzWeight) - Convert.ToDouble(stateEventArgs.carWeight) - Convert.ToDouble(stateEventArgs.carKdWeight);
            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_netQty", Convert.ToString(netQty));


            //调用校验存储过程成功
            if ("1000".Equals(v_resCode))
            {
                tipMsg("皮重    " + stateEventArgs.carWeight / 1000 + "吨");
                writeMointorSingle("皮重(千克)", Convert.ToString(stateEventArgs.carWeight));

                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightMZ_" + channelNo, stateEventArgs.carMzWeight);
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, Convert.ToString(stateEventArgs.carWeight));
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightKD_" + channelNo, stateEventArgs.carKdWeight);
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightJZ_" + channelNo, Convert.ToString(netQty));

                actionFrontGateUp();//抬道闸
                actionLedGreen();
                tipMsg("计量完成请驶离");
                idle(3);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                return;
            }
            else
            {
                tipMsg(v_resMsg);
                idle(3);
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

            if (getWeigth() < WEIGHT_LINE)
            {
                idle(10);
                resetAndToNextSetp(null, Commons.STEP.IDLE);

                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightMZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightPZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightKD_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightJZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoPZ_" + channelNo, "");
            }
            else
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;//继续等待出厂结束
                idle(1);
            }
        }



        public override void actionFrontGateUp()
        {
            LogTool.WriteLog(typeof(CarPzXW), "抬前道闸");
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.frontGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                    Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelPZ_" + channelNo, "0");
                }
            }

        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public override void actionFrontGateDown()
        {
            LogTool.WriteLog(typeof(CarPzXW), "落前道闸");
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.frontGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                    Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelPZ_" + channelNo, "1");
                }
            }
            
        }



        /// <summary>
        ///  抬起车辆后方道闸
        /// </summary>
        public override void actionBackGateUp()
        {
            LogTool.WriteLog(typeof(CarPzXW), "抬后道闸");
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.backGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelMZ_" + channelNo, "0");
        }

        /// <summary>
        ///  落下车辆后方道闸
        /// </summary>
        public override void actionbackGateDown()
        {
            LogTool.WriteLog(typeof(CarPzXW), "落后道闸");
            var enums = Enum.GetValues(modbusPointAddress);
            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.backGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                    idle(1);
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelMZ_" + channelNo, "1");
        }



        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            LogTool.WriteLog(typeof(CarPzXW), "触发红灯");
            modbus.setCommand(Convert.ToString((int)(modbusPoint_xwPz.lightGreenOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_xwPz.lightRedOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsMZ_" + channelNo, "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            LogTool.WriteLog(typeof(CarPzXW), "触发绿灯");
            modbus.setCommand(Convert.ToString((int)(modbusPoint_xwPz.lightRedOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_xwPz.lightGreenOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsMZ_" + channelNo, "1");
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

        /// <summary>
        /// 
        /// </summary>
        /// <param name="msg"></param>
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowPZ_" + channelNo, msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                actiomLedShow(msg);
                actionVoice(msg);
                LogTool.WriteLog(typeof(BusinessBase), "tipMsg=" + msg);
            }
        }


        // <summary>
        /// 播放语音
        /// </summary>
        /// <param name="text">语音文本</param>
        public override void actionVoice(string text)
        {
            base.actionVoice(text);
        }

    }
}
