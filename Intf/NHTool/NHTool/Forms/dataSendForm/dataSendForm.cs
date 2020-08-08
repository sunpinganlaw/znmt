using NetUtilityLib;
using Newtonsoft.Json.Linq;
using NHTool.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace NHTool.form.dataSendForm
{
    public partial class dataSendForm : Form
    {

        private JObject v_json_in_clob;
        private DataBaseTool dbtool;

        private string ip = "";
        private string port = "";
        private string source = "";
        private string user = "";
        private string pwd = "";
        private string srcpath = "";
        private string destpath = "";

        private int showMaxCount = 15;
        private int count = 0;

        /// <summary>
        /// 实时状态点同步数据库线程
        /// </summary>
        private Thread dataSend = null;

        public dataSendForm()
        {

            InitializeComponent();
        }

        private void dataSendForm_Load(object sender, EventArgs e)
        {


            ip = ConfigTool.GetValueFromIni("orcl", "ip");
            port = ConfigTool.GetValueFromIni("orcl", "port");
            source = ConfigTool.GetValueFromIni("orcl", "source");
            user = ConfigTool.GetValueFromIni("orcl", "user");
            pwd = ConfigTool.GetValueFromIni("orcl", "pwd");

            srcpath = ConfigTool.GetValueFromIni("set", "srcpath");
            destpath = ConfigTool.GetValueFromIni("set", "destpath");

            this.cfg_ORA_IP_Txt.Text = ip;
            this.cfg_ORA_PORT_Txt.Text = port;
            this.cfg_ORA_SOURCE_Txt.Text = source;
            this.cfg_ORA_USER_Txt.Text = user;
            //this.cfg_ORA_PSW_Txt.Text = pwd;
            this.cfg_Path_Txt.Text = srcpath;
            this.bak_path_textBox.Text = destpath;

            dbtool = new DataBaseTool(ip, port, source, user, pwd);


            try
            {
                dataSend = new Thread(dataSendMsg);
                dataSend.IsBackground = true;
                System.Threading.Thread.Sleep(50);
                dataSend.Start();
            }
            catch (Exception e1)
            {

                LogTool.WriteLog(typeof(dataSendForm), "启动发送海关数据异常:" + e1.Message);
            }

        }



        /// <summary>
        /// 
        /// </summary>
        private void dataSendMsg()
        {
            showUI("启动发送海关数据线程打开成功");
            while (true)
            {
                try
                {
                    string fileName = "";
                    List<string> fileNameList = new List<string>();
                    DirectoryInfo folder = new DirectoryInfo(srcpath);

                    foreach (FileInfo file in folder.GetFiles("*.txt"))
                    {
                        fileNameList.Add(file.Name);

                    }
                    if (fileNameList.Count > 0)
                    {

                        fileName = fileNameList[0];
                    }
                    else
                    {

                         fileName = "hg.txt";
                    }


                    List<TRANSMST> msgs = ConfigTool.readTransmstTxt(fileName, "", srcpath);
                    showUI("收到有效数据条数：【"+ msgs.Count+"】");
                    JArray jarray = new JArray();
                    int number = 0;
                    string flag = System.DateTime.Now.ToString("yyyyMMddHHmm");
                    for (int i = 0; i < msgs.Count; i++)
                    {
                        number = number + 1;
                       TRANSMST item = msgs[i];
                        JObject temp = new JObject();
                        temp.Add("carId", item.carId);
                        temp.Add("carType", item.carType);
                        temp.Add("tradeType", item.tradeType);
                        temp.Add("shipName", item.shipName);
                        temp.Add("billNo", item.billNo);
                        temp.Add("fixShipNo", item.fixShipNo);
                        temp.Add("areaCode", item.areaCode);
                        temp.Add("batchNo", flag);
                        jarray.Add(temp);
                        if (number == 50)
                        {
                           
                            string resut = dbtool.msg_2_db(jarray.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""));
                            if (!resut.Equals("ok"))
                            {
                                string errMsg = "发送海关数据信息入库异常:" + resut;
                                showUI(errMsg);
                                LogTool.WriteLog(typeof(dataSendForm), errMsg);
                            }
                            else
                            {
                                showUI("发送有效数据条数：【" + number + "】");

                            }
                            jarray.Clear();
                            System.Threading.Thread.Sleep(100);
                            number = 0;
                        }
                        else
                        {

                            if (i == msgs.Count - 1)
                            {
                                number = 0;
                                if (jarray.Count > 0)
                                {

                                    string resut = dbtool.msg_2_db(jarray.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", ""));
                                    if (!resut.Equals("ok"))
                                    {
                                        string errMsg = "发送海关数据信息入库异常:" + resut;
                                        showUI(errMsg);
                                        LogTool.WriteLog(typeof(dataSendForm), errMsg);
                                    }
                                    else
                                    {
                                        showUI("发送有效数据条数：【" + jarray.Count + "】");

                                    }
                                    

                                }

                            }

                        }

                    }


                    moveFiles(srcpath,destpath);



                }
                catch (Exception ex)
                {
                    showUI("启动发送海关数据异常:" + ex.Message);
                    LogTool.WriteLog(typeof(dataSendForm), "启动发送海关数据异常:" + ex.Message);
                    
                }
               
                System.Threading.Thread.Sleep(50000);

            }



        }

        // copy all file(*.png) in folder src to dest
        private  void moveFiles(string srcFolder, string destFolder)
        {
            DirectoryInfo directoryInfo = new DirectoryInfo(srcFolder);
            FileInfo[] files = directoryInfo.GetFiles();

            foreach (FileInfo file in files) // Directory.GetFiles(srcFolder)
            {
                if (file.Extension == ".txt")
                {
                    string Directory = destFolder + "\\"+ "bak" + System.DateTime.Now.ToString("yyyyMMddHHmm");
                    System.IO.Directory.CreateDirectory(Directory);
                    string destfile = Directory;
                    file.MoveTo(Path.Combine(destfile, file.Name));
                }
               
            }
        }



        private void showUI(string msg)
        {
            this.inforRichBox.Invoke(new Action(() =>
            {
                count++;
                if (count > showMaxCount)
                {
                    this.inforRichBox.Clear();
                    count = 0;
                }
                this.inforRichBox.AppendText(System.DateTime.Now.ToString() + "--->>>" + msg + "\r\n");
            }));




        }




        private void button1_Click(object sender, EventArgs e)
        {
            //string file = @"D:\test\" + "IN_YARD20200304213009.xls";
            //ExcelHelper excelHelper = new ExcelHelper(file);
            //DataTable dt = excelHelper.ExcelToDataTable(null, false);
            //this.cfg_Path_Txt.Text = path;

            //moveFiles(path, @"D:\bak");

            //string Directory = destpath + "\\" + "bak" + System.DateTime.Now.ToString("yyyyMMddHHmm");
            //System.IO.Directory.CreateDirectory(Directory);


        }

        private void button1_Click_1(object sender, EventArgs e)
        {

            showUI( dbtool.msg_2_test("1"));
        }

        private void button2_Click(object sender, EventArgs e)
        {
            showUI( dbtool.msg_2_test("2"));
        }
    }
}
