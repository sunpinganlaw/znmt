using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using NHTool.Business.Car_IN;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NHTool.Common;
using NHTool.Business;
using NHTool.Business.CAR_CY;
using NHTool.Business.CAR_CZ.CAR_MZ;
using System.Threading;
using System.Xml.Linq;
using System.Xml;
using log4net;
using System.IO;
using NHTool.Business.CAR_CZ.CAR_PZ;
using System.Reflection;
using NHTool.Business.Car_XM;

namespace NHTool.Forms.carInForm
{
    public partial class carInForm : Form
    {
        public carInForm()
        {
            InitializeComponent();
        }

        public BusinessBase businessBase = null;
        public enum modbusPoint : int
        {
            groundSense = 1,
            inRadiation = 3,
            outRadiation = 2,
            frontRadiation = 4,
            backRadiation = 5,
            frontGateUpOut = 17,
            frontGateDownOut = 18,
            backGateUpOut = 19,
            backGateDownOut = 20,
            lightRedOut = 16,
            lightGreenOut = 16
        }


       
        string currentFlowId = "";
      

        private void businessLoad()
        {


            Dictionary<string, string> tmpCtlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            currentFlowId = Commons.getDcValue(tmpCtlConfig, "currentFlowId");
            string flowClassPackage = Commons.getDcValue(tmpCtlConfig, "flowClassPackage");
            string flowClassMame = Commons.getDcValue(tmpCtlConfig, "flowClassMame");
            //businessBase = BusinessBase.CreateInstance<BusinessBase>("NHTool", flowClassPackage, flowClassMame);
            businessBase = new CarXMZJ();
            businessBase.refreshEvent += updateUI;
            businessBase.mainProcess();


        }

        private void carInForm_Load(object sender, EventArgs e)
        {
            infoDataGridView.AllowUserToResizeColumns = false;
            infoDataGridView.AllowUserToResizeRows = false;
            infoDataGridView.Rows.Clear();

            businessLoad();

           

        }


        private void updateUI(JObject json)
        {
            Dictionary<string, string> infoDictionary = new Dictionary<string, string>();
            Dictionary<string, string> showDictionary = new Dictionary<string, string>();
            JObject result = json;
            foreach (var item in result)
            {

                if (item.Key.Equals(Commons.inRadiation))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        inRadiationPB.Image = NHTool.Properties.Resources.red;

                    }
                    else
                    {

                        inRadiationPB.Image = NHTool.Properties.Resources.green;
                    }

                }

