using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using NHTool.Common;
using UHFDemo;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Windows.Forms;
using System.ComponentModel;
using System.Threading;

namespace NHTool.Device.RFID
{
    public class ReadRfidDevice : Device
    {
        private ReaderMethod reader;
        private ReaderSetting m_curSetting = new ReaderSetting();
        private List<RealTimeTagData> RealTimeTagDataList = new List<RealTimeTagData>();
        public Dictionary<string, string> epcTag2AntId = new Dictionary<string, string>();
        public List<string> RealTimeEpcTag = new List<string>();
        public List<string> errorEpcTag = new List<string>();
        //private string epcTag = string.Empty;

        private InventoryBuffer m_curInventoryBuffer = new InventoryBuffer();

        private OperateTagBuffer m_curOperateTagBuffer = new OperateTagBuffer();
        private OperateTagISO18000Buffer m_curOperateTagISO18000Buffer = new OperateTagISO18000Buffer();

        private Dictionary<String, String> ctlConfig = new Dictionary<String, String>();
        Dictionary<String, String> ret = new Dictionary<String, String>();

        //盘存操作前，需要先设置工作天线，用于标识当前是否在执行盘存操作
        private bool m_bInventory = false;

        private int loopCount = 0;
        private string serverIp;
        private string serverPort;
        //是否显示串口监控数据
        private bool m_bDisplayLog = false;

        //实时盘存次数
        private int m_nTotal = 0;

        public bool initial(string ip, string port, string antPortStr)
        {
            bool result = true;

            serverIp = ip;
            serverPort = port;

            this.reader = new ReaderMethod();

            //回调函数
            this.reader.AnalyCallback = AnalyData;
            this.reader.ReceiveCallback = ReceiveData;
            this.reader.SendCallback = SendData;
            m_curInventoryBuffer.bLoopInventory = false;
            m_curInventoryBuffer.nIndexAntenna = 0;
            readAntPortConfig(antPortStr);
            ConnectTcp_rfid();
            return result;

        }

        private void readAntPortConfig(string antPortStr)
        {
            JObject antPortJson = (JObject)JsonConvert.DeserializeObject(antPortStr);
            foreach (var json in antPortJson)
            {
                if (json.Key.Equals("PORT1"))
                {
                    if (Convert.ToInt32(json.Value.ToString()).Equals(1))
                    {
                        m_curInventoryBuffer.lAntenna.Add(0x00);
                    }
                }
                else if (json.Key.Equals("PORT2"))
                {
                    if (Convert.ToInt32(json.Value.ToString()).Equals(1))
                    {
                        m_curInventoryBuffer.lAntenna.Add(0x01);
                    }
                }
                else if (json.Key.Equals("PORT3"))
                {
                    if (Convert.ToInt32(json.Value.ToString()).Equals(1))
                    {
                        m_curInventoryBuffer.lAntenna.Add(0x02);
                    }
                }
                else if (json.Key.Equals("PORT4"))
                {
                    if (Convert.ToInt32(json.Value.ToString()).Equals(1))
                    {
                        m_curInventoryBuffer.lAntenna.Add(0x03);
                    }
                }
            }
        }

        public override Dictionary<String, String> start()
        {
            return ConnectTcp_rfid();
        }

        public override Dictionary<String, String> stop()
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");

            DisconnectTcp_rfid();

            return ret;
        }

        public void clearEcpTagList()
        {
            RealTimeTagDataList.Clear();
            RealTimeEpcTag.Clear();
            errorEpcTag.Clear();
            epcTag2AntId.Clear();
        }

        public void InsertEpcTagList(ref List<String> epcTagList, string epcTag)
        {
            if (!epcTagList.Contains(epcTag.Trim()))
            {
                epcTagList.Add(epcTag.Trim());
            }
        }

        private void InsertEpcTag2AntIdRecord(string epcStr,string antIdStr)
        {
            if (epcTag2AntId.ContainsKey(epcStr))
            {
                epcTag2AntId[epcStr] = antIdStr;
            }else{
                epcTag2AntId.Add(epcStr, antIdStr);
            }
        }

