using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NHTool.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Device.Modbus;

using NHTool.Device.SeriPort;
using NHTool.Device.Radio;
using System.Windows.Forms;
using System.Threading;
using System.Timers;
using NHTool.Device.RFID;
using NHTool.Device.Voice;
using NHTool.Device.LED;
using NHTool.Device.Camera;
using System.Reflection;
using System.IO.Ports;

using NHTool.Device.BY17NCReader;
using NHTool.Device.SXLYRfid;
using System.Runtime.InteropServices;


namespace NHTool.Business
{
    
    /**
      * 汽车调度的基类，全局公共的方法写在此类
      * actionVoice(msg); 调试先关掉
      */
    public abstract class BusinessBase
    {
        public OPCTool myOPCTool = null;
        //调试信息的textArea
        public TextBox debugInfo = null;
        public string lastMsg = "";
        public delegate void SetTextBoxValue(string value);
        public void setDebugInfo(string value)
        {
            
        }

        //物理设备测点
        public System.Collections.Concurrent.ConcurrentDictionary<string, string> realDataDictionary = new System.Collections.Concurrent.ConcurrentDictionary<string, string>();
        //业务逻辑用的全局变量，步骤切换是保存上下文
        public StateEventArgs stateEventArgs = new StateEventArgs();
        //excel中的控制配置
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        //c#中调用数据库的工具类实例
        public HttpDbTool httpDbTool = null;
        public HttpTool httpToolCarControl = null;

        //发送测点缓存JSON对象
        public JObject jsonObjLow = new JObject();
        public JObject jsonObjFast = new JObject();
        public JObject jsonObjNormal = new JObject();

        public Dictionary<string, string> resultData = new Dictionary<string, string>();

        public Modbus modbus = null;

        public Camera camera = null;
        public SerialPortTool serport = null;
        public ReadRfidDevice readRfidDevice = null;
        public ReadRfidDevice readRfidDevice2 = null;
        public SpVoiceDevice voiceDecice = null;
        public SpVoiceDevice voiceDecice2 = null;
        public INBSVoiceDevice iNBSVoiceDevice = null;
        public INBSVoiceDevice iNBSVoiceDevice2 = null;//双向地磅，装2个音响时用
        public LED_Control ledDevice = null;
        public LED_Control ledDevice2 = null;
        public RfidReadrHF cardHFReader = null;
        public SimpleRfidReadrHF simpleCardHFReader = null;
        public Mc3000Led mc3000LedDevice = null;
        public SxlyBigLED m_SxlyBigLED = null;
        public SxlyRfidDevice m_SxlyRfidUHF = null;
        private string pubKey;


        public System.Timers.Timer refreshUITimer = new System.Timers.Timer();
        public delegate void refreshHandler(JObject json);
        public event refreshHandler refreshEvent;

        public delegate Control findControlByName(String name);
        public event findControlByName getControlByKey;

        public Type modbusPointAddress = null;

        public TcpClientTool tcpclient = new TcpClientTool();

        public Boolean isStopWaiting = false;//是否停止等待标志,选矿机交互标志
        public Form showForm = null;
        public string httpControlServerUrl = "";//
        public Dictionary<string, string> dictionaryUseInForm = new Dictionary<string, string>();//form和流程调度直接的数据交互
        public string currentFlowId = "";
        public string deviceTag = "";
        public string deviceName = "";
        public bool carNoCameraAvailable = true;
        private bool IsMainThreadRunStop = false;

        public bool IsMainThreadRunStop1
        {
            get { return IsMainThreadRunStop; }
            set { IsMainThreadRunStop = value; }
        }

