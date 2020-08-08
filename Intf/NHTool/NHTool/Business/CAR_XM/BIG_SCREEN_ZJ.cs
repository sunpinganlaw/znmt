using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using NHTool.Device.RFID;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Windows.Forms;
using NHTool.Device.LED;

namespace NHTool.Business.Car_XM
{
    class BIG_SCREEN_ZJ : BusinessBase
    {
        /// <summary>
        /// 主体程序流转，通过switch+stateEventArgs进行切换
        /// </summary>
        public override void mainProcess()
        {
            if (initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    HttpDbTool httpDbTool = new HttpDbTool();
                    while (true)
                    {
                        JObject dataJson = new JObject();

                        JObject retJson = null;
                        try
                        {
                            retJson = httpDbTool.invokeProc("pk_car_schedule.get_xm_screen_msg", dataJson);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(BIG_SCREEN_ZJ), e.StackTrace);
                            LogTool.WriteLog(typeof(BIG_SCREEN_ZJ), "获取大屏信息数据库调用失败：" + e.Message);
                        }

                        if (retJson != null)
                        {
                            /* TEST
                            tipMsg("  一    二    三    四    五    六   " +
                                " 11111 12345 12345 12345 12345 ABCD1 "+
                                " 22222 12345 12345 12345 12345 ABCD2 "+
                                " 33333 12345 12345 12345 12345 ABCD3 "+
                                " 44444 12345 12345 12345 12345 ABCD4 "+
                                " 55555 12345 12345 12345 12345 ABCD5 "+
                                " 66666 12345 12345 12345 12345 ABCD6 "+
                                " 77777 12345 12345 12345 12345 ABCD7 "+
                                " 88888 12345 12345 12345 12345 ABCD8 "
                                );
                            */
                            
                            tipMsg(getJsonValue(retJson, "msg"));
                            LogTool.WriteLog(typeof(BIG_SCREEN_ZJ), "发给大屏：" + getJsonValue(retJson, "msg"));
                        }
                        else
                        {
                            LogTool.WriteLog(typeof(BIG_SCREEN_ZJ), "未获取到大屏信息！！！");
                        }

                        idle(12);
                    }
                })).Start();
            }
        }



        int expectDeviceCount = 1;//期望初始化成功N个设备
        public override Boolean initial()
        {
            bool initResult = false;
            int initCount = 0;

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(CarXMZJ), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initCount++;
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }



            if (initCount.Equals(expectDeviceCount))
            {
                initResult = true;
                LogTool.WriteLog(typeof(CarXMZJ), "初始化成功 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            else
            {
                LogTool.WriteLog(typeof(CarXMZJ), "初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
                MessageBox.Show("初始化失败：启动 " + initCount + "/" + expectDeviceCount + " 个设备");
            }
            return initResult;
        }



        //提示信息
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                string LogicTagName = "01#" + deviceTag + "_LEDShow";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);

                ledDevice = new LED_Control();
                ledDevice.initial(ctlConfig["LED_IP"], Commons.pwd);
                ledDevice.sendText_ZJ_BigScreen(Commons.pwd, msg);
            }
        }

    }

}
