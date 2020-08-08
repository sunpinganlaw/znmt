using System;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using Newtonsoft.Json.Linq;

namespace NHTool.Forms.SelectMineMachine
{
          

    public partial class SoftKeyBroad : Form
    {
        public delegate void keyBroadDataSendHander(JObject  clickData);
        public event keyBroadDataSendHander keyBroadClickEvent;
        private string inputControlId;
        public SoftKeyBroad()
        {
            InitializeComponent();
        }


        public SoftKeyBroad(Form ParentForm,System.Drawing.Point curP,string controlId)
        {
            this.Owner = ParentForm;
            this.StartPosition = FormStartPosition.Manual;
            this.Location = new System.Drawing.Point(curP.X-80, curP.Y+20);
            inputControlId = controlId;
            this.ControlBox = false;
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType","normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data","1");
            keyBroadClickEvent(thisClick);
        }

        private void button12_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "2");
            keyBroadClickEvent(thisClick);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "3");
            keyBroadClickEvent(thisClick);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "4");
            keyBroadClickEvent(thisClick);
        }

        private void button5_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "5");
            keyBroadClickEvent(thisClick);
        }

        private void button6_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "6");
            keyBroadClickEvent(thisClick);
        }

        private void button7_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "7");
            keyBroadClickEvent(thisClick);
        }

        private void button8_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "8");
            keyBroadClickEvent(thisClick);
        }

        private void button9_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "9");
            keyBroadClickEvent(thisClick);
        }

        private void button11_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "normalData");
            thisClick.Add("ControlId", inputControlId);
            thisClick.Add("data", "0");
            keyBroadClickEvent(thisClick);
        }

        private void button10_Click(object sender, EventArgs e)
        {
            JObject thisClick = new JObject();
            thisClick.Add("eventType", "clearAll");
            thisClick.Add("ControlId", inputControlId);
            keyBroadClickEvent(thisClick);
        }
    }
}