        public abstract void mainProcess();//无内容,统一在具体子类实现
        public BusinessBase()
        {
            //构造函数只有必须的，其他统一在子类的initial进行处理
            refreshUITimer.Interval = 1000;
            refreshUITimer.Elapsed += resfreshUI;
            refreshUITimer.Start();


            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            String httpServerUrl = null;
            String httpSendUrl = null;

            if (ctlConfig.ContainsKey("httpServerUrl")
                && ctlConfig.ContainsKey("httpSendUrl")
                && ctlConfig.ContainsKey("httpControlServerUrl"))
            {
                currentFlowId = ctlConfig["currentFlowId"];
                httpControlServerUrl = ctlConfig["httpControlServerUrl"];
                httpServerUrl = ctlConfig["httpServerUrl"];
                httpSendUrl = ctlConfig["httpSendUrl"];
                deviceName = ctlConfig["deviceName"];

                if (httpSendUrl != null && httpSendUrl.Length > 5)
                {
                    startThread(httpSendUrl, "normal");
                }
            }
            else
            {
                MessageBox.Show("初始NHScada Http" + ",配置异常，请检查!");
                System.Environment.Exit(1);
            }
        }

        public virtual void resetProcess()
        {

        }

        public virtual string getPlateInfo() {
            string value = "无车牌";
            if (camera != null)
            {
                value = camera.CapturePlate();
            }
            else 
            {
                LogTool.WriteLog(typeof(BusinessBase), "相机对象为空！");
            }
            return value;
        }

        public virtual void InitStartCyj()
        {
        }
        //无内容,统一在具体子类实现,通知过孙平安检查下
        //程序初始化，此处基类只初始化相关配置，必要的公用工具，其他的设备初始化，均在最底层实现类中进行
        public virtual Boolean initial() { return false; }

        /// <summary>
        /// 初始化Modbus设备(实际地址映射采用基类的枚举Commons.modbusPoint)
        /// </summary>
        /// <param name="ip">亚当模块IP</param>
        /// <param name="port">亚当模块Port</param>
        /// <param name="unit">modbus设备的ID号(0-255)默认不填写为01</param>
        /// <returns></returns>
        public virtual bool initiModbusDevice(string ip, string port, byte unit = 0x01)
        {
            modbusPointAddress = typeof(Commons.modbusPoint);
            bool result = true;
            resultData.Clear();
            modbus = new Modbus();
            modbus.modbusDeviceEvent += modbusDeviceDataHandler;
            resultData = modbus.initial(ip, port, unit);
            if (resultData[Commons.RES_CODE] != "0")
            {
                LogTool.WriteLog(typeof(BusinessBase), resultData[Commons.RES_MSG]);
                result = false;
            }
            else
            {
                modbus.start();

            }
            return result;

        }

        //web页面下发控制的htt命令的处理回调函数，统一在具体子类实现
        public virtual JObject processCarHttpRequestMethod(String httpStr){
            return new JObject();
        }

        /// <summary>
        /// 初始化Modbus设备(实际地址映射采用参数中的modbusPoint)
        /// </summary>
        /// <param name="ip">亚当模块IP</param>
        /// <param name="port">亚当模块Port</param>
        /// <param name="modbusPoint">实际亚当模块的地址枚举实例[typeof(Commons.modbusPoint)]</param>
        /// <param name="unit">modbus设备的ID号(0-255)默认不填写为01</param>
        /// <returns></returns>
        public virtual bool initiModbusDevice(string ip, string port, Type modbusPoint, byte unit = 0x01)
        {
            modbusPointAddress = modbusPoint;
            bool result = true;
            resultData.Clear();
            modbus = new Modbus();
            modbus.modbusDeviceEvent += modbusDeviceDataHandler;
            resultData = modbus.initial(ip, port, unit);
            if (resultData[Commons.RES_CODE] != "0")
            {
                LogTool.WriteLog(typeof(BusinessBase), resultData[Commons.RES_MSG]);
                result = false;
            }
            else
            {
                modbus.start();
            }

            return result;

        }

        public virtual bool Connect(string ip,Int16 port,string userName,string password) {
            if (camera != null) {
                return camera.Connect(ip,port,userName,password);
            }
            return false;
        }

        public virtual bool DisConnectCamera()
        {
            if (camera != null){
                return camera.DisConnect();
            }
            return false;
        }

        public virtual void StopRealPlay() {
            if (camera != null) {
                camera.StopLiveView();
            }
        }

        public virtual void OpenRealPlay(PictureBox control)
        {
            if (camera != null)
            {
                camera.StartLiveView(control);
            }
        }

