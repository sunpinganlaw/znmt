using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using NHTool.Common;
using Newtonsoft.Json.Linq;
using NHTool.Business;
using System.Threading;

namespace NHTool.Forms.SelectMineMachine
{

    public partial class SelectMineMachineForm : Form
    {
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        public Dictionary<string, string> dictionaryUseInForm = null;
        public SelectMineMachineForm()
        {
            InitializeComponent();
        }

        private string selectForecastId = "";
        HttpDbTool httpDbTool = null;
        private JArray forecastList = null;
        private BusinessBase businessBase = null;
        private DataBaseTool myDataBase = new DataBaseTool();
        string databaseUsed = "0";
        private void formLoad(object sender, EventArgs e)
        {
            forecastList = new JArray();
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            string flowClassPackage = Commons.getDcValue(ctlConfig, "flowClassPackage");
            string flowClassMame = Commons.getDcValue(ctlConfig, "flowClassMame");
            databaseUsed = Commons.getDcValue(ctlConfig, "DataBaseUsed");
            businessBase = BusinessBase.CreateInstance<BusinessBase>("NHTool", flowClassPackage, flowClassMame);
            dictionaryUseInForm = businessBase.dictionaryUseInForm;
            businessBase.mainProcess();
            businessBase.showForm = this;
            if (httpDbTool == null)
            {
                httpDbTool = new HttpDbTool();
            }

            if (databaseUsed.Equals("1"))//1选择采样数据库的方式来查询数据
            {
                myDataBase.qryCarForecastInfoList(ref forecastList);
            }
            else if (databaseUsed.Equals("0"))//0选择采样http的方式远程调用查询数据库
            {
                qryForecastList();
            }

            Font gridViewFont = new Font("GB2312", 14);
            mineInfoDataGridView.Font = gridViewFont;
            loadForecastList();
            Commons.putDcValue(ref dictionaryUseInForm, "isFoundCard", "0");
            new Thread(new ThreadStart(delegate
            {
                //主循环开始
                while (true)
                {
                    try
                    {
                        if (this.IsHandleCreated)
                        {
                            this.carID.Invoke(new Action(() =>
                            {
                                if (Commons.getDcValue(dictionaryUseInForm, "IsFlowFinish").Equals("1"))
                                {
                                    this.carID.Text = "";
                                }
                                else
                                {
                                    this.carID.Text = Commons.getDcValue(dictionaryUseInForm, "carId");
                                }
                                 
                            }));

                            this.flowId.Invoke(new Action(() =>
                            {
                                if (Commons.getDcValue(dictionaryUseInForm, "IsFlowFinish").Equals("1"))
                                {
                                    this.flowId.Text = "";
                                }
                                else
                                {
                                    this.flowId.Text = Commons.getDcValue(dictionaryUseInForm, "flowId");
                                }
                            }));

                            this.buttonFrontUp.Invoke(new Action(() =>
                            {
                                if (Commons.getDcValue(dictionaryUseInForm, "isFoundCard").Equals("0"))
                                {
                                    this.buttonFrontUp.Enabled = false;
                                }
                                else if (Commons.getDcValue(dictionaryUseInForm, "isFoundCard").Equals("1"))
                                {
                                    this.buttonFrontUp.Enabled = true;
                                }
                            }));
                            this.ticketNo.Invoke(new Action(() =>
                            {
                                if (Commons.getDcValue(dictionaryUseInForm, "IsFlowFinish").Equals("1"))
                                {
                                    this.ticketNo.Text = "";
                                }
  
                            }));
                            this.ticketQty.Invoke(new Action(() =>
                            {
                                if (Commons.getDcValue(dictionaryUseInForm, "IsFlowFinish").Equals("1"))
                                {
                                    this.ticketQty.Text = "";
                                }
                    
                            }));
                            this.trainNo.Invoke(new Action(() =>
                            {
                                if (Commons.getDcValue(dictionaryUseInForm, "IsFlowFinish").Equals("1"))
                                {
                                    this.trainNo.Text = "";
                                }
                               
                            }));
                        }
                        Thread.Sleep(500);
                    }
                    catch (Exception e2)
                    {
                        MessageBox.Show(e2.Message);
                    }
                }
            })).Start();

        }


        private void qryForecastList()
        {
            JObject dataJson = new JObject();
            JObject ret = httpDbTool.invokeQry("car.qryForecastInfo", dataJson);
            String resCode = (String)ret[Commons.RES_CODE];
      
            if ("0".Equals(resCode))
            {
                if (ret["rows"] != null)
                {
                    forecastList = (JArray)ret["rows"];
                }
            }
            else
            {
                //MessageBox.Show("查询失败来煤预报信息失败");
            }
        }

