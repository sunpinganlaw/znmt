using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NHTool.Common; 
namespace NHTool.form
{
    public partial class OpcForm : Form
    {
        private OPCTool oPCTool = null;
        public OpcForm()
        {
            InitializeComponent();
        }

        private void opcForm_Load(object sender, EventArgs e)
        {
            startOpc.Enabled = false;
            stopOpc.Enabled = false;

            if (oPCTool == null || (oPCTool != null && oPCTool.isStop))
            {
                oPCTool = new OPCTool();
                oPCTool.startOpcServer();
            }

            startOpc.Enabled = true;
            stopOpc.Enabled = true;
        }

        private void startOpc_Click(object sender, EventArgs e)
        {
            startOpc.Enabled = false;
            stopOpc.Enabled = false;

            if (oPCTool == null || (oPCTool != null && oPCTool.isStop))
            {
                oPCTool = new OPCTool();
                oPCTool.startOpcServer();
            }
            else
            {
                MessageBox.Show("OPC服务已经启动！");
            }

            startOpc.Enabled = true;
            stopOpc.Enabled = true;
        }

        private void stopOpc_Click(object sender, EventArgs e)
        {
            startOpc.Enabled = false;
            stopOpc.Enabled = false;

            if (oPCTool!=null && !oPCTool.isStop)
            {
                oPCTool.stopOpcServer();
                oPCTool = null;
            }
            else
            {
                MessageBox.Show("OPC服务已经停止！");
            }
            startOpc.Enabled = true;
            stopOpc.Enabled = true;
        }

        private void getRunStatus_Click(object sender, EventArgs e)
        {

        }

        private void getAllPoint_Click(object sender, EventArgs e)
        {

        }

        private void getPointValue_Click(object sender, EventArgs e)
        {

        }

        private void setPointValue_Click(object sender, EventArgs e)
        {

        }

        private void OpcForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            stopOpc.PerformClick();
        }
    }
}
