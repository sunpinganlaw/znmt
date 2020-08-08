using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using NHTool.Business.TRAIN_SCHEDULE;

namespace NHTool.Forms.trainForm
{
    public partial class TrainForm : Form
    {

        //excel中的控制配置
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        public TrainForm()
        {
            InitializeComponent();
        }


        private void trainForm_Load(object sender, EventArgs e)
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            string powerFactory = ctlConfig["PowerFactory"];

            //九江就启动九江的调度，其它电厂以此类推
            if (powerFactory.Equals("JJ"))
            {
                TrainScheduleJJ trainScheduler = new TrainScheduleJJ(ctlConfig);
                trainScheduler.startSchedule();
            }
            
        }



        private void trainForm_FormClosing(object sender, FormClosingEventArgs e)
        {

        }
    }
}
