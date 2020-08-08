using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using NHTool.Common;
using NHTool.Business;
using NHTool.Device.Modbus;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.IO;
using NHTool.Forms;

/********************************************
* modify by dafeige  20190312****************
1.createSubFormList()创建下拉试图列表并初始化；
2.selectSubFormToShow(object sender, EventArgs e) 选择列表是的触发函数
3.createQuitForm(object sender, EventArgs e) 创建退出界面
********************************************/
namespace NHTool
{
    //https://blog.csdn.net/Bunengshuo_/article/details/76793910
    public partial class mainForm : Form
    {
        public Dictionary<String, Form> thisForms = new Dictionary<String, Form>();
        public Dictionary<String, Form> subFormFormList = new Dictionary<String, Form>();
        NHTool.form.OpcForm opcForm = null;
        //public carInStateMachine CarMachine=null;
        //public StateEventArgs stateDate=null;
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        private string currentSelectFormName = "";
        private string quitPwd = "";
        public mainForm()
        {
            InitializeComponent();
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
        }

        private void testButton_Click(object sender, EventArgs e)
        {
            //测试批量读 7435,7293毫秒，1个0.75毫秒
            //TestOpc.testBatchRead();

            //测试批量写 8033毫秒=800个，一个10毫秒
            //TestOpc.testBatchWrite();

            
        }


        private void mainForm_Load(object sender, EventArgs e)
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            createSubFormList();
        }

        private void createSubFormList()
        {
            LogTool.WriteLog(typeof(mainForm), "配置文件读取到的个数：" + Convert.ToString(ctlConfig.Count));
            string viewFormStr = ctlConfig["initModules"];
            quitPwd = ctlConfig["AppQuitPwd"];//读取退出程序的验证密码
            JArray viewFormJson = (JArray)JsonConvert.DeserializeObject(viewFormStr);
            ToolStripItem[] subFormList = new ToolStripItem[viewFormJson.Count+1];
            int i = 0;
            foreach (var item in viewFormJson)
            {
                JObject singleJson = (JObject)item;
                ToolStripItem subItem = new ToolStripMenuItem();
                subItem.Name = Convert.ToString(singleJson["formClassMame"]);
                subItem.Text = Convert.ToString(singleJson["formName"]);
                Form subFormToShow = BusinessBase.CreateInstance<Form>("NHTool", Convert.ToString(singleJson["formClassPackage"]), Convert.ToString(singleJson["formClassMame"]));
                subFormFormList.Add(Convert.ToString(singleJson["formName"]), subFormToShow);
                subItem.Click += new System.EventHandler(this.selectSubFormToShow);
                subFormList[i] = subItem;
                i++;
            }


            Form subForm = subFormFormList[subFormList[0].Text];
            subForm.ShowIcon = false;//标题栏不显示Icon
            subForm.MdiParent = this;
            subForm.TopLevel = false;
            subForm.Dock = DockStyle.Fill;
            subForm.WindowState = FormWindowState.Maximized;
            subForm.FormBorderStyle = FormBorderStyle.None;
            subForm.Show();
            currentSelectFormName = subFormList[0].Text;



            ToolStripItem quitItem= new ToolStripMenuItem();
            quitItem.Name = "QuitForm";
            quitItem.Text = "退出";
            quitItem.Click += new System.EventHandler(this.createQuitForm);
            subFormList[i] = quitItem;
          
            this.formSelect.DropDownItems.AddRange(subFormList);
        }


        private string readFormListJson(string fileName) 
        {
            string listStr = "";
            var filepath = Application.StartupPath + "\\" + fileName;
            if (File.Exists(filepath))
                listStr = File.ReadAllText(filepath, System.Text.Encoding.Default);
            return listStr;
        }

