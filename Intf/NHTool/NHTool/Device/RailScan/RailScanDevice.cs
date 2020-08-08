using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NHTool.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using NHTool.Device.SeriPort;
using System.IO.Ports;
using UHFDemo;
using System.Collections.Concurrent;

namespace NHTool.Device.RailScan
{
    public class RailScanDevice
    {
        public SerialPortTool railPort = null;
        Dictionary<String, String> ret = new Dictionary<String, String>();
        public Byte[] RecvDataBuff = null;
        public Byte[,] SendDataBuff = null;
        public Byte[] parseDataBuff = null;
        public Int32 m_DeviceStatus = 0;
        private const Byte STR2_HEAD = 0xFA;
        private const Byte STR2_END = 0xF5;
        private const Byte VALIDBIT = 0x007F;
        private const Byte RAIL_NORMAL = 0x07;
        private const Byte RAIL_TWO_WAY = 0x08;
        private const Byte RAIL_OLD = 0x09;
        private const Byte ASCII6TO8 = 0x20;

        private const Byte STR2_SET_FREQUENCY = 0x01; // 设置频率
        private const Byte STR2_SET_SUSTAINED_TIME1 = 0x02; // 设置“数据有效指示”持续时间
        private const Byte STR2_SET_BAUDRATE = 0x03; // 设置波特率
        private const Byte STR2_RESET = 0x04; // 系统复位
        private const Byte STR2_CLOSE = 0x05; // 关闭功放
        private const Byte STR2_GET_STATUS = 0x06; // 获取系统状态
        private const Byte STR2_SET_SUSTAINED_TIME2 = 0x0C; // 读“数据有效指示”持续时间
        private const Byte STR2_OPEN = 0x0A;
        private const int SBUFFNUM = 64;
        private const int RBUFFNUM = 4096;

        public RAILINFOMATION RailInfo;
        private Thread readThread = null;
        private Thread checkStateThread = null;
        private int proStart = 0;
        private int proEnd = 0;

        private int proSendStart = 0;
        private int proSendEnd = 0;

        private const int RAILBUFF = 256;
        public int Pstart = 0;
        public int Pend = 0;
        public string[] RailID = new string[RAILBUFF];
        public string[] RailNum = new string[RAILBUFF];
        private Thread incomeThread = null;
        private Thread turnoverThread = null;
        public ConcurrentQueue<string> trainlist = null;//队列

        private String func = null;//区分入厂还是翻车机
        HttpDbTool httpDbTool = null;
        public RailScanDevice()
        {
            RecvDataBuff = new Byte[RBUFFNUM];
            SendDataBuff = new Byte[SBUFFNUM, 2];
            parseDataBuff = new Byte[32];
            RailInfo = new RAILINFOMATION();
        }

