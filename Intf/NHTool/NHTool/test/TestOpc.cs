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

namespace NHTool
{
    class TestOpc
    {
        public static void testGetTime()
        {
            DateTime dtBegin = System.DateTime.Now;
            DateTime dtEnd = System.DateTime.Now;
            TimeSpan ts = dtBegin.Subtract(dtEnd);
            Console.WriteLine("example1 time {0}", ts.TotalMilliseconds);
        }

        //测试批量读 7435,7293毫秒，1个0.75毫秒
        public static void testBatchRead()
        {
            DateTime dtBegin = System.DateTime.Now;

            for (long i = 0; i < 10000 * 1000; i++)
            {
                /*
                //OPCTool.setValue("testBoolean", "0");
                OPCTool.getRetValue(OPCTool.getValue("testBoolean"));
                //OPCTool.setValue("testBoolean", "1");
                OPCTool.getRetValue(OPCTool.getValue("testBoolean"));

                //OPCTool.setValue("testDouble", "1111.345");
                OPCTool.getRetValue(OPCTool.getValue("testDouble"));

                //OPCTool.setValue("testDword", "111111111");
                OPCTool.getRetValue(OPCTool.getValue("testDword"));

                //OPCTool.setValue("testFloat", "333.3333");
                OPCTool.getRetValue(OPCTool.getValue("testFloat"));

                //OPCTool.setValue("testLong", "111111111");
                OPCTool.getRetValue(OPCTool.getValue("testLong"));

                //OPCTool.setValue("testShort", "1234");
                OPCTool.getRetValue(OPCTool.getValue("testShort"));

                //OPCTool.setValue("testString", "qcd");
                OPCTool.getRetValue(OPCTool.getValue("testString"));
                if (i % 10000 == 0)
                {
                    Console.WriteLine("10000个");
                }
                */
            }
            DateTime dtEnd = System.DateTime.Now;
            TimeSpan ts = dtEnd.Subtract(dtBegin);
            Console.WriteLine("testBatchRead time {0}", ts.TotalMilliseconds);
        }

        //测试批量写 8033毫秒=800个，一个10毫秒
        public static void testBatchWrite()
        {

            DateTime dtBegin = System.DateTime.Now;

            for (long i = 0; i < 10000 * 100; i++)
            {
                /*
                OPCTool.setValue("testBoolean", "0");
                //OPCTool.getRetValue(OPCTool.getValue("testBoolean"));
                OPCTool.setValue("testBoolean", "1");
                //OPCTool.getRetValue(OPCTool.getValue("testBoolean"));

                OPCTool.setValue("testDouble", "1111.345");
                //OPCTool.getRetValue(OPCTool.getValue("testDouble"));

                OPCTool.setValue("testDword", "111111111");
                //OPCTool.getRetValue(OPCTool.getValue("testDword"));

                OPCTool.setValue("testFloat", "333.3333");
                //OPCTool.getRetValue(OPCTool.getValue("testFloat"));

                OPCTool.setValue("testLong", "111111111");
                //OPCTool.getRetValue(OPCTool.getValue("testLong"));

                OPCTool.setValue("testShort", "1234");
                //OPCTool.getRetValue(OPCTool.getValue("testShort"));

                OPCTool.setValue("testString", "qcd");
                //OPCTool.getRetValue(OPCTool.getValue("testString"));

                if (i % 100 == 0)
                {
                    //Console.WriteLine("100个");
                }
                */
            }
            DateTime dtEnd = System.DateTime.Now;
            TimeSpan ts = dtEnd.Subtract(dtBegin);
            Console.WriteLine("example1 time {0}", ts.TotalMilliseconds);
        }

