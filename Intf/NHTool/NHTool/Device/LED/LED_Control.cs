using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices; // 用 DllImport 需用此 命名空间
using NHTool.Common;

/*****************************************************
 *************create by dafeige 20181115**************
 *****initial()初始化设备对象*************************
 *****sendText_Screen()小屏幕发送显示信息到LED********
 *****sendText_ZJ_BigScreen()织金卸煤沟大屏幕需要*****
 ****************************************************/
namespace NHTool.Device.LED
{
    public class LED_Control
    {
        private const int WM_LED_NOTIFY = 1025;
        Dictionary<String, String> ret = new Dictionary<String, String>();
        LED_Driver LEDSender;
        private Dictionary<String, String> ctlConfig = new Dictionary<String, String>();
        private string serverIp;
        private Dictionary<String, String> deviceState = new Dictionary<String, String>();

        public Dictionary<String, String> Device_Initial()
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");

            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");

            if (ctlConfig.ContainsKey("LED_IP"))
            {
                serverIp = ctlConfig["LED_IP"];
                this.LEDSender = new LED_Driver();
            }
            else
            {
                ret.Clear();
                ret.Add(Common.Commons.RES_CODE, "1999");
                ret.Add(Common.Commons.RES_MSG, "LED配置参数缺失");
            }


            return ret;

        }

        /// <summary>
        /// 初始化LED
        /// </summary>
        /// <param name="ip">设备IP地址</param>
        /// <param name="wmHandle">操作常量句柄</param>
        /// <returns></returns>
        public bool initial(string ip, UInt32 wmHandle)
        {

            this.serverIp = ip;
            this.LEDSender = new LED_Driver();

            Open_Led(wmHandle);
            return true;

        }

        private void GetDeviceParam(ref TDeviceParam param)
        {

            param.devType = LEDSender.DEVICE_TYPE_UDP;
            param.comPort = (ushort)Convert.ToInt16("1");
            param.comSpeed = (ushort)19200;
            param.locPort = (ushort)Convert.ToInt16("9999");
            param.rmtHost = serverIp;
            param.rmtPort = 6666;
            param.dstAddr = (ushort)Convert.ToInt16("0");
        }

        public void Open_Led(UInt32 wmHandle)
        {
            TSenderParam param = new TSenderParam();
            GetDeviceParam(ref param.devParam);
            param.notifyMode = LEDSender.NOTIFY_EVENT;
            param.wmHandle = (UInt32)wmHandle;
            param.wmMessage = (UInt32)WM_LED_NOTIFY;
            Parse(LEDSender.Do_LED_SetPower(ref param, 1));
        }

        public void Close_Led(UInt32 wmHandle)
        {
            TSenderParam param = new TSenderParam();
            GetDeviceParam(ref param.devParam);
            param.notifyMode = LEDSender.NOTIFY_EVENT;
            param.wmHandle = (UInt32)wmHandle;
            param.wmMessage = (UInt32)WM_LED_NOTIFY;
            Parse(LEDSender.Do_LED_SetPower(ref param, 0));
        }

        public void sendText_Screen(UInt32 wmHandle, string sendText)
        {
            TSenderParam param = new TSenderParam();
            ushort K;

            GetDeviceParam(ref param.devParam);
            param.notifyMode = LEDSender.NOTIFY_EVENT;
            //param.notifyMode = LEDSender.NOTIFY_MULTI;
            param.wmHandle = (UInt32)wmHandle;
            param.wmMessage = WM_LED_NOTIFY;

            K = (ushort)LEDSender.Do_MakeRoot(LEDSender.ROOT_PLAY, LEDSender.COLOR_MODE_DOUBLE, LEDSender.SURVIVE_ALWAYS);
            LEDSender.Do_AddChapter(K, 3000000, LEDSender.WAIT_CHILD);

            //------------------------------------------------------------------------
            //第1分区
            LEDSender.Do_AddRegion(K, 0, 0, 64, 32, 0);

            //第1页面
            LEDSender.Do_AddLeaf(K, 10000, LEDSender.WAIT_CHILD);
            //自动换行的文字
            LEDSender.Do_AddText(K, 0, 0, 64, 32, LEDSender.V_TRUE, 0, sendText, "宋体", 12, 0xff, LEDSender.WFS_NONE, LEDSender.V_TRUE, 0, 1, 5, 1, 5, 0, 1, 3000);

            Parse(LEDSender.Do_LED_SendToScreen(ref param, K));
        }


        public void sendText_ZJ_BigScreen(UInt32 wmHandle, string sendText)
        {
            TSenderParam param = new TSenderParam();
            ushort K;

            GetDeviceParam(ref param.devParam);
            param.notifyMode = LEDSender.NOTIFY_EVENT;
            //param.notifyMode = LEDSender.NOTIFY_MULTI;
            param.wmHandle = (UInt32)wmHandle;
            param.wmMessage = WM_LED_NOTIFY;

            K = (ushort)LEDSender.Do_MakeRoot(LEDSender.ROOT_PLAY, LEDSender.COLOR_MODE_DOUBLE, LEDSender.SURVIVE_ALWAYS);
            LEDSender.Do_AddChapter(K, 3000000, LEDSender.WAIT_CHILD);

            //------------------------------------------------------------------------
            //第1分区
            LEDSender.Do_AddRegion(K, 0, 0, 256, 128, 0);

            //第1页面
            LEDSender.Do_AddLeaf(K, 10000, LEDSender.WAIT_CHILD);
            //自动换行的文字
            LEDSender.Do_AddText(K, 0, 0, 256, 128, LEDSender.V_TRUE, 0, sendText, "宋体", 
                10, //fontsize 12
                0x00ff00, //color      0xff红色
                LEDSender.WFS_NONE, 
                LEDSender.V_TRUE, 
                0, 1, 5, 1, 5, 0, 1, 3000);

            Parse(LEDSender.Do_LED_SendToScreen(ref param, K));
        }

        private void Parse(Int32 K)
        {
            deviceState.Clear();
            if (K == LEDSender.R_DEVICE_READY) deviceState.Add("1", "正在执行命令或者发送数据");
            else if (K == LEDSender.R_DEVICE_INVALID) deviceState.Add("-1", "打开通讯设备失败(串口不存在、或者串口已被占用、或者网络端口被占用)");
            else if (K == LEDSender.R_DEVICE_BUSY) deviceState.Add("0", "设备忙，正在通讯中...");
        }
    }
}