        public virtual bool initCameraDevice(string ip, Int16 port, string userName, string password)
        {
            bool result = false;
            try
            {
                camera = new Camera();
                result = this.Connect(ip,port,userName,password);

                camera.catchCarNo += processCaughtCarNo;
            }
            catch (Exception e){
                Console.WriteLine(e.Message);
            }
            return result;
        }

        /// <summary>
        /// 初始化Rfid设备
        /// </summary>
        /// <param name="ip">设备IP</param>
        /// <param name="port">设备端口号</param>
        /// <returns></returns>
        public virtual bool initiRfidDevice(string ip, string port, string antPortStr)
        {
            bool result = true;
            readRfidDevice = new ReadRfidDevice();
            result = readRfidDevice.initial(ip, port, antPortStr);
            return result;

        }

       public void initHFRfidReader()
        {
            cardHFReader = new RfidReadrHF();
            cardHFReader.gf_Rfid_Reader += processHFCardID;
            cardHFReader.ConnectDevice();
        }

        
       public void initSimpleHFRfidReader()
       {
           simpleCardHFReader = new SimpleRfidReadrHF();
           simpleCardHFReader.gf_Rfid_Reader += processHFCardID;
           simpleCardHFReader.ConnectDevice();
       }


        public void InitMc3000LedDevice()
        {
            mc3000LedDevice = new Mc3000Led();
        }


        /// <summary>
        /// 初始化Rfid设备
        /// </summary>
        /// <param name="ip">设备IP</param>
        /// <param name="port">设备端口号</param>
        /// <returns></returns>
        public virtual bool initiRfidDevice2(string ip, string port, string antPortStr)
        {
            bool result = true;
            readRfidDevice2 = new ReadRfidDevice();
            result = readRfidDevice2.initial(ip, port, antPortStr);
            return result;
        }

        public void InitSxlyBigLED()
        {
            m_SxlyBigLED = new SxlyBigLED();
        }

        public void InitSxlyRfidUHF()
        {
            m_SxlyRfidUHF = new SxlyRfidDevice();
            m_SxlyRfidUHF.Sxly_Rfid_Reader += processRfid4UHF;
            m_SxlyRfidUHF.ConnectDevice();
        }
        /// <summary>
        ///  更新modbus设备数据
        /// </summary>
        /// <param name="resultDictionary">modbus设备返回的数据</param>
        public virtual void modbusDeviceDataHandler(Dictionary<string, string> resultDictionary)
        {
            if (resultDictionary.ContainsKey(Commons.RES_CODE))
            {
                string[] keys = Enum.GetNames(modbusPointAddress);
                foreach (var key in keys)
                {
                    if (key != null)
                    {
                        if (realDataDictionary.ContainsKey(key))
                        {
                            realDataDictionary[key] = Commons.deviceInfoErrFlag;

                        }
                        else
                        {
                            realDataDictionary.TryAdd(key, Commons.deviceInfoErrFlag);
                        }

                    }


                }

            }
            else
            {
                foreach (var item in resultDictionary)
                {
                    pubKey = Enum.GetName(modbusPointAddress, int.Parse(item.Key));

                    if (pubKey != null)
                    {
                        string Value = "";
                        if (item.Value.Equals("True") || item.Value.Equals("true"))
                        {
                            Value = Commons.signalON.ToString();

                        }
                        if (item.Value.Equals("False") || item.Value.Equals("false"))
                        {
                            Value = Commons.signalOFF.ToString();

                        }
                        if (realDataDictionary.ContainsKey(pubKey))
                        {
                            realDataDictionary[pubKey] = Value;

                        }
                        else
                        {
                            realDataDictionary.TryAdd(pubKey, Value);
                        }


                        //临时调试日志信息
                        if (pubKey.Equals("inRadiation")) 
                        {
                            if (Value.Equals(Commons.signalOFF.ToString())) 
                            {
                                LogTool.WriteLog(typeof(BusinessBase), "卸煤沟处对射inRadiation值>>>>>>>>：" + Value);
                            }
                            else
                            {
                                LogTool.WriteLog(typeof(BusinessBase), "卸煤沟处对射inRadiation值--------：" + Value);
                            }
                        }



                    }


                }


            }

        }