        public override int getState()
        {
            bool retRes = this.reader.getrfidDeviceState();
            return Convert.ToInt32(retRes);
        }

        private Dictionary<String, String> ConnectTcp_rfid()
        {
            Dictionary<String, String> retDc = Commons.createDictionary("0", "succ");
            int nRet = 0;
            try
            {
                //处理Tcp连接读写器
                string strException = string.Empty;
                IPAddress ipAddress = IPAddress.Parse(serverIp);
                int nPort = Convert.ToInt32(serverPort);
                nRet = reader.ConnectServer(ipAddress, nPort, out strException);

                if (nRet != 0)
                {
                    Commons.putDicResCodeMsg(ref retDc, "1", "连接读写器失败，失败原因： " + strException);
                }
                else
                {
                    Commons.putDicResCodeMsg(ref retDc, "0", "连接读写器 " + serverIp + "@" + serverPort);
                }

            }
            catch (System.Exception ex)
            {
                Commons.putDicResCodeMsg(ref retDc, "1", ex.Message);
            }
            return retDc;
        }

        private void DisconnectTcp_rfid()
        {
            //处理断开Tcp连接读写器
            reader.SignOut();

        }

        public Dictionary<String, String> read_RFID_Info()
        {
            Dictionary<String, String> retDc = Commons.createDictionary("1", "ERR");
            //判断状态
            try
            {
                m_curInventoryBuffer.ClearInventoryPar();
                m_curInventoryBuffer.btRepeat = 0x01;
                m_curInventoryBuffer.bLoopCustomizedSession = false;

                if (m_curInventoryBuffer.lAntenna.Count == 0)
                {
                    MessageBox.Show("请至少选择一个天线");
                    Commons.putDicResCodeMsg(ref retDc, "1999", "error");
                    return retDc;
                }
                //默认循环发送命令
                m_bInventory = true;
                m_curInventoryBuffer.bLoopInventory = true;
                m_curInventoryBuffer.bLoopInventoryReal = true;
                m_curInventoryBuffer.ClearInventoryRealResult();
                m_nTotal = 0;

                byte btWorkAntenna = m_curInventoryBuffer.lAntenna[m_curInventoryBuffer.nIndexAntenna];
                int tcpCommRet = reader.SetWorkAntenna(m_curSetting.btReadId, btWorkAntenna);
                if (tcpCommRet.Equals(-1))
                {
                    DisconnectTcp_rfid();
                    Thread.Sleep(1000);
                    LogTool.WriteLog(typeof(ReadRfidDevice), "天线读取失败，重新连接！");
                    ConnectTcp_rfid();
                }
                m_curSetting.btWorkAntenna = btWorkAntenna;
                Commons.putDicResCodeMsg(ref retDc, "0", "succ");
            }
            catch (System.Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return retDc;
        }

        public void stop_read_RFID_Info()
        {
            m_bInventory = false;
            m_curInventoryBuffer.bLoopInventory = false;
            m_curInventoryBuffer.bLoopInventoryReal = false;
        }

        public void insertErrorEpcTagList(string epcStr)
        {
            errorEpcTag.Add(epcStr);
        }

        public void clearErrorEpcTagList()
        {
            errorEpcTag.Clear();
        }

        //实时盘存标签函数
        //-----------输入参数-------------- 
        //btReaderId:读写器地址，0xff为公共地址 
        //btRepeat：每命令重复盘存次数，0xff为快速模式。
        //btTimeOut：超时控制，单位是秒，如果在此时间内读写器未响应或命令未执行完毕则返回超时 
        //---------------------------------
        //-----------输出参数--------------
        // 0：成功盘存但未盘存到标签
        // 1：成功盘存并盘存到标签
        // -1：盘存过程出现错误
        // -2：盘存超时
        //---------------------------------
        //注意：在此函数及其调用的函数中不要更新界面，因为界面线程正在等待此函数返回。
        private int realTimeInventory(byte btReaderId, byte btRepeat, byte btTimeOut)
        {
            DateTime startTime;
            TimeSpan timeOutControl;

            //这里使用等待数据的方法，数据全部接收完毕后再进行处理
            m_curSetting.btRealInventoryFlag = 0;
            RealTimeTagDataList.Clear();  //清空标签信息表
            reader.InventoryReal(255, 1); // 先发送实时盘存命令，用0xFF公共地址，每条命令重复盘存一次

            startTime = DateTime.Now;

            while (m_curSetting.btRealInventoryFlag == 0) //等待读写器返回数据完成，若超时，返回超时标志
            {
                timeOutControl = DateTime.Now - startTime;
                if (timeOutControl.TotalMilliseconds > btTimeOut * 1000)//超时返回 
                {
                    return -2;
                }
            }

            if (m_curSetting.btRealInventoryFlag == 1) //命令执行成功
            {
                if (RealTimeTagDataList.Count > 0)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
            if (m_curSetting.btRealInventoryFlag == 100) //命令执行失败
            {
                return -1;
            }
            return 0;
        }

        private void ReceiveData(byte[] btAryReceiveData)
        {
            if (m_bDisplayLog)
            {
                string strLog = CommondMethod.ByteArrayToString(btAryReceiveData, 0, btAryReceiveData.Length);


            }
        }

        private void SendData(byte[] btArySendData)
        {
            if (m_bDisplayLog)
            {
                string strLog = CommondMethod.ByteArrayToString(btArySendData, 0, btArySendData.Length);


            }

        }

        private void AnalyData(MessageTran msgTran)
        {
            if (msgTran.PacketType != 0xA0)
            {
                return;
            }
            switch (msgTran.Cmd)
            {
                case 0x80:
                    ProcessInventory(msgTran);
                    break;
                case 0x81:
                    ProcessReadTag(msgTran);
                    break;
                case 0x89:
                    ProcessInventoryReal(msgTran);
                    break;
                case 0x74:
                    ProcessSetWorkAntenna(msgTran);
                    break;
                default:
                    break;
            }
        }

        private void ProcessInventory(MessageTran msgTran)
        {
            string strCmd = "盘存标签";
            string strErrorCode = string.Empty;

            if (msgTran.AryData.Length == 9)
            {
                m_curInventoryBuffer.nCurrentAnt = msgTran.AryData[0];
                m_curInventoryBuffer.nTagCount = Convert.ToInt32(msgTran.AryData[1]) * 256 + Convert.ToInt32(msgTran.AryData[2]);
                m_curInventoryBuffer.nReadRate = Convert.ToInt32(msgTran.AryData[3]) * 256 + Convert.ToInt32(msgTran.AryData[4]);
                int nTotalRead = Convert.ToInt32(msgTran.AryData[5]) * 256 * 256 * 256
                    + Convert.ToInt32(msgTran.AryData[6]) * 256 * 256
                    + Convert.ToInt32(msgTran.AryData[7]) * 256
                    + Convert.ToInt32(msgTran.AryData[8]);
                m_curInventoryBuffer.nDataCount = nTotalRead;
                m_curInventoryBuffer.lTotalRead.Add(nTotalRead);
                m_curInventoryBuffer.dtEndInventory = DateTime.Now;

                RunLoopInventroy();

                return;
            }
            RunLoopInventroy();
        }

        private void ProcessSetWorkAntenna(MessageTran msgTran)
        {
            int intCurrentAnt = 0;
            intCurrentAnt = m_curSetting.btWorkAntenna + 1;
            string strCmd = "设置工作天线成功,当前工作天线: 天线" + intCurrentAnt.ToString();

            string strErrorCode = string.Empty;

            if (msgTran.AryData.Length == 1)
            {
                if (msgTran.AryData[0] == 0x10)
                {
                    m_curSetting.btReadId = msgTran.ReadId;

                    //校验是否盘存操作
                    if (m_bInventory)
                    {
                        RunLoopInventroy();
                    }
                    return;
                }
            }
            else
            {
                strErrorCode = "未知错误";
                LogTool.WriteLog(typeof(ReadRfidDevice), strErrorCode);
            }
            if (m_bInventory)
            {
                m_curInventoryBuffer.nCommond = 1;
                m_curInventoryBuffer.dtEndInventory = DateTime.Now;
                RunLoopInventroy();
            }
        }

        private void RunLoopInventroy()
        {
            //校验盘存是否所有天线均完成
            if (m_curInventoryBuffer.nIndexAntenna < m_curInventoryBuffer.lAntenna.Count - 1 || m_curInventoryBuffer.nCommond == 0)
            {
                if (m_curInventoryBuffer.nCommond == 0)
                {
                    m_curInventoryBuffer.nCommond = 1;

                    if (m_curInventoryBuffer.bLoopInventoryReal)
                    {
                        reader.InventoryReal(m_curSetting.btReadId, m_curInventoryBuffer.btRepeat);
                    }
                    else
                    {
                        if (m_curInventoryBuffer.bLoopInventory)
                            reader.Inventory(m_curSetting.btReadId, m_curInventoryBuffer.btRepeat);
                    }
                }
                else
                {
                    m_curInventoryBuffer.nCommond = 0;
                    m_curInventoryBuffer.nIndexAntenna++;

                    byte btWorkAntenna = m_curInventoryBuffer.lAntenna[m_curInventoryBuffer.nIndexAntenna];
                    reader.SetWorkAntenna(m_curSetting.btReadId, btWorkAntenna);
                    m_curSetting.btWorkAntenna = btWorkAntenna;
                }
            }
            //校验是否循环盘存
            else if (m_curInventoryBuffer.bLoopInventory)
            {
                m_curInventoryBuffer.nIndexAntenna = 0;
                m_curInventoryBuffer.nCommond = 0;

                byte btWorkAntenna = m_curInventoryBuffer.lAntenna[m_curInventoryBuffer.nIndexAntenna];
                reader.SetWorkAntenna(m_curSetting.btReadId, btWorkAntenna);
                m_curSetting.btWorkAntenna = btWorkAntenna;
            }
        }

        private void ProcessReadTag(MessageTran msgTran)
        {
            string strCmd = "读标签";
            string strErrorCode = string.Empty;
            if (msgTran.AryData.Length == 1)
            {
                strErrorCode = CommondMethod.FormatErrorCode(msgTran.AryData[0]);
                string strLog = strCmd + "失败，失败原因： " + strErrorCode;
                m_curSetting.btRealInventoryFlag = 100; //读写器返回错误信息
            }
            else
            {
                RealTimeTagData tagData = new RealTimeTagData();
                int nLen = msgTran.AryData.Length;
                int nDataLen = Convert.ToInt32(msgTran.AryData[nLen - 3]);
                int nEpcLen = Convert.ToInt32(msgTran.AryData[2]) - nDataLen - 4;

                string strPC = CommondMethod.ByteArrayToString(msgTran.AryData, 3, 2);
                string strEPC = CommondMethod.ByteArrayToString(msgTran.AryData, 5, nEpcLen);
                string strCRC = CommondMethod.ByteArrayToString(msgTran.AryData, 5 + nEpcLen, 2);
                string strData = CommondMethod.ByteArrayToString(msgTran.AryData, 7 + nEpcLen, nDataLen);

                byte byTemp = msgTran.AryData[nLen - 2];
                byte byAntId = (byte)((byTemp & 0x03) + 1);


                tagData.strEpc = strEPC;
                tagData.strPc = strPC;
                tagData.strTid = strData;
                tagData.btAntId = byAntId;

                RealTimeTagDataList.Add(tagData);

                int nReaddataCount = msgTran.AryData[0] * 255 + msgTran.AryData[1]; //数据总数量
                if (RealTimeTagDataList.Count == nReaddataCount)  //收到所有的数据
                {
                    m_curSetting.btRealInventoryFlag = 1; //读写器返回错误信息
                }
            }
        }

        private void ProcessInventoryReal(MessageTran msgTran)
        {
            string strCmd = "";
            strCmd = "实时盘存";
            string strErrorCode = string.Empty;

            if (msgTran.AryData.Length == 1) //收到错误信息数据包
            {
                strErrorCode = CommondMethod.FormatErrorCode(msgTran.AryData[0]);
                string strLog = strCmd + "失败，失败原因： " + strErrorCode;
                m_curSetting.btRealInventoryFlag = 100; //读写器返回盘存错误
            }
            else if (msgTran.AryData.Length == 7) //收到命令结束数据包
            {
                m_curInventoryBuffer.nReadRate = Convert.ToInt32(msgTran.AryData[1]) * 256 + Convert.ToInt32(msgTran.AryData[2]);
                m_curInventoryBuffer.nDataCount = Convert.ToInt32(msgTran.AryData[3]) * 256 * 256 * 256 + Convert.ToInt32(msgTran.AryData[4]) * 256 * 256 + Convert.ToInt32(msgTran.AryData[5]) * 256 + Convert.ToInt32(msgTran.AryData[6]);
                m_curSetting.btRealInventoryFlag = 1; //成功收到盘存命令结束数据包
            }
            else //收到实时标签数据信息
            {
                m_nTotal++;
                int nLength = msgTran.AryData.Length;
                int nEpcLength = nLength - 4;
                RealTimeTagData tagData = new RealTimeTagData();

                string strEPC = CommondMethod.ByteArrayToString(msgTran.AryData, 3, nEpcLength);
                string strPC = CommondMethod.ByteArrayToString(msgTran.AryData, 1, 2);
                string strRSSI = (msgTran.AryData[nLength - 1] - 129).ToString() + " dBm";
                byte btTemp = msgTran.AryData[0];
                byte btAntId = (byte)((btTemp & 0x03) + 1);
                byte btFreq = (byte)(btTemp >> 2);
                string strFreq = GetFreqString(btFreq) + " MHz";


                tagData.strEpc = strEPC;
                tagData.strPc = strPC;
                tagData.strRssi = strRSSI;
                tagData.strCarrierFrequency = strFreq;
                tagData.btAntId = btAntId;

                //TODO：根据excel中的配置，以逗号分割的，开头几位拉匹配，不满足规格的删除

                RealTimeTagDataList.Add(tagData);
                string lowerEpcTagStr = tagData.strEpc.Replace(" ", "").ToLower();
                InsertEpcTag2AntIdRecord(lowerEpcTagStr, Convert.ToString(tagData.btAntId));
                InsertEpcTagList(ref RealTimeEpcTag, lowerEpcTagStr);
            }
            RunLoopInventroy();
        }

        //读写器复位
        private void ResetReader_rfid(object sender, EventArgs e)
        {
            int nRet = reader.Reset(m_curSetting.btReadId);
            if (nRet != 0)
            {
                string strLog = "复位读写器失败";

            }
            else
            {
                string strLog = "复位读写器";

            }
        }

        private string GetFreqString(byte btFreq)
        {
            string strFreq = string.Empty;

            if (m_curSetting.btRegion == 4)
            {
                float nExtraFrequency = btFreq * m_curSetting.btUserDefineFrequencyInterval * 10;
                float nstartFrequency = ((float)m_curSetting.nUserDefineStartFrequency) / 1000;
                float nStart = nstartFrequency + nExtraFrequency / 1000;
                string strTemp = nStart.ToString("0.000");
                return strTemp;
            }
            else
            {
                if (btFreq < 0x07)
                {
                    float nStart = 865.00f + Convert.ToInt32(btFreq) * 0.5f;

                    string strTemp = nStart.ToString("0.00");

                    return strTemp;
                }
                else
                {
                    float nStart = 902.00f + (Convert.ToInt32(btFreq) - 7) * 0.5f;

                    string strTemp = nStart.ToString("0.00");

                    return strTemp;
                }
            }
        }


    }
}
