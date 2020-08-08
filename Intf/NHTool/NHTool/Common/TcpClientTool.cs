using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace NHTool.Common
{
    public class TcpClientTool
    {

        public delegate void ReceiveEventHandler(object sender, MyEventArgs e);
        public event ReceiveEventHandler OnReceive = null;
		private bool isConnected = false;
        private TcpClient tcpClient;
        private NetworkStream networkStream = null;
        private BinaryReader reader;
        private BinaryWriter writer;
		private string hostAddress = null;
		private int port = 0;
	
       /// <summary>
		/// 服务器地址
		/// </summary>
		public string HostAddress
		{
			get
			{
				return hostAddress;
			}
			set
			{
				hostAddress = value;
			}
		}

		/// <summary>
		/// 服务器监听端口
		/// </summary>
		public int Port
		{
			get
			{
				return port;
			}
			set
			{
				port = value;
			}
		}

		/// <summary>
		/// 是否与服务器处于连接状态
		/// </summary>
		public bool IsConnected
		{
			get
			{
				return isConnected;
			}
		}

		public TcpClientTool()
		{
			//
			// TODO: 在此处添加构造函数逻辑
			//
		}

        public TcpClientTool(string hostAddress, int port)
		{
			this.hostAddress = hostAddress;
			this.port = port;
		}

        /// <summary>
        /// 连接远程服务器
        /// </summary>
        /// <returns></returns>
        public bool ConnectToServer()
        {
            try
            {
                IPAddress ipaddress = IPAddress.Parse(hostAddress);
                tcpClient = new TcpClient();
                tcpClient.Connect(ipaddress, port);
                Thread.Sleep(500);
                if (tcpClient != null)
                {

                    networkStream = tcpClient.GetStream();
                    writer = new BinaryWriter(networkStream);
                    reader = new BinaryReader(networkStream);
                }
                isConnected = true;
                return true;
            }
            catch (Exception ex)
            {
                isConnected = false;
                 return false;
                
            }
        }
        /// <summary>
        /// 向远程服务器发送命令
        /// </summary>
        /// <param name="msg"></param>
        /// <returns></returns>
        public bool  SendMessage(string msg)
        {
            
            try
            {
                byte[] jsondata = Encoding.Default.GetBytes(msg);
                writer.Write(jsondata);
                 writer.Flush();
                return true;
            }
            catch (Exception ex)
            {
                if (reader != null)
                {
                    reader.Close();
                }
                if (writer != null)
                {
                    writer.Close();
                }
                if (tcpClient != null)
                {
                    tcpClient.Close();
                }

               
                isConnected = false;
                return false;
            }
        }
        /// <summary>
        /// 接收服务器发送过来的数据
        /// </summary>
        private void receiveMessage()
        {
            while (tcpClient.Connected)
            {
                try
                {
                    if (networkStream.CanRead)
                    {
                        byte[] bytes = new byte[tcpClient.ReceiveBufferSize];
                        int numBytesRead = networkStream.Read(bytes, 0, (int)tcpClient.ReceiveBufferSize);
                        if (numBytesRead > 0)
                        {   
                            byte[] bytesRead = new byte[numBytesRead];
                            Array.Copy(bytes, bytesRead, numBytesRead);
                            string returndata = Encoding.Default.GetString(bytesRead);
                            MyEventArgs e = new MyEventArgs(returndata);
                            if (OnReceive != null)
                            {
                                OnReceive(this, e);
                            }
                        }
                        else
                        {
                           
                        }
                    }

                }
                catch (Exception ex)
                {
                    
                    if (reader != null)
                    {
                        reader.Close();
                    }
                    if (writer != null)
                    {
                        writer.Close();
                    }
                    if (tcpClient != null)
                    {
                        tcpClient.Close();
                    }

                }
            }
        }


       /// <summary>
       /// 断开与服务器的连接
       /// </summary>
        public void disconnect()
        {
          
            if (reader != null)
            {
                reader.Close();
            }
            if (writer != null)
            {
                writer.Close();
            }
            if (tcpClient != null)
            {
                tcpClient.Close();
            }

            isConnected = false;
        }

    }


    public class MyEventArgs : EventArgs
    {
        private string message;

        /// <summary>
        /// 信息
        /// </summary>
        public string Message
        {
            get { return message; }
        }

        //构造函数
        public MyEventArgs(string message)
        {
            this.message = message;
        }
    }
}
