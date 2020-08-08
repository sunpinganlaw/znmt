using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NHTool.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;
using System.Net.NetworkInformation;

namespace NHTool.Forms.scadaForm
{
    public partial class scadaForm : Form
    {
        public OPCTool myOPCTool = null;

        private string ip = "";
        private string port = "";
        private string source = "";
        private string user = "";
        private string pwd = "";


        //UDP服务器接收数据端口
        private int udpServerPort = 9009;
        //http服务地址
        private string httpServerUrl = "";

        private DataBaseTool dbtool;

        private int showMaxCount = 50;
        private int hertCount = 0;


        private int count1 = 0;
        private int count2 = 0;
        private int count3 = 0;
        private int count4 = 0;

        private const string cmdOKCode = "1";
        private const string cmdERRCode = "1000";

        private const string cmdOKMsg = "执行成功";
        private const string cmdErrMsg = "执行失败";

        private string cyjcommandCode = "ScadaCmd_DB.CYJ.commandCode";
        private string cyjdealMsg = "ScadaCmd_DB.CYJ.dealMsg";
        private string cyjdealResult = "ScadaCmd_DB.CYJ.dealResult";
        private string cyjmachineCode = "ScadaCmd_DB.CYJ.machineCode";
        private string cyjmachineType = "ScadaCmd_DB.CYJ.machineType";
        private string cyjreadTage = "ScadaCmd_DB.CYJ.readTage";
        private string cyjsampleCode = "ScadaCmd_DB.CYJ.sampleCode";



        private string zyjcommandCode = "ScadaCmd_DB.ZYJ.commandCode";
        private string zyjdealMsg = "ScadaCmd_DB.ZYJ.dealMsg";
        private string zyjdealResult = "ScadaCmd_DB.ZYJ.dealResult";
        private string zyjmachineCode = "ScadaCmd_DB.ZYJ.machineCode";
        private string zyjmachineType = "ScadaCmd_DB.ZYJ.machineType";
        private string zyjreadTage = "ScadaCmd_DB.ZYJ.readTage";
        private string zyjsampleCode = "ScadaCmd_DB.ZYJ.sampleCode";

        /// <summary>
        ///定时入库的频率（MS)
        /// </summary>
        private int pointDB_Time = 1000;


        /// <summary>
        ///数据库后台调度命令到kepServer线程频率（MS)
        /// </summary>
        private int OrclCmd_Time = 1000;


        /// <summary>
        ///组态页面命令到后台数据库校验线程频率（MS)
        /// </summary>
        private int ScadaCmd_Time = 1000;

        

        /// <summary>
        ///数据库里的汇总系统下发组态显示
        /// </summary>
        private int DBPoint_Time = 1000;



        /// <summary>
        /// 采样机 制样机等设备的需要的采样制样结果信息表
        /// </summary>
        Dictionary<String, List<string>> DeviceInfoConfig = null;

        /// <summary>
        /// 显示窗口类型每局
        /// </summary>
        public enum showTyPe : int
        {
            CFG_DB = 1,

            POINT_DB = 2,

            OrclCmd_POINT=3,
            
            ScadaCmd_DB=4,

            DB_POINT=5,

            INTF_DATA = 6
        }


        /// <summary>
        /// 实时状态点同步数据库线程
        /// </summary>
        private Thread POINT_DB_THREAD = null;

        /// <summary>
        /// 数据库后台调度命令到kepServer线程
        /// </summary>
        private Thread OrclCmd_POINT_THREAD = null;

        /// <summary>
        /// 组态页面命令到后台数据库校验线程
        /// </summary>
        private Thread ScadaCmd_DB_THREAD = null;


        /// <summary>
        /// 数据库业务测点下发组态软件显示
        /// </summary>
        private Thread DB_POINT_THREAD = null;


        public scadaForm()
        {
         
            myOPCTool = new OPCTool();
            InitializeComponent();
        }


        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {


        }

       
       

