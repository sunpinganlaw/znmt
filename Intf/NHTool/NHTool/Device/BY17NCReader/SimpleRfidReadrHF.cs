using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Windows.Forms;
using System.Threading;
using NHTool.Common;
using UHFDemo;

/********************************************
* create by dafeige  20190312****************
1.使用设置设备的IP地址m_RfidRemortIP，主机的IP地址m_RfidLocalIP
 * 初始化后调用ConnectDevice()，在线监听刷卡信息，然后处理识别卡号
 * 
 * modify by xieyt，20191007，简单处理刷卡信息，其它业务逻辑的事情全部去掉
********************************************/

namespace NHTool.Device.BY17NCReader
{
    public class SimpleRfidReadrHF
    {
        public delegate void HF_RFID_READER_EVENT(string cardData);
        public event HF_RFID_READER_EVENT gf_Rfid_Reader;

        private UdpClient myUdpClient;
        private IPEndPoint receEndPoint;
        private IPEndPoint SendEndPoint;
        private UnicodeEncoding encoding = new UnicodeEncoding();
        private Thread receiver;
        private string m_RfidRemortIP = "";
        private string m_RfidLocalIP = "";
        private string m_UdpPort = "";
        private Boolean done;
        public int priCommd = 0;

        public int read_function = 0;
        public string ic_code = "";
        //excel中的控制配置
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();

        public SimpleRfidReadrHF()
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            if (ctlConfig.ContainsKey("HFReadRemortIP") && ctlConfig.ContainsKey("HFReadLocalIP"))
            {
                m_RfidRemortIP = ctlConfig["HFReadRemortIP"];
                m_RfidLocalIP = ctlConfig["HFReadLocalIP"];
                m_UdpPort = ctlConfig["BY_17NC_Port"];
            }
            else
            {
                //MessageBox.Show("读取车卡识别器BY-17NC配置信息异常");
                System.Environment.Exit(1);
            }
            done = false;
            read_function = 1;
        }

        public Boolean ConnectDevice()
        {
            SendEndPoint = new IPEndPoint(IPAddress.Parse(m_RfidRemortIP), Convert.ToInt32(m_UdpPort));
            try
            {
                myUdpClient = new UdpClient(Convert.ToInt32(m_UdpPort));
                receiver = new Thread(new ThreadStart(thrdListener));
                receiver.IsBackground = true;
                receiver.Start();
                return true;
            }
            catch (Exception ex)
            {
                LogTool.WriteLog(typeof(SimpleRfidReadrHF), "开启刷监听失败..." + ex.ToString());
                return false;
            }
        }

        private void funStopBind()
        {
            done = true;
            if (receiver != null)
            {
                try
                {
                    if (receiver.IsAlive)
                        receiver.Abort();
                }
                catch (Exception ex)
                {
                    //MessageBox.Show(ex.ToString());
                    return;
                }
            }
        }

        private void thrdListener()
        {
            int datalen;
            done = false;
            try
            {
                while (!done)
                {
                    string strGetMessage = "";
                    receEndPoint = null;
                    byte[] buffer = myUdpClient.Receive(ref receEndPoint);
                    datalen = buffer.Length;

                    if (datalen > 0)
                    {
                        for (int i = 0; i < datalen; i++)
                        {
                            strGetMessage = strGetMessage + funBtoHex(buffer[i]);
                        }
                            
                        if ((buffer[0] == 0xbb) & (buffer[1] == 0xff))
                        {
                            switch (buffer[2])
                            {
                                case 1://刷卡
                                    LogTool.WriteLog(typeof(SimpleRfidReadrHF), "接收到的刷矿卡号:" + strGetMessage);
                                    ic_code = CommondMethod.ByteArrayToString(buffer, 3, 4).Replace(" ", "");
                                    gf_Rfid_Reader(ic_code);
                                    break;
                                default:
                                    LogTool.WriteLog(typeof(SimpleRfidReadrHF), "读卡器未知反馈");
                                    break;
                            }

                        }
                    }
                    else
                    {
                        strGetMessage = "接收" + receEndPoint.ToString() + ":" + strGetMessage;
                        LogTool.WriteLog(typeof(RfidReadrHF), "readCard:len <0 " + strGetMessage);
                    }

                    //休眠200毫秒，避免CPU太高
                    Thread.Sleep(200);
                }

            }
            catch (Exception ex)
            {
                //MessageBox.Show(ex.Data + " " + ex.Message.ToString());
            }
            finally
            {
                recoverUdpPort();
            }

        }

        public string AskiiToStr(Byte[] Data, int startPos, int len)
        {
            string name = "";
            for (int j = startPos; j < (startPos + len); j++)
            {
                if (Data[j] > 1 && Data[j] < 127)//是ASCII码，直接转
                {
                    name += ASCIIEncoding.Default.GetString(Data, j, 1);
                }
                else
                {
                    byte[] tmp = new byte[2];
                    tmp[0] = Data[j];
                    tmp[1] = Data[j + 1];
                    name += Encoding.Default.GetString(tmp, 0, 2);
                    j = j + 1;
                }
            }
            return name;
        }



        private string funBtoHex(byte num)
        {
            string strhex;
            strhex = num.ToString("X");
            if (strhex.Length == 1)
                strhex = " 0" + strhex;
            else
                strhex = " " + strhex;
            return strhex;

        }



        private void recoverUdpPort()
        {
            myUdpClient.Close();
            myUdpClient = new UdpClient(Convert.ToInt32(m_UdpPort));
        }

        public static string fromByteString(Byte[] dataArray, int startAdd, int dataLength)
        {
            string railStr = "";
            for (int i = 0; i < dataLength; i++)
            {

                railStr += Convert.ToString(dataArray[startAdd + i] & 0x0f);
            }
            return railStr;
        }
    }
}
