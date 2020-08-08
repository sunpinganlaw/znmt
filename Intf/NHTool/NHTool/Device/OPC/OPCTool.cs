using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using HaiGrang.Package.OpcNetApiChs.Common;
using HaiGrang.Package.OpcNetApiChs.Opc;
using HaiGrang.Package.OpcNetApiChs.DaNet;
using HaiGrang.Package.OpcNetApiChs.Da;
using System.Threading;
using System.Net;
using System.IO;
using System.Windows.Forms;
using System.Data.OleDb;
using System.Data;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NHTool.Common;
using EasyModbus;

namespace NHTool
{
    /**   
      * 功能    : 读取OPC测点，发送到组态，并执行下发的命令
      * 创建人  : 2018-09 宿迁 
      * 修改记录: wz 2018-11-16 ，因为新疆这边，需要c#读取测点，故pointsConfig中的Point增加了value字段
     *                            增加测点读取时quality判断，不是GOOD，则认为异常，且异常时，统一value改为""
     *                            增加getPointValue方法
      */
    public class OPCTool
    {
        private RefreshGroup refreshGroup;//预留，暂没用
        public Boolean isStop = true;
        private HttpTool httpTool=null;
        private OpcServer opcServer = null;
        private OPCDATASOURCE source = OPCDATASOURCE.OPC_DS_CACHE;//OPC_DS_CACHE高性能。 OPC_DS_DEVICE;
        private SyncIOGroup syncIOGroup = null;

        //测点配置的Dictionary对象
        public Dictionary<String, Point> pointsConfig = new Dictionary<String, Point>();

        //其他配置信息
        private Dictionary<String, String> ctlConfig = new Dictionary<String, String>();

        //获取测点 point.GroupId + "#" + point.LogicDeviceId + "_" +logicTagName
        public String getPointValue(string groupId, string LogicDeviceId, string logicTagName)
        {
            string key = groupId + "#" + LogicDeviceId + "_" + logicTagName;
            if (pointsConfig.ContainsKey(key))
            {
                Point point = pointsConfig[key];
                if (point != null)
                {
                    return point.value;
                }
                else
                {
                    return "";
                }
            }
            else
            {
                return "";
            }
        }