        //测试读写
        public static void testNew1(){
            //OPCTool opcTool = new OPCTool();
            //HttpTool.testHttpSendUseTime();
            //TestJson.testGenJson();
            //TestJson.testParseJson();
       
            /*
            OPCTool.startOpcServer();
            for (;;)
            {
                Console.WriteLine("testBoolean=" + OPCTool.getRetValue(OPCTool.getValue("testBoolean")));
                Console.WriteLine("testDouble=" + OPCTool.getRetValue(OPCTool.getValue("testDouble")));
                Console.WriteLine("testDword=" + OPCTool.getRetValue(OPCTool.getValue("testDword")));
                Console.WriteLine("testFloat=" + OPCTool.getRetValue(OPCTool.getValue("testFloat")));
                Console.WriteLine("testLong=" + OPCTool.getRetValue(OPCTool.getValue("testLong")));
                Console.WriteLine("testShort=" + OPCTool.getRetValue(OPCTool.getValue("testShort")));
                Console.WriteLine("testString=" + OPCTool.getRetValue(OPCTool.getValue("testString")));
                Thread.Sleep(1000);
            }
            */
            //opcTool.testHttpServer();

            /*
            OPCTool.startOpcServer();
            OPCTool.setValue("testBoolean", "0");
            Console.WriteLine("testBoolean=" + OPCTool.getRetValue(OPCTool.getValue("testBoolean")));
            OPCTool.setValue("testBoolean", "1");
            Console.WriteLine("testBoolean=" + OPCTool.getRetValue(OPCTool.getValue("testBoolean")));

            OPCTool.setValue("testDouble", "1111.345");
            Console.WriteLine("testDouble=" + OPCTool.getRetValue(OPCTool.getValue("testDouble")));

            OPCTool.setValue("testDword", "111111111");
            Console.WriteLine("testDword=" + OPCTool.getRetValue(OPCTool.getValue("testDword")));

            OPCTool.setValue("testFloat", "333.3333");
            Console.WriteLine("testFloat=" + OPCTool.getRetValue(OPCTool.getValue("testFloat")));

            OPCTool.setValue("testLong", "111111111");
            Console.WriteLine("testLong=" + OPCTool.getRetValue(OPCTool.getValue("testLong")));

            OPCTool.setValue("testShort", "1234");
            Console.WriteLine("testShort=" + OPCTool.getRetValue(OPCTool.getValue("testShort")));

            OPCTool.setValue("testString", "qcd");
            Console.WriteLine("testString=" + OPCTool.getRetValue(OPCTool.getValue("testString")));
            */
        }




        public void test2()
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
                int refreshRate = 1000;
                if (HRESULTS.Succeeded(result))//连接成功了，既xyz为正数
                {
                    HaiGrang.Package.OpcNetApiChs.DaNet.RefreshEventHandler refreshEventHandler = new HaiGrang.Package.OpcNetApiChs.DaNet.RefreshEventHandler(this.DataChangeHandler);
                    RefreshGroup refreshGroup = new RefreshGroup(tmpServer, refreshRate, refreshEventHandler);
                    refreshGroup.Add("Channel1.Device1.Group1.long");
                    refreshGroup.Add("Channel1.Device1.Group1.test");
                }
            }
        }

        private void DataChangeHandler(object sender, HaiGrang.Package.OpcNetApiChs.DaNet.RefreshEventArguments e)//数据变化操作方法（object型对象，更新事件属性）
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

        public void test()
        {
            //R/W RO ,读写情况
            Host host = new Host("localhost");//创建本机对象
            //Host host = new Host("192.168.0.120");//创建本机对象
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
                if (HRESULTS.Succeeded(result))//连接成功了，既xyz为正数
                {
                    //Console.WriteLine("" + status.eServerState.ToString());
                    //status.eServerState.ToString();//服务器状态对象的eServerState属性值转化成字符串赋值给状态条的Text属性
                    SyncIOGroup syncIOGroup = new SyncIOGroup(tmpServer);
                    syncIOGroup.Add("Channel1.Device1.Group1.long");
                    ItemDef tmpItem = syncIOGroup.Item("Channel1.Device1.Group1.long");
                    OPCDATASOURCE source = OPCDATASOURCE.OPC_DS_CACHE;// OPC_DS_DEVICE;
                    OPCItemState state;
                    DateTime dtBegin = System.DateTime.Now;
                    for (long i = 0; i < 1 * 10000; i++)//550毫秒
                    {
                        result = syncIOGroup.Read(source, tmpItem, out state);
                        if (!HRESULTS.Failed(result))
                        {
                            if (state != null)//如果状态不为空
                            {
                                if (HRESULTS.Succeeded(result))//如果结果成功,既xyz等于1，也就是说source和ItemData可以读出来
                                {
                                    if (state.DataValue != null)
                                    {
                                        state.DataValue.ToString();
                                        syncIOGroup.GetQualityString(state.Quality);
                                        DateTime dt = DateTime.FromFileTime(state.TimeStamp);
                                    }
                                    //状态的数据值传给textBox7的Text属性

                                    //Console.WriteLine(state.DataValue.ToString() + "-" + syncIOGroup.GetQualityString(state.Quality) + dt.ToString());
                                }
                                else//否则（如果结果失败）
                                {
                                    //Console.WriteLine("ERROR:" + syncIOGroup.GetErrorString(state.Error));
                                }
                            }
                            else//否则
                            {
                                // "没有数据！";//toolStripStatusLabel1的Text属性显示为 没有数据！
                            }
                        }
                        /*
                        if ((tmpItem.OpcIRslt != null) && (tmpItem.OpcIRslt.DataValue != null))
                        {
                            String value = tmpItem.OpcIRslt.DataValue.ToString();
                            Console.WriteLine(value);
                        }
                        else
                        {
                            Console.WriteLine("value is null!");
                        }
                        */
                    }
                    DateTime dtEnd = System.DateTime.Now;
                    TimeSpan ts = dtEnd.Subtract(dtBegin);
                    Console.WriteLine("example1 time {0}", ts.TotalMilliseconds);
                }
            }
        }
    }
}
