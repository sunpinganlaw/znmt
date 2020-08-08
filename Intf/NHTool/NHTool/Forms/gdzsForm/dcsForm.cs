using NHTool.Common;
using NHTool.Device.GDZS;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NHTool.Forms.gdzsForm
{
    public partial class dcsForm : Form
    {
        
        UdpServer dcsUdpServer = null;
        UdpHeader udpHeader = new UdpHeader();
        List<UdpPointInfo> udpPointList = null;
        private int count = 0;
        private int showMaxCount = 50;
        private System.Collections.Concurrent.ConcurrentDictionary<String, UdpPointInfo> valuesMap = new System.Collections.Concurrent.ConcurrentDictionary<string, UdpPointInfo>();
        public OPCTool myOPCTool = null;
        Dictionary<String, Point> opcPointsConfig = new Dictionary<string, Point>();

        public dcsForm()
        {
            InitializeComponent();
        }


        /// 加载CSV文件
        /// </summary>
        /// <param name="csvLine"></param>
        /// <returns></returns>
        private UdpPointInfo udpPointFromCsv(string csvLine)

        {
            UdpPointInfo pointInfo = new UdpPointInfo();
            string[] values = csvLine.Split(',');
            pointInfo.SourceName = values[0];
            pointInfo.SourceNumber = values[1];
            pointInfo.Type = values[3];
            pointInfo.Value = null;
            pointInfo.DeviceCode = values[4];
            valuesMap.TryAdd(pointInfo.SourceNumber, pointInfo);
            Point opcpoint = new Point();
            opcpoint.TotalTagName = pointInfo.DeviceCode;
            opcPointsConfig.Add(opcpoint.TotalTagName, opcpoint);
            return pointInfo;

        }


        /// <summary>
        /// 更新程序运行过程信息到界面
        /// </summary>
        /// <param name="msg">信息</param>
        private void showUI(string msg)
        {

            this.inforRichBox.Invoke(new Action(() =>
            {
                count++;
                if (count > showMaxCount)
                {
                    this.inforRichBox.Clear();
                    count = 0;
                }
                this.inforRichBox.AppendText(System.DateTime.Now.ToString() + "--->>>" + msg + "\r\n");
            }));


        }

        private void dcsForm_Load(object sender, EventArgs e)
        {
            udpPointList = File.ReadAllLines(System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase + "etc\\dip_pnt.csv").Skip(1).Select(v => udpPointFromCsv(v)).ToList();

            cfg_Path_Txt.Text ="etc\\dip_pnt.csv";
            cfg_PORT_Txt.Text =  ConfigTool.GetValueFromIni("UDP", "port");
            if (udpPointList != null && udpPointList.Count > 0)
            {
                myOPCTool = new OPCTool();
                myOPCTool.startOpcServer(opcPointsConfig);



                showUI("加载DCS测点信息成功，加载条数：" + udpPointList.Count);
                dcsUdpServer = new UdpServer(int.Parse(cfg_PORT_Txt.Text));
                dcsUdpServer.ReceivedDataEvent += DcsUdpServer_ReceivedDataEvent;
                dcsUdpServer.Start();
            }
        }


        /// <summary>
        /// Udp接收到数据的异步处理
        /// </summary>
        /// <param name="state"></param>
        private void DcsUdpServer_ReceivedDataEvent(SocketState state)
        {
            showUI("接收到信息成功：" + state.ReceivedTime.ToString());

            byte[] receiveData = new byte[state.Buffer.Length];

            Array.Copy(state.Buffer, receiveData, state.Buffer.Length);
            if (state.Buffer.Length < 16)
            {
                showUI("dataOriginal-----invaild headerNumbers receiveData" + state.Buffer.Length);

            }
            else
            {
                udpHeader.DomainNum = Convert.ToString(receiveData[0], 16);
                udpHeader.DropNum = Convert.ToString(receiveData[1], 16);
                byte[] date = new byte[4];
                byte[] pointNumber = new byte[2];
                Array.Copy(receiveData, 4, date, 0, 4);
                Array.Copy(receiveData, 10, pointNumber, 0, 2);
                System.DateTime startTime = TimeZone.CurrentTimeZone.ToLocalTime(new System.DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc));
                //DateTime dt = startTime.AddMilliseconds(BitConverter.ToUInt32(date, 0));
                //udpHeader.PackTimeMs = BitConverter.ToDouble(date, 0).ToString();
                udpHeader.NPoints = BitConverter.ToUInt16(pointNumber, 0);
                showUI("收到报文发出时间：" + System.DateTime.Now.ToString() + " 报文有效点数：" + udpHeader.NPoints);
                if (udpHeader.NPoints > 100)
                {
                    showUI("报文中有效点数非法：" + udpHeader.NPoints);

                }
                else
                {

                    if (receiveData.Length - 16 != udpHeader.NPoints * 12)
                    {

                        showUI("报文字节非法：" + udpHeader.NPoints + ":对应的字节数量为" + (receiveData.Length - 16).ToString());
                    }
                    else
                    {

                        for (int i = 0; i < udpHeader.NPoints; i++)
                        {
                            byte[] point = new byte[12];
                            Array.Copy(receiveData, 16 + 12 * i, point, 0, 12);
                            byte[] deviceCodeByte = new byte[4];
                            Array.Copy(point, 0, deviceCodeByte, 0, 4);
                            string SourceNumber = BitConverter.ToInt32(deviceCodeByte, 0).ToString();
                            if (valuesMap.ContainsKey(SourceNumber))
                            {
                                valuesMap[SourceNumber].setBytes(point);

                                string infoMsg ="解析报文数据："+ valuesMap[SourceNumber].DeviceCode + "-->>"+valuesMap[SourceNumber].Value.ToString();

                                showUI(infoMsg);
                                LogTool.WriteLog(typeof(dcsForm), infoMsg);

                                Dictionary<string, string> retSet = myOPCTool.setValue(valuesMap[SourceNumber].DeviceCode, valuesMap[SourceNumber].Value);
                                String resCode = retSet[Commons.RES_CODE];
                                if ("1".Equals(resCode))
                                {
                                    String resMsg = retSet[Commons.RES_MSG];
                                    string errMsg = "写命令数据失败:-->" + valuesMap[SourceNumber].DeviceCode + ":" + valuesMap[SourceNumber].Value + "-->" + resMsg;
                                    LogTool.WriteLog(typeof(dcsForm), errMsg);
                                    showUI(errMsg);
                                }

                            }
                            else
                            {

                                continue;
                            }

                        }
                    }
                }
                
            }

        }
    }
}
