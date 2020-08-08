using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using NHTool.Common;

/***********************************
 * 山西铝业汽车采样机前LED大屏
 * 采用UDP通信方式*******************
 * **********************************/
namespace NHTool.Device.LED
{
    public class SxlyBigLED
    {
        private string remortDeviceIp = "";
        private int ledScreenWidth = 128;
        private int ledScreenHeight = 80;
        private string ledScreenFontSize= "1";//字体大小
        private List<string> showCarList = new List<string>();

        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();

        //发送内码文字（UDP；TCP）
        [DllImport("QYLED.dll", CallingConvention = CallingConvention.StdCall)]
        public static extern byte SendInternalText_Net(string TshowContent, string TcardIP, int TnetProtocol, int TareaWidth, int TareaHigth, int Tuid, int TscreenColor, int TshowStyle, int TshowSpeed, int TstopTime, int TfontColor, int TfontBody, int TfontSize, int TupdateStyle, bool TpowerOffSave);
        

        public SxlyBigLED()
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            remortDeviceIp = ctlConfig["SXLYDP"];
            ledScreenFontSize = ctlConfig["LEDFONTSIZE"];
        }

        public Boolean sendStrToLed()
        {
            Boolean excuRes = false;
            try{
                int result = 0;
                result = SendInternalText_Net(getShowStr(), remortDeviceIp,1,ledScreenWidth,ledScreenHeight, 1, 0, 9, 1, 0, 1, 1, Convert.ToInt32(ledScreenFontSize), 1, false);
            }catch(Exception ex){
                LogTool.WriteLog(typeof(Mc3000Led), ex.ToString());
            }
            
            return excuRes;
        }

        private string getShowStr()
        {
            string showStr = "";
            foreach (var item in showCarList)
            {
                showStr += item;
            }
            return showStr;
        }

        public void setShowString(string showStr)
        {
            if (ledScreenFontSize.Equals("1"))  //16*16
            {
                if (showCarList.Count < 5)  //可以显示5行文字
                {
                    showCarList.Add(showStr);
                }
                else
                {
                    showCarList.Add(showStr);
                    showCarList.RemoveAt(0);
                }
            }
            else if (ledScreenFontSize.Equals("3"))//32*32
            {
                showCarList.Clear();
                showCarList.Add(showStr);
            }
        }
    }
}
