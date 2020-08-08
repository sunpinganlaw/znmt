using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices; // 用 DllImport 需用此 命名空间
using System.Windows.Forms;

namespace NHTool.Device.NFC
{
    /**   
     * 功能    : NFC高频卡COM串口数据读写器驱动
     * 创建人  : yangff 2018-11-20  
     * 修改记录:     
     * 
     */
    public class NFC_COM_Driver
    {
        ushort icdev = 0;			//通讯设备标识符
	    byte model = 0x60;			// 密码验证模式->0x60：验证A密钥 ,0x61：验证B密钥
	    byte[] pKey = new byte[6];				// 密钥内容，6 字节
	    byte requestmodel =0x52;		//寻卡模式
	    byte[] mydata = new byte[16];			//卡数据缓冲
	    byte bcnt = 0x04;			//mifare卡就用0x04
	    ushort pTagType;				//返回卡类型
	    byte[] pSnr = new byte[1024];			//返回卡序列号
	    byte pLen;					//卡序列号长度
	    byte pSize;					//返回激活卡的容量
        private Dictionary<String, String> ctlConfig = new Dictionary<String, String>();
        public  Dictionary<String, String> ret = new Dictionary<String, String>();
        private long status = 1;						//导出函数返回值状态
        private int devicePort;
        private int deviceBual;


        public  Dictionary<String, String>  Device_Initial()
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            for (int i = 0; i < 6; i++)
            {
                pKey[i] = 0xff;
            }
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            if(ctlConfig.ContainsKey("NFC_COM_PORT") && ctlConfig.ContainsKey("NFC_COM_BUAL")){
                    devicePort = Convert.ToInt32(ctlConfig["NFC_COM_PORT"]);
                    deviceBual = Convert.ToInt32(ctlConfig["NFC_COM_BUAL"]);
            }else{
                ret.Clear();
                ret.Add(Common.Commons.RES_CODE, "1999");
                ret.Add(Common.Commons.RES_MSG, "NFC读卡器配置参数缺失");
            }
             return ret;
        }

        //动态链接库初始化
        [DllImport("MasterRD.dll")]
        private static extern int rf_init_com(Int32 port, Int32 baud);

        [DllImport("MasterRD.dll")]
        private static extern long rf_ClosePort();

        //寻TYPE_A卡
        [DllImport("MasterRD.dll")]
        private static extern int rf_request(ushort icdev, byte requestmodel, ref ushort pTagType);

        [DllImport("MasterRD.dll")]
        private static extern int rf_anticoll(ushort icdev, byte bcnt, ref byte pSnr, ref byte pLen);

        [DllImport("MasterRD.dll")]
        private static extern int rf_select(ushort icdev, byte[] pSnr, byte pLen, ref byte pSize);

        [DllImport("MasterRD.dll")]
        private static extern int rf_M1_authentication2(ushort icdev, byte model, byte block, byte[] pKey);

        [DllImport("MasterRD.dll")]
        private static extern int rf_M1_write(ushort icdev, byte block, byte[] pData);//写卡

        [DllImport("MasterRD.dll")]
        private static extern int rf_beep(ushort icdev, byte msec);//蜂鸣器

        [DllImport("MasterRD.dll")]
        private static extern int rf_M1_read(ushort icdev, byte block,ref byte pData, ref byte pSize);//读卡


        public bool readSerialPacketCode(byte block,ref string PacketCode)
        {
            rf_ClosePort();
            status = rf_init_com(devicePort, deviceBual);
            while(status>0)
            {
                rf_ClosePort();
                status = rf_init_com(devicePort, deviceBual);
                return false;
            }

            status = rf_request(icdev, requestmodel, ref pTagType);
            if (status > 0)
            {
                MessageBox.Show("未识别到芯片");
                rf_ClosePort();
                return false;
            }

            status = rf_anticoll(icdev, bcnt,ref pSnr[0], ref pLen);
            if (status > 0)
            {
                MessageBox.Show("芯片防冲撞失败");
                rf_ClosePort();
                return false;
            }

            status = rf_select(icdev, pSnr, pLen, ref pSize);
            if (status > 0)
            {
                MessageBox.Show("芯片防冲撞失败");
                rf_ClosePort();
                return false;
            }


            status = rf_M1_authentication2(icdev, model, block, pKey);
            if (status > 0)
            {
                MessageBox.Show("卡秘钥验证失败！");
                rf_ClosePort();
                return false;
            }

            status = rf_M1_read(icdev, block,ref mydata[0], ref pLen);
            if (status > 0)
            {
                MessageBox.Show("封装码读卡失败！");
                rf_ClosePort();
                return false;
            }
            else
            {
                rf_beep(icdev,10);
            }

            PacketCode = System.Text.Encoding.Default.GetString(mydata);
            rf_ClosePort();
            return true;
        }

        public bool writeSerialPacketCode(byte block,string PacketCode)
        {
            rf_ClosePort();
            status = rf_init_com(devicePort, deviceBual);
            while (status>0)
            {
                rf_ClosePort();
                status = rf_init_com(devicePort, deviceBual);
                return false;
            }

            status = rf_request(icdev, requestmodel, ref pTagType);
            if (status > 0)
            {
                MessageBox.Show("未识别到芯片");
                rf_ClosePort();
                return false;
            }


            status = rf_anticoll(icdev, bcnt, ref pSnr[0], ref pLen);
            if (status > 0)
            {
                MessageBox.Show("芯片防冲撞失败");
                rf_ClosePort();
                return false;
            }

            status = rf_select(icdev, pSnr, pLen, ref pSize);
            if (status > 0)
            {
                MessageBox.Show("芯片防冲撞失败");
                rf_ClosePort();
                return false;
            }

            status = rf_M1_authentication2(icdev, model, block, pKey);
            if (status > 0)
            {
                MessageBox.Show("卡秘钥验证失败！");
                rf_ClosePort();
                return false;
            }

            mydata = System.Text.Encoding.Default.GetBytes(PacketCode);

            status = rf_M1_write(icdev, block, mydata);
            if (status > 0)
            {
                MessageBox.Show("封装码写卡失败！");
                rf_ClosePort();
                return false;
            }
            else
            {
                rf_beep(icdev, 10);
            }
            rf_ClosePort();
            return true;
        }
    }
}
