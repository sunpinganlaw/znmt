using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Windows.Forms;
using System.Threading;
using System.Media;
using System.IO;
namespace NHTool.Business.CAR_CZ.CAR_MZ
{
    class CarMzKC:CarCzBusiness
    {

        private DataBaseTool dataBaseTool = null;
        string v_resCode = "";
        string v_resMsg = "";
        string v_carId = "";
        string v_mzQty = "";
        string v_kdQty = "";

        public enum modbusPoint_kc : int
        {
            inRadiation = 1,
            outRadiation = 2,
            frontRadiation = 3,
            backRadiation = 5,
            frontGateUpOut = 18,
            frontGateDownOut = 19,
            backGateUpOut = 22,
            backGateDownOut = 20,
            lightRedOut = 17
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
                LogTool.WriteLog(typeof(CarMzKC), "亚当模块初始化失败");
                MessageBox.Show("亚当模块初始化失败");
            }

            if (initiRfidDevice(ctlConfig["RFID_IP"], ctlConfig["RFID_PORT"], ctlConfig["RFID_ANT_PORT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKC), "前RFID初始化失败");
                MessageBox.Show("RFID初始化失败");
            }

            if (initiVoiceDecice())
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKC), "声音模块初始化失败");
                MessageBox.Show("声音模块初始化失败");
            }

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKC), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

            if (initiSerialPortDevice(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]))
            {
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarMzKC), "地磅串口初始化失败");
                MessageBox.Show("地磅串口初始化失败");

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
            if (text.StartsWith("一车一杆严禁冲卡")) 
            {

                wavFiname = "一车一杆严禁冲卡.wav";
            }

            if (text.StartsWith("请停车称重")) 
            {

                wavFiname = "汽车上衡.wav";
            }
            if (text.StartsWith("正在扫描车卡"))
            {

                wavFiname = "正在检查卡片.wav";
            }
            if (text.StartsWith("正在扫描车卡"))   
            {

                wavFiname = "正在检查卡片.wav";
            }
             if (text.StartsWith("正在称重保持稳定"))     
            {

                wavFiname = "正在称重.wav";
            }

            if (text.StartsWith("称量完成请下磅"))     
            {

                wavFiname = "称重完成.wav";
            }

            
            if(!wavFiname.Equals(""))
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
            if (tcpclient != null)
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

            if (tcpclient != null)
            {
                tcpclient.HostAddress = "192.168.1.13";
                tcpclient.Port = 6001;
                if (tcpclient.IsConnected)
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
        


     /// <summary>
     ///  主体程序流转，通过switch+stateEventArgs进行切换
     /// </summary>
        public override void mainProcess()
        {
            //初始为空闲环节
            stateEventArgs.nextStep = Commons.STEP.IDLE;

            if (initial()) //成功初始化
            {
                actionbackGateDown();
                new Thread(new ThreadStart(delegate
                {
                    //主循环开始
                    while (true)
                    {
                        try
                        {
                            writeMointorSingle("currentStep", stateEventArgs.nextStep.ToString());
                            LogTool.WriteLogInfo(typeof(CarMzKC), "currentStep=" + stateEventArgs.nextStep.ToString());
                            //根据类全局变量stateEventArgs的step，判断下一步进行的分支环节
                            switch (stateEventArgs.nextStep)
                            {
                                case Commons.STEP.IDLE: carIdleStep(); break;
                                case Commons.STEP.CAR_GetCarNo: getCarNoStep(40, "MZ"); break;
                                case Commons.STEP.CAR_ReadyWeight: readyWeightStep(3); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_Weighting: weightingStep(); break;
                                case Commons.STEP.CAR_WeightFinish: weightFinishStep("MZ"); break;//继承时，具体重载指定
                                case Commons.STEP.CAR_OUT: carOutStep(); break;
                                default: idle(1); break;
                            }
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(CarMzKC), "主循环异常:" + e.Message);
                        }
                    }
                })).Start();
            }
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



        public override Boolean invokeDbCzBefore(string czType)
        {
            Boolean ret = false;
            JObject retJson = new JObject();
            if ("MZ".Equals(czType))
            {
                //调用存储过程，查询车卡，返回车牌号
                JObject dataJson = new JObject();
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                //retJson = httpDbTool.invokeProc("pk_car_schedule.cz_before_preProcess", dataJson);

                dataBaseTool.cz_before_preProcess(Commons.getDcValue(ctlConfig, "currentFlowId"), Convert.ToString(stateEventArgs.cardID), out v_carId, out v_resCode, out v_resMsg);



                LogTool.WriteLog(typeof(CarMzKC), "invokeDbCzBefore,Mz,require dataJson=" + dataJson.ToString());
          
                //调用校验存储过程成功
                if (v_resMsg != "err" && v_resCode == "1000")
                {
                    stateEventArgs.carNo = v_carId;
                    ret = true;
                }
                else
                {
                    stateEventArgs.actionResultMsg = v_resMsg;

                }
            }
            else if ("PZ".Equals(czType))
            {
                //调用存储过程，查询车卡，返回车牌号
                JObject dataJson = new JObject();
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
               

                dataBaseTool.hp_before_preProcess(Commons.getDcValue(ctlConfig, "currentFlowId"), Convert.ToString(stateEventArgs.cardID), out v_mzQty, out v_kdQty, out v_carId, out v_resCode, out v_resMsg);

                LogTool.WriteLog(typeof(CarMzKC), "invokeDbCzBefore,Pz,require dataJson=" + dataJson.ToString());
               

                //调用校验存储过程成功
                if (v_resMsg != "err" && v_resCode == "1000")
                {
                    stateEventArgs.carNo = v_carId;
                    ret = true;
                }
                else
                {
                    stateEventArgs.actionResultMsg = v_resMsg;
                }
            }
            return ret;
        }


        /// <summary>
        /// /汽车调度，称重或回皮结束
        /// </summary>
        /// <param name="czType"></param>
        public override void weightFinishStep(String czType)
        {
            //调用存储过程 
            JObject retJson = new JObject();
            JObject dataJson = new JObject();
            if ("MZ".Equals(czType))
            {
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));

               
                dataBaseTool.cz_after_process(Commons.getDcValue(ctlConfig, "currentFlowId"), Convert.ToString(stateEventArgs.cardID), Convert.ToString(stateEventArgs.carWeight), out v_resCode, out v_resMsg);
                LogTool.WriteLogInfo(typeof(CarMzKC), "invokeDbCzFinish,Mz,require dataJson=" + dataJson.ToString());
            }
            else if ("PZ".Equals(czType))
            {
                dataJson.Add("cardId", Convert.ToString(stateEventArgs.cardID));
                dataJson.Add("flowId", Commons.getDcValue(ctlConfig, "currentFlowId"));
                dataJson.Add("realQty", Convert.ToString(stateEventArgs.carWeight));

       
                dataBaseTool.hp_after_process(Commons.getDcValue(ctlConfig, "currentFlowId"), Convert.ToString(stateEventArgs.cardID), Convert.ToString(stateEventArgs.carWeight), out v_resCode, out v_resMsg);
                LogTool.WriteLogInfo(typeof(CarMzKC), "invokeDbCzFinish,Pz,require dataJson=" + dataJson.ToString());
            }

           
        
            //调用校验存储过程成功
            if (v_resMsg != "err" && v_resCode == "1000")
            {
                tipMsg("称量完成请下磅");
                actionFrontGateUp();//抬道闸
                actionLedGreen();
                stateEventArgs.nextStep = Commons.STEP.CAR_OUT;
                return;
            }
            else
            {
                tipMsg(stateEventArgs.carNo + v_resMsg);
                idle(3);
            }
        }


    }
}