        //根据配置文件，启动OPCServer对象。 R/W RO ,读写情况
        public Boolean startOpcServer()
        {
            Boolean isStartSucc = false;
            string step = "开始";
            try
            {
                //1初始化配置参数，并检查配置
                step = "初始化NHConfigs.xls";
                pointsConfig = ConfigTool.initOpcPointsConfigExcel("NHConfigs.xls", "opcPointConfig");
                ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
                String httpServerUrl = null;
                String httpSendUrl = null;
                String remortOpcProgId = null;
                String remortOpcIp = null;
                if (ctlConfig.ContainsKey("httpServerUrl")
                    && ctlConfig.ContainsKey("httpSendUrl")
                    && pointsConfig.Count >= 0
                    && ctlConfig.ContainsKey("remortOpcProgId")
                    && ctlConfig.ContainsKey("remortOpcIp"))
                {
                    httpServerUrl = ctlConfig["httpServerUrl"];
                    httpSendUrl = ctlConfig["httpSendUrl"];
                    remortOpcProgId = ctlConfig["remortOpcProgId"].Trim();
                    remortOpcIp = ctlConfig["remortOpcIp"].Trim();
                }
                else
                {
                    MessageBox.Show(step + ",配置异常，请检查!");
                    return false;
                }

                //2初始化opcServer
                step = "初始化opcServer";
                Host host = new Host(remortOpcIp);//创建本机对象
                OpcServerBrowser myBrowser = new OpcServerBrowser(host);  //创建浏览OPC服务器对象myBrowser

                Guid SerGuid;
                int CLSIDFromProgID = myBrowser.CLSIDFromProgID(remortOpcProgId, out SerGuid);
                if (HRESULTS.Succeeded(CLSIDFromProgID))//如果结果成功 既xyz为正数
                {
                    opcServer = new OpcServer();//声明一个OPCServer对象
                    int result = opcServer.Connect(host, SerGuid);//服务器对象的连接方法（利用主机对象和全局唯一标识）.如果在本机 主机对象可以不写.
                    SERVERSTATUS status;
                    result = opcServer.GetStatus(out status);
                    if (HRESULTS.Succeeded(result))//连接成功了，既xyz为正数
                    {
                        //Console.WriteLine("" + status.eServerState.ToString());
                        syncIOGroup = new SyncIOGroup(opcServer);

                        foreach (string key in pointsConfig.Keys)
                        {
                            Point tmpPoint = pointsConfig[key];
                            syncIOGroup.Add(tmpPoint.TotalTagName);
                        }
                    }
                }
                isStop = false;

                //3启动http服务，接收命令请求,转给本实例的 processHttpRequestMethod 方法处理
                //step = "初始化http服务";
                /*if (ctlConfig.ContainsKey("httpSendEnable") && ctlConfig["httpSendEnable"].Equals("0"))
                {
                    isStartSucc = true;
                    startThread("", "normal");
                }
                */

                //泰州特殊处理：如果是泰州，则把采样头信号单独取出来直接发modbus从站 xieyt@20191030
                if (ctlConfig.ContainsKey("FACTORY") && ctlConfig["FACTORY"].Equals("TZ"))
                {
                    LogTool.WriteLog(typeof(OPCTool), "进入泰州特殊处理模块");
                    processTZThread();
                }


                if (httpSendUrl != null && httpSendUrl.Length > 5)
                {
  
                    //4启动发送测点线程                          
                    httpTool = new HttpTool();
                    httpTool.initHttpServer(this.processHttpRequestMethod, httpServerUrl);

                    //4启动发送测点线程
                    startThread(httpSendUrl, "low");
                    startThread(httpSendUrl, "normal");
                    startThread(httpSendUrl, "fast");
                    isStartSucc = true;
                    Thread.Sleep(1000);
                    //MessageBox.Show("OPC服务启动成功！");

                }
                else
                {
                    isStartSucc = true;
                }               
            }
            catch (Exception e)
            {
                MessageBox.Show("启动OPC服务异常:" + step + "--" + e.Message);
                Thread.Sleep(1000);
                stopOpcServer();
            }
            return isStartSucc;
        }

        //启动线程发送
        private void startThread(String httpSendUrl, String type)
        {
            HttpTool httpToolSend = new HttpTool();
            //4启动发送测点,每隔1秒发送所有测点,地址后面从配置读取
            new Thread(new ThreadStart(delegate
            {
                int millSecond = 1000;
                JObject tmpJson = null;

                if (type.Equals("low"))
                {
                    millSecond = 4000;
                }
                else if (type.Equals("normal"))
                {
                    millSecond = 1000;
                }
                else if (type.Equals("fast"))
                {
                    millSecond = 250;
                }

                while (!isStop)
                {
                    try
                    {
                        tmpJson = new JObject();
                        Thread.Sleep(millSecond);
                        int sendNums = 0;
                        //DateTime dtBegin = System.DateTime.Now;
                        foreach (string key in pointsConfig.Keys)
                        {
                            Point tmpPoint = pointsConfig[key];
                            if (type.Equals(tmpPoint.UpdateFrequency))
                            {
                                Dictionary<String, String> tmpRet = getValue(tmpPoint.LogicTagName);
                                String resCode = tmpRet[Commons.RES_CODE];
                                if (string.Equals(resCode, "0"))
                                {
                                    String quality = tmpRet["quality"];
                                    if ("GOOD".Equals(quality))
                                    {
                                        tmpJson.Remove(tmpPoint.LogicTagName);
                                        tmpJson.Add(tmpPoint.LogicTagName, tmpRet["value"]);
                                        tmpPoint.value = tmpRet["value"];
                                    }
                                    else
                                    {
                                        tmpJson.Remove(tmpPoint.LogicTagName);
                                        tmpJson.Add(tmpPoint.LogicTagName, "");
                                        tmpPoint.value = "";
                                    }
                                    sendNums++;
                                }
                                else
                                {
                                    tmpJson.Remove(tmpPoint.LogicTagName);
                                    tmpJson.Add(tmpPoint.LogicTagName, "");
                                    tmpPoint.value = "";
                                }
                            }
                        }
                        if (sendNums > 0)
                        {
                            tmpJson.Remove("batchGetTime");
                            DateTime dt = DateTime.Now;
                            tmpJson.Add("batchGetTime", string.Format("{0:yyyyMMddHHmmss}", dt));
                            tmpJson.Remove("UpdateFrequency");
                            tmpJson.Add("UpdateFrequency", type);
                            if (httpSendUrl != null && httpSendUrl.Length > 0)
                            {
                                //LogTool.WriteLog(typeof(OPCTool), "发出的测点信息:" + tmpJson.ToString());
                                httpToolSend.sendHttpMsg(tmpJson.ToString(), httpSendUrl);

                            }
                          
                        }
                        //DateTime dtEnd = System.DateTime.Now;
                        //TimeSpan ts = dtEnd.Subtract(dtBegin);
                        //Console.WriteLine("example1 time {0}", ts.TotalMilliseconds);
                        tmpJson.RemoveAll();
                        tmpJson = null;
                    }
                    catch (Exception e)
                    {
                     
                        //Console.WriteLine(e.StackTrace);
                    }
                }

            })).Start();
        }