        /// <summary>
        /// 初始化串口设备
        /// </summary>
        /// <param name="name">串口名称(com3)</param>
        /// <param name="baud">波特率</param>
        /// <param name="par">奇偶校验(0:无校验,1:奇校验,2:偶校验)</param>
        /// <param name="dBits">数据位</param>
        /// <param name="sBits">停止位</param>
        /// <returns></returns>
        public virtual bool initiSerialPortDevice(string name, string baud, string par, string dBits, string sBits)
        {
            bool result = true;
            try
            {
                serport = new SerialPortTool(name, baud, par, dBits, sBits);
                serport.comPort.DataReceived += comPort_DataReceived;
            }
            catch (Exception ex)
            {

                result = false;
                LogTool.WriteLog(typeof(BusinessBase), ex);

            }

            return result;
        }


        // <summary>
        /// 更新串口重量数据，各个项目需要根据衡器协议覆写方法解析
        /// </summary>
        /// <param name="args">串口事件返回的数据</param>
        public DateTime lastGetWegihtTime = DateTime.Now;
      public virtual void  comPort_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            SerialPort comPort = (SerialPort)sender;
            byte EndByte = 0X3D;
            List<byte> _byteData = new List<byte>();
            bool found = false;//是否检测到结束符号
            while (comPort.BytesToRead > 0 || !found)
            {
                byte[] readBuffer = new byte[comPort.ReadBufferSize + 1];
                int count = comPort.Read(readBuffer, 0, comPort.ReadBufferSize);
                for (int i = 0; i < count; i++)
                {
                    _byteData.Add(readBuffer[i]);

                    if (readBuffer[i] == EndByte)
                    {
                        found = true;
                    }
                }
            }
            if (_byteData.Count >= 8)
            {
                byte[] readBuffer = new byte[_byteData.Count];
                int index = _byteData.Count - 1;
                for (int i = 0; i < _byteData.Count; i++)
                {

                    readBuffer[i] = _byteData[index - i];
                }
                string readString = Encoding.ASCII.GetString(readBuffer, 0, readBuffer.Length - 1);

                float weight = 0;
                float.TryParse(readString, out weight);
                weight = weight * 1000;



                if (realDataDictionary.ContainsKey("carWeight"))
                {
                    realDataDictionary["carWeight"] = Convert.ToString(weight);
                }
                else
                {
                    realDataDictionary.TryAdd("carWeight", Convert.ToString(weight));
                }

                lastGetWegihtTime = DateTime.Now;

            }
        }


       public void InitINBSVoiceDevice()
       {
           iNBSVoiceDevice = new INBSVoiceDevice();
       }


       public void InitINBSVoiceDevice2()
       {
           iNBSVoiceDevice2 = new INBSVoiceDevice("2");
       }

        /// <summary>
        /// 初始化语音操作
        /// </summary>
        /// <returns></returns>
        public virtual bool initiVoiceDecice()
        {
            voiceDecice = new SpVoiceDevice();
            return true;
        }


        /// <summary>
        /// 初始化语音操作
        /// </summary>
        /// <returns></returns>
        public virtual bool initiVoiceDecice2()
        {
            voiceDecice2 = new SpVoiceDevice();
            return true;
        }


        /// <summary>
        /// 语音播放回调函数
        /// </summary>
        /// <param name="b">是否播放完毕</param>
        /// <param name="InputWordPosition">已经播放的文字长度</param>
        /// <param name="InputWordLength">已经播放位置位置</param>
        public virtual void voiceDeviceCallBack(bool b, int InputWordPosition, int InputWordLength)
        {


        }


        /// <summary>
        /// 初始化LED
        /// </summary>
        /// <param name="ip">IP地址</param>
        /// <returns></returns>
        public virtual bool initiLedDevice(string ip)
        {
            ledDevice = new LED_Control();
            ledDevice.initial(ip, Commons.pwd);

            return true;
        }


