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
********************************************/

namespace NHTool.Device.BY17NCReader
{
    public class RfidReadrHF
    {
        public delegate void HF_RFID_READER_EVENT(string cardData, int stepFunction);
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
        private Boolean priIsSendClose;
        public int priCommd = 0;

        public string m_Device_id = "";
        public string m_Record_No = "";
        public string m_Car_Id = "";
        public string m_coal_No = "";
        public string m_plan_Id = "";
        public int read_function = 0;
        public string ic_code = "";
        public string m_szs_sampleId = "";
        public string m_szs_weight1 = "";
        public int m_WriteBlockSampleId = 0;
        public int m_WriteBlockweight1 = 0;
        public int m_WriteBlockSampleMan = 0;
        public int m_WriteBlockTime = 0;
        public string action_flag = "IDEL";
        //excel中的控制配置
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();

        public RfidReadrHF()
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            if (ctlConfig.ContainsKey("HFReadRemortIP") && ctlConfig.ContainsKey("HFReadLocalIP"))
            {
                m_RfidRemortIP = ctlConfig["HFReadRemortIP"];
                m_RfidLocalIP = ctlConfig["HFReadLocalIP"];
                m_UdpPort = ctlConfig["BY_17NC_Port"];
                m_Device_id = ctlConfig["BY_17NC_DEVICEID"];
                m_WriteBlockSampleId = Convert.ToInt32(ctlConfig["BY_17NC_WRITE_BLOCK"]);
                m_WriteBlockweight1 = Convert.ToInt32(ctlConfig["BY_17NC_WRITE_BLOCK_WEIGHT"]);
                m_WriteBlockSampleMan = Convert.ToInt32(ctlConfig["BY_17NC_WRITE_BLOCK_SAMPLE"]);
                m_WriteBlockTime = Convert.ToInt32(ctlConfig["BY_17NC_WRITE_BLOCK_TIME"]);
            }
            else
            {
                //MessageBox.Show("读取车卡识别器BY-17NC配置信息异常");
                System.Environment.Exit(1);
            }
            done = false;
            priIsSendClose = false;
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
                //MessageBox.Show("开启监听失败..." + ex.ToString());
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
                    string receiveStr = "";
                    receEndPoint = null;
                    byte[] buffer = myUdpClient.Receive(ref receEndPoint);
                    datalen = buffer.Length;