        //停止OPC
        public void stopOpcServer()
        {
            try
            {
                this.isStop = true;



                if (httpTool!=null)
                {

                    httpTool.isStop = true;
                    Thread.Sleep(2000);
                    httpTool.stopHttpServer();
                }
               

                ctlConfig.Clear();
                pointsConfig.Clear();
                pointsConfig = null;
                pointsConfig = new Dictionary<String, Point>();

                if (syncIOGroup != null)
                {
                    syncIOGroup.Dispose();
                    syncIOGroup = null;
                }

                if (opcServer != null)
                {
                    opcServer.Disconnect();
                    opcServer = null;
                }

                //MessageBox.Show("OPC服务停止成功！");
            }
            catch (Exception e)
            {
                MessageBox.Show("停止OPC服务异常:" + e.Message);
                if (opcServer != null)
                {
                    opcServer.Disconnect();
                }
                Thread.Sleep(1000);
            }
        }

        //处理http的设置命令的请求,C#中只处理设置的，读取的在java中处理
        public JObject processHttpRequestMethod(String httpStr)
        {
            JObject ret = new JObject();
            string srcCode = "";//"srcCode": "999", //消息来源，生产为 999，仿真为 998
            string groupId = "";//组
            string cmd_rec_id_for_write_back = "";
            //string logicDeviceId = "";
            JArray param = new JArray();
            try
            {
                //前期判断
                JObject jsonIn = (JObject)JsonConvert.DeserializeObject(httpStr);
                JObject baseInfo = (JObject)jsonIn["baseInfo"];
                srcCode = (string)baseInfo["srcCode"];
                cmd_rec_id_for_write_back = (string)baseInfo["cmd_rec_id_for_write_back"];
                ret.Add("cmd_rec_id_for_write_back", cmd_rec_id_for_write_back);
                groupId = (string)baseInfo["groupId"];
                //获得测点组
                param = (JArray)jsonIn["param"];

                //检查测点是否在配置文件中存在（配置文件加载后，主key=groupId + "#" + logicDeviceId +"."+ device;
                for (int i = 0; i < param.Count; i++)
                {
                    JObject tmpObj = (JObject)param[i];
                    string device = (string)tmpObj["device"];
                    string point = (string)tmpObj["point"];
                    string key = groupId + "#" + device + "_" + point;
                    if (!pointsConfig.ContainsKey(key))
                    {
                        ret.Add(Commons.RES_CODE, "1");
                        ret.Add(Commons.RES_MSG, "测点:" + key + "不存在！");
                        return ret;
                    }
                }

                //遍历写测点
                for (int i = 0; i < param.Count; i++)
                {
                    JObject tmpObj = (JObject)param[i];
                    string device = (string)tmpObj["device"];
                    string point = (string)tmpObj["point"];
                    string value = (string)tmpObj["value"];
                    string key = groupId + "#" + device + "_" + point;
                    Dictionary<string, string> retSet = setValue(key, value);
                    String resCode = retSet[Commons.RES_CODE];
                    if ("1".Equals(resCode))
                    {
                        ret.Add(Commons.RES_CODE, "1");
                        ret.Add(Commons.RES_MSG, retSet[Commons.RES_MSG]);
                        return ret;
                    }
                }

                ret.Add(Commons.RES_CODE, "0");
                ret.Add(Commons.RES_MSG, "succ");
                LogTool.WriteLogInfo(typeof(OPCTool), httpStr + ",执行命令成功," + param.ToString());
                return ret;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                ret.Add(Commons.RES_CODE, "1");
                ret.Add(Commons.RES_MSG, "err：" + e.Message);
                LogTool.WriteLog(typeof(OPCTool), httpStr + ",执行命令失败," + param.ToString() + "," + e.Message);
                return ret;
            }
        }

