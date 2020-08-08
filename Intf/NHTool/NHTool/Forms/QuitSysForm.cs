using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NHTool.Forms
{
    public partial class QuitSysForm : Form
    {
        public delegate void sendMsgEventHandler(string sendMsg);
        public event sendMsgEventHandler sendMsgClickEvent;

        public QuitSysForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            sendMsgClickEvent(PwdText.Text);
        }

        void QuitSysForm_FormClosing(object sender, System.Windows.Forms.FormClosingEventArgs e)
        {
            sendMsgClickEvent("quitConfirmForm");
        }
    }
}
