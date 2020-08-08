namespace NHTool.form.dataSendForm
{
    partial class dataSendForm
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(dataSendForm));
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.inforRichBox = new System.Windows.Forms.RichTextBox();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.cfg_ORA_PSW_Txt = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.cfg_ORA_SOURCE_Txt = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.cfg_ORA_USER_Txt = new System.Windows.Forms.TextBox();
            this.cfg_ORA_PORT_Txt = new System.Windows.Forms.TextBox();
            this.cfg_ORA_IP_Txt = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.bak_path_textBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.cfg_Path_Txt = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.inforRichBox);
            this.groupBox1.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox1.Location = new System.Drawing.Point(427, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(612, 561);
            this.groupBox1.TabIndex = 21;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "系统运行过程信息";
            // 
            // inforRichBox
            // 
            this.inforRichBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.inforRichBox.Location = new System.Drawing.Point(6, 25);
            this.inforRichBox.Name = "inforRichBox";
            this.inforRichBox.Size = new System.Drawing.Size(600, 530);
            this.inforRichBox.TabIndex = 21;
            this.inforRichBox.Text = "";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.cfg_ORA_PSW_Txt);
            this.groupBox3.Controls.Add(this.label8);
            this.groupBox3.Controls.Add(this.cfg_ORA_SOURCE_Txt);
            this.groupBox3.Controls.Add(this.label12);
            this.groupBox3.Controls.Add(this.cfg_ORA_USER_Txt);
            this.groupBox3.Controls.Add(this.cfg_ORA_PORT_Txt);
            this.groupBox3.Controls.Add(this.cfg_ORA_IP_Txt);
            this.groupBox3.Controls.Add(this.label6);
            this.groupBox3.Controls.Add(this.label9);
            this.groupBox3.Controls.Add(this.label10);
            this.groupBox3.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox3.Location = new System.Drawing.Point(12, 25);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(379, 231);
            this.groupBox3.TabIndex = 24;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "数据库配置信息";
            // 
            // cfg_ORA_PSW_Txt
            // 
            this.cfg_ORA_PSW_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_ORA_PSW_Txt.Location = new System.Drawing.Point(171, 181);
            this.cfg_ORA_PSW_Txt.Name = "cfg_ORA_PSW_Txt";
            this.cfg_ORA_PSW_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_ORA_PSW_Txt.TabIndex = 10;
            this.cfg_ORA_PSW_Txt.Text = "50000";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label8.Location = new System.Drawing.Point(19, 184);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(72, 16);
            this.label8.TabIndex = 9;
            this.label8.Text = "ORA_PSW:";
            // 
            // cfg_ORA_SOURCE_Txt
            // 
            this.cfg_ORA_SOURCE_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_ORA_SOURCE_Txt.Location = new System.Drawing.Point(171, 99);
            this.cfg_ORA_SOURCE_Txt.Name = "cfg_ORA_SOURCE_Txt";
            this.cfg_ORA_SOURCE_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_ORA_SOURCE_Txt.TabIndex = 8;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label12.Location = new System.Drawing.Point(16, 102);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(96, 16);
            this.label12.TabIndex = 7;
            this.label12.Text = "ORA_SOURCE:";
            // 
            // cfg_ORA_USER_Txt
            // 
            this.cfg_ORA_USER_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_ORA_USER_Txt.Location = new System.Drawing.Point(171, 136);
            this.cfg_ORA_USER_Txt.Name = "cfg_ORA_USER_Txt";
            this.cfg_ORA_USER_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_ORA_USER_Txt.TabIndex = 3;
            this.cfg_ORA_USER_Txt.Text = "50000";
            // 
            // cfg_ORA_PORT_Txt
            // 
            this.cfg_ORA_PORT_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_ORA_PORT_Txt.Location = new System.Drawing.Point(171, 62);
            this.cfg_ORA_PORT_Txt.Name = "cfg_ORA_PORT_Txt";
            this.cfg_ORA_PORT_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_ORA_PORT_Txt.TabIndex = 3;
            // 
            // cfg_ORA_IP_Txt
            // 
            this.cfg_ORA_IP_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_ORA_IP_Txt.Location = new System.Drawing.Point(171, 25);
            this.cfg_ORA_IP_Txt.Name = "cfg_ORA_IP_Txt";
            this.cfg_ORA_IP_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_ORA_IP_Txt.TabIndex = 3;
            this.cfg_ORA_IP_Txt.Text = "1005";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label6.Location = new System.Drawing.Point(19, 139);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(80, 16);
            this.label6.TabIndex = 2;
            this.label6.Text = "ORA_USER:";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("宋体", 12F);
            this.label9.Location = new System.Drawing.Point(16, 65);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(80, 16);
            this.label9.TabIndex = 1;
            this.label9.Text = "ORA_PORT:";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label10.Location = new System.Drawing.Point(24, 28);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(64, 16);
            this.label10.TabIndex = 0;
            this.label10.Text = "ORA_IP:";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.bak_path_textBox);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Controls.Add(this.cfg_Path_Txt);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox2.Location = new System.Drawing.Point(12, 286);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(379, 124);
            this.groupBox2.TabIndex = 27;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "配置信息";
            // 
            // bak_path_textBox
            // 
            this.bak_path_textBox.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.bak_path_textBox.Location = new System.Drawing.Point(171, 71);
            this.bak_path_textBox.Name = "bak_path_textBox";
            this.bak_path_textBox.Size = new System.Drawing.Size(139, 26);
            this.bak_path_textBox.TabIndex = 5;
            this.bak_path_textBox.Text = "1005";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(24, 74);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(80, 16);
            this.label1.TabIndex = 4;
            this.label1.Text = "备份地址:";
            // 
            // cfg_Path_Txt
            // 
            this.cfg_Path_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_Path_Txt.Location = new System.Drawing.Point(171, 25);
            this.cfg_Path_Txt.Name = "cfg_Path_Txt";
            this.cfg_Path_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_Path_Txt.TabIndex = 3;
            this.cfg_Path_Txt.Text = "1005";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.Location = new System.Drawing.Point(24, 28);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(80, 16);
            this.label2.TabIndex = 0;
            this.label2.Text = "文件地址:";
            // 
            // dataSendForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1051, 578);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "dataSendForm";
            this.Text = "解析海关报文数据";
            this.Load += new System.EventHandler(this.dataSendForm_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RichTextBox inforRichBox;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.TextBox cfg_ORA_PSW_Txt;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox cfg_ORA_SOURCE_Txt;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox cfg_ORA_USER_Txt;
        private System.Windows.Forms.TextBox cfg_ORA_PORT_Txt;
        private System.Windows.Forms.TextBox cfg_ORA_IP_Txt;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TextBox cfg_Path_Txt;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox bak_path_textBox;
        private System.Windows.Forms.Label label1;
    }
}