        //获取物理测点数据，返回Dictionary对象， Common.RES_CODE：0成功，1失败，当失败时，Common.RES_MSG为异常信息，成功时，Common.RES_MSG不设置
        public Dictionary<String, String> getValue(String pointKey)
        {
            Dictionary<String, String> ret = new Dictionary<String, String>();
            try
            {
                ret.Add(Commons.RES_CODE, "1");
                if (isStop)
                {
                    ret.Add(Commons.RES_MSG, "server is stop!");
                    return ret;
                }
                OPCItemState state;
                if (pointsConfig[pointKey].TotalTagName.IndexOf("SJQ") != -1)
                {
                    Console.WriteLine("");
                }
                ItemDef tmpItem = syncIOGroup.Item(pointsConfig[pointKey].TotalTagName);

                string dataType = tmpItem.OpcIInfo.CanonicalDataType.ToString();


                int result = syncIOGroup.Read(source, tmpItem, out state);
                if (!HRESULTS.Failed(result))
                {
                    //如果状态不为空
                    if (state != null)
                    {
                        //如果结果成功,既等于1，也就是说source和ItemData可以读出来
                        if (HRESULTS.Succeeded(result))
                        {
                            if (state.DataValue != null)
                            {
                                ret.Remove(Commons.RES_CODE);
                                ret.Add(Commons.RES_CODE, "0");

                                if (dataType == "8211")//------------皮带秤测点特殊处理，读出来为数组类型
                                {

                                    UInt32[] value = (UInt32[])state.DataValue;
                                    ret.Add("value", value[1].ToString());

                                }
                                else
                                {
                                    ret.Add("value", state.DataValue.ToString());
                                }

                                
                                if (dataType == "VT_BOOL")
                                {
                                    string outvalue = "";
                                    ret.TryGetValue("value", out outvalue);

                                    if (outvalue == "False")
                                    {

                                        ret["value"] = "0";

                                    }
                                    if (outvalue == "True")
                                    {

                                        ret["value"] = "1";

                                    }
                                }
                                ret.Add("quality", syncIOGroup.GetQualityString(state.Quality));
                                //DateTime dt = DateTime.FromFileTime(state.TimeStamp);
                                //ret.Add("time", dt.ToString("G"));// 2016/5/9 13:09:55    
                            }
                        }
                        else//否则（如果结果失败）
                        {
                            ret.Add(Commons.RES_MSG, syncIOGroup.GetErrorString(state.Error));
                        }
                    }
                    else//否则
                    {
                        ret.Add(Commons.RES_MSG, "state is null!");
                    }
                }
                else
                {
                    ret.Add(Commons.RES_MSG, "Read is failure!");
                }
            }
            catch (Exception e)
            {
                ret.Add(Commons.RES_MSG, e.Message);
            }
            return ret;
        }

