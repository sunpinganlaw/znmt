using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using NHTool.Common;
using UHFDemo;

/********************************************
* create by dafeige  20190312****************
1.使用LED之前，要设置好LEd控制板的
 * IP地址IpAddr；
 * 端口UdpPort，默认33333；
 * 设备号deviceId，默认1
2.直接调用sendContentToLed(string ledStr,ushort colorType)
 * ledStr推送显示的字符串；
 * colorType显示文字的颜色；
********************************************/

namespace NHTool.Device.LED
{
    public class Mc3000Led
    {
        private string IpAddr = "";
        private ushort UdpPort = 0;
        private ushort deviceId = 0;
        private ushort colorType = 0;
        private const ushort LedWidth = 64;
        private const ushort LedHeight = 32;
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        /****
        功能： 初始化电脑上的网络库，启动数据收发用的UDP端口，设置通讯超时和重试参数，并设置用于通讯的目标控制卡IP地址（以后可以调用MC_SetRemoteIP函数修改）。在使用网络通讯模式时必须首先调用本函数进行初始化。
        参数： 由程序员给定的16位任意不同的设备编号。若初始化成功，对该设备调用通讯函数时必须使用相同的设备编号。用户一次最多打开256个ID
        Password 网络访问密码
        RemoteIP 用于通讯的目标控制卡IP地址
        TimeOut	参见MC_ComInitial函数中的同名参数说明
        Retries	参见MC_ComInitial函数中的同名参数说明
        UDPPort	数据收发用的UDP端口。可使用任何电脑上未使用的端口。
        返回： 若网络初始化成功，返回0，否则返回非0(可能是ID已被使用或已打开256个ID、电脑网络有问题、UDP端口被占)*/
        [DllImport("MiniLED.dll", CallingConvention=CallingConvention.Cdecl)]
        public static extern int MC_NetInitial(ushort mDevID, string Password, string RemoteIP, int TimeOut, int Retries, ushort UDPPort);

        [DllImport("MiniLED.dll", CallingConvention=CallingConvention.Cdecl)]
        public static extern bool MC_Close(ushort mDevID);

        /******
         * 功能：在显示屏指定区域范围内显示文字串。文字串的字体缺省使用控制卡上的第一套字库。
        参数：	ID参见MC_ComInitial函数中的同名参数说明
        Left,Top,Width,Height 文字输出区域的左上角坐标及宽、高，该坐标相对于屏幕，屏幕左上角坐标为(0,0)。文字在该区域中按从左到右，从上到下的顺序排版输出，当输出到区域的右边界，将自动换行到下一行的最左边继续输出；当输出到下边，且剩下的空间小于当前要输出的字符的高度时，控制器将终止在该区域的输出
        XPos, YPos 文字串第一个字符相对于文字输出区域左上角的坐标
        Color  字符串的缺省显示颜色，0：黑，1：红，2：绿，3：黄
        Str    待显示的字符串的存储地址，最后以0结尾。最长不超过200个字符。
        Option 字母串显示选项，用于指定字符串文字编码类型：0-单字节编码 1-UCS2(Unicode) 2-双(变)字节编码 其它值保留。ASCII码及ISO-8859系列字符编码均为单字节编码；Unicode固定为2字节编码；GB2312、BIG5则为双(变)字节编码，其英文及数字采用单字节编码，汉字采用双字节编码。该字符串中使用的所有字库的编码类型必须与该编码类型一致。
        返回： 命令成功返回TRUE(非0)，否则返回FALSE(0)。若字符串内部的扩展显示码格式有错误，命令仍然会返回成功，但是屏幕上不会有任何变化。*/
        [DllImport("MiniLED.dll", CallingConvention=CallingConvention.Cdecl)]
        public static extern bool MC_ShowString(ushort mDevID, ushort Left, ushort Top, ushort Width, ushort Height, ushort XPos, ushort YPos, ushort Color, byte[] str, byte Option);


        /********
        功能： 修改网络通讯目标控制器ip地址，随后的网络通讯数据将发往新的目标ip。
        参数： ID	参见MC_NetInitial函数中的同名参数说明
        RemoteIP	参见MC_NetInitial函数中的同名参数说明
        返回： 修改成功返回TRUE(非0)，否则返回FALSE(0)。********/
        [DllImport("MiniLED.dll", CallingConvention = CallingConvention.Cdecl)]
        public static extern bool MC_SetRemoteIP(ushort mDevID, string RemoteIP);

        [DllImport("MiniLED.dll")]
        public static extern bool MC_SetPowerMode(ushort mDevID, byte Mode);

        /****func 0:重启播放流程 1:控制卡复位 2:释放网络，允许其它网络主机访问***/
        [DllImport("MiniLED.dll")]
        public static extern bool Reset(ushort mDevID, ushort func);

        public Mc3000Led()
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            IpAddr = ctlConfig["Mc3000IP"];
            UdpPort = (ushort)Convert.ToUInt32(ctlConfig["Mc3000Port"]);
            deviceId = (ushort)Convert.ToUInt32(ctlConfig["Mc3000DeviceId"]);
            colorType = (ushort)Convert.ToUInt32(ctlConfig["Mc3000ColorType"]);
        }

        public bool sendContentToLed(string ledStr)
        {
            bool excuResult = false;
            int ret = MC_NetInitial(deviceId, "", IpAddr, 2, 2, UdpPort);
            if (ret.Equals(0))
            {
                try{
                    MC_ShowString(deviceId, 0, 0, LedWidth, LedHeight, 0, 0, colorType, getShowByteArray(ledStr), 2);
                    excuResult = true;
                }catch(Exception e){
                    LogTool.WriteLog(typeof(Mc3000Led), e);
                    MessageBox.Show(Convert.ToString(deviceId) + "#LED显示数据出错");
                }finally{
                    MC_Close(deviceId);
                }
            }
            else
            {
                MessageBox.Show(Convert.ToString(deviceId) + "#通讯设备初始化失败!");
            }
            return excuResult;
        }

        public byte[] getShowByteArray(string ledStr)
        {
            byte[] retStr = new byte[16];
            byte[] showStr =  System.Text.Encoding.Default.GetBytes(ledStr);
            if (ledStr.Length >= 16)
            {
                for (int i = 0; i < 16; i++)
                {
                    retStr[i] = showStr[i];
                }
            }
            else
            {
                int nLen = showStr.Length;
                for (int i = 0; i < nLen; i++)
                {
                    retStr[i] = showStr[i];
                }
                for (int i = nLen; i < 16; i++)
                {
                    retStr[i] = 0x20;
                }
            }
            return retStr;
        }

        public bool setDeviceIp(string ipStr)
        {
            bool excuResult = false;
            int ret = MC_NetInitial(deviceId, "", IpAddr, 2, 2, UdpPort);
            if (ret.Equals(0))
            {
                try
                {
                    excuResult = MC_SetRemoteIP(deviceId, ipStr);
                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(Mc3000Led), e);
                    MessageBox.Show(Convert.ToString(deviceId) + "#LED，IP设置失败");
                }
                finally
                {
                    MC_Close(deviceId);
                }
            }
            else
            {
                MessageBox.Show(Convert.ToString(deviceId) + "#LED，IP设置失败");
            }
            return excuResult;
        }
    }
}
