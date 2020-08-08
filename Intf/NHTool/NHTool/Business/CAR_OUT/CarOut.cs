using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Windows.Forms;

namespace NHTool.Business.CAR_OUT
{
    public class CarOut:BusinessBase
    {
        

        /// <summary>
        /// 主体程序流转，通过switch+stateEventArgs进行切换
        /// </summary>
        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.nextStep = Commons.STEP.IDLE;

            if (initial()) //成功初始化
            {
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
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(20); break;
                                case Commons.STEP.CAR_OUT: checkCarOutSign(); break;
                                case Commons.STEP.CAR_OutFinish: carOutStep(); break;
                                default: idle(1); break;
                            }
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarOut), "主循环异常:" + e.Message);
                        }
                    }
                })).Start();
            }
        }

        /// <summary>
        /// 汽车调度，空闲时，持续判断 入红外对射被挡住超2秒，则认为汽车开始入厂
        /// </summary>
        public virtual void carIdleStep()
        {
            restData();
          
            if (!stateEventArgs.nextStep.Equals(stateEventArgs.currentStep))
            {
                tipMsg("欢迎光临可以出厂");
                restData();
            }

            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                tipMsg("汽车驶入开始扫卡");
            }
        }

       
       

        /// <summary>
        /// 汽车调度，称重或回皮环节的获取车卡
        /// </summary>
        /// <param name="seconds">秒</param>
        /// <param name="czType">类型</param>
        public virtual void getCarNoStep(int seconds)
        {
            tipMsg("正在扫描车卡");

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
                idle(1);
                if (readRfidDevice.RealTimeEpcTag.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
                {
                    foreach (var epcStr in readRfidDevice.RealTimeEpcTag)//检查缓存到的所有的EPC卡号
                    {
                        if (!readRfidDevice.errorEpcTag.Contains(epcStr))//这些EPC卡号不存在已有的错误列表里
                        {
                            writeMointorSingle("车卡号", epcStr);
                            stateEventArgs.cardID = epcStr;
                            Boolean invokeRet = invokeHpBefore();

                            if (invokeRet)
                            {
                                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                                readRfidDevice.stop_read_RFID_Info();
                                readRfidDevice.clearEcpTagList();

                                actionFrontGateUp();
                                actionLedGreen();
                                writeMointorSingle("车牌号", stateEventArgs.carNo);
                                tipMsg(stateEventArgs.carNo + "扫卡成功");
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

        public virtual Boolean invokeHpBefore()
        {
            Boolean ret = false;
            JObject retJson = new JObject();
          
                //调用存储过程，查询车卡，返回车牌号
                JObject dataJson = new JObject();
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                retJson = httpDbTool.invokeProc("pk_car_schedule.cz_before_preProcess", dataJson);
                LogTool.WriteLog(typeof(CarOut), "invokeDbCzBefore,Mz,require dataJson=" + dataJson.ToString());
                LogTool.WriteLog(typeof(CarOut), "invokeDbCzBefore,Mz,retJson=" + retJson.ToString());
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
      
            
            return ret;
        }


      
        /// <summary>
        /// /汽车调度，称重或回皮结束
        /// </summary>
        /// <param name="czType"></param>
        public virtual void weightFinishStep(String czType)
        {
            //调用存储过程 
            JObject retJson = new JObject();
            JObject dataJson = new JObject();
            if ("MZ".Equals(czType))
            {
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));
                retJson = httpDbTool.invokeProc("pk_car_schedule.cz_after_process", dataJson);
            }
            else if ("PZ".Equals(czType))
            {
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));
                retJson = httpDbTool.invokeProc("pk_car_schedule.hp_after_process", dataJson);
            }
            LogTool.WriteLog(typeof(CarOut), "invokeDbCzFinish,Mz,require dataJson=" + dataJson.ToString());
            LogTool.WriteLog(typeof(CarOut), "invokeDbCzFinish,Mz,retJson=" + retJson.ToString());
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


        public void checkCarOutSign()
        {
            if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "正在离开");
                stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
            }
        }





        /// <summary>
        /// 汽车调度，称重离开道闸
        /// </summary>
        public virtual void carOutStep()
        {
            if (!isRadiationBolckOk("出口处红外", "outRadiation", 2))
            {
                tipMsg(stateEventArgs.carNo + "已经离开");
                stateEventArgs.nextStep = Commons.STEP.IDLE;
               
            }
        }
    }
}
