namespace NHTool.Forms.scadaForm
{
    partial class scadaForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(scadaForm));
            this.PointDB_txtBox = new System.Windows.Forms.TabControl();
            this.cfgTab = new System.Windows.Forms.TabPage();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.cfg_HERT_Txt = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
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
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.cfg_inforRichBox = new System.Windows.Forms.RichTextBox();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.groupBox10 = new System.Windows.Forms.GroupBox();
            this.DBPoint_inforRichBox = new System.Windows.Forms.RichTextBox();
            this.groupBox11 = new System.Windows.Forms.GroupBox();
            this.DBPoint_txtBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.groupBox6 = new System.Windows.Forms.GroupBox();
            this.pointDB_inforRichBox = new System.Windows.Forms.RichTextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.pointDB_Time_txt = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.tabPage3 = new System.Windows.Forms.TabPage();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.OrclCmd_inforRichBox = new System.Windows.Forms.RichTextBox();
            this.groupBox7 = new System.Windows.Forms.GroupBox();
            this.OrclCmd_txtBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.tabPage4 = new System.Windows.Forms.TabPage();
            this.groupBox8 = new System.Windows.Forms.GroupBox();
            this.ScadaCmd_inforRichBox = new System.Windows.Forms.RichTextBox();
            this.groupBox9 = new System.Windows.Forms.GroupBox();
            this.ScadaCmd_txtBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.statusStrip2 = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLabel2 = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripStatusLabel4 = new System.Windows.Forms.ToolStripStatusLabel();
            this.PointDB_txtBox.SuspendLayout();
            this.cfgTab.SuspendLayout();
            this.groupBox5.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.groupBox10.SuspendLayout();
            this.groupBox11.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.groupBox6.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.tabPage3.SuspendLayout();
            this.groupBox4.SuspendLayout();
            this.groupBox7.SuspendLayout();
            this.tabPage4.SuspendLayout();
            this.groupBox8.SuspendLayout();
            this.groupBox9.SuspendLayout();
            this.statusStrip2.SuspendLayout();
            this.SuspendLayout();
            // 
            // PointDB_txtBox
            // 
            this.PointDB_txtBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.PointDB_txtBox.Controls.Add(this.cfgTab);
            this.PointDB_txtBox.Controls.Add(this.tabPage1);
            this.PointDB_txtBox.Controls.Add(this.tabPage2);
            this.PointDB_txtBox.Controls.Add(this.tabPage3);
            this.PointDB_txtBox.Controls.Add(this.tabPage4);
            this.PointDB_txtBox.Location = new System.Drawing.Point(1, 1);
            this.PointDB_txtBox.Name = "PointDB_txtBox";
            this.PointDB_txtBox.SelectedIndex = 0;
            this.PointDB_txtBox.Size = new System.Drawing.Size(1071, 562);
            this.PointDB_txtBox.TabIndex = 0;
            this.PointDB_txtBox.SelectedIndexChanged += new System.EventHandler(this.tabControl1_SelectedIndexChanged);
            // 
            // cfgTab
            // 
            this.cfgTab.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.cfgTab.Controls.Add(this.groupBox5);
            this.cfgTab.Controls.Add(this.groupBox3);
            this.cfgTab.Controls.Add(this.groupBox1);
            this.cfgTab.Font = new System.Drawing.Font("宋体", 15F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfgTab.Location = new System.Drawing.Point(4, 22);
            this.cfgTab.Name = "cfgTab";
            this.cfgTab.Padding = new System.Windows.Forms.Padding(3);
            this.cfgTab.Size = new System.Drawing.Size(1063, 536);
            this.cfgTab.TabIndex = 0;
            this.cfgTab.Text = "数据中心配置";
            this.cfgTab.UseVisualStyleBackColor = true;
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.cfg_HERT_Txt);
            this.groupBox5.Controls.Add(this.label7);
            this.groupBox5.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox5.Location = new System.Drawing.Point(28, 277);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(379, 109);
            this.groupBox5.TabIndex = 24;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "系统监视与控制";
            // 
            // cfg_HERT_Txt
            // 
            this.cfg_HERT_Txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cfg_HERT_Txt.Location = new System.Drawing.Point(171, 25);
            this.cfg_HERT_Txt.Name = "cfg_HERT_Txt";
            this.cfg_HERT_Txt.Size = new System.Drawing.Size(139, 26);
            this.cfg_HERT_Txt.TabIndex = 3;
            this.cfg_HERT_Txt.Text = "1005";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label7.Location = new System.Drawing.Point(19, 28);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(144, 16);
            this.label7.TabIndex = 0;
            this.label7.Text = "数据监控中心心跳:";
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
            this.groupBox3.Location = new System.Drawing.Point(28, 16);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(379, 231);
            this.groupBox3.TabIndex = 23;
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
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.cfg_inforRichBox);
            this.groupBox1.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox1.Location = new System.Drawing.Point(441, 6);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(612, 494);
            this.groupBox1.TabIndex = 22;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "系统运行过程信息";
            // 
            // cfg_inforRichBox
            // 
            this.cfg_inforRichBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.cfg_inforRichBox.Location = new System.Drawing.Point(6, 25);
            this.cfg_inforRichBox.Name = "cfg_inforRichBox";
            this.cfg_inforRichBox.Size = new System.Drawing.Size(600, 463);
            this.cfg_inforRichBox.TabIndex = 21;
            this.cfg_inforRichBox.Text = "";
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.groupBox10);
            this.tabPage1.Controls.Add(this.groupBox11);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(1063, 536);
            this.tabPage1.TabIndex = 4;
            this.tabPage1.Text = "业务信息下发组态显示";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // groupBox10
            // 
            this.groupBox10.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox10.Controls.Add(this.DBPoint_inforRichBox);
            this.groupBox10.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox10.Location = new System.Drawing.Point(434, 21);
            this.groupBox10.Name = "groupBox10";
            this.groupBox10.Size = new System.Drawing.Size(612, 494);
            this.groupBox10.TabIndex = 28;
            this.groupBox10.TabStop = false;
            this.groupBox10.Text = "系统运行过程信息";
            // 
            // DBPoint_inforRichBox
            // 
            this.DBPoint_inforRichBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.DBPoint_inforRichBox.Location = new System.Drawing.Point(6, 25);
            this.DBPoint_inforRichBox.Name = "DBPoint_inforRichBox";
            this.DBPoint_inforRichBox.Size = new System.Drawing.Size(600, 463);
            this.DBPoint_inforRichBox.TabIndex = 21;
            this.DBPoint_inforRichBox.Text = "";
            // 
            // groupBox11
            // 
            this.groupBox11.Controls.Add(this.DBPoint_txtBox);
            this.groupBox11.Controls.Add(this.label4);
            this.groupBox11.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox11.Location = new System.Drawing.Point(16, 21);
            this.groupBox11.Name = "groupBox11";
            this.groupBox11.Size = new System.Drawing.Size(379, 109);
            this.groupBox11.TabIndex = 27;
            this.groupBox11.TabStop = false;
            this.groupBox11.Text = "系统监视与控制";
            // 
            // DBPoint_txtBox
            // 
            this.DBPoint_txtBox.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.DBPoint_txtBox.Location = new System.Drawing.Point(171, 25);
            this.DBPoint_txtBox.Name = "DBPoint_txtBox";
            this.DBPoint_txtBox.Size = new System.Drawing.Size(139, 26);
            this.DBPoint_txtBox.TabIndex = 3;
            this.DBPoint_txtBox.Text = "1005";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label4.Location = new System.Drawing.Point(19, 28);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(152, 16);
            this.label4.TabIndex = 0;
            this.label4.Text = "数据上传间隔时间：";
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.groupBox6);
            this.tabPage2.Controls.Add(this.groupBox2);
            this.tabPage2.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(1063, 536);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "入库实时信息";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // groupBox6
            // 
            this.groupBox6.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox6.Controls.Add(this.pointDB_inforRichBox);
            this.groupBox6.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox6.Location = new System.Drawing.Point(448, 13);
            this.groupBox6.Name = "groupBox6";
            this.groupBox6.Size = new System.Drawing.Size(612, 494);
            this.groupBox6.TabIndex = 26;
            this.groupBox6.TabStop = false;
            this.groupBox6.Text = "系统运行过程信息";
            // 
            // pointDB_inforRichBox
            // 
            this.pointDB_inforRichBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.pointDB_inforRichBox.Location = new System.Drawing.Point(6, 25);
            this.pointDB_inforRichBox.Name = "pointDB_inforRichBox";
            this.pointDB_inforRichBox.Size = new System.Drawing.Size(600, 463);
            this.pointDB_inforRichBox.TabIndex = 21;
            this.pointDB_inforRichBox.Text = "";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.pointDB_Time_txt);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox2.Location = new System.Drawing.Point(30, 13);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(379, 109);
            this.groupBox2.TabIndex = 25;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "系统监视与控制";
            // 
            // pointDB_Time_txt
            // 
            this.pointDB_Time_txt.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pointDB_Time_txt.Location = new System.Drawing.Point(171, 25);
            this.pointDB_Time_txt.Name = "pointDB_Time_txt";
            this.pointDB_Time_txt.Size = new System.Drawing.Size(139, 26);
            this.pointDB_Time_txt.TabIndex = 3;
            this.pointDB_Time_txt.Text = "1005";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(19, 28);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(152, 16);
            this.label1.TabIndex = 0;
            this.label1.Text = "数据上传间隔时间：";
            // 
            // tabPage3
            // 
            this.tabPage3.Controls.Add(this.groupBox4);
            this.tabPage3.Controls.Add(this.groupBox7);
            this.tabPage3.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.tabPage3.Location = new System.Drawing.Point(4, 22);
            this.tabPage3.Name = "tabPage3";
            this.tabPage3.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage3.Size = new System.Drawing.Size(1063, 536);
            this.tabPage3.TabIndex = 2;
            this.tabPage3.Text = "调度后台命令";
            this.tabPage3.UseVisualStyleBackColor = true;
            // 
            // groupBox4
            // 
            this.groupBox4.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox4.Controls.Add(this.OrclCmd_inforRichBox);
            this.groupBox4.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox4.Location = new System.Drawing.Point(434, 21);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(612, 494);
            this.groupBox4.TabIndex = 28;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "系统运行过程信息";
            // 
            // OrclCmd_inforRichBox
            // 
            this.OrclCmd_inforRichBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.OrclCmd_inforRichBox.Location = new System.Drawing.Point(6, 25);
            this.OrclCmd_inforRichBox.Name = "OrclCmd_inforRichBox";
            this.OrclCmd_inforRichBox.Size = new System.Drawing.Size(600, 463);
            this.OrclCmd_inforRichBox.TabIndex = 21;
            this.OrclCmd_inforRichBox.Text = "";
            // 
            // groupBox7
            // 
            this.groupBox7.Controls.Add(this.OrclCmd_txtBox);
            this.groupBox7.Controls.Add(this.label2);
            this.groupBox7.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox7.Location = new System.Drawing.Point(16, 21);
            this.groupBox7.Name = "groupBox7";
            this.groupBox7.Size = new System.Drawing.Size(379, 109);
            this.groupBox7.TabIndex = 27;
            this.groupBox7.TabStop = false;
            this.groupBox7.Text = "系统监视与控制";
            // 
            // OrclCmd_txtBox
            // 
            this.OrclCmd_txtBox.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.OrclCmd_txtBox.Location = new System.Drawing.Point(171, 25);
            this.OrclCmd_txtBox.Name = "OrclCmd_txtBox";
            this.OrclCmd_txtBox.Size = new System.Drawing.Size(139, 26);
            this.OrclCmd_txtBox.TabIndex = 3;
            this.OrclCmd_txtBox.Text = "1005";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.Location = new System.Drawing.Point(19, 28);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(152, 16);
            this.label2.TabIndex = 0;
            this.label2.Text = "数据上传间隔时间：";
            // 
            // tabPage4
            // 
            this.tabPage4.Controls.Add(this.groupBox8);
            this.tabPage4.Controls.Add(this.groupBox9);
            this.tabPage4.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.tabPage4.Location = new System.Drawing.Point(4, 22);
            this.tabPage4.Name = "tabPage4";
            this.tabPage4.Size = new System.Drawing.Size(1063, 536);
            this.tabPage4.TabIndex = 3;
            this.tabPage4.Text = "组态页面命令";
            this.tabPage4.UseVisualStyleBackColor = true;
            // 
            // groupBox8
            // 
            this.groupBox8.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox8.Controls.Add(this.ScadaCmd_inforRichBox);
            this.groupBox8.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox8.Location = new System.Drawing.Point(434, 21);
            this.groupBox8.Name = "groupBox8";
            this.groupBox8.Size = new System.Drawing.Size(612, 494);
            this.groupBox8.TabIndex = 28;
            this.groupBox8.TabStop = false;
            this.groupBox8.Text = "系统运行过程信息";
            // 
            // ScadaCmd_inforRichBox
            // 
            this.ScadaCmd_inforRichBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.ScadaCmd_inforRichBox.Location = new System.Drawing.Point(6, 25);
            this.ScadaCmd_inforRichBox.Name = "ScadaCmd_inforRichBox";
            this.ScadaCmd_inforRichBox.Size = new System.Drawing.Size(600, 463);
            this.ScadaCmd_inforRichBox.TabIndex = 21;
            this.ScadaCmd_inforRichBox.Text = "";
            // 
            // groupBox9
            // 
            this.groupBox9.Controls.Add(this.ScadaCmd_txtBox);
            this.groupBox9.Controls.Add(this.label3);
            this.groupBox9.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox9.Location = new System.Drawing.Point(16, 21);
            this.groupBox9.Name = "groupBox9";
            this.groupBox9.Size = new System.Drawing.Size(379, 109);
            this.groupBox9.TabIndex = 27;
            this.groupBox9.TabStop = false;
            this.groupBox9.Text = "系统监视与控制";
            // 
            // ScadaCmd_txtBox
            // 
            this.ScadaCmd_txtBox.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.ScadaCmd_txtBox.Location = new System.Drawing.Point(171, 25);
            this.ScadaCmd_txtBox.Name = "ScadaCmd_txtBox";
            this.ScadaCmd_txtBox.Size = new System.Drawing.Size(139, 26);
            this.ScadaCmd_txtBox.TabIndex = 3;
            this.ScadaCmd_txtBox.Text = "1005";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label3.Location = new System.Drawing.Point(19, 28);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(152, 16);
            this.label3.TabIndex = 0;
            this.label3.Text = "数据上传间隔时间：";
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
            this.notifyIcon1.Text = "南环组态数据监控中心V1.0";
            this.notifyIcon1.Visible = true;
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // statusStrip2
            // 
            this.statusStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLabel2,
            this.toolStripStatusLabel4});
            this.statusStrip2.Location = new System.Drawing.Point(0, 544);
            this.statusStrip2.Name = "statusStrip2";
            this.statusStrip2.Size = new System.Drawing.Size(1084, 22);
            this.statusStrip2.TabIndex = 26;
            this.statusStrip2.Text = "statusStrip2";
            // 
            // toolStripStatusLabel2
            // 
            this.toolStripStatusLabel2.Name = "toolStripStatusLabel2";
            this.toolStripStatusLabel2.Size = new System.Drawing.Size(153, 17);
            this.toolStripStatusLabel2.Text = "南环组态数据监控中心V1.0";
            // 
            // toolStripStatusLabel4
            // 
            this.toolStripStatusLabel4.Name = "toolStripStatusLabel4";
            this.toolStripStatusLabel4.Size = new System.Drawing.Size(0, 17);
            // 
            // scadaForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1084, 566);
            this.Controls.Add(this.statusStrip2);
            this.Controls.Add(this.PointDB_txtBox);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "scadaForm";
            this.Text = "组态数据监控中心";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.scadaForm_FormClosing);
            this.Load += new System.EventHandler(this.scadaForm_Load);
            this.PointDB_txtBox.ResumeLayout(false);
            this.cfgTab.ResumeLayout(false);
            this.groupBox5.ResumeLayout(false);
            this.groupBox5.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.groupBox10.ResumeLayout(false);
            this.groupBox11.ResumeLayout(false);
            this.groupBox11.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.groupBox6.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.tabPage3.ResumeLayout(false);
            this.groupBox4.ResumeLayout(false);
            this.groupBox7.ResumeLayout(false);
            this.groupBox7.PerformLayout();
            this.tabPage4.ResumeLayout(false);
            this.groupBox8.ResumeLayout(false);
            this.groupBox9.ResumeLayout(false);
            this.groupBox9.PerformLayout();
            this.statusStrip2.ResumeLayout(false);
            this.statusStrip2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TabControl PointDB_txtBox;
        private System.Windows.Forms.TabPage cfgTab;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.TabPage tabPage3;
        private System.Windows.Forms.TabPage tabPage4;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RichTextBox cfg_inforRichBox;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.TextBox cfg_ORA_SOURCE_Txt;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox cfg_ORA_USER_Txt;
        private System.Windows.Forms.TextBox cfg_ORA_PORT_Txt;
        private System.Windows.Forms.TextBox cfg_ORA_IP_Txt;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.TextBox cfg_HERT_Txt;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox cfg_ORA_PSW_Txt;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TextBox pointDB_Time_txt;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.StatusStrip statusStrip2;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel2;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel4;
        private System.Windows.Forms.GroupBox groupBox6;
        private System.Windows.Forms.RichTextBox pointDB_inforRichBox;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.RichTextBox OrclCmd_inforRichBox;
        private System.Windows.Forms.GroupBox groupBox7;
        private System.Windows.Forms.TextBox OrclCmd_txtBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.GroupBox groupBox8;
        private System.Windows.Forms.RichTextBox ScadaCmd_inforRichBox;
        private System.Windows.Forms.GroupBox groupBox9;
        private System.Windows.Forms.TextBox ScadaCmd_txtBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.GroupBox groupBox10;
        private System.Windows.Forms.RichTextBox DBPoint_inforRichBox;
        private System.Windows.Forms.GroupBox groupBox11;
        private System.Windows.Forms.TextBox DBPoint_txtBox;
        private System.Windows.Forms.Label label4;
    }
}