        public Dictionary<string, string> InitRailScanPortHA(String function, string name, string baud, string par, string dBits, string sBits)
        {    //初始化c#中调用数据库的工具类实例
            if (null != httpDbTool)
            {
                httpDbTool = new HttpDbTool();
            }
            func = function;
            trainlist = new ConcurrentQueue<string>();
            ret.Clear();
            railPort = new SerialPortTool(name, baud, par, dBits, sBits);
            railPort.comPort.DataReceived += processRecvComData;
            readThread = new Thread(SendDataThread);
            readThread.IsBackground = true;
            readThread.Start();

            checkStateThread = new Thread(CheckDeviceStatue);
            checkStateThread.IsBackground = true;
            checkStateThread.Start();

            if ("income".Equals(function))
            {
                //处理入厂和出厂的数据
                incomeThread = new Thread(dealIncomeThread);
                incomeThread.IsBackground = true;
                incomeThread.Start();
            }
            if ("turnover".Equals(function))
            {
                //翻车机前，执行调度用
                turnoverThread = new Thread(dealTurnoverThread);
                turnoverThread.IsBackground = true;
                turnoverThread.Start();
            }
            OpenRailDevice();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            return ret;
        }
        public Dictionary<string, string> InitRailScanPort(string name, string baud, string par, string dBits, string sBits)
        {    //初始化c#中调用数据库的工具类实例
            if (null != httpDbTool)
            {
                httpDbTool = new HttpDbTool();
            }
           
            ret.Clear();
            railPort = new SerialPortTool(name, baud, par, dBits, sBits);
            railPort.comPort.DataReceived += processRecvComData;
            readThread = new Thread(SendDataThread);
            readThread.IsBackground = true;
            readThread.Start();
 
           
            OpenRailDevice();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            return ret;
        }
        private void processRecvComData(object sender, SerialDataReceivedEventArgs e)
        {
            int parseLen = 0;
            SerialPort comPort = (SerialPort)sender;
            while (comPort.BytesToRead > 0)
            {
                byte[] readBuffer = new byte[comPort.ReadBufferSize + 1];
                int count = comPort.Read(readBuffer, 0, comPort.ReadBufferSize);
                for (int i = 0; i < count; i++)
                {
                    if (proEnd >= 4095)
                    {
                        RecvDataBuff[proEnd] = readBuffer[i];
                        proEnd = 0;
                    }
                    else
                    {
                        RecvDataBuff[proEnd] = readBuffer[i];
                        proEnd++;
                    }
                }
                //只记录有用的车卡信息
                //  LogTool.WriteLog(typeof(RailScanDevice),CommondMethod.ByteArrayToString(readBuffer,0,count));
                if (AnalysisData(out parseLen))
                {
                    if (CheckSum(parseLen))
                    {
                        ParseData();
                    }
                }
            }
        }
        public void OpenRailDevice()
        {
            SendDataBuff[proSendEnd, 0] = STR2_OPEN;
            proSendEnd++;
            if (proSendEnd >= SBUFFNUM)
            {
                proSendEnd = 0;
            }
            LogTool.WriteLog(typeof(RailScanDevice), "重新打开功放");
        }

        public void CheckDeviceStatue()
        {
            while (checkStateThread.IsBackground)
            {
                //600s 开一次功放
                Thread.Sleep(600000);
                OpenRailDevice();
            }
        }

        private void SendDataThread()
        {
            while (readThread.IsBackground)
            {
                Thread.Sleep(300);
                if (!proSendStart.Equals(proSendEnd))
                {
                    Byte[] data = new Byte[4];
                    int len = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        data[i] = 0;
                    }
                    switch (SendDataBuff[proSendStart, 0])
                    {
                        case STR2_SET_FREQUENCY:
                            data[0] = STR2_HEAD;
                            data[1] = STR2_SET_FREQUENCY;
                            data[2] = SendDataBuff[proSendStart, 1];
                            data[3] = STR2_END;
                            len = 4;
                            break;
                        case STR2_SET_BAUDRATE:
                            data[0] = STR2_HEAD;
                            data[1] = STR2_SET_BAUDRATE;
                            data[2] = SendDataBuff[proSendStart, 1];
                            data[3] = STR2_END;
                            len = 4;
                            break;
                        case STR2_GET_STATUS:
                            data[0] = STR2_HEAD;
                            data[1] = STR2_GET_STATUS;
                            data[2] = STR2_END;
                            len = 3;
                            break;
                        case STR2_OPEN:
                            data[0] = STR2_HEAD;
                            data[1] = STR2_OPEN;
                            data[2] = STR2_END;
                            len = 3;
                            break;
                        case STR2_CLOSE:
                            data[0] = STR2_HEAD;
                            data[1] = STR2_CLOSE;
                            data[2] = STR2_END;
                            len = 3;
                            break;
                        default:
                            break;
                    }
                    proSendStart++;
                    if (proSendStart >= SBUFFNUM)
                    {
                        proSendStart = 0;
                    }
                    railPort.WriteData(data, 0, len);
                }
            }
        }

