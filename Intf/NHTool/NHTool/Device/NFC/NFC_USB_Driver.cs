using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices; // 用 DllImport 需用此 命名空间
using System.Windows.Forms;

namespace NHTool.Device.NFC
{
    /**   
     * 功能    : NFC高频卡USB数据读写器驱动
     * 创建人  : yangff 2018-11-20  
     * 修改记录:     
     * 
     * 
     */
    public class NFC_USB_Driver
    {
        public  const byte BLOCK0_EN = 0x01;
        public  const byte BLOCK1_EN = 0x02;
        public  const byte BLOCK2_EN = 0x04;
        public  const byte NEEDSERIAL = 0x08;
        public  const byte EXTERNKEY = 0x10;
        public  const byte NEEDHALT = 0x20;

        byte status;//存放返回值
	    byte myareano;//区号
	    byte authmode;//密码类型，用A密码或B密码
	    byte myctrlword;//控制字
	    byte[] mypicckey = new byte[6];//密码
	    byte[] mypiccserial = new byte[4];//卡序列号
	    byte[] mypiccdata = new byte[48]; //卡数据缓冲
        public  Dictionary<String, String> ret = new Dictionary<String, String>();

       [DllImport("OUR_MIFARE.dll")]
       private static extern byte piccreadex(byte myctrlword, ref byte mypiccserial, byte myareano, byte authmode, byte[] mypicckey, ref byte mypiccdata);

       [DllImport("OUR_MIFARE.dll")]
       private static extern byte piccrequest(ref byte mypiccserial);

       [DllImport("OUR_MIFARE.dll")]
       private static extern byte piccauthkey1(byte[] mypiccserial, byte myareano, byte authmode,byte[] mypicckey);

       [DllImport("OUR_MIFARE.dll")]
       private static extern byte piccread(byte block, ref byte mypiccdata);

       [DllImport("OUR_MIFARE.dll")]
       private static extern byte piccwrite(byte block, byte[] mypiccdata);

       [DllImport("OUR_MIFARE.dll")]
       private static extern byte pcdbeep(UInt32 xms);

        public  Dictionary<String, String>  Device_Initial(){
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");

            myareano = 2;
            authmode = 1;//大于0表示用A密码认证，推荐用A密码认证
            myctrlword = BLOCK0_EN + BLOCK1_EN + BLOCK2_EN + EXTERNKEY;
            for (int i = 0; i < 6;i++ )
            {
                mypicckey[i] = 0xff;
            }

            return ret;
        }

        public bool readUSBPackCode(ref string MyPackCode,byte readArea,byte  block)
        {
            bool reqResult = false;
            byte[] tempByteArray = new byte[16];
 
            status = piccrequest(ref mypiccserial[0]);
            if (!status.Equals(0))
            {
                MessageBox.Show("读取卡序列号失败");
                return false;
            }

            status = piccauthkey1(mypiccserial, readArea, authmode, mypicckey);
            if (!status.Equals(0))
            {
                MessageBox.Show("密码装载失败");
                return false;
            }

            status = piccread(block, ref tempByteArray[0]);
            if (!status.Equals(0))
            {
                MessageBox.Show("读取字块失败");
                return false;
            }
            else
            {
                pcdbeep(100);
            }


            MyPackCode = System.Text.Encoding.Default.GetString(tempByteArray);
            reqResult = true;
            return reqResult;
        }

        public bool writeUSBPackCode(string MyPackCode, byte readArea,byte block)
        {
            byte[] tempByteArray = new byte[16];

            status = piccrequest(ref mypiccserial[0]);
            if (!status.Equals(0))
            {
                MessageBox.Show("读取卡序列号失败");
                return false;
            }

            status = piccauthkey1(mypiccserial, readArea, authmode, mypicckey);
            if (!status.Equals(0))
            {
                MessageBox.Show("密码装载失败");
                return false;
            }

            tempByteArray = System.Text.Encoding.Default.GetBytes(MyPackCode);
            status = piccwrite(block, tempByteArray);
            if (!status.Equals(0))
            {
                MessageBox.Show("写字块失败");
                return false;
            }
            else
            {
                pcdbeep(100);
            }

            return true;
        }

    }
}
