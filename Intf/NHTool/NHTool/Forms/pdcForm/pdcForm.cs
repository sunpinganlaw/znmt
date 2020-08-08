using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EasyModbus;
using System.IO.Ports;
using System.Threading;
using log4net;
using NHTool.Common;
using Newtonsoft.Json.Linq;
using NHTool.Device.SeriPort;

namespace NHTool.Forms.pdcForm
{
    public partial class pdcForm : Form
    {

        private SerialPortTool seriportTool = null;
        private string readFlag = "";
        private int count = 1;
        private DataBaseTool dataBaseTool = null;
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        public pdcForm()
        {
            InitializeComponent();


        }

        private void pdcForm_Load(object sender, EventArgs e)
        {
             dataBaseTool = new DataBaseTool();
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            seriportTool = new SerialPortTool(ctlConfig["SERIAL_COM"], ctlConfig["SERIAL_BUAL"], ctlConfig["SERIAL_DATAPARITY"], ctlConfig["SERIAL_DATABIT"], ctlConfig["SERIAL_STOPBIT"]);
            seriportTool.comPort.DataReceived += comPort_DataReceived;

            Thread cmdThread = new Thread(PdcCmdOutStart);
            cmdThread.IsBackground = true;
            System.Threading.Thread.Sleep(50);
            cmdThread.Start();

            

        }



        //01 03 2C 00 00 00 06 FF FF FE 04 00 00 07 D1 00 00 00 00 FF FF FC 18 FF FF FF BA 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 02 00 3E 90 35 F1 55 

        //02 03 2C 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF FF FC 18 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 02 00 43 C6 35 E3 A9 

        // <summary>
        /// 更新串口重量数据，各个项目需要根据衡器协议覆写方法解析
        /// </summary>
        /// <param name="args">串口事件返回的数据</param>
        public DateTime lastGetWegihtTime = DateTime.Now;
        public virtual void comPort_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            Thread.Sleep(100);
            SerialPort comPort = (SerialPort)sender;

            byte[] ReceiveData = null;

            while (comPort.BytesToRead > 0)
            {
                byte[] readBuffer = new byte[comPort.ReadBufferSize + 1];
                int count = comPort.Read(readBuffer, 0, comPort.ReadBufferSize);
                ReceiveData = new byte[count];
                for (int i = 0; i < count; i++)
                {
                    ReceiveData[i] = readBuffer[i];
                }
            }

            try
            {
                string returnStr = "";
                if (ReceiveData != null)
                {
                    for (int i = 0; i < ReceiveData.Length; i++)
                    {
                        returnStr += ReceiveData[i].ToString("X2");
                    }
                }

                this.inforRichBox.Invoke(new Action(() =>
                {
                    count++;
                    this.inforRichBox.AppendText(returnStr + "\r\n");
                    if(count>20)
                    {

                        this.inforRichBox.Clear();
                        count = 0;
                    }

                }));

            

                if (ReceiveData.Length >= 49)
                {
                    lastGetWegihtTime = DateTime.Now;

                    byte[] vaue_ss = new byte[2];
                    vaue_ss[0] = ReceiveData[9];
                    vaue_ss[1] = ReceiveData[10];
                    string vaue_ss_str = "";
                    for (int i = 0; i < vaue_ss.Length; i++)
                    {
                        vaue_ss_str += vaue_ss[i].ToString("X2");
                    }

                    byte[] vaue_lj = new byte[4];
                    vaue_lj[0] = ReceiveData[43];
                    vaue_lj[1] = ReceiveData[44];
                    vaue_lj[2] = ReceiveData[45];
                    vaue_lj[3] = ReceiveData[46];
                    string vaue_ll_str = "";
                    for (int i = 0; i < vaue_lj.Length; i++)
                    {
                        vaue_ll_str += vaue_lj[i].ToString("X2");
                    }


                    var pdc_ss = Convert.ToUInt32(vaue_ss_str, 16);
                    var pdc_lj = Convert.ToUInt32(vaue_ll_str, 16);

                    if (ReceiveData[0] == 0x01)
                    {
                        this.pdcSSBox_1.Invoke(new Action(() =>
                        {
                            this.pdcSSBox_1.Text = pdc_ss.ToString();
                        }));

                        this.pdcLJBox_1.Invoke(new Action(() =>
                        {
                            this.pdcLJBox_1.Text = pdc_lj.ToString();
                        }));

                        JArray jarry = new JArray();
                        JObject temp = new JObject();
                        temp.Add("SubDeviceID", "PDCA_SS");
                        temp.Add("CurrentValue", pdc_ss.ToString());
                        jarry.Add(temp);


                        temp = new JObject();
                        temp.Add("SubDeviceID", "PDCA_LJ");
                        temp.Add("CurrentValue", pdc_lj.ToString());
                        jarry.Add(temp);
                        if (jarry.Count > 0)
                        {


                            dataBaseTool.record_SubDeviceState(Convert.ToString(jarry).Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));
                        }


                    }
                    if (ReceiveData[0] == 0x02)
                    {

                        this.pdcSSBox_2.Invoke(new Action(() =>
                        {
                            this.pdcSSBox_2.Text = pdc_ss.ToString();
                        }));

                        this.pdcLJBox_2.Invoke(new Action(() =>
                        {
                            this.pdcLJBox_2.Text = pdc_lj.ToString();
                        }));


                        JArray jarry = new JArray();
                        JObject temp = new JObject();
                        temp.Add("SubDeviceID", "PDCB_SS");
                        temp.Add("CurrentValue", pdc_ss.ToString());
                        jarry.Add(temp);


                        temp = new JObject();
                        temp.Add("SubDeviceID", "PDCB_LJ");
                        temp.Add("CurrentValue", pdc_lj.ToString());
                        jarry.Add(temp);
                        if (jarry.Count > 0)
                        {


                            dataBaseTool.record_SubDeviceState(Convert.ToString(jarry).Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));
                        }
                    }


                }
                else
                {


                }
            }catch(Exception ex)
            {
                //LogTool.WriteLog(typeof(pdcForm), ex);

            }
           
           

        }

        //---01 03 00 00 00 16 C4 04(5段皮带甲）
        //02 03 00 00 00 16  C4 37 (5段皮带乙）
        private void PdcCmdOutStart()
        {
            while (true)
            {

                byte[] quest = new byte[8];
                quest[0] = 0x01;
                quest[1] = 0x03;
                quest[2] = 0x00;
                quest[3] = 0x00;
                quest[4] = 0x00;
                quest[5] = 0x16;
                quest[6] = 0xC4;
                quest[7] = 0X04;
                seriportTool.WriteData(quest);
                System.Threading.Thread.Sleep(2000);
                quest[0] = 0x02;
                quest[6] = 0xC4;
                quest[7] = 0x37;
                seriportTool.WriteData(quest);
                System.Threading.Thread.Sleep(2000);
            }

        }

        //private void InitializeComponent()
        //{
        //    this.SuspendLayout();
        //    // 
        //    // pdcForm
        //    // 
        //    this.ClientSize = new System.Drawing.Size(953, 524);
        //    this.Name = "pdcForm";
        //    this.ResumeLayout(false);

        //}

        //private void InitializeComponent()
        //{
        //    this.SuspendLayout();
        //    // 
        //    // pdcForm
        //    // 
        //    this.ClientSize = new System.Drawing.Size(702, 399);
        //    this.Name = "pdcForm";
        //    this.ResumeLayout(false);

        //}
    }
}
