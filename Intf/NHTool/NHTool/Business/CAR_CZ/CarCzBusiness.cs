using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Windows.Forms;
using System.Data.OleDb;

namespace NHTool.Business.CAR_CZ
{
    abstract class CarCzBusiness : BusinessBase
    {
        public static int WEIGHT = 1000;//1000公斤，磅秤判断是否来车的阈值重量  50是test
        public static int MIN_CHANGE_WEIGHT = 50;//公斤，连续两次重量的变化值的最大阈值


        public CarCzBusiness()
        {
            if (ctlConfig.ContainsKey("RUN_MODE") && "TEST".Equals(ctlConfig["RUN_MODE"]))
            {
                //调试模式，人站上去就可以调试
                WEIGHT = 40;//公斤，磅秤判断是否来车的阈值重量
                MIN_CHANGE_WEIGHT = 40;//公斤，连续两次重量的变化值的最大阈值
            }
        }
      

        /// <summary>
        /// 汽车调度，空闲时，持续判断 入红外对射被挡住超2秒，则认为汽车开始入厂
        /// </summary>
        public virtual void carIdleStep()
        {
            //2)获取通道状态，持续2秒, 但目前都改为根据重量，不判断红外对射了 
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceScanCard", "2");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceBalance", "2");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", "");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_total", "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_mzQty", "0");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_pzQty", "0");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_netQty", "0");
                stateEventArgs.currentChannel = "1";
            }
            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                if (getWeigth() > WEIGHT)
                {
                    tipMsg("汽车驶入衡器开始扫卡");
                    stateEventArgs.nextStep = Commons.STEP.CAR_ReadyWeight;
                }
                else
                {
                    restData();
                    stateEventArgs.nextStep = Commons.STEP.IDLE;
                    idle(1);
                    tipMsg("一车一杆严禁冲卡");
                }
            }
        }

        /// <summary>
        /// 汽车调度，准备称重,持续2秒，重量超过Commons.WEIGHT，表示车上榜
        /// </summary>
        /// <param name="seconds">持续时间(秒)</param>
        public virtual void readyWeightStep(int seconds)
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
               
                tipMsg("请停车称重");
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
            }
        }

        public int getWeigth()
        {
            int weigth = 0;
            TimeSpan interval = DateTime.Now - lastGetWegihtTime;
            if (interval.Seconds > 3)
            {
                weigth = Commons.ERR_WEIGHT;
                LogTool.WriteLog(typeof(CarCzBusiness), "getWeight time exipred ," + interval.Seconds+"s");
            }
            else
            {
                try
                {
                    weigth = Convert.ToInt32(Commons.getDcValue(realDataDictionary, "carWeight"));
                }
                catch (Exception e)
                {
                    return Commons.ERR_WEIGHT;
                }
            }
            LogTool.WriteLogInfo(typeof(CarCzBusiness), "weigth=" + weigth);
            return weigth;
        }

        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public virtual void getCarNoStep(int seconds,string czType)
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("正在扫描车卡");
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
                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;
                            Boolean invokeRet = invokeDbCzBefore(czType);

                            if (invokeRet)
                            {
                                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();
                                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_total", "0");
                                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceScanCard", "2");
                                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceBalance", "0");
                                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", stateEventArgs.carNo);

                                actionFrontGateDown();
                                actionLedRed();
                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                tipMsg(stateEventArgs.carNo + "扫卡成功");
                                idle(15);//等待停车称重
                                return;
                            }
                            else
                            {
                                //扫到其他卡，异常，不提示 ，继续扫卡
                                tipMsg(stateEventArgs.actionResultMsg);
                                readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, epcStr);//将存储过程检测到的废卡的EPC插入错卡列表里                         
                            }
                        }
                    }
                }

            }

            //超时seconds扫不到车卡时，切换到idle
            tipMsg("没有扫描到车卡");
            resetAndToNextSetp(null, Commons.STEP.IDLE);
            idle(1);
        }

        public virtual Boolean invokeDbCzBefore(string czType)
        {
            Boolean ret = false;
            JObject retJson = new JObject();
            if ("MZ".Equals(czType))
            {
                //调用存储过程，查询车卡，返回车牌号
                JObject dataJson = new JObject();
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                retJson = httpDbTool.invokeProc("pk_car_schedule.cz_before_preProcess", dataJson);
                LogTool.WriteLog(typeof(CarCzBusiness), "invokeDbCzBefore,Mz,require dataJson="+dataJson.ToString());
                LogTool.WriteLog(typeof(CarCzBusiness), "invokeDbCzBefore,Mz,retJson=" + retJson.ToString());
                //调用校验存储过程成功
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    stateEventArgs.carNo = getJsonValue(retJson, "carId");
                    ret = true;
                }
                else
                {
                    stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
                }
            }
            else if ("PZ".Equals(czType))
            {
                //调用存储过程，查询车卡，返回车牌号
                JObject dataJson = new JObject();
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                retJson = httpDbTool.invokeProc("pk_car_schedule.hp_before_preProcess", dataJson);
                LogTool.WriteLog(typeof(CarCzBusiness), "invokeDbCzBefore,Pz,require dataJson=" + dataJson.ToString());
                LogTool.WriteLog(typeof(CarCzBusiness), "invokeDbCzBefore,Pz,retJson=" + retJson.ToString());
                //调用校验存储过程成功
                if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
                {
                    stateEventArgs.carNo = getJsonValue(retJson, "carId");
                    stateEventArgs.carMzWeight = getJsonValue(retJson, "mzQty");
                    stateEventArgs.carKdWeight = getJsonValue(retJson, "kdQty");
                    ret = true;
                }
                else
                {
                    stateEventArgs.actionResultMsg = getJsonValue(retJson, "logicRetMsg");
                }
            }
            return ret;
        }


        /// <summary>
        /// 汽车调度，检查是否满足称重条件
        ///如果车卡异常，还需要将异常的车卡写入到errlist中，方便进行排错
        /// </summary>
        public virtual  void weightingStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }

            if (getWeigth() < WEIGHT)
            {
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                return;
            }

            if (Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "inRadiation"))
                || Commons.signalON.Equals(Commons.getDcValue(realDataDictionary, "outRadiation")))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
                tipMsg("车辆位置不正确");
                idle(3);
                return;
            }

            //汽车在磅秤上,连续10秒的重量平均值
            tipMsg("正在称重保持稳定");
            Boolean isSucc = true;
            int beforeWeight = getWeigth();
            for (int i = 0; i < 5; i++)
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
                }else 
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

                //记录数据到本地数据库xieyt
                recordToLocalDB(stateEventArgs.cardID, stateEventArgs.carNo, Convert.ToString(beforeWeight));
            }
            else
            {
                tipMsg(stateEventArgs.carNo+"重量不稳");
                idle(3);
                stateEventArgs.nextStep = Commons.STEP.CAR_Weighting;
            }            
        }


        /// <summary>
        /// 称重数据记录到本地数Access据库,不需要记录的电厂就不配置AccessDBStr
        /// </summary>
        /// <param name="cardId">车卡号</param>
        /// <param name="carId">车牌号</param>
        /// <param name="weight">重量数据</param>
        public void recordToLocalDB(string cardId, string carId, string weight)
        {
            //没有配置AccessDBStr的就不记录了
            if (ctlConfig.ContainsKey("AccessDBStr")) 
            {
                OleDbParameter[] param = { new OleDbParameter("@cardId", cardId), new OleDbParameter("@carId", carId), new OleDbParameter("@weight", weight) };

                string sql = "insert into car_weight_local(card_id, car_id, weight, weight_time) values(@cardId, @carId, @weight, Now())";
                
                AccessDataBaseTool adbt = new AccessDataBaseTool(Commons.getDcValue(ctlConfig, "AccessDBStr"));
                adbt.ExecuteNonQuery(sql, param);
            }
        }

        /// <summary>
        /// 称重数据记录到本地数Access据库,不需要记录的电厂就不配置AccessDBStr
        /// </summary>
        /// <param name="cardId">车卡号</param>
        /// <param name="carId">车牌号</param>
        /// <param name="weight">重量数据</param>
        public void recordToLocalDB(string cardId, string carId, string weight, string deviceNo)
        {
            //没有配置AccessDBStr的就不记录了
            if (ctlConfig.ContainsKey("AccessDBStr"))
            {
                OleDbParameter[] param = { new OleDbParameter("@cardId", cardId), 
                                            new OleDbParameter("@carId", carId), 
                                            new OleDbParameter("@weight", weight), 
                                            new OleDbParameter("@deviceNo", deviceNo) };

                string sql = "insert into car_weight_local(card_id, car_id, weight, weight_time, device_no) " +
                             "values(@cardId, @carId, @weight, Now(), @deviceNo)";

                AccessDataBaseTool adbt = new AccessDataBaseTool(Commons.getDcValue(ctlConfig, "AccessDBStr"));
                adbt.ExecuteNonQuery(sql, param);
            }
        }

        /// <summary>
        /// /汽车调度，称重或回皮结束
        /// </summary>
        /// <param name="czType"></param>
        public virtual void weightFinishStep(String czType)
        {
            //调用存储过程 
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            JObject retJson = new JObject();
            JObject dataJson = new JObject();
            if ("MZ".Equals(czType))
            {
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));
                retJson = httpDbTool.invokeProc("pk_car_schedule.cz_after_process", dataJson);
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_mzQty", Convert.ToString(stateEventArgs.carWeight));
            }
            else if ("PZ".Equals(czType))
            {
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));
                retJson = httpDbTool.invokeProc("pk_car_schedule.hp_after_process", dataJson);
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_pzQty", Convert.ToString(stateEventArgs.carWeight));
                double netQty = Convert.ToDouble(stateEventArgs.carMzWeight) - Convert.ToDouble(stateEventArgs.carWeight) - Convert.ToDouble(stateEventArgs.carKdWeight);
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_netQty", Convert.ToString(netQty));
            }
            LogTool.WriteLog(typeof(CarCzBusiness), "invokeDbCzFinish,Mz,require dataJson=" + dataJson.ToString());
            LogTool.WriteLog(typeof(CarCzBusiness), "invokeDbCzFinish,Mz,retJson=" + retJson.ToString());
            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                tipMsg("称量完成请下磅");
                actionFrontGateUp();//抬道闸
                actionLedGreen();
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                return;
            }
            else
            {
                tipMsg(stateEventArgs.carNo + getJsonValue(retJson, "logicRetMsg"));
                idle(3);
            }
        }

        /// <summary>
        /// 汽车调度，称重离开道闸
        /// </summary>
        public virtual void carOutStep()
        {
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (getWeigth() < WEIGHT)
            {
                idle(6);//为安全起见，再等待一段时间再落道闸，本身道闸下落也比较慢               
                actionFrontGateDown();
                actionLedRed();
                resetAndToNextSetp(null, Commons.STEP.IDLE);
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_deviceBalance", "2");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_total", "1");
                Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", "");
            }
            else
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;//继续等待出厂结束
                idle(1);
            }
        }



        /// <summary>
        /// 校验抓拍到的车牌，连续3次一样，则认为是抓拍到的车牌，与扫描卡号的做比较
        /// 校验通过true， 否则false
        /// </summary>
        /// <param name="scanCarNo"></param>
        /// <returns></returns>
        public bool checkCarNoByCamera(string scanCarNo)
        {
            bool result = false;
            int sameCarNoCounter = 0;

            string cameraCarNo = ""; //本次抓拍到的车牌号
            string lastCameraCarNo = ""; //上次抓拍到的车牌号
            for (int i = 1; i <= 12; i++)
            {
                cameraCarNo = getPlateInfo();
                tipMsg("抓拍车牌" + cameraCarNo.Substring(1));
                writeMointorSingle("抓拍车牌", cameraCarNo);

                //拍到车牌了
                if (cameraCarNo != null && !"".Equals(cameraCarNo) && !"无车牌".Equals(cameraCarNo))
                {
                    //上次车牌还没有，说明是第一次,则记录下
                    if ("".Equals(lastCameraCarNo))
                    {
                        lastCameraCarNo = cameraCarNo;
                    }

                    //与同，则计1次，否则重来
                    if (cameraCarNo.Equals(lastCameraCarNo))
                    {
                        sameCarNoCounter++;
                    }
                    else
                    {
                        lastCameraCarNo = cameraCarNo;
                        sameCarNoCounter = 0;//重新计数
                    }

                    //连续3次抓拍车号相同，则就认为是这个车牌了
                    if (sameCarNoCounter >= 3)
                    {
                        //与扫描车卡的车牌号吻合，则就校验通过
                        if (cameraCarNo.Substring(1).Equals(scanCarNo))
                        {
                            result = true;
                            break;
                        }
                    }
                }

                //1秒拍一次
                idle(1);
            }

            return result;
        }
    }
}