        private void loadForecastList()
        {
            mineInfoDataGridView.Rows.Clear();
            if (forecastList != null)
            {
                String[] firstRow = new String[5];
                for (int i = 0; i < 5; i++)
                {
                    firstRow[i] = " ";
                }
                mineInfoDataGridView.Rows.Add(firstRow);
                for (int i = 0; i < forecastList.Count(); i++)
                {
                    JObject perRowObj = (JObject)forecastList[i];
                    String[] perRow = new String[5];
                    perRow[0] = Commons.getJsonValue(perRowObj, "coal_name");
                    perRow[1] = Commons.getJsonValue(perRowObj, "mine_name");
                    perRow[2] = Commons.getJsonValue(perRowObj, "ven_name");
                    perRow[3] = Commons.getJsonValue(perRowObj, "carrier_name");
                    perRow[4] = Commons.getJsonValue(perRowObj, "forecast_id");
                    mineInfoDataGridView.Rows.Add(perRow);
                }
            }
        }

        private void selectOneRow(object sender, DataGridViewCellEventArgs e)
        {
            if (mineInfoDataGridView.SelectedCells[4].Value != null)
            {
                selectForecastId = mineInfoDataGridView.SelectedCells[4].Value.ToString();
            }
            else
            {
                selectForecastId = null;
            }
        }


        //提交
        private void buttonFrontUp_Click(object sender, EventArgs e)
        {
            //TODO:检查数据不为空
            if (mineInfoDataGridView.CurrentCell == null)
            {
                MessageBox.Show("请选择一条煤票信息！");
                return;
            }
            JObject dataJson = new JObject();
            dataJson.Add("cardId", Convert.ToString(Commons.getDcValue(dictionaryUseInForm, "cardId")));
            dataJson.Add("flowId", Convert.ToString(Commons.getDcValue(dictionaryUseInForm, "currentFlowId")));
            dataJson.Add("carId", Convert.ToString(Commons.getDcValue(dictionaryUseInForm, "carId")));
            JObject perRowObj = null;
            if (forecastList != null)
            {
                for (int i = 0; i < forecastList.Count(); i++)
                {
                    perRowObj = (JObject)forecastList[i];
                    if(selectForecastId != null && selectForecastId.Equals(Commons.getJsonValue(perRowObj, "forecast_id"))){
                        break;
                    }                                     
                }
            }

            if (perRowObj != null)
            {
                dataJson.Add("mineNo", Convert.ToString(perRowObj["mine_no"]));
                dataJson.Add("coalNo", Convert.ToString(perRowObj["coal_no"]));
                dataJson.Add("venNo", Convert.ToString(perRowObj["ven_no"]));
                dataJson.Add("tickQty", Convert.ToString(this.ticketQty.Text));
                dataJson.Add("trainNo", Convert.ToString(this.trainNo.Text));
                dataJson.Add("orgNo", Convert.ToString(perRowObj["carrier_no"]));
                dataJson.Add("tickNo", Convert.ToString(this.ticketNo.Text));
                dataJson.Add("doActionTag", Convert.ToString("ADD"));

                if (databaseUsed.Equals("1"))//1选择采样数据库的方式来查询数据
                {
                    if (myDataBase.saveCarRecord(Convert.ToString(dataJson)))
                    {
                        businessBase.isStopWaiting = true;
                        mineInfoDataGridView.CurrentCell = null;
                        selectForecastId = null;
                        string selectMineName = Commons.getJsonValue(perRowObj, "mine_name");
                        MessageBox.Show(selectMineName + "的信息录入成功，请入厂！");
                    }
                    else
                    {
                        MessageBox.Show("保存数据失败,请核对信息！");
                    }
                }
                else if (databaseUsed.Equals("0"))//0选择采样http的方式远程调用访问数据库
                {
                    JObject retJson = httpDbTool.invokeProc("pk_register.p_car_add_transRec", dataJson);
                    //调用校验存储过程成功
                    if ("0".Equals(Commons.getJsonValue(retJson, Commons.RES_CODE)) && "1000".Equals(Commons.getJsonValue(retJson, "logicRetCode")))
                    {
                        //流程
                        businessBase.isStopWaiting = true;
                        //TODO:清空页面填充数据
                        mineInfoDataGridView.CurrentCell = null;
                        selectForecastId = null;
                        string selectMineName = Commons.getJsonValue(perRowObj, "mine_name");
                        MessageBox.Show(selectMineName + "的信息录入成功，请入厂！");
                    }
                    else
                    {
                        MessageBox.Show("保存数据失败,请核对信息！");
                    }
                }
            }
        }