        //设置测点，返回Dictionary对象， Common.RES_CODE：0成功，1失败，当失败时，Common.RES_MSG为异常信息，成功时，Common.RES_MSG不设置
        public Dictionary<String, String> setValue(String pointKey, String value)
        {
            Dictionary<String, String> ret = new Dictionary<String, String>();
            try
            {
                ret.Add(Commons.RES_CODE, "1");
                if (isStop)
                {
                    ret.Add(Commons.RES_MSG, "server is stop!");
                    return ret;
                }
                ItemDef tmpItem = syncIOGroup.Item(pointsConfig[pointKey].TotalTagName);
                syncIOGroup.Write(tmpItem, value);
                ret.Remove(Commons.RES_CODE);
                ret.Add(Commons.RES_CODE, "0");
            }
            catch (Exception e)
            {
                ret.Add(Commons.RES_MSG, e.Message);
            }
            return ret;
        }

        //解析读取或设置测点返回的Dictionary对象
        public String getRetValue(Dictionary<String, String> ret)
        {
            String resCode = ret[Commons.RES_CODE];
            if (string.Equals(resCode, "0"))
            {
                return ret["value"];
            }
            else
            {
                if (ret.ContainsKey(Commons.RES_MSG))
                {
                    return ret[Commons.RES_MSG];
                }
                else
                {
                    return "err,no msg!";
                }
            }
        }

        //监听测点
        public void listenPoints()
        {
            Host host = new Host("localhost");//创建本机对象
            OpcServerBrowser myBrowser = new OpcServerBrowser(host);  //创建浏览OPC服务器对象myBrowser
            Guid SerGuid;
            String progId = "KEPware.KEPServerEx.V4";//remortOpcProgId=
            int CLSIDFromProgID = myBrowser.CLSIDFromProgID(progId, out SerGuid);
            if (HRESULTS.Succeeded(CLSIDFromProgID))//如果结果成功 既xyz为正数
            {
                OpcServer tmpServer = new OpcServer();//声明一个OPCServer对象
                int result = tmpServer.Connect(host, SerGuid);//服务器对象的连接方法（利用主机对象和全局唯一标识）.如果在本机 主机对象可以不写.
                SERVERSTATUS status;
                result = tmpServer.GetStatus(out status);

                //刷新间隔毫秒
                int refreshRate = 1000;
                if (HRESULTS.Succeeded(result))//连接成功了，既xyz为正数
                {
                    HaiGrang.Package.OpcNetApiChs.DaNet.RefreshEventHandler refreshEventHandler = new HaiGrang.Package.OpcNetApiChs.DaNet.RefreshEventHandler(this.DataChangeHandler);
                    refreshGroup = new RefreshGroup(tmpServer, refreshRate, refreshEventHandler);
                    //增加监听的测点
                    refreshGroup.Add("Channel1.Device1.Group1.long");
                    refreshGroup.Add("Channel1.Device1.Group1.test");
                }
            }
        }

        //监听到数据变化时，会触发（object型对象，更新事件属性）
        private void DataChangeHandler(object sender, HaiGrang.Package.OpcNetApiChs.DaNet.RefreshEventArguments e)
        {
            DateTime dtBegin = System.DateTime.Now;
            for (int i = 0; i < e.items.Length; i++)//循环，定义一个变量i并赋值为0，i<当前出发的项的元素总数，i+1
            {
                //int hnd = e.items[i].OpcIDef.HandleClient;//当前发生的更新事件的第i项的客户句柄（句柄是用来标识项目的）

                object val = e.items[i].OpcIRslt.DataValue;//把OPC服务器里面的第i项的数据值赋值给val
                Console.WriteLine(e.items[i].OpcIDef.ItemID + "=" + val);
                //string qt = refreshGroup.GetQualityString(e.items[i].OpcIRslt.Quality);//以当前的第i项的质量属性为参数 利用同步组对象的GetQualityString得到质量 传给string型变量qt
                //ItemDef item = refreshGroup.FindClientHandle(hnd);//根据当前发生的第i项的客户句柄..利用更新数组对象FindClientHandle方法找到客户端句柄 传给 ItemDef对象item
                //string name = item.OpcIDef.ItemID;//把OPC服务器里面的项的ID值赋给字符串name
            }

            DateTime dtEnd = System.DateTime.Now;
            TimeSpan ts = dtBegin.Subtract(dtEnd);
            Console.WriteLine("example1 time {0}", ts.TotalMilliseconds);
        }