                if (item.Key.Equals(Commons.outRadiation))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        outRadiationPB.Image = NHTool.Properties.Resources.red;

                    }
                    else
                    {

                        outRadiationPB.Image = NHTool.Properties.Resources.green;
                    }

                }
                if (item.Key.Equals(Commons.frontRadiation))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        frontRadiationPB.Image = NHTool.Properties.Resources.red;

                    }
                    else
                    {

                        frontRadiationPB.Image = NHTool.Properties.Resources.green;
                    }


                }
                if (item.Key.Equals(Commons.backRadiation))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        backRadiationPB.Image = NHTool.Properties.Resources.red;

                    }
                    else
                    {

                        backRadiationPB.Image = NHTool.Properties.Resources.green;
                    }

                }

                if (item.Key.Equals(Commons.frontGateUpOut))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        frontGatePB.Image = NHTool.Properties.Resources.UP;

                    }

                }
                if (item.Key.Equals(Commons.frontGateDownOut))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        frontGatePB.Image = NHTool.Properties.Resources.DOWN;

                    }

                }

                if (item.Key.Equals(Commons.backGateUpOut))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        backGatePB.Image = NHTool.Properties.Resources.UP;

                    }

                }
                if (item.Key.Equals(Commons.backGateDownOut))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        backGatePB.Image = NHTool.Properties.Resources.DOWN;

                    }

                }

                if (item.Key.Equals(Commons.lightRedOut))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        LedPB.Image = NHTool.Properties.Resources.RL;

                    }

                }
                if (item.Key.Equals(Commons.lightGreenOut))
                {
                    if (item.Value.ToString().Equals(Commons.signalON))
                    {
                        LedPB.Image = NHTool.Properties.Resources.GL;

                    }

                }

                infoDictionary.Add(item.Key.ToString(), item.Value.ToString());

                if ("疑似车卡号".Equals(item.Key.ToString()) && !(item.Value.ToString()).Equals(likeCardId.Text.Trim()))
                {
                    likeCardId.Text = item.Value.ToString().Trim();
                }
             
            }


            if (infoKeyText.Text.Trim().Length > 0)
            {

                if (infoKeyText.Text.Trim().IndexOf(',') < 0)
                {

                    foreach (var item in infoDictionary)
                    {

                        if (item.Key.ToString().StartsWith(infoKeyText.Text.Trim()))
                        {
                            showDictionary.Add(item.Key.ToString(), item.Value.ToString());

                        }
                    }

                }
                else
                {
                    string[] keys = infoKeyText.Text.Trim().Split(',');
                    foreach (var key in keys)
                    {

                        foreach (var item in infoDictionary)
                        {

                            if (item.Key.ToString().StartsWith(key))
                            {
                                showDictionary.Add(item.Key.ToString(), item.Value.ToString());

                            }
                        }

                    }


                }


            }
            else
            {
                showDictionary = infoDictionary;

            }




            if (this.IsHandleCreated)
            {
                this.infoDataGridView.Invoke(new Action(() =>
                {
                    this.infoDataGridView.DataSource = showDictionary.ToArray();
                    infoDataGridView.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;

                }));

                this.infoTimeLabel.Invoke(new Action(() =>
                {

                    this.infoTimeLabel.Text = DateTime.Now.ToString("T");
                }));

            }








        }

        private void buttonFrontUp_Click(object sender, EventArgs e)
        {
            businessBase.actionFrontGateUp();
        }

        private void buttonFrontDown_Click(object sender, EventArgs e)
        {
            businessBase.actionFrontGateDown();
        }

        private void buttonLedGreen_Click(object sender, EventArgs e)
        {
            businessBase.actionLedGreen();

        }

        private void buttonLedRed_Click(object sender, EventArgs e)
        {
            businessBase.actionLedRed();
        }

        private void buttonBackUp_Click(object sender, EventArgs e)
        {
            businessBase.actionBackGateUp();

        }

        private void buttonBackDown_Click(object sender, EventArgs e)
        {
            businessBase.actionbackGateDown();
        }

        //测试
        HttpDbTool httpDbTool = null;
        private void button1_Click(object sender, EventArgs e)
        {
            //初始化c#中调用数据库的工具类实例
            httpDbTool = new HttpDbTool();
            //Dictionary<String, String> rfid_result = bus.readRfidDevice.read_RFID_Info();

            //OPCTool opcTool = new OPCTool();
            //opcTool.startOpcServer();
            //string a = System.DateTime.Now.ToString("yyyy-MM-dd  HH:mm:ss");
            //testCyAfter();
            //testCheckCar();
            //testCzBeforeCar();
            //testCzAfterCar();
            DateTime beforeTime = DateTime.Now;
            //Thread.Sleep(5 * 1000);
            TimeSpan interval = DateTime.Now - beforeTime;
            //MessageBox.Show("差值"+interval.Seconds);
            LogTool.WriteLog(typeof(carInForm), "差值" + interval.Seconds);
        }


        OPCTool myOPCTool = null;
        private void testCzBeforeCar()
        {
            //测试称重
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }

            JObject dataJson = new JObject();
            dataJson.Add("cardId", "ab0000000002018020511354");
            dataJson.Add("flowId", "CZ1");
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.cz_before_preProcess", dataJson);
            debugInfo.AppendText("testCzBeforeCar retJson=\n" + retJson.ToString());
            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                string carNo = getJsonValue(retJson, "carId");
                debugInfo.AppendText("carNo=" + carNo);
            }
        }

        private void testCzAfterCar()
        {
            //测试称重
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }
            //调用存储过程 
            JObject dataJson = new JObject();
            dataJson.Add("cardId", "ab0000000002018020511354");
            dataJson.Add("flowId", "CZ1");
            dataJson.Add("realQty", "1200");
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.cz_after_process", dataJson);
            debugInfo.AppendText("testCzAfterCar retJson=\n" + retJson.ToString());
            //调用校验存储过程成功
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {

            }
        }
        private void testCheckCar()
        {
            if (myOPCTool == null)
            {
                myOPCTool = new OPCTool();
                myOPCTool.startOpcServer();
            }


            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }
            if (myOPCTool.getPointValue("01", getLogicDeviceID(), "DeviceStatus").Equals("3"))
            {
                /*****以下代码调用存储过程发送启动检车命令************/
                JObject dataJson = new JObject();
                dataJson.Add("machin_code", getMachinCode());
                dataJson.Add("machin_type", "1");
                dataJson.Add("record_no", "");
                dataJson.Add("command_code", "1");
                dataJson.Add("sample_code", "20991211843");
                dataJson.Add("data_status", "0");
                dataJson.Add("op_code", "admin");
                dataJson.Add("sampleCoordList", testCyBefore());
                dataJson.Add("sampleCoordNumList", "3");
                String opcCommand = httpDbTool.invokeProcRetStr("pkg_device.sendWTQCCYJCmd", dataJson);
                JObject ret = myOPCTool.processHttpRequestMethod(opcCommand);
                //JObject ret = new JObject();// myOPCTool.processHttpRequestMethod(opcCommand);
                if (!getJsonValue(ret, Commons.RES_CODE).Equals("0"))
                {

                }


            }
        }


        private void testCyAfter()
        {
            JObject dataJson = new JObject();
            Dictionary<String, String> ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                dataJson.Add("cyjNo", "1");
            }
            if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                dataJson.Add("cyjNo", "2");
            }

            JArray sampleCoordList = testCyBefore();

            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }

            dataJson.Add("cardId", "ab0000000002018020511390");
            dataJson.Add("flowId", "CY1");
            dataJson.Add("recordNo", "90000000");
            dataJson.Add("machinCode", "RC_CYJ2");
            dataJson.Add("sampleCode", "20181128304");
            dataJson.Add("sampleType", "");
            dataJson.Add("samplePoints", "3");
            dataJson.Add("sampleCoordNumList", "3,11,7");
            dataJson.Add("sampleCoordList", sampleCoordList);
            dataJson.Add("startTime", System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            dataJson.Add("endTime", System.DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.cy_after_process", dataJson);
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {

            }
        }
        private JArray testCyBefore()
        {
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }
            JObject dataJson = new JObject();
            dataJson.Add("cardId", "ab0000000002018020511390");
            dataJson.Add("flowId", "CY1");
            JObject retJson = httpDbTool.invokeProc("pk_car_schedule.cy_before_preProcess", dataJson);
            String ss = retJson.ToString();

            JArray retStr = null;
            if ("0".Equals(getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(getJsonValue(retJson, "logicRetCode")))
            {
                String carNo = getJsonValue(retJson, "carId");
                String sampleCode = getJsonValue(retJson, "sampleCode");
                String recordNo = getJsonValue(retJson, "recordNo");
                String carType = getJsonValue(retJson, "carType");//
                String sampleCfgPonits = getJsonValue(retJson, "sampleCfgPoints");//
                String batchChgFlag = getJsonValue(retJson, "batchChgFlag");
                String sampleCoordNumList = getJsonValue(retJson, "sampleCoordNumList");
                JArray sampleCoordList = (JArray)retJson["sampleCoordList"];
                retStr = sampleCoordList;
                String bigWater = getJsonValue(retJson, "bigWater");
                String SuofenInterval = getJsonValue(retJson, "SuofenInterval");
                String SuofenCnt = getJsonValue(retJson, "SuofenCnt");
            }
            return retStr;
        }

        private string getLogicDeviceID()
        {
            Dictionary<String, String> ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            string logicDeviceID = "";
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                logicDeviceID = "RC_CYJ1";
            }
            else if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                logicDeviceID = "RC_CYJ2";
            }
            return logicDeviceID;
        }

        private string getMachinCode()
        {
            Dictionary<String, String> ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            string machin_code = "";
            if ("CY1".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                machin_code = "RC_CYJ1";
            }
            else if ("CY2".Equals(Commons.getDcValue(ctlConfig, "currentFlowId")))
            {
                machin_code = "RC_CYJ2";
            }
            return machin_code;
        }

        public static string getJsonValue(JObject josn, String key)
        {
            if (josn[key] != null)
            {
                return (string)josn[key];
            }
            else
            {
                return "";
            }
        }

        private void testDb()
        {
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }


            JObject dataJson = new JObject();
            dataJson.Add("begDate", "2018-11-01");
            dataJson.Add("endDate", "2018-11-02");
            //JObject ret = invokeQry("ship.qryRelayPlan", dataJson);
            JObject ret = httpDbTool.invokeQry("user.getUsers", dataJson);
            String resCode = (String)ret[Commons.RES_CODE];
            if ("0".Equals(resCode))
            {
                MessageBox.Show("成功：" + ret.ToString());
            }
            else
            {
                MessageBox.Show("失败：" + ret.ToString());
            }


            dataJson = new JObject();
            dataJson.Add("cardId", "000000000002014082900519");
            ret = httpDbTool.invokeProc("PK_CAR_SCHEDULE.check_car_id", dataJson);


            resCode = (String)ret[Commons.RES_CODE];
            if ("0".Equals(resCode))
            {
                MessageBox.Show("成功：" + ret.ToString());
            }
            else
            {
                MessageBox.Show("失败：" + ret.ToString());
            }


            dataJson = new JObject();
            dataJson.Add("cardId", "000000000002014082900519");
            String retStr = httpDbTool.invokeProcRetStr("PK_CAR_SCHEDULE.check_car_id", dataJson);
            MessageBox.Show(retStr);

        }

        private void button2_Click(object sender, EventArgs e)
        {
            businessBase.readRfidDevice.stop_read_RFID_Info();
            businessBase.readRfidDevice.clearEcpTagList();
        }

        //采样-复位
        private void button3_Click(object sender, EventArgs e)
        {
            businessBase.stateEventArgs.nextStep = Commons.STEP.IDLE;
        }

        //采样-扫卡
        private void readCard_Click(object sender, EventArgs e)
        {
            businessBase.stateEventArgs.nextStep = Commons.STEP.CAR_GetCarNo;
        }

       
        //关闭窗口
        private void carInForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (businessBase.myOPCTool != null)
            {
                businessBase.myOPCTool.stopOpcServer();
            }
            if (businessBase.serport!=null)
            {
                businessBase.serport.ClosePort();

            }
            System.Environment.Exit(0);
        }

       
      
        private void button6_Click(object sender, EventArgs e)
        {
             string v_attachInfo="";
             string v_resCode="";
             string v_resMsg="";
             string t = "";
             DataBaseTool dbtool = new DataBaseTool();
             dbtool.cy_before_preProcess("CY1", "000000000000201408300480", out v_attachInfo, out v_resCode, out v_resMsg);
            XmlDocument document = new XmlDocument();
            document.LoadXml(v_attachInfo);

            XmlNode xNode = document.SelectSingleNode("root/recordNo");//取is_success节点的值
            string is_success = xNode.InnerText.ToString();

           xNode = document.SelectSingleNode("root/carId");//取is_success节点的值
           string carid = xNode.InnerText.ToString();

           xNode = document.SelectSingleNode("root/sampleCode");//取is_success节点的值
           string sampleCode = xNode.InnerText.ToString();

           xNode = document.SelectSingleNode("root/samplePoints");//取is_success节点的值
           string samplePoints = xNode.InnerText.ToString();

           xNode = document.SelectSingleNode("root/CoordNumList");//取is_success节点的值
           string CoordNumList = xNode.InnerText.ToString();



           xNode = document.SelectSingleNode("root/CoordList");//取is_success节点的值
           string tt = xNode.InnerText.ToString().Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "");;
         

             JArray sampleCoordListOrginal = new JArray();
             sampleCoordListOrginal = JArray.Parse(tt);
            List<CoordList> CoordList=  sampleCoordListOrginal.ToObject<List<CoordList>>();
   
          

            CoordList = CoordList.OrderBy(o => o.cy_x_int).ToList();//升序
   

              JsonSerializer serializer = new JsonSerializer();
             StringWriter sw = new StringWriter();
             serializer.Serialize(new JsonTextWriter(sw), CoordList);
             string result = sw.GetStringBuilder().ToString();
             JArray datArray = JArray.Parse(result);


            foreach (var item in datArray)
            {

                JObject jdata = (JObject)item;
                string aa = jdata["cy_x"].ToString();
                string bb = jdata["cy_y"].ToString();
                string hh = jdata["cy_h"].ToString();
                LogTool.WriteLogInfo(typeof(carInForm), aa + "--" + bb + "--" + hh);
            }

            string ts = "";
        }

        public class CoordList : IComparable<CoordList>
        {
            public string cy_x { get; set; }
            public string cy_y { get; set; }
            public string cy_h { get; set; }

            public int cy_x_int { get; set; }

            public CoordList(string cy_x, string cy_y, string cy_h)
            {
                this.cy_x = cy_x;
                this.cy_y = cy_y;
                this.cy_h = cy_h;
                this.cy_x_int = int.Parse(cy_x);
            }


            //重写的CompareTo方法，根据Id排序
            public int CompareTo(CoordList other)
            {
                if (null == other)
                {
                    return 1;//空值比较大，返回1
                }
                return this.cy_x_int.CompareTo(other.cy_x_int);//升序

            }

        }
        private void button7_Click(object sender, EventArgs e)
        {
            string v_resCode = "";
            string v_resMsg = "";
            DataBaseTool dbtool = new DataBaseTool();
            XmlDocument doc = new XmlDocument();
            XmlDeclaration dec = doc.CreateXmlDeclaration("1.0", "GB2312", null);
            doc.AppendChild(dec);
            //创建一个根节点（一级）
            XmlElement root = doc.CreateElement("root");
            doc.AppendChild(root);

            XmlElement recordNo = doc.CreateElement("recordNo");
            recordNo.InnerText = "111";
            root.AppendChild(recordNo);

            XmlElement machinCode = doc.CreateElement("machinCode");
            machinCode.InnerText = "cy1";
            root.AppendChild(machinCode);

            XmlElement sampleCode = doc.CreateElement("sampleCode");
            sampleCode.InnerText = "20177";
            root.AppendChild(sampleCode);


            XmlElement sampleType = doc.CreateElement("sampleType");
            sampleType.InnerText = "0";
            root.AppendChild(sampleType);

            XmlElement samplePoints = doc.CreateElement("samplePoints");
            samplePoints.InnerText = "3";
            root.AppendChild(samplePoints);

            XmlElement sampleWeight = doc.CreateElement("sampleWeight");
            sampleWeight.InnerText = "0";
            root.AppendChild(sampleWeight);

            XmlElement SFNum = doc.CreateElement("SFNum");
            SFNum.InnerText = "3";
            root.AppendChild(SFNum);

          
            XmlElement coordinate1 = doc.CreateElement("coordinate1");
            coordinate1.InnerText = "";


            XmlElement coordinate2 = doc.CreateElement("coordinate2");
            coordinate2.InnerText = "";

            XmlElement coordinate3 = doc.CreateElement("coordinate3");
            coordinate3.InnerText = "";

            root.AppendChild(coordinate1);
            root.AppendChild(coordinate2);
            root.AppendChild(coordinate3);

            XmlElement startTime = doc.CreateElement("startTime");
            startTime.InnerText = System.DateTime.Now.ToString();
            root.AppendChild(startTime);

            XmlElement endTime = doc.CreateElement("endTime");
            endTime.InnerText = System.DateTime.Now.ToString();
            root.AppendChild(endTime);

            dbtool.cy_after_process("CY1", "000000000000201408300480", ConvertXmlToString(doc), out v_resCode, out v_resMsg);
          

           
        }


        /// <summary>
        /// 将XmlDocument转化为string
        /// </summary>
        /// <param name="xmlDoc"></param>
        /// <returns></returns>
        public string ConvertXmlToString(XmlDocument xmlDoc)
        {
            MemoryStream stream = new MemoryStream();
            XmlTextWriter writer = new XmlTextWriter(stream, null);
            writer.Formatting = System.Xml.Formatting.Indented;
            xmlDoc.Save(writer);
            StreamReader sr = new StreamReader(stream, System.Text.Encoding.UTF8);
            stream.Position = 0;
            string xmlString = sr.ReadToEnd();
            sr.Close();
            stream.Close();
            return xmlString;
        }

        private void button8_Click(object sender, EventArgs e)
        {
            string v_resCode = "";
            string v_resMsg = "";
                string v_attachInfo="";
            DataBaseTool dbtool = new DataBaseTool();

            JObject dataJson = new JObject();
            dataJson.Add("machin_code", "RC_CYJ1");
            dataJson.Add("machin_type", "1");
            dataJson.Add("record_no", "01");
            dataJson.Add("command_code", "7");
            dataJson.Add("sample_code", "20181227134");
            dataJson.Add("data_status", "0");
            dataJson.Add("op_code", "admin");

            dbtool.sendWTQCCYJCmd(Convert.ToString(dataJson), out v_attachInfo, out v_resCode, out v_resMsg);


            if (myOPCTool == null)
            {
                myOPCTool = new OPCTool();
                myOPCTool.startOpcServer();
            }

            JObject ret = myOPCTool.processHttpRequestMethod(v_attachInfo.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", ""));



        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            businessBase.actiomLedShow(coalID.Text);
        }

        private void button1_TextChanged(object sender, EventArgs e)
        {

        }

        private void buttonReset_Click(object sender, EventArgs e)
        {
            businessBase.resetProcess();
        }

        private void infoKeyText_TextChanged(object sender, EventArgs e)
        {
          
        }

 
        private void groupBox5_Enter(object sender, EventArgs e)
        {

        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void likeCardId_TextChanged(object sender, EventArgs e)
        {

        }


 
        private void cameraBtn_Click(object sender, EventArgs e)
        {
            businessBase.carNoCameraAvailable = !businessBase.carNoCameraAvailable;

            if (businessBase.carNoCameraAvailable)
            {
                cameraBtn.Text = "停用车牌识别器";
            }
            else 
            {
                cameraBtn.Text = "启用车牌识别器";
            }
            
        }


 
    }
}
