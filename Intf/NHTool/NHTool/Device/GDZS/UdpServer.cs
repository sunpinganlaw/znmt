using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace NHTool.Device.GDZS
{
    class UdpServer: ISocketUdpServer
    {

        private readonly Socket _udpSocket = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
        private bool _isListening;

        public UdpServer(IPEndPoint localPoint)
        {
            _udpSocket.ReceiveBufferSize = 1024 * 8;
            _udpSocket.Bind(localPoint);
        }

        public UdpServer(int port)
        {
            IPEndPoint localPoint = new IPEndPoint(IPAddress.Any, port);
            _udpSocket.ReceiveBufferSize = 1024 * 8;
            _udpSocket.Bind(localPoint);
        }




        public Socket GetUdpSocket()
        {
            return _udpSocket;
        }

        public void Start()
        {
            _isListening = true;
            BeginReceive();
        }

        public void Stop()
        {
            _isListening = false;
        }

        public int SendData(byte[] data, IPEndPoint remoteEndPoint)
        {
            return _udpSocket.SendTo(data, remoteEndPoint);
        }

        public event ReceiveDataHandler ReceivedDataEvent;

        public event ErrorHandler ErrorEvent;

        private void BeginReceive()
        {
            if (_isListening)
            {
                SocketState state = new SocketState { Self = _udpSocket };
                _udpSocket.BeginReceiveFrom(state.Buffer, 0, state.Buffer.Length, SocketFlags.None,
                    ref state.RemotePoint, ReceiveCallback, state);
            }
        }

        private void ReceiveCallback(IAsyncResult ar)
        {
            var state = ar.AsyncState as SocketState;
            try
            {
                if (state != null)
                {
                    int receiveLen = state.Self.EndReceiveFrom(ar, ref state.RemotePoint);
                    if (receiveLen > 0)
                    {
                        byte[] receivedData = new byte[receiveLen];
                        Array.Copy(state.Buffer, 0, receivedData, 0, receiveLen);
                        state.Buffer = receivedData;
                        state.ReceivedTime = DateTime.Now;
                        ReceivedDataEvent?.Invoke(state);
                    }
                }
            }
            catch (Exception error)
            {
                ErrorEvent?.Invoke(error.Message, state?.RemotePoint);
            }
            finally
            {
                if (state != null) BeginReceive();
            }
        }
        


    }
}