        /// <summary>
        /// 初始化LED2
        /// </summary>
        /// <param name="ip">IP地址</param>
        /// <returns></returns>
        public virtual bool initiLedDevice2(string ip)
        {
            ledDevice2 = new LED_Control();
            ledDevice2.initial(ip, Commons.pwd);

            return true;
        }

        public virtual void processRfid4UHF(string callBackInfo, string stepfunction)
        {

        }

        public virtual void processHFCardID(string cardId,int stepfunction)
        {

        }

        public virtual void processHFCardID(string cardId)
        {

        }

        public virtual void processCaughtCarNo(string carNo)
        {

        }

        public virtual void stopSysRun()
        {

        }

        //处理http的设置命令的请求,C#中只处理设置的，读取的在java中处理
        public virtual JObject processHttpRequestMethod(String httpStr)
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


                }

                //遍历写测点
                for (int i = 0; i < param.Count; i++)
                {
                    JObject tmpObj = (JObject)param[i];
                    string device = (string)tmpObj["device"];
                    string point = (string)tmpObj["point"];
                    string value = (string)tmpObj["value"];
                    string key = groupId + "#" + device + "_" + point;

                    String resCode = "";
                    if ("1".Equals(resCode))
                    {
                        ret.Add(Commons.RES_CODE, "1");
                        //ret.Add(Commons.RES_MSG, retSet[Commons.RES_MSG]);
                        return ret;
                    }
                }

                ret.Add(Commons.RES_CODE, "0");
                ret.Add(Commons.RES_MSG, "succ");
                LogTool.WriteLog(typeof(OPCTool), httpStr + ",执行命令成功," + param.ToString());
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



        /// <summary>
        /// 发送到NHScada中的http数据
        /// </summary>
        /// <param name="httpSendUrl"></param>
        /// <param name="type"></param>
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
                    tmpJson = jsonObjLow;
                }
                else if (type.Equals("normal"))
                {
                    millSecond = 1000;
                    tmpJson = jsonObjNormal;
                }
                else if (type.Equals("fast"))
                {
                    millSecond = 250;
                    tmpJson = jsonObjFast;
                }

                while (!IsMainThreadRunStop)
                {
                    try
                    {
                        tmpJson = new JObject();
                        Thread.Sleep(millSecond);
                        int sendNums = 0;

                        foreach (var item in realDataDictionary)
                        {
                            if (item.Key != null && item.Key.StartsWith("01#"))
                            {
                                tmpJson.Remove(item.Key);
                                tmpJson.Add(item.Key, item.Value);
                                sendNums++;
                            }
                        }

                        if (sendNums > 0)
                        {
                            tmpJson.Remove("batchGetTime");
                            DateTime dt = DateTime.Now;
                            tmpJson.Add("batchGetTime", string.Format("{0:yyyyMMddHHmmss}", dt));
                            tmpJson.Remove("UpdateFrequency");
                            tmpJson.Add("UpdateFrequency", type);
                            httpToolSend.sendHttpMsg(tmpJson.ToString(), httpSendUrl);
                        }
                        tmpJson.RemoveAll();
                        tmpJson = null;
                    }
                    catch (Exception ex)
                    {
                        LogTool.WriteLog(typeof(BusinessBase), ex);
                    }
                }

            })).Start();
        }


        /// <summary>
        /// 更新内存中的实时数据到UI界面（JSON)
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public virtual void resfreshUI(object sender, ElapsedEventArgs e)
        {
            JObject uiJson = new JObject();
            if (refreshEvent != null)
            {

                foreach (var item in realDataDictionary)
                {
                    uiJson.Add(item.Key, item.Value);
                }
                uiJson.Add("batchGetTime", string.Format("{0:yyyyMMddHHmmss}", DateTime.Now));
                refreshEvent(uiJson);

            }

        }



        /// <summary>
        /// 抬起车辆前方道闸
        /// </summary>
        public virtual void actionFrontGateUp()
        {
            var enums = Enum.GetValues(modbusPointAddress);

            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.frontGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
                if (item.ToString().Equals(Commons.frontGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
            }

          

        }

        /// <summary>
        ///  落下车辆前方道闸
        /// </summary>
        public virtual void actionFrontGateDown()
        {

            var enums = Enum.GetValues(modbusPointAddress);

            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.frontGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
                if (item.ToString().Equals(Commons.frontGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }



        }



        /// <summary>
        /// 抬起车辆后方道闸
        /// </summary>
        public virtual void actionBackGateUp()
        {
            var enums = Enum.GetValues(modbusPointAddress);

            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.backGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
                if (item.ToString().Equals(Commons.backGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
            }

          
        }

        /// <summary>
        ///  落下车辆后方道闸
        /// </summary>
        public virtual void actionbackGateDown()
        {

            var enums = Enum.GetValues(modbusPointAddress);

            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.backGateDownOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
                if (item.ToString().Equals(Commons.backGateUpOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }
        }




        /// <summary>
        /// 红灯
        /// </summary>
        public virtual void actionLedRed()
        {

            var enums = Enum.GetValues(modbusPointAddress);

            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.lightGreenOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
                if (item.ToString().Equals(Commons.lightRedOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
            }
            


        }

        /// <summary>
        /// 绿灯
        /// </summary>
        public virtual void actionLedGreen()
        {

            var enums = Enum.GetValues(modbusPointAddress);

            foreach (var item in enums)
            {
                if (item.ToString().Equals(Commons.lightGreenOut))
                {
                    modbus.setCommand(((int)item).ToString(), "True", Commons.modbusType.COIL_STATUS);
                }
                if (item.ToString().Equals(Commons.lightRedOut))
                {
                    modbus.setCommand(((int)item).ToString(), "False", Commons.modbusType.COIL_STATUS);
                }
            }


        }





        /// <summary>
        /// 播放语音
        /// </summary>
        /// <param name="text">语音文本</param>
        public virtual void actionVoice(string text)
        {

            voiceDecice.Speak(text, voiceDeviceCallBack);

        }

        /// <summary>
        /// LED提示
        /// </summary>
        /// <param name="text">提示文本</param>
        public virtual void actiomLedShow(string text)
        {

            ledDevice.sendText_Screen(Commons.pwd, text);

        }


        /// <summary>
        /// 触发天线 读超高频卡
        /// </summary>
        public Dictionary<String, String> actionGetCarNo()
        {

            Dictionary<String, String> retDc = Commons.createDictionary("0", "succ");

            return retDc;

        }

        /**
         * 读取矿卡，成功后，并构造CoalCardInfo对象，更新到StateEventArgs中    
         */
        public Dictionary<String, String> actionGetCoalNo()
        {
            Dictionary<String, String> retDc = Commons.createDictionary("0", "succ");
            if ("0".Equals(Commons.getDcResCode(retDc)))
            {
                CoalCardInfo coalCardInfo = new CoalCardInfo();
                //根据retDc填充对象
                stateEventArgs.coalCardInfo = coalCardInfo;
            }
            return retDc;
        }

       
        /**
         * 休眠n秒        
         */
        public void idle(int n)
        {
            try
            {
                Thread.Sleep(n * 1000);
            }
            catch (Exception e)
            {

            }
        }

        /**
         * 判断红外对射挡住n秒
         * @param tip           :压入测点内存的提示前缀，方便调试时查看，可以不填
         * @param rfidId        :内存测点中的红外对射的ID
         */
        public Boolean isRadiationBolckOk(string tip, string rfidId, int seconds)
        {
            Boolean channelIsOpen = true;
            string channelStatus = "";
            for (int i = 0; i < seconds; i++)
            {
                channelStatus = Commons.getDcValue(realDataDictionary, rfidId);
                //如果通道打开
                if (!Commons.signalON.Equals(channelStatus))
                {
                    channelIsOpen = false;
                    break;
                }
                idle(1);
            }
            return channelIsOpen;
        }


        /// <summary>
        /// 判断红地感线圈产生信号N秒
        /// </summary>
        /// <param name="tip"></param>
        /// <param name="rfidId"></param>
        /// <param name="seconds"></param>
        /// <returns></returns>
        public Boolean isGroundSense(string tip, string gsId, int seconds)
        {
            Boolean isSignalON = true;
            string signalStatus = "";
            for (int i = 0; i < seconds; i++)
            {
                signalStatus = Commons.getDcValue(realDataDictionary, gsId);
                if (!Commons.signalON.Equals(signalStatus))
                {
                    isSignalON = false;
                    break;
                }
                idle(1);
            }
            return isSignalON;
        }


        //重置对象
        public virtual void restData()
        {
            stateEventArgs.carWeight = 0;
            stateEventArgs.carNo = "";
            stateEventArgs.cardID = "";
            stateEventArgs.nextStep = Commons.STEP.IDLE;
            stateEventArgs.coalCardInfo = new CoalCardInfo();
            stateEventArgs.recordNo = "";
            stateEventArgs.sampleCode = "";
            stateEventArgs.sampleType = "";
            stateEventArgs.carType = "";
            stateEventArgs.sampleCfgPonits = "";
            stateEventArgs.batchChgFlag = "";
            stateEventArgs.sampleStartTime = "";
            stateEventArgs.sampleCoordList = new JArray();
            stateEventArgs.stepExcuteCount = 0;
            stateEventArgs.mineCardID = "";
            stateEventArgs.mineName = "";
            stateEventArgs.ditchCd = "";
            stateEventArgs.queueOrder = "";
            stateEventArgs.actionResultMsg = "";

            
            if (readRfidDevice != null)
            {
                readRfidDevice.stop_read_RFID_Info();
                readRfidDevice.clearEcpTagList();
            }

            if (readRfidDevice2 != null)
            {
                readRfidDevice2.stop_read_RFID_Info();
                readRfidDevice2.clearEcpTagList();
            }
        }

        //提示信息，LED+声音
        public virtual void tipMsg(string msg)
        {
            if (!lastMsg.Equals(msg))
            {
                string LogicTagName = "01#" + deviceTag + "_LEDShow";
                Commons.putDictionaryData(ref realDataDictionary, LogicTagName, msg);
                lastMsg = msg;
                writeMointorSingle("tipMsg", msg);
                actiomLedShow(msg);
                actionVoice(msg);
                LogTool.WriteLog(typeof(BusinessBase), "tipMsg=" + msg);
            }
        }

        public void writeMointorSingle(String key, String value)
        {
            if (realDataDictionary.ContainsKey(key))
            {
                realDataDictionary[key] = value;
            }
            else
            {
                realDataDictionary.TryAdd(key, value);
            }
        }

        //失败时进行处理
        public void resetAndToNextSetp(string tipMsgIn, Commons.STEP nextStep)
        {
            restData();
            stateEventArgs.nextStep = nextStep;
            if (tipMsgIn != null && tipMsgIn.Length > 0)
            {
                tipMsg(tipMsgIn);
            }
        }

        /**
         * 获取Json对象中的值
         */
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

        /// <summary>
        /// 创建对象实例
        /// </summary>
        /// <typeparam name="T">要创建对象的类型</typeparam>
        /// <param name="assemblyName">类型所在程序集名称</param>
        /// <param name="nameSpace">类型所在命名空间</param>
        /// <param name="className">类型名</param>
        /// <returns></returns>
        public static T CreateInstance<T>(string assemblyName, string nameSpace, string className)
        {
            try
            {
                string fullName = nameSpace + "." + className;//命名空间.类型名
                //此为第一种写法
                object ect = Assembly.Load(assemblyName).CreateInstance(fullName);//加载程序集，创建程序集里面的 命名空间.类型名 实例
                return (T)ect;//类型转换并返回
                //下面是第二种写法
                //string path = fullName + "," + assemblyName;//命名空间.类型名,程序集
                //Type o = Type.GetType(path);//加载类型
                //object obj = Activator.CreateInstance(o, true);//根据类型创建实例
                //return (T)obj;//类型转换并返回
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
                //发生异常，返回类型的默认值
                return default(T);
            }
        }

        public virtual void processCarWeightPacketMethod(byte[] packetData)
        {
        }

    }

}
