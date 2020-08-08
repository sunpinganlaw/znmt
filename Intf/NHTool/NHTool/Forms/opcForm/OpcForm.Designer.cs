namespace NHTool.form
{
    partial class OpcForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.startOpc = new System.Windows.Forms.Button();
            this.stopOpc = new System.Windows.Forms.Button();
            this.getRunStatus = new System.Windows.Forms.Button();
            this.richTextBox1 = new System.Windows.Forms.RichTextBox();
            this.getPointValue = new System.Windows.Forms.Button();
            this.pointTagName = new System.Windows.Forms.TextBox();
            this.pointValue = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.pointName = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.setPointValue = new System.Windows.Forms.Button();
            this.getAllPoint = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // startOpc
            // 
            this.startOpc.Location = new System.Drawing.Point(48, 39);
            this.startOpc.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.startOpc.Name = "startOpc";
            this.startOpc.Size = new System.Drawing.Size(110, 18);
            this.startOpc.TabIndex = 0;
            this.startOpc.Text = "启动OPC";
            this.startOpc.UseVisualStyleBackColor = true;
            this.startOpc.Click += new System.EventHandler(this.startOpc_Click);
            // 
            // stopOpc
            // 
            this.stopOpc.Location = new System.Drawing.Point(201, 39);
            this.stopOpc.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.stopOpc.Name = "stopOpc";
            this.stopOpc.Size = new System.Drawing.Size(111, 18);
            this.stopOpc.TabIndex = 1;
            this.stopOpc.Text = "停止OPC";
            this.stopOpc.UseVisualStyleBackColor = true;
            this.stopOpc.Click += new System.EventHandler(this.stopOpc_Click);
            // 
            // getRunStatus
            // 
            this.getRunStatus.Location = new System.Drawing.Point(368, 39);
            this.getRunStatus.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.getRunStatus.Name = "getRunStatus";
            this.getRunStatus.Size = new System.Drawing.Size(116, 18);
            this.getRunStatus.TabIndex = 2;
            this.getRunStatus.Text = "获取运行状态";
            this.getRunStatus.UseVisualStyleBackColor = true;
            this.getRunStatus.Click += new System.EventHandler(this.getRunStatus_Click);
            // 
            // richTextBox1
            // 
            this.richTextBox1.Location = new System.Drawing.Point(48, 77);
            this.richTextBox1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.richTextBox1.Name = "richTextBox1";
            this.richTextBox1.Size = new System.Drawing.Size(634, 158);
            this.richTextBox1.TabIndex = 3;
            this.richTextBox1.Text = "";
            // 
            // getPointValue
            // 
            this.getPointValue.Location = new System.Drawing.Point(380, 252);
            this.getPointValue.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.getPointValue.Name = "getPointValue";
            this.getPointValue.Size = new System.Drawing.Size(74, 18);
            this.getPointValue.TabIndex = 4;
            this.getPointValue.Text = "获取测点值";
            this.getPointValue.UseVisualStyleBackColor = true;
            this.getPointValue.Click += new System.EventHandler(this.getPointValue_Click);
            // 
            // pointTagName
            // 
            this.pointTagName.Location = new System.Drawing.Point(102, 250);
            this.pointTagName.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.pointTagName.Name = "pointTagName";
            this.pointTagName.Size = new System.Drawing.Size(259, 21);
            this.pointTagName.TabIndex = 5;
            // 
            // pointValue
            // 
            this.pointValue.Location = new System.Drawing.Point(512, 252);
            this.pointValue.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.pointValue.Name = "pointValue";
            this.pointValue.Size = new System.Drawing.Size(172, 21);
            this.pointValue.TabIndex = 6;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(467, 255);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(17, 12);
            this.label1.TabIndex = 7;
            this.label1.Text = ">>";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(102, 294);
            this.textBox1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(259, 21);
            this.textBox1.TabIndex = 8;
            // 
            // textBox2
            // 
            this.textBox2.Location = new System.Drawing.Point(460, 294);
            this.textBox2.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(128, 21);
            this.textBox2.TabIndex = 9;
            // 
            // pointName
            // 
            this.pointName.AutoSize = true;
            this.pointName.Location = new System.Drawing.Point(47, 254);
            this.pointName.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.pointName.Name = "pointName";
            this.pointName.Size = new System.Drawing.Size(53, 12);
            this.pointName.TabIndex = 10;
            this.pointName.Text = "测点名称";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(47, 296);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 11;
            this.label2.Text = "测点名称";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(386, 296);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 12);
            this.label3.TabIndex = 12;
            this.label3.Text = "测点待设值";
            // 
            // setPointValue
            // 
            this.setPointValue.Location = new System.Drawing.Point(612, 294);
            this.setPointValue.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.setPointValue.Name = "setPointValue";
            this.setPointValue.Size = new System.Drawing.Size(70, 20);
            this.setPointValue.TabIndex = 13;
            this.setPointValue.Text = "设置";
            this.setPointValue.UseVisualStyleBackColor = true;
            this.setPointValue.Click += new System.EventHandler(this.setPointValue_Click);
            // 
            // getAllPoint
            // 
            this.getAllPoint.Location = new System.Drawing.Point(560, 39);
            this.getAllPoint.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.getAllPoint.Name = "getAllPoint";
            this.getAllPoint.Size = new System.Drawing.Size(122, 18);
            this.getAllPoint.TabIndex = 14;
            this.getAllPoint.Text = "获取所有测点";
            this.getAllPoint.UseVisualStyleBackColor = true;
            this.getAllPoint.Click += new System.EventHandler(this.getAllPoint_Click);
            // 
            // OpcForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(886, 444);
            this.Controls.Add(this.getAllPoint);
            this.Controls.Add(this.setPointValue);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.pointName);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.pointValue);
            this.Controls.Add(this.pointTagName);
            this.Controls.Add(this.getPointValue);
            this.Controls.Add(this.richTextBox1);
            this.Controls.Add(this.getRunStatus);
            this.Controls.Add(this.stopOpc);
            this.Controls.Add(this.startOpc);
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "OpcForm";
            this.Text = "opcForm";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.OpcForm_FormClosing);
            this.Load += new System.EventHandler(this.opcForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button startOpc;
        private System.Windows.Forms.Button stopOpc;
        private System.Windows.Forms.Button getRunStatus;
        private System.Windows.Forms.RichTextBox richTextBox1;
        private System.Windows.Forms.Button getPointValue;
        private System.Windows.Forms.TextBox pointTagName;
        private System.Windows.Forms.TextBox pointValue;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.Label pointName;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button setPointValue;
        private System.Windows.Forms.Button getAllPoint;
    }
}