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

namespace NHTool.Business.CAR_CZ.CAR_MZ
{
    class CarMzZJ : CarCzBusiness
    {
        public string channelNo;
        string strIp = null;
        uint fonSize = 20;
        string carType = "";//车型    0：普通4轴车  1：半挂6轴车

        //织金回皮的点，需要现场确认
        public enum modbusPoint_zjMz : int
        {
            inRadiation = 1,  //入口红外
            //宋老师：  4轴车用BC     6轴车用AC

            /*
            |  |
            B  C
            |  |
            A  |
            |  |    -> 出车方向
            */

            outRadiation = 2, //出口A红外(里下)
            outRadiationB = 3, //出口B红外（里上）
            outRadiationC = 4, //出口C红外（外）

            lightRedOut = 17, //红
            lightGreenOut = 18, //绿
            frontGateUpOut = 19, //抬道闸
            frontGateDownOut = 20 //落道闸
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();

            bool initResult = false;
            int initCount = 0;
            int expectDeviceCount = 5;//期望初始化成功N个设备


            //海康车牌识别器
            if (initCameraDevice(ctlConfig["CAMERA_IP"], 8000, "admin", ctlConfig["CAMERA_PWD"]))
            {
                initCount++;
                LogTool.WriteLog(typeof(CarMzZJ), "摄像头" + ctlConfig["CAMERA_IP"] + "初始化成功");
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzZJ), "摄像头初始化失败");
            }


            if (initiModbusDevice(ctlConfig["ADAM_IP"], ctlConfig["ADAM_PORT"], typeof(modbusPoint_zjMz)))
            {
                LogTool.WriteLog(typeof(CarMzZJ), "亚当模块初始化成功【IP：" + ctlConfig["ADAM_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzZJ), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }


            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                LogTool.WriteLog(typeof(CarMzZJ), "天线初始化成功【IP：" + ctlConfig["RFID_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzZJ), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }


            //初始化LED屏的参数
            strIp = ctlConfig["LED_IP"];
            fonSize = Convert.ToUInt32(ctlConfig["LED_FONT_SIZE"]);
            if (strIp != null && fonSize > 0)
            {
                LogTool.WriteLog(typeof(CarMzZJ), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzZJ), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }


            if (initiSerialPortDevice(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]))
            {
                LogTool.WriteLog(typeof(CarMzZJ), "地磅串口初始化成功！");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzZJ), "地磅串口初始化失败");
                MessageBox.Show("地磅串口初始化失败");
            }


            //启动接受控制测点的服务器
            httpToolCarControl = new HttpTool();
            httpToolCarControl.initHttpServer(this.processCarHttpRequestMethod, httpControlServerUrl);

            //测点皮重MZ1, MZ2
            channelNo = currentFlowId.Substring(2);
            deviceTag = "MZ" + channelNo;
            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarMzZJ), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzZJ), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
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
                weightData = Convert.ToInt32(fromByteString(parseData, 2, 6));

                //放入内存库
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
                            //LogTool.WriteLog(typeof(CarMzZJ), "currentStep=" + stateEventArgs.nextStep.ToString());
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_ReadyWeight: readyWeightStep(3); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(30, "MZ"); break;
                                case Commons.STEP.CAR_GetMineCard: getMineInfoStep(60); break; //获取矿卡信息
                                case Commons.STEP.CAR_Weighting: weightingStep(); break;
                                case Commons.STEP.CAR_WeightFinish: weightFinishStep("MZ"); break;//继承时，具体重载指定
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
        /// 汽车调度，空闲时，持续判断 入红外对射被挡住超2秒，则认为汽车开始入厂 xieyt
        /// </summary>
        public override void carIdleStep()
        {
            //2)获取通道状态，持续2秒, 但目前都改为根据重量，不判断红外对射了 
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;

                //重置数据，为扫卡称重做准备
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoMZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightCZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_mineMZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelMZ_" + channelNo, "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsMZ_" + channelNo, "1");
                stateEventArgs.currentChannel = channelNo;
                carType = "";
            }