        private Boolean AnalysisData(out int nLen)
        {
            int state = 0;
            Boolean m_ParseFlag = false;
            nLen = 0;
            for (int i = 0; i < 32; i++)
            {
                parseDataBuff[i] = 0x00;
            }
            //有数据没有解析
            while (!proStart.Equals(proEnd) && !m_ParseFlag)
            {
                switch (state)
                {
                    case 0://找报头
                        if (RecvDataBuff[proStart].Equals(STR2_HEAD))
                        {
                            nLen = 0;
                            parseDataBuff[nLen] = RecvDataBuff[proStart];
                            state = 1;
                        }
                        break;
                    case 1:
                        //接收数据
                        nLen++;
                        if (nLen < 32)
                        {
                            //写入接收包
                            parseDataBuff[nLen] = RecvDataBuff[proStart];
                            if (RecvDataBuff[proStart].Equals(STR2_END))
                            {
                                //接收到完整数据包	
                                state = 0;
                                nLen = nLen + 1;
                                m_ParseFlag = true;
                            }
                        }
                        else
                        {
                            //数据包太长，扔掉当前接收的，重新查找报头
                            state = 0;
                        }
                        break;
                }
                proStart++;
                if (proStart >= 4096) proStart = 0;
            }
            return m_ParseFlag;
        }