                    if (datalen > 0)
                    {
                        for (int i = 0; i < datalen; i++)
                            strGetMessage = strGetMessage + funBtoHex(buffer[i]);
                        if ((buffer[0] == 0xbb) & (buffer[1] == 0xff))
                        {
                            switch (buffer[2])
                            {
                                case 1://刷卡
                                    strGetMessage = "接收" + receEndPoint.ToString() + "刷卡:" + strGetMessage;
                                    ic_code = CommondMethod.ByteArrayToString(buffer, 3, 4).Replace(" ", "");
                                   // LogTool.WriteLog(typeof(RfidReadrHF), "readCard:" + strGetMessage);
                                    break;
                                case 0x70://检测卡返回卡号
                                    ic_code = CommondMethod.ByteArrayToString(buffer, 3, 4).Replace(" ", "");
                                    if (action_flag.Equals("found_card"))
                                    {
                                        funReadOne1(0x11, 0x60, false);//读取车号
                                    }
                                    else if (action_flag.Equals("found_recordno"))
                                    {
                                        funReadOne1(0x0D, 0x60, false);//读取过磅单号
                                    }
                                    else if (action_flag.Equals("found_coalno"))
                                    {
                                        funReadOne1(0x0E, 0x60, false);//读取卡内每种
                                    }
                                    else if (action_flag.Equals("found_planid"))
                                    {
                                        funReadOne1(0x0C, 0x60, false);//读取卡内计划单号
                                    }
                                    else if (action_flag.Equals("write_sampleId"))//写入采样单元号
                                    {
                                        byte[] writeDataList = System.Text.Encoding.ASCII.GetBytes(m_szs_sampleId);
                                        funWriteSampleId(Convert.ToByte(m_WriteBlockSampleId), 0x60, writeDataList, false);
                                    }
                                    else if (action_flag.Equals("write_weight"))//写入重量
                                    {
                                        double weightStr = Convert.ToDouble(m_szs_weight1);
                                        byte[] weightArray = BitConverter.GetBytes(weightStr);
                                        funWriteWeight(Convert.ToByte(m_WriteBlockweight1), 0x60, weightArray, false);
                                    }
                                    else if (action_flag.Equals("write_time"))//写入过磅时间
                                    {
                                        string nowTime = System.DateTime.Now.ToString("yyyyMMdd HHmmss ");
                                        byte[] writeDataList = System.Text.Encoding.ASCII.GetBytes(nowTime);
                                        funWriteSampleId(Convert.ToByte(m_WriteBlockTime), 0x60, writeDataList, false);
                                    }
                                    else if (action_flag.Equals("write_sample_name"))//写入采样人信息
                                    {
                                        byte[] writeDataList = System.Text.Encoding.ASCII.GetBytes("autoSamplebyNept");
                                        funWriteSampleId(Convert.ToByte(m_WriteBlockSampleMan), 0x60, writeDataList, false);
                                    }
                                    if (priIsSendClose == true)
                                        funCloseCard();
                                    break;
                                case 0x10:
                                    if (action_flag.Equals("found_card"))
                                    {
                                        receiveStr = AskiiToStr(buffer, 3, 8);
                                        m_Car_Id = receiveStr;
                                        gf_Rfid_Reader(m_Car_Id, read_function);
                                    }
                                    else if (action_flag.Equals("found_recordno"))
                                    {
                                        receiveStr = fromByteString(buffer, 3, 12);
                                        m_Record_No = receiveStr;
                                        gf_Rfid_Reader(m_Record_No, read_function);
                                    }
                                    else if (action_flag.Equals("found_planid"))
                                    {
                                        receiveStr = fromByteString(buffer, 3, 6);
                                        m_plan_Id = receiveStr;
                                        gf_Rfid_Reader(m_plan_Id, read_function);
                                    }
                                    else if (action_flag.Equals("found_coalno"))
                                    {
                                        receiveStr = AskiiToStr(buffer, 3, 4);
                                        m_coal_No = receiveStr;
                                        gf_Rfid_Reader(m_coal_No, read_function);
                                    }
                                    if (priIsSendClose == true)
                                        funCloseCard();
                                    break;
                                case 0xAF:
                                    strGetMessage = "接收" + receEndPoint.ToString() + ":" + strGetMessage;
                                    if (action_flag.Equals("write_sampleId"))
                                    {
                                        gf_Rfid_Reader("write_sampleId_ok", read_function);
                                    }
                                    else if (action_flag.Equals("write_weight"))
                                    {
                                        gf_Rfid_Reader("write_weight_ok", read_function);
                                    }
                                    else if (action_flag.Equals("write_sample_name"))
                                    {
                                        gf_Rfid_Reader("write_sampleName_ok", read_function);
                                    }
                                    else if (action_flag.Equals("write_time"))
                                    {
                                        gf_Rfid_Reader("write_time_ok", read_function);
                                    }
                                    if (priIsSendClose == true)
                                        funCloseCard();
                                    break;
                                case 0xFE: //开关卡操作
                                    strGetMessage = "接收" + receEndPoint.ToString() + ":" + strGetMessage;
                                    break;
                                default:
                                    strGetMessage = "接收" + receEndPoint.ToString() + ":" + strGetMessage;
                                    break;
                            }

                        }
                    }
                    else
                    {
                        strGetMessage = "接收" + receEndPoint.ToString() + ":" + strGetMessage;
                        LogTool.WriteLog(typeof(RfidReadrHF), "readCard:len <0 " + strGetMessage);
                    }
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

        public void funReadOne1(byte b_Block, byte passAB, bool isShow)
        {

            byte[] outData = new byte[11];//发送数据
            byte[] b_Pass = { 0x1A, 0x2B, 0x3C, 0x4D, 0x5F, 0xFF };

            outData[0] = 0xAA;
            outData[1] = 0XFF;
            outData[2] = 0X10;
            outData[3] = b_Block;
            outData[4] = passAB;
            for (int j = 0; j < 6; j++)
            {
                outData[5 + j] = b_Pass[j];
            }
            try
            {
                // priType = 0;
                myUdpClient.Send(outData, 11, SendEndPoint);
                string strRead = "发送" + SendEndPoint + ":";
                if (isShow)
                {
                    for (int i = 0; i < 11; i++)
                    {
                        strRead = strRead + funBtoHex(outData[i]);
                    }
                    LogTool.WriteLog(typeof(RfidReadrHF), strRead);
                }
            }
            catch (Exception ex)
            {
                //MessageBox.Show(ex.Message.ToString());
            }

        }

