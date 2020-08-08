using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Threading;

namespace NHTool.Business.Car_IN
{
    /**
     * 汽车入厂基类，所有在入厂环节公共的方法都写在此类
     */
    abstract class CarInBusiness : BusinessBase
    {
        //无initial方法和 mainProcess方法

        /**
         * 主体程序流转，通过switch+stateEventArgs进行切换
         * 扫卡
         * 调用存储过程校验
         * 卡号，车卡等显示到界面
         * 切换到等待流程
         * 
         * 页面点击确定，调用存储过程成功后，切换扫卡环节
         */

        /**
         * 汽车调度，空闲时，持续判断 入红外对射被挡住超2秒，则认为汽车开始入厂
         * @param  
         * @return             
         */
        public virtual void carIdleStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                recoverDeviceState();
                stateEventArgs.currentStep = stateEventArgs.nextStep;
                stateEventArgs.currentChannel = "1";
                tipMsg("欢迎光临停车刷卡");
                Dictionary<String, String> rfid_result = readRfidDevice.read_RFID_Info();
                if (rfid_result[Commons.RES_CODE].Equals("0"))//成功启动天线读取车卡
                {
                    stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                }
            }
            /*
            if (isRadiationBolckOk("进口处红外", "inRadiation", 2))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
                tipMsg("汽车驶入采样区域开始扫卡");
            }*/
        }

        /**
           入厂环节的获取车卡
         * 如果车卡异常，还需要将异常的车卡写入到errlist中，方便进行排错
         * @param  
         * @return             
         */
        public virtual void getCarNoStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            stateEventArgs.stepExcuteCount++;
            if (readRfidDevice.epcTag2AntId.Count > 0)//天线的缓存列表里是否有缓存到的EPC卡
            {
                foreach (var epcStr in readRfidDevice.epcTag2AntId)//检查缓存到的所有的EPC卡号
                {
                    string scanEpcTag = epcStr.Key;
                    if (!readRfidDevice.errorEpcTag.Contains(scanEpcTag))//这些EPC卡号不存在已有的错误列表里
                    {
                        writeMointorSingle("车卡号", scanEpcTag);
                        stateEventArgs.cardID = scanEpcTag;
                        string tempCarId = "";
                        if (checkRCBeforeCardId(scanEpcTag, out tempCarId))
                        {
                            stateEventArgs.carNo = tempCarId;
                            stateEventArgs.stepExcuteCount = 0;
                            stateEventArgs.currentChannel = getChannelNum(epcStr.Value);
                            readRfidDevice.stop_read_RFID_Info();
                            readRfidDevice.clearEcpTagList();
                            Commons.putDcValue(ref dictionaryUseInForm, "carId", stateEventArgs.carNo);
                            Commons.putDcValue(ref dictionaryUseInForm, "cardId", stateEventArgs.cardID);
                            Commons.putDcValue(ref dictionaryUseInForm, "flowId", "RC" + stateEventArgs.currentChannel);
                            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "1");
                            deviceTag = "RC" + stateEventArgs.currentChannel;
                            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", stateEventArgs.carNo);
                            stateEventArgs.nextStep = Commons.STEP.CAR_WaitSelectStep;
                            tipMsg(stateEventArgs.carNo + "下车登记");
                            deviceAction(stateEventArgs.currentChannel, "LedGreen");
                            deviceAction(stateEventArgs.currentChannel, "GateUp"); 
                            return;
                        }
                        else
                        {
                            //tipMsg(stateEventArgs.actionResultMsg);
                            readRfidDevice.InsertEpcTagList(ref readRfidDevice.errorEpcTag, scanEpcTag);//将存储过程检测到的废卡的EPC插入错卡列表里                         
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
                    resetAndToNextSetp(null, Commons.STEP.IDLE);
                }
            }

        }

        public virtual bool checkRCBeforeCardId(string epcId, out string carId)
        {
            carId = "1";
            return false;
        }

        public virtual void carWaitSelectStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            if (isStopWaiting)
            {
                isStopWaiting = false;
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "0");
            }
        }

        public virtual void checkCarOutSign()
        {
            //if (isRadiationBolckOk("出口处红外", "outRadiation", 2))
            //{
            //    tipMsg(stateEventArgs.carNo + "正在离开采样区域");
            //    stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
            //}
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            deviceAction(stateEventArgs.currentChannel, "GateUp");
            tipMsg(stateEventArgs.carNo + "正在离开采样区域");
            stateEventArgs.nextStep = Commons.STEP.CAR_OutFinish;
            idle(30);
        }

        //在前一步的【扫到矿卡车卡后，调用存储过程进行校验，成功后】，再调用rc_after_process过程，成功后，提示入厂，抬道闸，切换CAR_OUT步骤
        public virtual void carInFinishStep(int seconds)
        {
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
            dataJson.Add("coalNo", Convert.ToString(stateEventArgs.coalCardInfo.coalNo));
            dataJson.Add("vendorNo", Convert.ToString(stateEventArgs.coalCardInfo.vendorNo));
            dataJson.Add("ticketQty", Convert.ToString(stateEventArgs.coalCardInfo.ticketQty));
            dataJson.Add("trainNo", Convert.ToString(stateEventArgs.coalCardInfo.trainNo));
            dataJson.Add("carrierNo", Convert.ToString(stateEventArgs.coalCardInfo.carrierNo));
            dataJson.Add("ticketNo", Convert.ToString(stateEventArgs.coalCardInfo.ticketNo));
            dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.rc_after_process", dataJson);

            //调用校验存储过程成功
            Boolean isSccInvokeDb = false;
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                tipMsg(stateEventArgs.carNo + "请入厂");
                actionFrontGateUp();//抬道闸
                isSccInvokeDb = true;
            }

            if (!isSccInvokeDb)
            {
                tipMsg("入厂信息保存失败");
                idle(3);
                resetAndToNextSetp(null, Commons.STEP.IDLE);
            }
        }

        /**
         * 汽车调度，进厂离开道闸
         * @param  
         * @return             
         */
        public virtual void carOutStep()
        {
            if (!stateEventArgs.currentStep.Equals(stateEventArgs.nextStep))
            {
                stateEventArgs.currentStep = stateEventArgs.nextStep;
            }
            //if (!isRadiationBolckOk("入厂-出口处红外对射", Commons.outRadiation, 2))
            //{
            //    resetAndToNextSetp(stateEventArgs.carNo + "完成入厂", Commons.STEP.IDLE);
            //    idle(8);//为安全起见，再等待一段时间再落道闸，本身道闸下落也比较慢
            //    recoverDeviceState();
            //}
            Commons.putDcValue(ref dictionaryUseInForm, "IsFlowFinish", "1");
            resetAndToNextSetp(stateEventArgs.carNo + "完成入厂", Commons.STEP.IDLE);
            Commons.putDictionaryData(ref realDataDictionary, "01#" + deviceTag + "_carNo", "");
            idle(20);//为安全起见，再等待一段时间再落道闸，本身道闸下落也比较慢
        }

        //对于克拉玛入厂具体多通道公用一个亚当模块的额情形
        public virtual void deviceAction(string ChannelNum, string actionType)
        {

        }

        //所有设备回到初始状态
        public virtual void recoverDeviceState()
        {

        }

        public string getChannelNum(string num)
        {
            string channelNum;
            if (num.Equals("1") || num.Equals("2"))
            {
                channelNum = "2";
            }
            else if (num.Equals("3") || num.Equals("4"))
            {
                channelNum = "1";
            }
            else
            {
                channelNum = "1";
            }
            return channelNum;
        }
    }
}

        
