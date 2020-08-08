using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Net;
using System.Windows.Forms;
using System.IO;
using NHTool.delegateDeclare;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;


namespace NHTool.Common
{
    public class HttpTool
    {
        private HttpListener httpListener;
        public Boolean isStop = false;
        private HttpWebRequest request = null;
        private Stream requestStream = null;
        private Encoding encoding = Encoding.GetEncoding("utf-8");
        private HttpWebResponse response = null;
        private Stream responseStream = null;
        private StreamReader responseStreamReader = null;

        //启动http服务
        public void initHttpServer(HttpProcessHandler handler, String httpAddr)
        {
            if (httpAddr == null || httpAddr.Equals("")) 
            {
                return;
            }

            this.httpListener = new HttpListener();
            this.httpListener.AuthenticationSchemes = AuthenticationSchemes.Anonymous; 
            this.httpListener.Prefixes.Add(httpAddr);

            this.httpListener.Start();

            new Thread(new ThreadStart(delegate
            {
                try
                {
                    processData(httpListener, handler);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                    //httpListener.Stop();
                }
            })).Start();
            Console.WriteLine("httpListener start,bind " + httpAddr + "!");
            LogTool.WriteLogInfo(typeof(HttpTool), "httpListener start,bind " + httpAddr + "!");
            
        }

        private void processData(HttpListener httpListener, HttpProcessHandler handler)
        {
            while (!this.isStop)
            {
                HttpListenerContext context = null;
                HttpListenerRequest hRequest = null;
                HttpListenerResponse hResponse = null;
                Stream inputStream = null;
                StreamReader inputStreamReader = null;
                Stream output = null;
                try
                {
                    context = this.httpListener.GetContext();
                    /*
                    if (context.Request.RemoteEndPoint != null)
                    {
                        string ipAddress; ipAddress = context.Request.RemoteEndPoint.Address.ToString();
                        Console.WriteLine(ipAddress);
                    }*/

                    hRequest = context.Request;
                    hResponse = context.Response;
                    if (hRequest.HttpMethod == "POST")
                    {       
                        inputStream = hRequest.InputStream;
                        inputStreamReader = new StreamReader(inputStream, Encoding.GetEncoding("utf-8"));
                        string inString = inputStreamReader.ReadToEnd();
                        //Console.WriteLine("GET COMMAND JSON:" + inString);

                        try
                        {
                            byte[] inRes = null;
                            JObject ret = handler(inString);
                            if (ret != null)
                            {
                                byte[] outRes = Encoding.UTF8.GetBytes(ret.ToString());
                                output = hResponse.OutputStream;
                                output.Write(outRes, 0, outRes.Length);
                            }

                        }
                        catch (Exception e)
                        {
                            //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
                        }
                    }
                    else
                    {
                        Console.WriteLine("in GET");
                    }
                }
                catch (Exception e)
                {
                    //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
                }
                finally
                {
                    if (inputStreamReader != null)
                    {
                        try
                        {
                            inputStreamReader.Close();
                        }
                        catch (Exception e)
                        {
                            //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
                        }
                    }
                    if (inputStream != null)
                    {
                        try
                        {
                            inputStream.Close();
                        }
                        catch (Exception e)
                        {
                            //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
                        }
                    }
                    if (output != null)
                    {
                        try
                        {
                            output.Close();
                        }
                        catch (Exception e)
                        {
                            //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
                        }
                    }
                    if (hResponse != null)
                    {
                        try
                        {
                            hResponse.Close();
                        }
                        catch (Exception e)
                        {
                            //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
                        }
                    }
                    output = null;
                    context = null;
                    hRequest = null;
                    hResponse = null;
                }
            }

            httpListener.Close();
            httpListener = null;
            Console.WriteLine("httpListener close!");
        }

        public void stopHttpServer()
        {
            try{
                if (httpListener != null)
                {
                    httpListener.Close();
                    httpListener = null;
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
            }

        }

        //测试发送消耗时间，如果没有解析返回，则1.085毫秒/个，否则为1.788毫秒/个
        public void testHttpSendUseTime()
        {
            DateTime dtBegin = System.DateTime.Now;
            HttpTool HttpTool = new HttpTool();
            for (int i = 0; i < 10000*10; i++)
            {
                //HttpTool.sendHttpMsg("{'ab':'cd'}");
            }
            DateTime dtEnd = System.DateTime.Now;
            TimeSpan ts = dtEnd.Subtract(dtBegin);
            Console.WriteLine("example1 time {0}", ts.TotalMilliseconds);
        }

        public string sendHttpMsg(String postDataStr, String reqUrl)
        {
            string retString = "";
            try
            {
                //Console.WriteLine("send ret str:" + postDataStr); 
                this.request = (HttpWebRequest)WebRequest.Create(reqUrl);
                this.request.ServicePoint.ConnectionLimit = int.MaxValue;
                this.request.Method = "POST";
                this.request.Timeout = 5000;
                this.request.ProtocolVersion = HttpVersion.Version10;
                this.request.ContentType = "application/x-www-form-urlencoded";
                this.request.KeepAlive = false;
                if (this.request != null)
                {
                    byte[] data = encoding.GetBytes(postDataStr);
                    this.request.ContentLength = data.Length;
                    this.requestStream = request.GetRequestStream();
                    this.requestStream.Write(data, 0, data.Length);
                    this.requestStream.Flush();

                    this.response = (HttpWebResponse)request.GetResponse();
                    Stream responseStream = response.GetResponseStream();
                    this.responseStreamReader = new StreamReader(responseStream, Encoding.GetEncoding("utf-8"));
                    retString = this.responseStreamReader.ReadToEnd();
                    Console.WriteLine("get ret str:" + retString);
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.Message); 
            }
            finally
            {
                closeForSendMsg();
            }
            return retString;
        }

        public void closeForSendMsg()
        {
            try
            {
                if (this.responseStreamReader != null)
                {
                    this.responseStreamReader.Close();
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
            }

            try
            {
                if (this.responseStream != null)
                {
                    this.responseStream.Close();
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
            }

            try
            {
                if (this.response != null)
                {
                    this.response.Close();
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
            }
            try
            {
                if (this.requestStream != null)
                {
                    this.requestStream.Close();
                    this.requestStream = null;
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
            }

            try
            {
                if (this.request != null)
                {
                    this.request.Abort();
                }
            }
            catch (Exception e)
            {
                //LogTool.WriteLogInfo(typeof(HttpTool), e.StackTrace); 
            }
        }
    }
}
