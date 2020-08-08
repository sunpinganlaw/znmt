using System;
using System.Collections.Generic;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Threading;
using NHTool.Device;
using NHTool.Device.SeriPort;

namespace NHTool.Device.LIMS.EnergyMeter
{
     class energyMeter : Device
    {
        int deviceState;
        Dictionary<String, String> ret = new Dictionary<String, String>();
        private Thread readThread = null;
        public SerialPortTool energyMeterSerialPort;
        
        public delegate void energyMeterHander(Dictionary<String, String> resultDictionary);
        /// <summary>
        /// 能量计串口接收到仪器返回数据的事件
        /// </summary>
        public event energyMeterHander energyMeterEvent;

        public delegate void energyMeterMsgHander(string msg);

        public energyMeterCmdTyoe nowCmd;


        private bool comBusy = false;
        /// <summary>
        ///能量计类执行过程中的消息事件(程序日志信息）
        /// </summary>
        public event energyMeterMsgHander energyMeterMsgEvent;


        public enum energyMeterCmdTyoe : int
        {
            /// <summary>
            /// 查询能量计硬件号
            /// </summary>
            VER = 1,
            /// <summary>
            /// 查询能量计量程
            /// </summary>
            RAG = 2,
            /// <summary>
            /// 查询能量计名称
            /// </summary>
            NAM = 3,
            /// <summary>
            /// 查询能量计序列号
            /// </summary>
            SSN = 4,
            /// <summary>
            /// 设置波长长度
            /// </summary>
            WAV1064 = 5,
            /// <summary>
            /// 设置量程自适应
            /// </summary>
            AUT1 = 6,
            /// <summary>
            /// 设置外触发
            /// </summary>
            SRC1 = 7,
            /// <summary>
            /// 设置内触发
            /// </summary>
            SRC0 = 8,
            /// <summary>
            /// 设置上升沿触发
            /// </summary>
            POL1 = 9,
            /// <summary>
            /// 设置下降沿触发
            /// </summary>
            POL0 = 10
        }




        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="name">串口名称</param>
        /// <param name="baud">串口波特率</param>
        /// <param name="par">校验位</param>
        /// <param name="dBits">数据位</param>
        /// <param name="sBits">停止位</param>
        /// <returns></returns>
        public Dictionary<String, String> initial(string name, string baud, string par, string dBits, string sBits)
        {

            energyMeterSerialPort = new SerialPortTool(name, baud, par, dBits, sBits);
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            deviceState = (int)NHTool.Common.Commons.deviceState.INITIAL;
            energyMeterSerialPort.comPort.DataReceived += ComPort_DataReceived;

            if (energyMeterMsgEvent != null)
            {

                energyMeterMsgEvent("能量计收到初始化命令");

            }

            return ret;

        }


        /// <summary>
        /// 能量计发送命令
        /// </summary>
        /// <param name="cmdTyoe">命令类型</param>
        /// <returns></returns>
        public Dictionary<String, String> energyMeterControl(energyMeterCmdTyoe cmdTyoe)
        {
            energyMeterSerialPort.DiscardBuffer();
            nowCmd = cmdTyoe;
            byte[] array = System.Text.Encoding.ASCII.GetBytes(Enum.GetName(typeof(energyMeterCmdTyoe), cmdTyoe)+"\n");  //数组array为对应的ASCII数组    
            string ASCIIstr2 = null;
            for (int i = 0; i < array.Length; i++)
            {
                int asciicode = (int)(array[i]);
                ASCIIstr2 += Convert.ToString(asciicode);//字符串ASCIIstr2 为对应的ASCII字符串
            }
            energyMeterSerialPort.WriteData(ASCIIstr2);

            if (energyMeterMsgEvent != null)
            {

                energyMeterMsgEvent("能量计收到命令：--->>>"+ Enum.GetName(typeof(energyMeterCmdTyoe),cmdTyoe)+"-->>对应报文:--->>>"+ ASCIIstr2);

            }
            
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            return ret;

        }