        //专门处理泰州的特殊方法，因为泰州非得盯着采样头的信号。暂时先硬编码。
        private void processTZThread()
        {
            ModbusClient modbusClientRC = new ModbusClient();
            modbusClientRC.Connect("192.168.6.66", 503);

            ModbusClient modbusClientRL = new ModbusClient();
            modbusClientRL.Connect("192.168.6.66", 502);

            new Thread(new ThreadStart(delegate
            {
                int millSecond = 80; //80毫秒处理一次
                Dictionary<String, String> tmpRet;
                String resCode;

                while (true)
                {
                    try
                    {
                        //LogicTagName = point.GroupId + "#" + point.LogicDeviceId + "_" + getExcelCellValue(dataRow, columns, 7);
                        //入厂#1采样机
                        tmpRet = getValue("01#RC_CYJ1_FirstSample");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(183, Convert.ToInt32(tmpRet["value"]));
                            }
                        }


                        //入厂#2采样机
                        tmpRet = getValue("01#RC_CYJ2_FirstSample");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(259, Convert.ToInt32(tmpRet["value"]));
                            }
                        }
                        ////////////////////////////////////////////////////////////////////////////////////////////

                        //一期入炉#1采样机A采样头
                        tmpRet = getValue("01#RL1Q_CYJ1_FirstSampleMachine1");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(170, Convert.ToInt32(tmpRet["value"]));
                            }
                        }