        private Boolean CheckSum(int len)
        {
            Byte sum = 0x00;
            for (int i = 1; i < len - 2; i++)
            {
                sum += parseDataBuff[i];
            }
            sum = Convert.ToByte((~sum + 1) & VALIDBIT);
            if (sum.Equals(parseDataBuff[len - 2]))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private Boolean invokeCardDB()
        {
            return false;
        }
        private Byte[] fromByteData(List<Byte> dataArray, int startAdd, int dataLength)
        {
            Byte[] retArray = new Byte[dataLength];
            for (int i = 0; i < dataLength; i++)
            {
                retArray[i] = dataArray[startAdd + i];
            }
            return retArray;
        }

        private string fromByteString(Byte[] dataArray, int startAdd, int dataLength)
        {
            string railStr = "";
            for (int i = 0; i < dataLength; i++)
            {

                railStr += Convert.ToString(dataArray[startAdd + i] & 0x0f);
            }
            return railStr;
        }

        private void ParseData()
        {
            Byte Fun;
            Byte Type;//车类型：机车；路用货车；企业自备车
            Byte Class;//车厢类型
            int i;
            Fun = parseDataBuff[1];
            Type = parseDataBuff[2];
            Class = parseDataBuff[3];
            switch (Fun)
            {
                case RAIL_NORMAL:
                    //获取车次号
                    RailInfo.m_RailNum = CommondMethod.ByteArrayToString(parseDataBuff, 3, 8);

                    //获取车厢号
                    RailInfo.m_RailID = fromByteString(parseDataBuff, 9, 7);
                    RailInfo.m_CurrentRailID = RailInfo.m_RailID;
                    RailInfo.m_DumperRailID = RailInfo.m_RailID;
                    RailInfo.m_WeightRailID = RailInfo.m_RailID;
                    //获取车厢制造年
                    RailInfo.m_Date = CommondMethod.ByteArrayToString(parseDataBuff, 19, 20) + "年";
                    RailInfo.m_Type = GetRailType(Type);
                    //获取车厢种类
                    RailInfo.m_Class = GetRailClass(Class);
                    //获取车辆制造商
                    RailInfo.m_Manufacturer = GetRailManufacturer(parseDataBuff[18]);
                    //获取车厢类型
                    //第一次的时候上一个号为null
                    if (RailInfo.m_LastRailID == null)
                    {
                        trainlist.Enqueue(RailInfo.m_RailID);
                        //记录读取到车号 
                        LogTool.WriteLog(typeof(RailScanDevice), RailInfo.m_RailID);
                    }
                    else
                    {

                        if (!RailInfo.m_LastRailID.Equals(RailInfo.m_RailID) && !RailInfo.m_RailID.Equals(""))
                        {
                            trainlist.Enqueue(RailInfo.m_RailID);
                            //记录读取到车号     
                            LogTool.WriteLog(typeof(RailScanDevice), RailInfo.m_RailID);
                        }
                        else
                        {
                            //   不处理
                        }
                    }

                    RailInfo.m_LastRailID = RailInfo.m_RailID;

                    break;
                case RAIL_OLD://机车
                    break;
                case RAIL_TWO_WAY://双向机车
                    break;
                case STR2_CLOSE:
                    break;
                case STR2_OPEN:
                    break;
                case STR2_GET_STATUS://当前状态data2--波特率，data3--功放
                    if ((Byte)(parseDataBuff[3] & 0x40) > 0)
                    {
                        m_DeviceStatus = 1;
                    }
                    else
                    {
                        m_DeviceStatus = 0;
                        CheckDevice();
                    }
                    break;
                default:
                    break;
            }
        }

        private string GetRailType(Byte Type)
        {
            Type = (Byte)(Type + ASCII6TO8);
            string str = "预留";
            if (Type.Equals(0x54))
            {
                str = "路用货车";
            }
            if (Type.Equals(0x4a))
            {
                str = "机车";
            }
            if (Type.Equals(0x51))
            {
                str = "企业自备车";
            }
            return str;
        }

        string GetRailClass(Byte Class)
        {
            string str;
            Class = (Byte)(Class + ASCII6TO8);
            switch (Class)
            {
                case 0x42:
                    str = "冷藏车";
                    break;
                case 0x43:
                    str = "敞车";
                    break;
                case 0x44:
                    str = "长大货车";
                    break;
                case 0x47:
                    str = "罐车";
                    break;
                case 0x4a:
                    str = "家畜车";
                    break;
                case 0x4b:
                    str = "矿石车";
                    break;
                case 0x4c:
                    str = "粮食车";
                    break;
                case 0x4e:
                    str = "平车";
                    break;
                case 0x50:
                    str = "棚车";
                    break;
                case 0x53:
                    str = "守车";
                    break;
                case 0x54:
                    str = "特种车";
                    break;
                case 0x55:
                    str = "水泥车";
                    break;
                case 0x57:
                    str = "毒品车";
                    break;
                case 0x58:
                    str = "集装箱车";
                    break;
                default:
                    str = "预留";
                    break;
            }
            return str;
        }

        string GetRailManufacturer(Byte Manufacturer)
        {
            string str;
            Manufacturer = (Byte)(Manufacturer + ASCII6TO8);
            switch (Manufacturer)
            {
                case 0x41:
                    str = "齐齐哈尔铁路车辆有限责任公司";
                    break;
                case 0x42:
                    str = "哈尔滨车辆厂";
                    break;
                case 0x43:
                    str = "沈阳机车车辆有限责任公司";
                    break;
                case 0x44:
                    str = "大连机车车辆厂";
                    break;
                case 0x45:
                    str = "唐山机车车辆厂";
                    break;
                case 0x46:
                    str = "北京二七车辆厂";
                    break;
                case 0x47:
                    str = "石家庄车辆厂";
                    break;
                case 0x48:
                    str = "太原机车车辆厂";
                    break;
                case 0x49:
                    str = "济南机车车辆厂";
                    break;
                case 0x4a:
                    str = "浦镇车辆厂";
                    break;
                case 0x4b:
                    str = "四方机车车辆厂";
                    break;
                case 0x4c:
                    str = "戚墅堰机车车辆厂";
                    break;
                case 0x4d:
                    str = "铜陵车辆厂";
                    break;
                case 0x4e:
                    str = "武汉江岸车辆厂";
                    break;
                case 0x4f:
                    str = "株洲车辆厂";
                    break;
                case 0x50:
                    str = "柳州机车车辆厂";
                    break;
                case 0x51:
                    str = "洛阳机车厂";
                    break;
                case 0x52:
                    str = "广州铁道车辆厂";
                    break;
                case 0x53:
                    str = "成都机车车辆厂";
                    break;
                case 0x54:
                    str = "贵州内燃机厂";
                    break;
                case 0x55:
                    str = "眉山车辆厂";
                    break;
                case 0x56:
                    str = "贵阳车辆厂";
                    break;
                case 0x57:
                    str = "西安车辆厂";
                    break;
                case 0x58:
                    str = "兰州车辆厂";
                    break;
                case 0x59:
                    str = "内蒙一机厂";
                    break;
                case 0x5a:
                    str = "重庆重型铸锻厂";
                    break;
                default:
                    str = "预留";
                    break;
            }
            return str;
        }

        public void CheckDevice()
        {
           // railPort.OpenPort();
            //LogTool.WriteLog(typeof(RailScanDevice), "重新开→关车号识别器串口");
            SendDataBuff[proSendEnd, 0] = STR2_GET_STATUS;
            proSendEnd++;
            if (proSendEnd >= SBUFFNUM)
            {
                proSendEnd = 0;
            }
            //LogTool.WriteLog(typeof(RailScanDevice), "重新打开功放");
        }
        //处理入厂车号识别器
        public void dealIncomeThread()
        {
            while (incomeThread.IsBackground)
            {
                if (trainlist.Count != 0)
                {
                  
                    string railID;
                    trainlist.TryPeek(out railID);
                    int f = invokeTrainIncome(railID);
                    if (f == 0)
                    {

                        trainlist.TryDequeue(out railID);

                    }
                }
                Thread.Sleep(1000);
            }
        }
        //处理翻车机前的车号识别 
        public void dealTurnoverThread()
        {
            while (turnoverThread.IsBackground)
            {
                if (trainlist.Count != 0)
                {
                    //翻车调度
                    string railID;
                    trainlist.TryPeek(out railID);
                    int f = invokeTrainTurnOver(railID);
                    if (f == 0)
                    {
                        trainlist.TryDequeue(out railID);
                        LogTool.WriteLog(typeof(RailScanDevice), "处理车号" + railID);
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(RailScanDevice), "车号" + railID + "处理失败！弹出队列");
                        trainlist.TryDequeue(out railID);
                    }
                }
                Thread.Sleep(1000);
            }
        }