        public void funCardSerialCode()
        {
            byte[] outData = new byte[4];
            outData[0] = 0xAA;
            outData[1] = 0xFF;
            outData[2] = 0x70;
            outData[3] = 0x52;

            try
            {
                myUdpClient.Send(outData, 4, SendEndPoint);
            }
            catch (Exception ex)
            {
                LogTool.WriteLog(typeof(RfidReadrHF), "查询卡序列号失败:" + ex.ToString());
            }
        }

        public void funWriteSampleId(byte b_Block, byte passAB, byte[] b_WData, bool isShow)
        {//写一块读卡方式1
            byte[] outData = new byte[27];//发送数据
            byte[] b_Pass = { 0x1A, 0x2B, 0x3C, 0x4D, 0x5F, 0xFF };
            int i;

            if (b_WData.Length > 0)
            {
                outData[0] = 0xAA;
                outData[1] = 0XFF;
                outData[2] = 0X20;
                outData[3] = b_Block;
                outData[4] = passAB;
                for (i = 0; i < 6; i++)
                {
                    outData[5 + i] = b_Pass[i];
                }
                for (i = 0; i < 16; i++)
                {
                    outData[11 + i] = b_WData[i];
                }
                try
                {
                    myUdpClient.Send(outData, 27, SendEndPoint);
                }
                catch (Exception ex)
                {
                    //MessageBox.Show(ex.Message.ToString());
                }
            }
        }

        public void funWriteWeight(byte b_Block, byte passAB, byte[] b_WData, bool isShow)
        {//写一块读卡方式1
            byte[] outData = new byte[27];//发送数据
            byte[] b_Pass = { 0x1A, 0x2B, 0x3C, 0x4D, 0x5F, 0xFF };
            int i;
            if (b_WData.Length > 0)
            {
                outData[0] = 0xAA;
                outData[1] = 0XFF;
                outData[2] = 0X20;
                outData[3] = b_Block;
                outData[4] = passAB;
                for (i = 0; i < 6; i++)
                {
                    outData[5 + i] = b_Pass[i];
                }
                for (i = 0; i < 8; i++)
                {
                    outData[11 + i] = b_WData[i];
                    outData[19 + i] = 0x00;
                }
                try
                {
                    myUdpClient.Send(outData, 27, SendEndPoint);
                }
                catch (Exception ex)
                {
                    //MessageBox.Show(ex.Message.ToString());
                }
            }
        }

        public void funCloseCard()
        {
            try
            {
                byte[] data = new byte[3];
                data[0] = 0xaa;
                data[1] = 0xff;
                data[2] = 0x40;
                myUdpClient.Send(data, 3, SendEndPoint);

                string strRead = "发送" + SendEndPoint + ":";
                for (int i = 0; i < 3; i++)
                {
                    strRead = strRead + funBtoHex(data[i]);
                }
                LogTool.WriteLog(typeof(RfidReadrHF), "关闭卡成功" + strRead);
            }
            catch (Exception ex)
            {
                //MessageBox.Show(ex.Message.ToString());
            }
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

        public bool deivceCloseCard(byte cmdType)
        {
            bool excuRes = false;
            byte[] outData = new byte[4];
            outData[0] = 0xAA;
            outData[1] = 0xFF;
            outData[2] = 0xFE;
            outData[3] = cmdType;//自动关卡0xff；不自动关卡0x00

            string strRead = "发送" + SendEndPoint + ":";
            for (int i = 0; i < 3; i++)
            {
                strRead = strRead + funBtoHex(outData[i]);
            }

            try
            {
                myUdpClient.Send(outData, 4, SendEndPoint);
                excuRes = true;
                LogTool.WriteLog(typeof(RfidReadrHF), "关闭自动关卡命令成功" + strRead);
            }
            catch (Exception ex)
            {
                LogTool.WriteLog(typeof(RfidReadrHF), "关闭自动关卡命令失败" + strRead);
            }
            return excuRes;
        }

        public void funfoundCard()
        {
            byte[] outData = new byte[4];
            outData[0] = 0xAA;
            outData[1] = 0xFF;
            outData[2] = 0x70;
            outData[3] = 0x52;//自动关卡0xff；不自动关卡0x00
            string strRead = "发送" + SendEndPoint + ":";

            try
            {
                myUdpClient.Send(outData, 4, SendEndPoint);
                for (int i = 0; i < 11; i++)
                {
                    strRead = strRead + funBtoHex(outData[i]);
                }
                //LogTool.WriteLog(typeof(RfidReadrHF), strRead);
            }
            catch (Exception ex)
            {
                //LogTool.WriteLog(typeof(RfidReadrHF), "发送巡卡指令失败");
            }
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