        void TextTickQty_GotFocus(object sender, EventArgs e)
        {
            System.Drawing.Point curP = System.Windows.Forms.Cursor.Position;
            SoftKeyBroad diyBroad = new SoftKeyBroad(this, curP, "ticketQty");
            diyBroad.keyBroadClickEvent += softKeyBroadClickProcess;
            diyBroad.Show();
        }

        void TextTickNo_GotFocus(object sender, EventArgs e)
        {
            System.Drawing.Point curP = System.Windows.Forms.Cursor.Position;
            SoftKeyBroad diyBroad = new SoftKeyBroad(this, curP, "ticketNo");
            diyBroad.keyBroadClickEvent += softKeyBroadClickProcess;
            diyBroad.Show();
        }

        void TextTrainNo_GotFocus(object sender, EventArgs e)
        {
            System.Drawing.Point curP = System.Windows.Forms.Cursor.Position;
            SoftKeyBroad diyBroad = new SoftKeyBroad(this, curP, "trainNo");
            diyBroad.keyBroadClickEvent += softKeyBroadClickProcess;
            //diyBroad.StartPosition = FormStartPosition.CenterParent;
            diyBroad.Show();
        }

        public void softKeyBroadClickProcess(JObject receiveData)
        {
            string eventType = Convert.ToString(receiveData["eventType"]);
            string proControl = Convert.ToString(receiveData["ControlId"]);
            if(eventType.Equals("normalData")){
                string dataStr = Convert.ToString(receiveData["data"]);
                TextBox textbox = (TextBox)this.GetType().GetField(proControl, System.Reflection.BindingFlags.NonPublic | System.Reflection.BindingFlags.Instance | System.Reflection.BindingFlags.IgnoreCase).GetValue(this);
                textbox.Text += dataStr;
            }
            else if(eventType.Equals("clearAll"))
            {
                TextBox textbox = (TextBox)this.GetType().GetField(proControl, System.Reflection.BindingFlags.NonPublic | System.Reflection.BindingFlags.Instance | System.Reflection.BindingFlags.IgnoreCase).GetValue(this);
                textbox.Text = "";
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.label13.Text = "ئاپتوموبىل نۇمۇرى :";
            this.label14.Text = "ئىشىك نومۇرى:";
            this.label2.Text = "تىزىم تالونى نومۇرى:";
            this.label1.Text = "ئېغىرلىق تونناژى:";
            this.label3.Text = "كوزۇپ؛ ۋاگون:";
            this.groupBox1.Text = "يېزىش ئۇچۇرى";
            this.groupBox6.Text = "رېئال ۋاقىتلىق ئۇچۇر";
            this.buttonFrontUp.Text = "جەزملەشتۈرۈش ";

            this.Column1.HeaderText = "تەمىنلىگۈچى";
            this.Column2.HeaderText = "ئاپتارۋان، ھارۋا كارۋىنى";
            this.Column3.HeaderText = "كۆمۈر كانى";
            this.Column4.HeaderText = "تۈر نامى";
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.label13.Text = "车牌号：";
            this.label14.Text = "通道号：";
            this.label2.Text = "票单号:";
            this.label1.Text = "票重:";
            this.label3.Text = "火车车厢:";
            this.groupBox1.Text = "信息录入";
            this.groupBox6.Text = "实时信息";
            this.buttonFrontUp.Text = "确认信息";

            this.Column1.HeaderText = "供应商名";
            this.Column2.HeaderText = "运输单位";
            this.Column3.HeaderText = "煤矿名";
            this.Column4.HeaderText = "煤种";
        }
        //mineInfoDataGridView.AllowUserToResizeColumns = false;
        //mineInfoDataGridView.AllowUserToResizeRows = false;

        //设置DataGridView的属性SelectionMode为FullRowSelect 
        //得到选中行的索引
        //int intRow = mineInfoDataGridView.SelectedCells[0].RowIndex;

        //得到列的索引
        //int intColumn = mineInfoDataGridView.SelectedCells[0].ColumnIndex;

        //得到选中行某列的值
        //string str = mineInfoDataGridView.CurrentRow.Cells[2].Value.ToString();
        //MessageBox.Show(str);
  
    }
}
