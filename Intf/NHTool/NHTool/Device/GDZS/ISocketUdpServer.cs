using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace NHTool.Device.GDZS
{
     interface ISocketUdpServer
    {
        void Start();
        void Stop();
        int SendData(byte[] data, IPEndPoint remoteEndPoint);

        event ReceiveDataHandler ReceivedDataEvent;
        event ErrorHandler ErrorEvent;
        
    }


    public delegate void ReceiveDataHandler(SocketState state);

    public delegate void OnlineChangeHandler(int onlines, EndPoint client);

    public delegate void ErrorHandler(string error, EndPoint client);


    public class SocketState
    {
        public byte[] Buffer = new byte[1024 * 8];
        public Socket Self;
        public EndPoint RemotePoint = new IPEndPoint(IPAddress.Any, 0);
        public DateTime ReceivedTime { get; set; }
    }
}