            if (getWeigth() > WEIGHT)
            {
                tipMsg("停车扫卡");
                actionLedRed();
                stateEventArgs.nextStep = Commons.STEP.CAR_ReadyWeight;
            }
            else
            {
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                idle(5);
                tipMsg("减速上磅停车刷卡");
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
            writeMointorSingle("nowWegiht", stateEventArgs.carNo);
            for (int i = 0; i < seconds; i++)
            {
                idle(1);
                if (getWeigth() < WEIGHT)
                {
                    isBeginWeigth = false;
                    break;
                }
            }

            if (isBeginWeigth)
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
            }
            else
            {
                //重量突然不行，则重新开始
                resetAndToNextSetp(null, Commons.STEP.IDLE);
            }
        }



        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public override void getCarNoStep(int seconds, string czType)
        {
            LogTool.WriteLog(typeof(CarCzBusiness), "进入getCarNoStep");
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("正在扫描车卡");
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //先打开天线
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
            idle(1);

            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (!rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("天线打开异常");
                idle(2);
                return;
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceScanCard", "0");

            //天线打开成功，在seconds秒内，每秒试读取卡数据，并调用存储过程校验
            for (int i = 0; i < seconds; i++)
            {
                if (getWeigth() < WEIGHT)
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
                            LogTool.WriteLog(typeof(CarCzBusiness), "扫到的卡号：" + epcStr);
                            //判断卡号是否合法 0000 和ab00开头的是车卡
                            if (epcStr.Length != 24 || (!epcStr.StartsWith("0000") && !epcStr.StartsWith("ab00")))
                            {
                                LogTool.WriteLog(typeof(CarCzBusiness), "本次无效车卡：" + epcStr);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                                continue;
                            }

                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;
                            carType = "";

                            JObject retJson = invokeDBCheckCarCard();//校验车卡

                            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                            {
                                stateEventArgs.carNo = getJsonValue(retJson, "carId");//取出扫描到的车牌
                                
                                //摄像头抓拍校验车牌
                                tipMsg("扫卡车牌" + stateEventArgs.carNo);
                                idle(2);

                                //与摄像头抓拍的车牌进行核对
                                if (carNoCameraAvailable)
                                {
                                    if (checkCarNoByCamera(stateEventArgs.carNo))
                                    {
                                        tipMsg("车牌验证通过");
                                        idle(2);
                                    }
                                    else
                                    {
                                        tipMsg("车牌验证不通过");
                                        idle(2);
                                        resetAndToNextSetp(null, Commons.STEP.IDLE);
                                        return;
                                    }
                                }
                                
                                //前面校验通过，准备进入获取矿卡步骤
                                stateEventArgs.nextStep = Commons.STEP.CAR_GetMineCard;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();

                                
                                carType = getJsonValue(retJson, "carType");

                                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoMZ_" + channelNo, stateEventArgs.carNo);

                                writeMointorSingle("车型", carType.Equals("1")?"六轴车":"四轴车");
                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                tipMsg(stateEventArgs.carNo);
                                idle(2);//等待停车称重
                                return;
                            }
                            else
                            {
                                tipMsg("扫卡校验不通过");
                                idle(2);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                            }
                        }
                    }
                }
            }

            //超时seconds扫不到车卡时，切换到idle
            tipMsg("未扫到有效车卡");
            resetAndToNextSetp(null, Commons.STEP.IDLE);
            idle(3);
        }

        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public void getMineInfoStep(int seconds)
        {
            LogTool.WriteLog(typeof(CarCzBusiness), "进入getMineInfoStep");
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("正在扫描矿卡");
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //先打开天线
            Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
            idle(1);

            //启动天线读取车卡,打开失败，继续尝试打开天线，并提示
            if (!rfid_result[Commons.RES_CODE].Equals("0"))
            {
                tipMsg("天线打开异常");
                idle(3);
                return;
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceScanCard", "0");
            //天线打开成功，在seconds秒内，每秒试读取卡数据，并调用存储过程校验
            for (int i = 0; i < seconds; i++)
            {
                if (getWeigth() < WEIGHT)
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
                            //判断卡号是否合法 e200开头的是矿卡
                            if (epcStr.Length != 24 || !epcStr.StartsWith("e200"))
                            {
                                LogTool.WriteLog(typeof(CarCzBusiness), "本次无效矿卡：" + epcStr);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                                continue;
                            }

                            writeMointorSingle("矿卡号", epcStr);
                            stateEventArgs.mineCardID = epcStr;
                            JObject retJson = invokeDBCheckMineCard();//校验矿卡

                            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                            {
                                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();

                                stateEventArgs.mineName = getJsonValue(retJson, "mineName");
                                Commons.putDictionaryData(ref realDataDictionary, "01#qc_mineMZ_" + channelNo, stateEventArgs.mineName);

                                writeMointorSingle("矿名", stateEventArgs.mineName);
                                tipMsg(stateEventArgs.mineName);
                                idle(2);//等待停车称重
                                return;
                            }
                            else
                            {
                                idle(1);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);
                            }
                        }
                    }
                }
            }

            //超时seconds扫不到车卡时，切换到idle
            tipMsg("未扫到有效矿卡");
            resetAndToNextSetp(null, Commons.STEP.IDLE);
            idle(1);
        }


        /// <summary>
        /// 汽车调度，检查是否满足称重条件
        /// 如果车卡异常，还需要将异常的车卡写入到errlist中，方便进行排错
        /// </summary>
        public override void weightingStep()
        {
            LogTool.WriteLog(typeof(CarCzBusiness), "进入称重weightingStep");
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (getWeigth() < WEIGHT)
            {
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                return;
            }


            //判断红外对射，分4轴车和6轴车（0:4轴  1:6轴）
            if ("0".Equals(carType))
            {
                if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "inRadiation"))
                    || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "outRadiationB"))
                    || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "outRadiationC")))
                {
                    tipMsg("车辆位置不正确");
                    idle(3);
                    return;
                }
            }
            else if ("1".Equals(carType))
            {
                if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "inRadiation"))
                    || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "outRadiation"))
                    || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "outRadiationC")))
                {
                    tipMsg("车辆位置不正确");
                    idle(3);
                    return;
                }
            }
            


            //汽车在磅秤上,连续6秒的重量平均值
            Boolean isSucc = true;
            int beforeWeight = getWeigth();
            for (int i = 0; i < 6; i++)
            {
                idle(1);
                int nowWeigth = getWeigth();
                //判断两边红外对射是否没有挡住,且重量变化在范围内
                if (
                    Commons.signalOFF.Equals(Commons.getDcValue(realDataDictionary, "inRadiation"))
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

                tipMsg("重量" + beforeWeight);
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightCZ_" + channelNo, Convert.ToString(beforeWeight));
                idle(3);

                //CZ BEFORE，完成入厂
                if (invokeDBCzBefore())
                {
                    //记录数据到本地数据库
                    recordToLocalDB(stateEventArgs.cardID, stateEventArgs.carNo, Convert.ToString(beforeWeight), channelNo);
                    localWeightBackUp();
                }
                else
                {
                    tipMsg(stateEventArgs.actionResultMsg);
                    idle(3);
                    resetAndToNextSetp(null, Commons.STEP.IDLE);//重新来
                }
            }
            else
            {
                tipMsg("重量不稳");
                idle(3);
                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
            }
        }


        /// <summary>
        /// /汽车调度，称重或回皮结束
        /// </summary>
        /// <param name="czType"></param>
        public override void weightFinishStep(String czType)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            //调用cz after,完成称重，并分配卸煤沟
            if (invokeDBCzAfter())
            {
                string msg = stateEventArgs.carNo + "    ";
                msg = msg + "毛重：" + stateEventArgs.carWeight + " ";
                msg = msg + "待卸" + stateEventArgs.ditchCd + " 车序" + stateEventArgs.queueOrder;
                msg = msg + stateEventArgs.mineName;

                tipMsg(msg);
                actionFrontGateUp();//抬道闸
                idle(3);
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                return;
            }
            else
            {
                tipMsg(stateEventArgs.actionResultMsg);
                idle(3);
                resetAndToNextSetp(null, Commons.STEP.IDLE);//重新来
            }
        }


        /// <summary>
        /// 汽车调度，称重离开道闸，织金需要在这里调用分配卸煤沟
        /// </summary>
        public override void carOutStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (getWeigth() < WEIGHT)
            {
                idle(5);
                actionFrontGateDown();
                idle(3);
                actionLedGreen();//绿灯迎接下一辆车
                idle(5);
                resetAndToNextSetp(null, Commons.STEP.IDLE);

                //恢复重置测点
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_carNoMZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_weightCZ_" + channelNo, "");
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_mineMZ_" + channelNo, "");
            }
            else
            {
                idle(2);
            }
        }


        /// <summary>
        /// 调用数据库cz before业务逻辑处理
        /// </summary>
        /// <returns></returns>
        public Boolean invokeDBCzBefore()
        {
            Boolean res = false;
            JObject dataJson = new JObject();
            JObject retJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
            dataJson.Add("mineCardId", Convert.ToString(stateEventArgs.mineCardID));
            dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));


            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.cz_before_preProcess", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarMzZJ), "invokeDBCzBefore数据库调用失败：" + e.Message);
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
                stateEventArgs.mineName = getJsonValue(retJson, "mineName");
                res = true;
            }
            else
            {
                stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
            }
            return res;
        }


        /// <summary>
        /// 服务器再备份下，互查；项目合同里的要求
        /// </summary>
        /// <param name="weight"></param>
        /// <returns></returns>
        public void localWeightBackUp()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", stateEventArgs.cardID);
            dataJson.Add("carId", stateEventArgs.carNo);
            dataJson.Add("weight", Convert.ToString(stateEventArgs.carWeight));
            dataJson.Add("deviceNo", channelNo);
            dataJson.Add("weightTime", DateTime.Now.ToString("yyyyMMddHHmmss"));

            try
            {
                httpDbTool.invokeProc("local_weight_buckup", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzZJ), "localWeightBackUp数据库调用失败：" + e.Message);
            }
        }


        /// <summary>
        /// 调用数据库cz after业务逻辑处理
        /// </summary>
        /// <returns></returns>
        public Boolean invokeDBCzAfter()
        {
            Boolean res = false;
            JObject retJson = new JObject();
            JObject dataJson = new JObject();

            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("mineCardId", Convert.ToString(stateEventArgs.mineCardID));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
            dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));

            try
            {
                retJson = httpDbTool.invokeProc("pk_car_schedule.cz_after_process", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarMzZJ), "invokeDBCzAfter数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }

            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_mzQty", Convert.ToString(stateEventArgs.carWeight));

            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                stateEventArgs.ditchCd = getJsonValue(retJson, "ditchCd");
                stateEventArgs.queueOrder = getJsonValue(retJson, "order");
                res = true;
            }
            else
            {
                stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
            }
            return res;
        }


        /// <summary>
        /// 调用数据库校验车卡是否合法
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBCheckCarCard()
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));

            JObject retJson;
            try
            {
                retJson = httpDbTool.invokeProc("check_car_card", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarMzZJ), "invokeDBCheckCarCard数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }
            return retJson;
        }

        /// <summary>
        /// 调用数据库校验矿卡是否合法
        /// </summary>
        /// <returns></returns>
        public JObject invokeDBCheckMineCard()
        {
            JObject dataJson = new JObject();
            dataJson.Add("mineCardId", Convert.ToString(stateEventArgs.mineCardID));

            JObject retJson;
            try
            {
                retJson = httpDbTool.invokeProc("check_mine_card", dataJson);
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(CarMzZJ), e.StackTrace);
                LogTool.WriteLog(typeof(CarMzZJ), "invokeDBCheckMineCard数据库调用失败：" + e.Message);
                retJson = new JObject();
                retJson.Add("logicRetCode", "1999");
                retJson.Add("logicRetMsg", "数据库调用失败");
                retJson.Add("resMsg", "数据库调用失败");
                retJson.Add("resCode", "1");
            }
            return retJson;
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
        /// 织金因为没有喇叭，所以不播放声音
        /// </summary>
        /// <param name="msg"></param>
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                Commons.putDictionaryData(ref realDataDictionary, "01#qc_LEDShowMZ_" + channelNo, msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                try
                {
                    Onbon_LED.SendText(msg, strIp, fonSize);
                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(CarMzZJ), "发信息到LED异常：" + e.StackTrace);
                    LogTool.WriteLog(typeof(CarMzZJ), "发信息到LED异常：" + e.Message);
                }
            }
        }


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

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelMZ_" + channelNo, "0");
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

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_barrelMZ_" + channelNo, "1");
        }


        /// <summary>
        /// 红灯
        /// </summary>
        public override void actionLedRed()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjMz.lightGreenOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjMz.lightRedOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsMZ_" + channelNo, "0");
        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public override void actionLedGreen()
        {
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjMz.lightRedOut)), "False", Commons.modbusType.COIL_STATUS);
            idle(1);
            modbus.setCommand(Convert.ToString((int)(modbusPoint_zjMz.lightGreenOut)), "True", Commons.modbusType.COIL_STATUS);

            Commons.putDictionaryData(ref realDataDictionary, "01#qc_trafficLightsMZ_" + channelNo, "1");
        }
    }
}