        private void scadaForm_Load(object sender, EventArgs e)
        {
          
            ip = ConfigTool.GetValueFromIni("orcl", "ip");
            port = ConfigTool.GetValueFromIni("orcl", "port");
            source = ConfigTool.GetValueFromIni("orcl", "source");
            user = ConfigTool.GetValueFromIni("orcl", "user");
            pwd = ConfigTool.GetValueFromIni("orcl", "pwd");

            //服务端口
            udpServerPort = int.Parse(ConfigTool.GetValueFromIni("udp", "udpServerPort"));
            httpServerUrl = ConfigTool.GetValueFromIni("httpServer", "httpServerUrl");

            this.cfg_ORA_IP_Txt.Text = ip;
            this.cfg_ORA_PORT_Txt.Text = port;
            this.cfg_ORA_SOURCE_Txt.Text = source;
            this.cfg_ORA_USER_Txt.Text = user;
            //this.cfg_ORA_PSW_Txt.Text = pwd;

            pointDB_Time = int.Parse(ConfigTool.GetValueFromIni("system", "pointDB_Time"));
            OrclCmd_Time = int.Parse(ConfigTool.GetValueFromIni("system", "OrclCmd_Time"));
            ScadaCmd_Time = int.Parse(ConfigTool.GetValueFromIni("system", "ScadaCmd_Time"));
            DBPoint_Time = int.Parse(ConfigTool.GetValueFromIni("system", "DBPoint_Time"));


            DBPoint_txtBox.Text = DBPoint_Time.ToString();
            pointDB_Time_txt.Text = pointDB_Time.ToString();
            ScadaCmd_txtBox.Text = ScadaCmd_Time.ToString();
            OrclCmd_txtBox.Text = OrclCmd_Time.ToString();


            try
            {
                POINT_DB_THREAD = new Thread(POINT_DB_FUN);
                POINT_DB_THREAD.IsBackground = true;
                System.Threading.Thread.Sleep(50);
                POINT_DB_THREAD.Start();
            }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), e1.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "启动测点入数据库线程异常:" + e1.Message);
            }

            try
            {
                DB_POINT_THREAD = new Thread(DB_POINT_FUN);
                DB_POINT_THREAD.IsBackground = true;
                System.Threading.Thread.Sleep(50);
                DB_POINT_THREAD.Start();
            }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), e1.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "启动提取数据库测点线程异常:" + e1.Message);
            }


            try
            {
                OrclCmd_POINT_THREAD = new Thread(OrclCmd_POINT_FUN);
                OrclCmd_POINT_THREAD.IsBackground = true;
                System.Threading.Thread.Sleep(50);
                OrclCmd_POINT_THREAD.Start();
            }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), e1.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "启动提取数据库命令线程异常:" + e1.Message);
            }


            try
            {
                ScadaCmd_DB_THREAD = new Thread(ScadaCmd_DB_FUN);
                ScadaCmd_DB_THREAD.IsBackground = true;
                System.Threading.Thread.Sleep(50);
                ScadaCmd_DB_THREAD.Start();
            }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), e1.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "启动命令入库线程异常:" + e1.Message);
            }


            try
            {
                //创建接收udp线程
                Thread UDPRecivceThread = new Thread(udpReceiveMsg);
                UDPRecivceThread.IsBackground = true;
                System.Threading.Thread.Sleep(50);
                UDPRecivceThread.Start();
            }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), e1.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "启动UDP服务线程异常:" + e1.Message);
            }


            try
            {
                //创建http接收数据服务线程
                HttpTool httpTool = new HttpTool();
                httpTool.initHttpServer(this.tcpReceiveMsg, httpServerUrl);
            }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), e1.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "启动http服务线程异常:" + e1.Message);
            }


            try
            {
                myOPCTool.startOpcServer(ip, port, source, user, pwd);
                dbtool = new DataBaseTool(ip, port, source, user, pwd);
             }
            catch (Exception e1)
            {
                LogTool.WriteLog(typeof(scadaForm), "启动线程OPC服务异常:" + e1.Message);
            }


            if (myOPCTool.pointsConfig.Count>0)
            {

                showUI("数据库配置的业务测点如下：", showTyPe.CFG_DB);
              
                foreach (var item in myOPCTool.pointsConfig)
                {

                    if (item.Value.ProtocolType!=null && item.Value.ProtocolType.Equals("DB"))
                    {
                        showUI(item.Key.ToString() + "---入库", showTyPe.CFG_DB);
                       
                    }
                    else
                    {
                        showUI(item.Key.ToString() , showTyPe.CFG_DB);
                    }
                }
                DeviceInfoConfig = dbtool.GetDeviceInfo();

            }
        }



        /// <summary>
        /// 业务关心的核心数据入ORACLE
        /// </summary>
        private void POINT_DB_FUN()
        {
           
            showUI("实时状态点同步数据库线程打开成功", showTyPe.POINT_DB);
            while (true)
            {
                JArray jarray = new JArray();
                foreach (var item in myOPCTool.pointsConfig)
                {
                    if (item.Value.ProtocolType != null && item.Value.ProtocolType.Equals("DB"))
                    {
                        JObject temp = new JObject();
                        temp.Add("key", item.Key.ToString());
                        Dictionary<String, String> tmpRet = myOPCTool.getValue(item.Key.ToString());
                        string value = "";
                        tmpRet.TryGetValue("value", out value);
                        if(value!="")
                        {
                            temp.Add("val", value);

                        }
                        else
                        {
                            string errMsg = "获取入库测点实时值：" + item.Key.ToString() + ":" + tmpRet[ Commons.RES_MSG];
                            showUI(errMsg, showTyPe.POINT_DB);
                            LogTool.WriteLog(typeof(scadaForm), errMsg);
                            continue;
                        }
                        jarray.Add(temp);
                    }

                }

                string resut= dbtool.point_2_db(jarray.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""));
                if (!resut.Equals("ok"))
                {
                    string errMsg = "同步数据库实时测点失败:" + resut;
                    showUI(errMsg, showTyPe.POINT_DB);
                    LogTool.WriteLog(typeof(scadaForm), errMsg);
                }
                else
                {
                    //showUI(jarray.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""), showTyPe.POINT_DB);

                }
                
                System.Threading.Thread.Sleep(pointDB_Time);
            }

        }





        /// <summary>
        /// 数据中心上传采样机集样罐信息，由启动和卸样命令触发
        /// </summary>
        /// <param name="relaDevice"></param>
        /// <param name="pointInfo"></param>
        private void DEVICE_INFO_2_DB(JObject v_deviceInfo, List<string> pointInfo , showTyPe tyPe)
        {
            //JArray jarray = new JArray();

            JObject temp = new JObject();
            if (pointInfo.Count > 0)
            {
                foreach (var itme in pointInfo)
                {

                    
                    //temp.Add("key", itme);
                    Dictionary<String, String> tmpRet = myOPCTool.getValue(itme);
                    string value = "";
                    tmpRet.TryGetValue("value", out value);
                    if (value != "")
                    {
                        //temp.Add("val", value);
                        temp.Add(itme, value);

                    }
                    else
                    {
                        string errMsg = "数据中心上传设备信息：" + itme.ToString() + ":" + tmpRet[Commons.RES_MSG];
                        showUI(errMsg, tyPe);
                        LogTool.WriteLog(typeof(scadaForm), errMsg);
                        continue;
                    }


                    
                    
                }
                
            }

            string resut = dbtool.device_info_2_db(v_deviceInfo.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""), temp.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""));
            if (!resut.Equals("ok"))
            {
                string errMsg = "同步采制样结果信息测点失败:" + resut;
                showUI(errMsg, showTyPe.OrclCmd_POINT);
                LogTool.WriteLog(typeof(scadaForm), errMsg);
            }
            else
            {
                //showUI(jarray.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""), showTyPe.POINT_DB);

            }

        }






        /// <summary>
        /// 数据库下发汇总数据到组态显示
        /// </summary>
        private void DB_POINT_FUN()
        {

            showUI("数据库下发组态显示线程打开成功", showTyPe.DB_POINT);
            while (true)
            {
                string resut = "";
                dbtool.db_2_point(out resut);

                if (resut.Length > 0)
                {
                    JArray jsonIn = (JArray)JsonConvert.DeserializeObject(resut);


                    for (int i = 0; i < jsonIn.Count; i++)
                    {
                        JObject tmpObj = (JObject)jsonIn[i];
                        string key = (string)tmpObj["key"];
                        string val = (string)tmpObj["val"];
                        
                        Dictionary<string, string> retSet = myOPCTool.setValue(key, val);
                        String resCode = retSet[Commons.RES_CODE];
                        if ("1".Equals(resCode))
                        {
                            String resMsg = retSet[Commons.RES_MSG];
                            string errMsg = "写命令数据失败:-->" + key + ":" + val + "-->" + resMsg;
                            LogTool.WriteLog(typeof(scadaForm), errMsg);
                            showUI(errMsg, showTyPe.DB_POINT);
                            continue;
                        }
                      
                    }
                    
                }
              
                System.Threading.Thread.Sleep(DBPoint_Time);
            }

        }


        /// <summary>
        /// Orac数据库命令发送到具体设备上
        /// </summary>
        private void OrclCmd_POINT_FUN()
        {
            showUI("数据库下发命令线程打开成功", showTyPe.OrclCmd_POINT);
            while (true)
            {
                string resut = "";
                string v_deviceInfo = "";
                dbtool.dbcmd_2_point(out resut,out v_deviceInfo);
                string keyValue = "cmd_rec_id";
                string cmd_rec_id = "";
                bool allSuccess = true;


                if (v_deviceInfo.Length > 0)
                {
                    JObject tmpObj = (JObject)JsonConvert.DeserializeObject(v_deviceInfo);

                    string machineCode = (string)tmpObj["machineCode"];
                    DEVICE_INFO_2_DB(tmpObj, DeviceInfoConfig[machineCode],showTyPe.OrclCmd_POINT);


                }
                if (resut.Length > 0)
                {
                    JArray jsonIn = (JArray)JsonConvert.DeserializeObject(resut);
                    
                    for (int i = 0; i < jsonIn.Count; i++)
                    {
                        JObject tmpObj = (JObject)jsonIn[i];
                        string key = (string)tmpObj["key"];
                        string val = (string)tmpObj["val"];

                        if (key.Equals(keyValue))
                        {
                            cmd_rec_id = val;
                            continue;
                        }
                        

                        Dictionary<string, string> retSet = myOPCTool.setValue(key, val);
                        String resCode = retSet[Commons.RES_CODE];
                       
                        if ("1".Equals(resCode))
                        {
                            String resMsg = retSet[Commons.RES_MSG];
                            string errMsg = "写命令数据失败:-->" + key + ":" + val +"-->"+ resMsg;
                            LogTool.WriteLog(typeof(scadaForm), errMsg);
                            showUI(errMsg, showTyPe.OrclCmd_POINT);
                            allSuccess = false;
                            break;
                        }

                    }
                    
                    if (allSuccess)
                    {

                        if (cmd_rec_id.Length > 0)
                        {
                            dbtool.dbcmd_writeback(cmd_rec_id,"1");

                        }
                    }

                }

                System.Threading.Thread.Sleep(OrclCmd_Time);
            }

        }



        /// <summary>
        /// 组态软件上下发的控制命令调用Oracl进行业务判断等
        /// </summary>
        private void ScadaCmd_DB_FUN()
        {
            showUI("组态软件命令转发线程打开成功", showTyPe.ScadaCmd_DB);
            LogTool.WriteLog(typeof(scadaForm), "组态软件命令转发线程打开成功");
            string errMsg = "";
            while (true)
            {
                string machineCode = "";
                string machineType = "";
                string commandCode = "";
                string sampleCode = "";
               
                //--------------解析组态软件采样机下发的命令--------------------------------
                Dictionary<String, String> tmpRet = myOPCTool.getValue(cyjreadTage);
                string value = "";
                tmpRet.TryGetValue("value", out value);
                if (value!=null && value.Length > 0 )
                {
                    if (value.Equals("0"))
                    {
                        tmpRet = myOPCTool.getValue(cyjmachineCode);
                        tmpRet.TryGetValue("value", out machineCode);

                        tmpRet = myOPCTool.getValue(cyjmachineType);
                        tmpRet.TryGetValue("value", out machineType);

                        tmpRet = myOPCTool.getValue(cyjcommandCode);
                        tmpRet.TryGetValue("value", out commandCode);

                        tmpRet = myOPCTool.getValue(cyjsampleCode);
                        tmpRet.TryGetValue("value", out sampleCode);

                        if (machineCode!=null && machineCode.Length > 0 && machineType!=null && machineType.Length > 0 && commandCode!=null &&  commandCode.Length > 0 && sampleCode!=null &&  sampleCode.Length > 0)
                        {
                            JObject temp = new JObject();
                            temp.Add("machineCode", machineCode);
                            temp.Add("machineType", machineType);
                            temp.Add("commandCode", commandCode);
                            temp.Add("sampleCode", sampleCode);
                            string cmdStr = temp.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", "");

                            DEVICE_INFO_2_DB(temp, DeviceInfoConfig[machineCode],showTyPe.ScadaCmd_DB);

                            string cmdResult=  dbtool.cmd_2_db(cmdStr);
                            if (cmdResult.Equals("ok"))
                            {

                                myOPCTool.setValue(cyjdealResult, cmdOKCode);
                                myOPCTool.setValue(cyjdealMsg, cmdOKMsg);
                                errMsg = "组态下发命令执行成功：" + cmdStr;
                                showUI(errMsg, showTyPe.ScadaCmd_DB);
                                LogTool.WriteLog(typeof(scadaForm), errMsg);

                            }
                            else
                            {
                                myOPCTool.setValue(cyjdealResult,cmdERRCode);
                                myOPCTool.setValue(cyjdealMsg, cmdErrMsg);
                                errMsg = "组态下发命令执行失败：" + cmdStr + ":--->>" + cmdResult;
                                showUI(errMsg, showTyPe.ScadaCmd_DB);
                                LogTool.WriteLog(typeof(scadaForm), errMsg);

                            }

                            myOPCTool.setValue(cyjreadTage, "1");
                        }
                        else
                        {
                            //errMsg = "检测到非法命令(命令信息不全)：-->>" + cyjmachineCode + ":" + machineCode  + ";" + cyjmachineType + ":" + machineType + ";" + cyjcommandCode + ":" + commandCode + ";" + cyjsampleCode + ":";
                            errMsg = "检测到非法命令(命令信息不全)：";
                            showUI(errMsg, showTyPe.ScadaCmd_DB);
                            LogTool.WriteLog(typeof(scadaForm), errMsg);
                        }

                    }
                }
                else
                {
                    errMsg = "采样机命令标志位存在异常";
                    showUI(errMsg, showTyPe.ScadaCmd_DB);
                    LogTool.WriteLog(typeof(scadaForm), errMsg);
                }


                //--------------解析组态软件制样机下发的命令--------------------------------
                tmpRet = myOPCTool.getValue(zyjreadTage);
                value = "";
                tmpRet.TryGetValue("value", out value);
                if (value != null && value.Length > 0 )
                {
                    if (value.Equals("0"))
                    {
                        tmpRet = myOPCTool.getValue(zyjmachineCode);
                        tmpRet.TryGetValue("value", out machineCode);

                        tmpRet = myOPCTool.getValue(zyjmachineType);
                        tmpRet.TryGetValue("value", out machineType);

                        tmpRet = myOPCTool.getValue(zyjcommandCode);
                        tmpRet.TryGetValue("value", out commandCode);

                        tmpRet = myOPCTool.getValue(zyjsampleCode);
                        tmpRet.TryGetValue("value", out sampleCode);

                  
                        if (machineCode != null && machineCode.Length > 0 && machineType != null && machineType.Length > 0 && commandCode != null && commandCode.Length > 0 && sampleCode != null && sampleCode.Length > 0)
                        {
                            JObject temp = new JObject();
                            temp.Add("machineCode", machineCode);
                            temp.Add("machineType", machineType);
                            temp.Add("commandCode", commandCode);
                            temp.Add("sampleCode", sampleCode);
                            string cmdStr = temp.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", "");
                            string cmdResult = dbtool.cmd_2_db(cmdStr);
                            if (cmdResult.Equals("ok"))
                            {

                                myOPCTool.setValue(zyjdealResult, cmdOKCode);
                                myOPCTool.setValue(zyjdealMsg, cmdOKMsg);

                                errMsg = "组态下发命令执行成功：" + cmdStr;
                                showUI(errMsg, showTyPe.ScadaCmd_DB);
                                LogTool.WriteLog(typeof(scadaForm), errMsg);

                            }
                            else
                            {
                                myOPCTool.setValue(zyjdealResult, cmdERRCode);
                                myOPCTool.setValue(zyjdealMsg, cmdErrMsg);
                                errMsg = "组态下发命令执行失败：" + cmdStr + ":--->>" + cmdResult;
                                showUI(errMsg, showTyPe.ScadaCmd_DB);
                                LogTool.WriteLog(typeof(scadaForm), errMsg);
                            }

                            myOPCTool.setValue(zyjreadTage, "1");
                        }
                        else
                        {
                            //errMsg = "检测到非法命令(命令信息不全)：-->>" + cyjmachineCode + ":" + machineCode  + ";" + cyjmachineType + ":" + machineType + ";" + cyjcommandCode + ":" + commandCode + ";" + cyjsampleCode + ":";
                            errMsg = "检测到非法命令(命令信息不全)：";
                            showUI(errMsg, showTyPe.ScadaCmd_DB);
                            LogTool.WriteLog(typeof(scadaForm), errMsg);
                        }

                    }
                }
                else
                {
                    errMsg = "制样机命令标志位存在异常";
                    showUI(errMsg, showTyPe.ScadaCmd_DB);
                    LogTool.WriteLog(typeof(scadaForm), errMsg);
                }

                System.Threading.Thread.Sleep(ScadaCmd_Time);
            }
        }


        //接收udp数据信息线程调用方法
        private void udpReceiveMsg()
        {
            string receiveString = null;
            byte[] receiveData = null;

            //实例化一个远程端点，IP和端口可以随意指定，等调用client.Receive(ref remotePoint)时会将该端点改成真正发送端端点
            IPEndPoint remotePoint = new IPEndPoint(IPAddress.Any, 0);
            UdpClient client = new UdpClient(udpServerPort);

            while (true)
            {
                try
                {
                    receiveData = client.Receive(ref remotePoint);//接收数据
                    receiveString = Encoding.Default.GetString(receiveData);
                    //MessageBox.Show("UDP线程接收：" + receiveString);

                    //demo： [{'key':'Simulator.Device1.p1','val':'12'},{'key':'Simulator.Device1.p2','val':'34'}]
                    //反序列化JSON字符串  
                    JArray jarray = (JArray)JsonConvert.DeserializeObject(receiveString);
                    for (int i = 0; i < jarray.Count; i++)
                    {
                        JObject tmpObj = (JObject)jarray[i];
                        string key = (string)tmpObj["key"];
                        string val = (string)tmpObj["val"];

                        Dictionary<string, string> retSet = myOPCTool.setValue(key, val);
                        String resCode = retSet[Commons.RES_CODE];

                        if ("1".Equals(resCode))
                        {
                            String resMsg = retSet[Commons.RES_MSG];
                            string errMsg = "接受接口数据失败:-->" + key + ":" + val + "-->" + resMsg;
                            LogTool.WriteLog(typeof(scadaForm), errMsg);
                            showUI(errMsg, showTyPe.ScadaCmd_DB);
                            break;
                        }
                    }

                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(scadaForm), e.StackTrace);
                    LogTool.WriteLog(typeof(scadaForm), "UDP线程接收异常：" + e.Message);
                }
            }
        }



        //接收tcp数据信息线程调用方法
        private JObject tcpReceiveMsg(String httpStr)
        {
            JObject retJSONObj = new JObject();

            try
            {
                JArray jarray = (JArray)JsonConvert.DeserializeObject(httpStr);
                for (int i = 0; i < jarray.Count; i++)
                {
                    JObject tmpObj = (JObject)jarray[i];
                    string key = (string)tmpObj["key"];
                    string val = (string)tmpObj["val"];

                    Dictionary<string, string> retSet = myOPCTool.setValue(key, val);
                    String resCode = retSet[Commons.RES_CODE];

                    if ("1".Equals(resCode))
                    {
                        String resMsg = retSet[Commons.RES_MSG];
                        string errMsg = "接受http接口数据失败:-->" + key + ":" + val + "-->" + resMsg;
                        LogTool.WriteLog(typeof(scadaForm), errMsg);
                        showUI(errMsg, showTyPe.ScadaCmd_DB);

                        retJSONObj.Add(Commons.RES_CODE, "1");
                        retJSONObj.Add(Commons.RES_MSG, errMsg);
                        return retJSONObj;
                    }
                }

                retJSONObj.Add(Commons.RES_CODE, "0");
                retJSONObj.Add(Commons.RES_MSG, "succ");
            }
            catch (Exception e)
            {
                LogTool.WriteLog(typeof(scadaForm), e.StackTrace);
                LogTool.WriteLog(typeof(scadaForm), "http服务线程接收异常：" + e.Message);
                retJSONObj.Add(Commons.RES_CODE, "1");
                retJSONObj.Add(Commons.RES_MSG, "err：" + e.Message);
            }

            return retJSONObj;
        }





        /// <summary>
        /// 更新程序运行过程信息到界面
        /// </summary>
        /// <param name="msg">信息</param>
        /// <param name="type">哪个窗口</param>
        private void showUI(string msg, showTyPe type)
        {
            if (type.Equals(showTyPe.CFG_DB))
            {

                cfg_inforRichBox.AppendText(msg + "\r\n");

            }else if(type.Equals(showTyPe.POINT_DB))
            {

                this.pointDB_inforRichBox.Invoke(new Action(() =>
                {
                    count1++;
                    if (count1 > showMaxCount)
                    {
                        this.pointDB_inforRichBox.Clear();
                        count1 = 0;
                    }
                    this.pointDB_inforRichBox.AppendText(System.DateTime.Now.ToString()+"--->>>" +msg + "\r\n");
                }));


            }
            else if(type.Equals(showTyPe.DB_POINT))
            {
                this.DBPoint_inforRichBox.Invoke(new Action(() =>
                {
                    count2++;
                    if (count2 > showMaxCount)
                    {
                        this.DBPoint_inforRichBox.Clear();
                        count2 = 0;
                    }
                    this.DBPoint_inforRichBox.AppendText(System.DateTime.Now.ToString() + "--->>>" + msg + "\r\n");
                }));


            }
            else if (type.Equals(showTyPe.OrclCmd_POINT))
            {
                this.OrclCmd_inforRichBox.Invoke(new Action(() =>
                {
                    count3++;
                    if (count3 > showMaxCount)
                    {
                        this.OrclCmd_inforRichBox.Clear();
                        count3 = 0;
                    }
                    this.OrclCmd_inforRichBox.AppendText(System.DateTime.Now.ToString() + "--->>>" + msg + "\r\n");
                }));


            }
            else if (type.Equals(showTyPe.ScadaCmd_DB))
            {
                this.ScadaCmd_inforRichBox.Invoke(new Action(() =>
                {
                    count4++;
                    if (count4 > showMaxCount)
                    {
                        this.ScadaCmd_inforRichBox.Clear();
                        count4 = 0;
                    }
                    this.ScadaCmd_inforRichBox.AppendText(System.DateTime.Now.ToString() + "--->>>" + msg + "\r\n");
                }));


            }

        }
        /// <summary>
        /// 心跳定时器，数据中心定时写值到组态软件中
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer1_Tick(object sender, EventArgs e)
        {
            hertCount = hertCount + 1;
            if(hertCount> showMaxCount)
            {

                hertCount = 0;

            }
            cfg_HERT_Txt.Text = hertCount.ToString();
            Dictionary<String, String> tmpRet = myOPCTool.setValue("ScadaCmd_DB.Heart.heartbeat", hertCount.ToString());
            if(tmpRet[Commons.RES_CODE].Equals("1"))
            {
                showUI(tmpRet[Commons.RES_MSG], showTyPe.CFG_DB);
            }

        }

        private void button1_Click(object sender, EventArgs e)
        {
           
            Dictionary<String, String> tmpRet = myOPCTool.getValue("DB_POINT.CYJ.RC.Code1");
            string ss = "";
            tmpRet.TryGetValue("value", out ss);
            cfg_ORA_IP_Txt.Text = ss;


        }

        private void scadaForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            myOPCTool.stopOpcServer();
        }
    }
}