        private void selectSubFormToShow(object sender, EventArgs e) 
        {
            string theFormName = sender.ToString();
            if (!currentSelectFormName.Equals(theFormName))
            {
                if (currentSelectFormName.Equals(""))
                {
                    if (subFormFormList.ContainsKey(theFormName))
                    {
                        Form subForm = subFormFormList[theFormName];
                        subForm.ShowIcon = false;//标题栏不显示Icon
                        subForm.MdiParent = this;
                        subForm.TopLevel = false;
                        subForm.Dock = DockStyle.Fill;
                        subForm.WindowState = FormWindowState.Maximized;
                        subForm.FormBorderStyle = FormBorderStyle.None;
                        subForm.Show();
                        currentSelectFormName = theFormName;
                    }
                }
                else
                {
                    subFormFormList[currentSelectFormName].Hide();
                    if (subFormFormList.ContainsKey(theFormName))
                    {
                        Form subForm = subFormFormList[theFormName];
                        subForm.ShowIcon = false;//标题栏不显示Icon
                        subForm.MdiParent = this;
                        subForm.TopLevel = false;
                        subForm.Dock = DockStyle.Fill;
                        subForm.WindowState = FormWindowState.Maximized;
                        subForm.FormBorderStyle = FormBorderStyle.None;
                        subForm.Show();
                        currentSelectFormName = theFormName;
                    }
                }

            }
            else
            {
                MessageBox.Show("当前页面已经被选中");
            }
        }

        private void createQuitForm(object sender, EventArgs e) 
        {
            QuitSysForm quitForm = new QuitSysForm();
            quitForm.sendMsgClickEvent += reciveMsgProcess;
            //quitForm.MdiParent = this;
            quitForm.Top = this.Top  + 500;
            quitForm.Left = this.Left + 700;
            subFormFormList.Remove("退出");
            subFormFormList.Add("退出", quitForm);
            quitForm.Show();
        }

        private void reciveMsgProcess(string msgStr)
        {
            if (msgStr.Equals(quitPwd))
            {
                System.Environment.Exit(0); 
            }
        }

        private void exitSubFormService()
        {

        }

        private void exitToolStripMenuItem1_Click(object sender, EventArgs e)
        {

        }

        private void LockToolStripMenuItem_Click(object sender, EventArgs e)
        {
			LogTool.WriteLog(typeof(mainForm), "testLog");
            hideOtherForm("-1");
        }

        private void oPCToolToolStripMenuItem_Click(object sender, EventArgs e)
        {
            String formName = "opcForm";
            hideOtherForm(formName);
            if (thisForms.ContainsKey(formName) && thisForms[formName].IsAccessible)
            {
                thisForms[formName].Show();
            }
            else
            {
                thisForms.Remove(formName);
                opcForm = new NHTool.form.OpcForm(); //为子窗体
                opcForm.ShowIcon = false;//标题栏不显示Icon
                opcForm.MdiParent = this;
                opcForm.TopLevel = false;
                opcForm.WindowState = FormWindowState.Maximized;
                opcForm.FormBorderStyle = FormBorderStyle.None;
                opcForm.Show();
                thisForms.Add(formName, opcForm);
            }
        }

        private void hideOtherForm(String fromName){
            foreach (string key in thisForms.Keys)
            {
                if (key.Equals(fromName))
                {
                    continue;
                }
                else
                {
                    if (thisForms.ContainsKey(key) && thisForms[key] != null)
                    {
                        thisForms[key].Hide();
                    }                   
                }
            }
        }

        private void menuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
        
        }

        private void mainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            
              foreach (string key in thisForms.Keys)
              {

                  thisForms[key].Close();
              }
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            
            LogTool.WriteLogInfo(typeof(Modbus), "sssss");

            Modbus modbusClinet = new Modbus();
            modbusClinet.modbusDeviceEvent += modbusEvent;
            
            modbusClinet.initial("","");
            modbusClinet.start();

           Dictionary<string ,string> result= modbusClinet.setCommand("0", Commons.True,Commons.modbusType.COIL_STATUS);
           if (result.ContainsKey(Commons.RES_CODE))
           {
               LogTool.WriteLog(typeof(Modbus),Commons.RES_MSG);

           }
        }

        private void modbusEvent(Dictionary<string ,string > ret)
        {
            if (ret.ContainsKey(Commons.RES_CODE))
            {

                LogTool.WriteLog(typeof(mainForm), ret[Commons.RES_MSG]);
            } 
        }
    }
}