                        //一期入炉#1采样机B采样头
                        tmpRet = getValue("01#RL1Q_CYJ1_FirstSampleMachine2");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(171, Convert.ToInt32(tmpRet["value"]));
                            }
                        }


                        //一期入炉#2采样机A采样头
                        tmpRet = getValue("01#RL1Q_CYJ2_FirstSampleMachine1");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(217, Convert.ToInt32(tmpRet["value"]));
                            }
                        }

                        //一期入炉#2采样机B采样头
                        tmpRet = getValue("01#RL1Q_CYJ2_FirstSampleMachine2");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(218, Convert.ToInt32(tmpRet["value"]));
                            }
                        }

                        //////////////////////////////////////////////////////////////////////////////////////

                        //二期入炉#1采样机A采样头
                        tmpRet = getValue("01#RL2Q_CYJ1_CYTARun");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(265, Convert.ToInt32(tmpRet["value"]));
                            }
                        }

                        //二期入炉#1采样机B采样头
                        tmpRet = getValue("01#RL2Q_CYJ1_CYTBRun");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(266, Convert.ToInt32(tmpRet["value"]));
                            }
                        }


                        //二期入炉#2采样机A采样头
                        tmpRet = getValue("01#RL2Q_CYJ2_CYTARun");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(286, Convert.ToInt32(tmpRet["value"]));
                            }
                        }

                        //二期入炉#2采样机B采样头
                        tmpRet = getValue("01#RL2Q_CYJ2_CYTBRun");
                        resCode = tmpRet[Commons.RES_CODE];
                        if (string.Equals(resCode, "0"))
                        {
                            String quality = tmpRet["quality"];
                            if ("GOOD".Equals(quality))
                            {
                                modbusClientRC.WriteSingleRegister(287, Convert.ToInt32(tmpRet["value"]));
                            }
                        }

                    }
                    catch (Exception e)
                    {
                        if (modbusClientRC != null)
                        {
                            modbusClientRC.Disconnect();
                        }

                        if (modbusClientRL != null)
                        {
                            modbusClientRL.Disconnect();
                        }

                        modbusClientRC = new ModbusClient();
                        modbusClientRC.Connect("192.168.6.66", 503);

                        modbusClientRL = new ModbusClient();
                        modbusClientRL.Connect("192.168.6.66", 502);
                    }

                    Thread.Sleep(millSecond);
                }

            })).Start();
        }



        /// <summary>
        /// 启动OPC Server
        /// </summary>
        /// <param name="ip"></param>
        /// <param name="port"></param>
        /// <param name="source"></param>
        /// <param name="user"></param>
        /// <param name="pwd"></param>
        /// <returns></returns>
        public String startOpcServer(string ip, string port, string source, string user, string pwd)
        {
            DataBaseTool db = new DataBaseTool(ip, port, source, user, pwd);

            string step = "开始";
            try
            {
                //1初始化配置参数，并检查配置

                pointsConfig = db.point_cfg();

                //2初始化opcServer
                step = "初始化opcServer";
                Host host = new Host("localhost");//创建本机对象
                OpcServerBrowser myBrowser = new OpcServerBrowser(host);  //创建浏览OPC服务器对象myBrowser

                Guid SerGuid;
                int CLSIDFromProgID = myBrowser.CLSIDFromProgID("KEPware.KEPServerEx.V5", out SerGuid);
                if (HRESULTS.Succeeded(CLSIDFromProgID))//如果结果成功 既xyz为正数
                {
                    opcServer = new OpcServer();//声明一个OPCServer对象
                    int result = opcServer.Connect(host, SerGuid);//服务器对象的连接方法（利用主机对象和全局唯一标识）.如果在本机 主机对象可以不写.
                    SERVERSTATUS status;
                    result = opcServer.GetStatus(out status);
                    if (HRESULTS.Succeeded(result))//连接成功了，既xyz为正数
                    {

                        syncIOGroup = new SyncIOGroup(opcServer);
                        foreach (string key in pointsConfig.Keys)
                        {
                            Point tmpPoint = pointsConfig[key];
                            syncIOGroup.Add(tmpPoint.TotalTagName);
                        }
                    }
                }

            }
            catch (Exception e)
            {
                MessageBox.Show("启动OPC服务异常:" + step + "--" + e.Message);
                Thread.Sleep(1000);
                stopOpcServer();
                return "启动OPC服务异常:" + step + "--" + e.Message;
            }
            isStop = false;



            return "success";

        }




        /// <summary>
        /// 启动OPC Server
        /// </summary>
        /// <param name="pointsConfig"></param>
        /// <returns></returns>
        public String startOpcServer(Dictionary<String, Point> pointsConfigOrg)
        {
           

            string step = "开始";
            try
            {
                //1初始化配置参数，并检查配置

                this.pointsConfig = pointsConfigOrg;

                //2初始化opcServer
                step = "初始化opcServer";
                Host host = new Host("localhost");//创建本机对象
                OpcServerBrowser myBrowser = new OpcServerBrowser(host);  //创建浏览OPC服务器对象myBrowser

                Guid SerGuid;
                int CLSIDFromProgID = myBrowser.CLSIDFromProgID("KEPware.KEPServerEx.V5", out SerGuid);
                if (HRESULTS.Succeeded(CLSIDFromProgID))//如果结果成功 既xyz为正数
                {
                    opcServer = new OpcServer();//声明一个OPCServer对象
                    int result = opcServer.Connect(host, SerGuid);//服务器对象的连接方法（利用主机对象和全局唯一标识）.如果在本机 主机对象可以不写.
                    SERVERSTATUS status;
                    result = opcServer.GetStatus(out status);
                    if (HRESULTS.Succeeded(result))//连接成功了，既xyz为正数
                    {

                        syncIOGroup = new SyncIOGroup(opcServer);
                        foreach (string key in pointsConfig.Keys)
                        {
                            Point tmpPoint = pointsConfig[key];
                            syncIOGroup.Add(tmpPoint.TotalTagName);
                        }
                    }
                }

            }
            catch (Exception e)
            {
                MessageBox.Show("启动OPC服务异常:" + step + "--" + e.Message);
                Thread.Sleep(1000);
                stopOpcServer();
                return "启动OPC服务异常:" + step + "--" + e.Message;
            }
            isStop = false;



            return "success";

        }


    }
}