        /// <summary>
        /// 能量计发送命令
        /// </summary>
        /// <param name="cmdTyoe">命令类型</param>
        /// <returns></returns>
        public Dictionary<String, String> energyMeterControl(energyMeterCmdTyoe cmdTyoe,int timeout=1000)
        {
            if (comBusy)
            {
                ret.Clear();
                ret.Add(Common.Commons.RES_CODE, "1");
                ret.Add(Common.Commons.RES_MSG, "串口通信忙");
                ret.Add(Enum.GetName(typeof(energyMeterCmdTyoe), cmdTyoe), "串口通信忙");
                
                if (energyMeterEvent != null)
                {
                    energyMeterEvent(ret);

                }
            }


            comBusy = true;
            energyMeterSerialPort.comPort.DataReceived -= ComPort_DataReceived;
            energyMeterSerialPort.DiscardBuffer();


            byte[] array = System.Text.Encoding.ASCII.GetBytes(Enum.GetName(typeof(energyMeterCmdTyoe), cmdTyoe) + "\n");  //数组array为对应的ASCII数组    
            string ASCIIstr2 = null;
            for (int i = 0; i < array.Length; i++)
            {
                int asciicode = (int)(array[i]);
                ASCIIstr2 += Convert.ToString(asciicode);//字符串ASCIIstr2 为对应的ASCII字符串
            }
            energyMeterSerialPort.WriteData(ASCIIstr2);

            if (energyMeterMsgEvent != null)
            {

                energyMeterMsgEvent("能量计收到命令：--->>>" + Enum.GetName(typeof(energyMeterCmdTyoe), cmdTyoe) + "-->>对应报文:--->>>" + ASCIIstr2);

            }

            bool timeOut = false;
            new Thread(new ThreadStart(() =>
            {

                DateTime dt = DateTime.Now;
                while (energyMeterSerialPort.comPort.BytesToRead < 2)
                {
                    Thread.Sleep(1);

                    if (DateTime.Now.Subtract(dt).TotalMilliseconds > timeout) //如果秒后仍然无数据返回，则视为超时
                    {


                        ret.Clear();
                        ret.Add(Common.Commons.RES_CODE, "1");
                        ret.Add(Common.Commons.RES_MSG, "读串口数据超时");
                        ret.Add(Enum.GetName(typeof(energyMeterCmdTyoe), cmdTyoe), "串口通信数据超时");
                        timeOut = true;
                        if (energyMeterEvent != null)
                        {
                            energyMeterEvent(ret);

                        }
                        comBusy = false;
                        break;
                    }
                }


                if (!timeOut)
                {
                    SerialPort comPort = energyMeterSerialPort.comPort;
                    byte EndByte = 0X0A;//-----换行标志符号
                    List<byte> _byteData = new List<byte>();
                    bool found = false;//是否检测到结束符号
                    string readString = "";


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
                    if (_byteData.Count > 1)
                    {
                        byte[] readBuffer = new byte[_byteData.Count];
                        int index = _byteData.Count - 1;
                        for (int i = 0; i < _byteData.Count; i++)
                        {

                            readBuffer[i] = _byteData[i];
                        }
                        readString = Encoding.ASCII.GetString(readBuffer, 0, readBuffer.Length - 1);
                    }


                    ret.Clear();
                    ret.Add(Common.Commons.RES_CODE, "0");
                    ret.Add(Common.Commons.RES_MSG, "OK");
                    ret.Add(Enum.GetName(typeof(energyMeterCmdTyoe), cmdTyoe), readString);

                    if (energyMeterEvent != null)
                    {
                        energyMeterEvent(ret);

                    }
                    comBusy = false;
                }

               


            })).Start();


            
            return ret;

        }






        /// <summary>
        /// 串口接收事件的委托
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ComPort_DataReceived(object sender, System.IO.Ports.SerialDataReceivedEventArgs e)
        {


            SerialPort comPort = (SerialPort)sender;
            byte EndByte = 0X0A;//-----换行标志符号
            List<byte> _byteData = new List<byte>();
            bool found = false;//是否检测到结束符号
            string readString = "";


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
            if (_byteData.Count > 1)
            {
                byte[] readBuffer = new byte[_byteData.Count];
                int index = _byteData.Count - 1;
                for (int i = 0; i < _byteData.Count; i++)
                {
                    
                    readBuffer[i] = _byteData[i];
                }
                 readString = Encoding.ASCII.GetString(readBuffer, 0, readBuffer.Length - 1);
            }



            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            ret.Add(Enum.GetName(typeof(energyMeterCmdTyoe), nowCmd), readString);

            if (energyMeterEvent != null)
            {
                energyMeterEvent(ret);

            }
            
        }

      

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override Dictionary<String, String> start()
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");

            deviceState = (int)NHTool.Common.Commons.deviceState.START;

            readThread = new Thread(readThreadWork);
            readThread.IsBackground = true;
            readThread.Start();
            return ret;

        }


        


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override Dictionary<String, String> stop()
        {
            energyMeterSerialPort.ClosePort();
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            deviceState = (int)NHTool.Common.Commons.deviceState.STOP;
            return ret;

        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override int getState()
        {
            return deviceState;
        }


        /// <summary>
        /// 
        /// </summary>
        private void readThreadWork()
        {

            while (true)
            {


                System.Threading.Thread.Sleep(5000);
            }

        }


       


    }

 }
