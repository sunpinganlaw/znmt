using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Windows.Forms;
using NHTool.Device.LED;

namespace NHTool.Business.Car_IN
{
    class ShowMineNameXW : BusinessBase
    {
        private DataBaseTool dataBaseTool = null;

        /// <summary>
        /// 主体程序流转，通过switch+stateEventArgs进行切换
        /// </summary>
        public override void mainProcess()
        {
            string mineName = "";
            if (initial()) //成功初始化
            {
                new Thread(new ThreadStart(delegate
                {
                    while (true)
                    {
                        try
                        {
                            mineName = dataBaseTool.showMineNameXW();
                            tipMsg(mineName);
                            //LogTool.WriteLog(typeof(ShowMineNameXW), mineName);
                            idle(5);
                        }
                        catch (Exception e)
                        {
                            LogTool.WriteLog(typeof(ShowMineNameXW), e.StackTrace);
                            LogTool.WriteLog(typeof(ShowMineNameXW), "获取大屏信息数据库调用失败：" + e.Message);
                        }
                    }
                })).Start();
            }
        }

        public override Boolean initial()
        {
            //初始化c#中调用数据库的工具类实例
            dataBaseTool = new DataBaseTool();

            bool initResult = false;

            if (initiLedDevice(ctlConfig["LED_IP"]))
            {
                LogTool.WriteLog(typeof(ShowMineNameXW), "LED初始化成功【IP：" + ctlConfig["LED_IP"] + "】");
                initResult = true;
            }
            else
            {
                LogTool.WriteLog(typeof(ShowMineNameXW), "LED初始化失败");
                MessageBox.Show("LED初始化失败");
            }

            return initResult;
        }



        //提示信息
        public override void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                writeMointorSingle("tipMsg", msg);
                actiomLedShow(msg);
            }
        }

    }

}