        //调运火车相关过程 
        public int invokeTrainIncome(string railID)
        {
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }
            int result = 0;
            JObject dataJson = new JObject();
            dataJson.Add("RAilID", railID);
            JObject retJson = null;
            try
            {

                retJson = httpDbTool.invokeProc("pkg_train.trainIncome", dataJson);
                if ("0".Equals(Commons.getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(Commons.getJsonValue(retJson, "logicRetCode")))
                {
                    result = 0;
                }
                else
                {
                    result = 1;
                }
                return result;
            }
            catch (Exception e)
            {
                result = 1;
                LogTool.WriteLog(typeof(RailScanDevice), "invokeSendCYCMD数据库调用失败：" + e.Message);
                return result;
              

            }

        }
        //调运翻车调度
        public int invokeTrainTurnOver(string railID)
        {
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }
            int result = 0;
            JObject dataJson = new JObject();
            dataJson.Add("RAilID", railID);
            JObject retJson = null;
            try
            {
                retJson = httpDbTool.invokeProc("pkg_train.do_schedule", dataJson);
                if ("0".Equals(Commons.getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(Commons.getJsonValue(retJson, "logicRetCode")))
                {
                    result = 0;
                }
                else
                {
                    result = 1;
                }
                return result;
            }
            catch (Exception e)
            {
                result = 1;

                LogTool.WriteLog(typeof(RailScanDevice), "数据库调用失败：" + e.Message);
                return result;

            }

        }

    }

    public class RAILINFOMATION
    {
        public string m_Type;//车类型 机车 路用货车 企业自备车等
        public string m_Class;//车厢种类
        public string m_RailNum;//车次
        public string m_RailID;
        public string m_Manufacturer;
        public string m_Date;
        public string m_LastRailID;
        public string m_LLastRailID;
        public string m_CurrentRailID;// Current Rail ID
        public string m_DumperRailID;// Current Dump Rail ID 
        public string m_LastDumperRailID;
        public string m_DumperRailNum;
        public string m_WeightRailID;
    }
}
