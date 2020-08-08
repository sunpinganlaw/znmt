using System.Collections.Generic;

namespace NHTool.Forms.carInForm
{
    partial class carInForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(carInForm));
            this.LedPB = new System.Windows.Forms.PictureBox();
            this.frontGatePB = new System.Windows.Forms.PictureBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.backGatePB = new System.Windows.Forms.PictureBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.inRadiationPB = new System.Windows.Forms.PictureBox();
            this.backRadiationPB = new System.Windows.Forms.PictureBox();
            this.frontRadiationPB = new System.Windows.Forms.PictureBox();
            this.outRadiationPB = new System.Windows.Forms.PictureBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.cameraBtn = new System.Windows.Forms.Button();
            this.label12 = new System.Windows.Forms.Label();
            this.buttonBackDown = new System.Windows.Forms.Button();
            this.buttonBackUp = new System.Windows.Forms.Button();
            this.label11 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.buttonLedRed = new System.Windows.Forms.Button();
            this.buttonLedGreen = new System.Windows.Forms.Button();
            this.buttonFrontDown = new System.Windows.Forms.Button();
            this.buttonFrontUp = new System.Windows.Forms.Button();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.label5 = new System.Windows.Forms.Label();
            this.likeCardId = new System.Windows.Forms.TextBox();
            this.infoTimeLabel = new System.Windows.Forms.Label();
            this.refreshBtn = new System.Windows.Forms.Button();
            this.infoKeyText = new System.Windows.Forms.TextBox();
            this.infoDataGridView = new System.Windows.Forms.DataGridView();
            this.groupBox6 = new System.Windows.Forms.GroupBox();
            this.carNO = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.cardID = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.coalID = new System.Windows.Forms.TextBox();
            this.label17 = new System.Windows.Forms.Label();
            this.debugInfo = new System.Windows.Forms.TextBox();
            this.buttonReset = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.LedPB)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.frontGatePB)).BeginInit();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.backGatePB)).BeginInit();
            this.groupBox2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.inRadiationPB)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.backRadiationPB)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.frontRadiationPB)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.outRadiationPB)).BeginInit();
            this.groupBox4.SuspendLayout();
            this.groupBox5.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.infoDataGridView)).BeginInit();
            this.groupBox6.SuspendLayout();
            this.SuspendLayout();
            // 
            // LedPB
            // 
            this.LedPB.BackColor = System.Drawing.SystemColors.Control;
            this.LedPB.Image = global::NHTool.Properties.Resources.GL;
            this.LedPB.InitialImage = global::NHTool.Properties.Resources.green;
            this.LedPB.Location = new System.Drawing.Point(151, 32);
            this.LedPB.Name = "LedPB";
            this.LedPB.Size = new System.Drawing.Size(94, 128);
            this.LedPB.TabIndex = 0;
            this.LedPB.TabStop = false;
            // 
            // frontGatePB
            // 
            this.frontGatePB.BackColor = System.Drawing.SystemColors.Control;
            this.frontGatePB.Image = global::NHTool.Properties.Resources.DOWN;
            this.frontGatePB.InitialImage = global::NHTool.Properties.Resources.green;
            this.frontGatePB.Location = new System.Drawing.Point(6, 32);
            this.frontGatePB.Name = "frontGatePB";
            this.frontGatePB.Size = new System.Drawing.Size(124, 54);
            this.frontGatePB.TabIndex = 1;
            this.frontGatePB.TabStop = false;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.backGatePB);
            this.groupBox1.Controls.Add(this.LedPB);
            this.groupBox1.Controls.Add(this.frontGatePB);
            this.groupBox1.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox1.Location = new System.Drawing.Point(12, 10);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(276, 176);
            this.groupBox1.TabIndex = 13;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "道闸红绿灯状态信息";
            // 
            // backGatePB
            // 
            this.backGatePB.BackColor = System.Drawing.SystemColors.Control;
            this.backGatePB.Image = global::NHTool.Properties.Resources.DOWN;
            this.backGatePB.InitialImage = global::NHTool.Properties.Resources.green;
            this.backGatePB.Location = new System.Drawing.Point(6, 100);
            this.backGatePB.Name = "backGatePB";
            this.backGatePB.Size = new System.Drawing.Size(124, 59);
            this.backGatePB.TabIndex = 2;
            this.backGatePB.TabStop = false;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.inRadiationPB);
            this.groupBox2.Controls.Add(this.backRadiationPB);
            this.groupBox2.Controls.Add(this.frontRadiationPB);
            this.groupBox2.Controls.Add(this.outRadiationPB);
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox2.Location = new System.Drawing.Point(12, 192);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(276, 144);
            this.groupBox2.TabIndex = 14;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "红外对射状态信息";
            // 
            // inRadiationPB
            // 
            this.inRadiationPB.BackColor = System.Drawing.SystemColors.Control;
            this.inRadiationPB.Image = global::NHTool.Properties.Resources.red;
            this.inRadiationPB.InitialImage = global::NHTool.Properties.Resources.red;
            this.inRadiationPB.Location = new System.Drawing.Point(203, 109);
            this.inRadiationPB.Name = "inRadiationPB";
            this.inRadiationPB.Size = new System.Drawing.Size(19, 19);
            this.inRadiationPB.TabIndex = 15;
            this.inRadiationPB.TabStop = false;
            // 
            // backRadiationPB
            // 
            this.backRadiationPB.BackColor = System.Drawing.SystemColors.Control;
            this.backRadiationPB.Image = global::NHTool.Properties.Resources.red;
            this.backRadiationPB.InitialImage = global::NHTool.Properties.Resources.red;
            this.backRadiationPB.Location = new System.Drawing.Point(203, 87);
            this.backRadiationPB.Name = "backRadiationPB";
            this.backRadiationPB.Size = new System.Drawing.Size(19, 19);
            this.backRadiationPB.TabIndex = 15;
            this.backRadiationPB.TabStop = false;
            // 
            // frontRadiationPB
            // 
            this.frontRadiationPB.BackColor = System.Drawing.SystemColors.Control;
            this.frontRadiationPB.Image = global::NHTool.Properties.Resources.red;
            this.frontRadiationPB.InitialImage = global::NHTool.Properties.Resources.red;
            this.frontRadiationPB.Location = new System.Drawing.Point(203, 60);
            this.frontRadiationPB.Name = "frontRadiationPB";
            this.frontRadiationPB.Size = new System.Drawing.Size(19, 19);
            this.frontRadiationPB.TabIndex = 4;
            this.frontRadiationPB.TabStop = false;
            // 
            // outRadiationPB
            // 
            this.outRadiationPB.BackColor = System.Drawing.SystemColors.Control;
            this.outRadiationPB.Image = global::NHTool.Properties.Resources.red;
            this.outRadiationPB.InitialImage = global::NHTool.Properties.Resources.red;
            this.outRadiationPB.Location = new System.Drawing.Point(203, 34);
            this.outRadiationPB.Name = "outRadiationPB";
            this.outRadiationPB.Size = new System.Drawing.Size(19, 19);
            this.outRadiationPB.TabIndex = 3;
            this.outRadiationPB.TabStop = false;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label4.Location = new System.Drawing.Point(17, 109);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(180, 19);
            this.label4.TabIndex = 3;
            this.label4.Text = "车辆入口红外对射：";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label3.Location = new System.Drawing.Point(17, 87);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(180, 19);
            this.label3.TabIndex = 2;
            this.label3.Text = "车后尾部红外对射：";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.Location = new System.Drawing.Point(17, 60);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(180, 19);
            this.label2.TabIndex = 1;
            this.label2.Text = "车头前方红外对射：";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(17, 34);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(180, 19);
            this.label1.TabIndex = 0;
            this.label1.Text = "车辆出口红外对射：";
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.cameraBtn);
            this.groupBox4.Controls.Add(this.label12);
            this.groupBox4.Controls.Add(this.buttonBackDown);
            this.groupBox4.Controls.Add(this.buttonBackUp);
            this.groupBox4.Controls.Add(this.label11);
            this.groupBox4.Controls.Add(this.label8);
            this.groupBox4.Controls.Add(this.buttonLedRed);
            this.groupBox4.Controls.Add(this.buttonLedGreen);
            this.groupBox4.Controls.Add(this.buttonFrontDown);
            this.groupBox4.Controls.Add(this.buttonFrontUp);
            this.groupBox4.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox4.Location = new System.Drawing.Point(299, 192);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(332, 191);
            this.groupBox4.TabIndex = 15;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "操作控制面板";
            // 
            // cameraBtn
            // 
            this.cameraBtn.BackColor = System.Drawing.Color.Transparent;
            this.cameraBtn.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cameraBtn.ForeColor = System.Drawing.Color.Blue;
            this.cameraBtn.Location = new System.Drawing.Point(20, 145);
            this.cameraBtn.Name = "cameraBtn";
            this.cameraBtn.Size = new System.Drawing.Size(178, 36);
            this.cameraBtn.TabIndex = 21;
            this.cameraBtn.Text = "停用车牌识别器";
            this.cameraBtn.UseVisualStyleBackColor = false;
            this.cameraBtn.Click += new System.EventHandler(this.cameraBtn_Click);
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label12.Location = new System.Drawing.Point(20, 111);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(85, 19);
            this.label12.TabIndex = 20;
            this.label12.Text = "红绿灯：";
            // 
            // buttonBackDown
            // 
            this.buttonBackDown.BackColor = System.Drawing.Color.Transparent;
            this.buttonBackDown.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonBackDown.ForeColor = System.Drawing.Color.Red;
            this.buttonBackDown.Location = new System.Drawing.Point(233, 60);
            this.buttonBackDown.Name = "buttonBackDown";
            this.buttonBackDown.Size = new System.Drawing.Size(88, 36);
            this.buttonBackDown.TabIndex = 19;
            this.buttonBackDown.Text = "落杆";
            this.buttonBackDown.UseVisualStyleBackColor = false;
            this.buttonBackDown.Click += new System.EventHandler(this.buttonBackDown_Click);
            // 
            // buttonBackUp
            // 
            this.buttonBackUp.BackColor = System.Drawing.Color.Transparent;
            this.buttonBackUp.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonBackUp.ForeColor = System.Drawing.Color.Blue;
            this.buttonBackUp.Location = new System.Drawing.Point(151, 60);
            this.buttonBackUp.Name = "buttonBackUp";
            this.buttonBackUp.Size = new System.Drawing.Size(79, 36);
            this.buttonBackUp.TabIndex = 18;
            this.buttonBackUp.Text = "抬杆";
            this.buttonBackUp.UseVisualStyleBackColor = false;
            this.buttonBackUp.Click += new System.EventHandler(this.buttonBackUp_Click);
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label11.Location = new System.Drawing.Point(20, 68);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(104, 19);
            this.label11.TabIndex = 17;
            this.label11.Text = "入口道闸：";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label8.Location = new System.Drawing.Point(20, 31);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(104, 19);
            this.label8.TabIndex = 16;
            this.label8.Text = "出口道闸：";
            // 
            // buttonLedRed
            // 
            this.buttonLedRed.BackColor = System.Drawing.Color.Transparent;
            this.buttonLedRed.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonLedRed.ForeColor = System.Drawing.Color.Red;
            this.buttonLedRed.Location = new System.Drawing.Point(233, 100);
            this.buttonLedRed.Name = "buttonLedRed";
            this.buttonLedRed.Size = new System.Drawing.Size(88, 36);
            this.buttonLedRed.TabIndex = 11;
            this.buttonLedRed.Text = "红灯";
            this.buttonLedRed.UseVisualStyleBackColor = false;
            this.buttonLedRed.Click += new System.EventHandler(this.buttonLedRed_Click);
            // 
            // buttonLedGreen
            // 
            this.buttonLedGreen.BackColor = System.Drawing.Color.Transparent;
            this.buttonLedGreen.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonLedGreen.ForeColor = System.Drawing.Color.Blue;
            this.buttonLedGreen.Location = new System.Drawing.Point(151, 100);
            this.buttonLedGreen.Name = "buttonLedGreen";
            this.buttonLedGreen.Size = new System.Drawing.Size(79, 36);
            this.buttonLedGreen.TabIndex = 10;
            this.buttonLedGreen.Text = "绿灯";
            this.buttonLedGreen.UseVisualStyleBackColor = false;
            this.buttonLedGreen.Click += new System.EventHandler(this.buttonLedGreen_Click);
            // 
            // buttonFrontDown
            // 
            this.buttonFrontDown.BackColor = System.Drawing.Color.Transparent;
            this.buttonFrontDown.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonFrontDown.ForeColor = System.Drawing.Color.Red;
            this.buttonFrontDown.Location = new System.Drawing.Point(233, 20);
            this.buttonFrontDown.Name = "buttonFrontDown";
            this.buttonFrontDown.Size = new System.Drawing.Size(88, 36);
            this.buttonFrontDown.TabIndex = 9;
            this.buttonFrontDown.Text = "落杆";
            this.buttonFrontDown.UseVisualStyleBackColor = false;
            this.buttonFrontDown.Click += new System.EventHandler(this.buttonFrontDown_Click);
            // 
            // buttonFrontUp
            // 
            this.buttonFrontUp.BackColor = System.Drawing.Color.Transparent;
            this.buttonFrontUp.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonFrontUp.ForeColor = System.Drawing.Color.Blue;
            this.buttonFrontUp.Location = new System.Drawing.Point(151, 20);
            this.buttonFrontUp.Name = "buttonFrontUp";
            this.buttonFrontUp.Size = new System.Drawing.Size(79, 36);
            this.buttonFrontUp.TabIndex = 8;
            this.buttonFrontUp.Text = "抬杆";
            this.buttonFrontUp.UseVisualStyleBackColor = false;
            this.buttonFrontUp.Click += new System.EventHandler(this.buttonFrontUp_Click);
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.label5);
            this.groupBox5.Controls.Add(this.likeCardId);
            this.groupBox5.Controls.Add(this.infoTimeLabel);
            this.groupBox5.Controls.Add(this.refreshBtn);
            this.groupBox5.Controls.Add(this.infoKeyText);
            this.groupBox5.Controls.Add(this.infoDataGridView);
            this.groupBox5.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox5.Location = new System.Drawing.Point(641, 14);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(565, 532);
            this.groupBox5.TabIndex = 15;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "运行内存信息：";
            this.groupBox5.Enter += new System.EventHandler(this.groupBox5_Enter);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(13, 84);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(104, 19);
            this.label5.TabIndex = 5;
            this.label5.Text = "疑似车卡号";
            this.label5.Click += new System.EventHandler(this.label5_Click);
            // 
            // likeCardId
            // 
            this.likeCardId.Location = new System.Drawing.Point(123, 78);
            this.likeCardId.Name = "likeCardId";
            this.likeCardId.Size = new System.Drawing.Size(251, 29);
            this.likeCardId.TabIndex = 4;
            this.likeCardId.TextChanged += new System.EventHandler(this.likeCardId_TextChanged);
            // 
            // infoTimeLabel
            // 
            this.infoTimeLabel.AutoSize = true;
            this.infoTimeLabel.Location = new System.Drawing.Point(432, 36);
            this.infoTimeLabel.Name = "infoTimeLabel";
            this.infoTimeLabel.Size = new System.Drawing.Size(85, 19);
            this.infoTimeLabel.TabIndex = 3;
            this.infoTimeLabel.Text = "接收时间";
            // 
            // refreshBtn
            // 
            this.refreshBtn.Location = new System.Drawing.Point(320, 26);
            this.refreshBtn.Name = "refreshBtn";
            this.refreshBtn.Size = new System.Drawing.Size(97, 37);
            this.refreshBtn.TabIndex = 2;
            this.refreshBtn.Text = "刷新";
            this.refreshBtn.UseVisualStyleBackColor = true;
            // 
            // infoKeyText
            // 
            this.infoKeyText.Location = new System.Drawing.Point(17, 33);
            this.infoKeyText.Name = "infoKeyText";
            this.infoKeyText.Size = new System.Drawing.Size(251, 29);
            this.infoKeyText.TabIndex = 1;
            this.infoKeyText.TextChanged += new System.EventHandler(this.infoKeyText_TextChanged);
            // 
            // infoDataGridView
            // 
            this.infoDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.infoDataGridView.Location = new System.Drawing.Point(7, 122);
            this.infoDataGridView.Name = "infoDataGridView";
            this.infoDataGridView.RowTemplate.Height = 23;
            this.infoDataGridView.Size = new System.Drawing.Size(552, 404);
            this.infoDataGridView.TabIndex = 0;
            // 
            // groupBox6
            // 
            this.groupBox6.Controls.Add(this.carNO);
            this.groupBox6.Controls.Add(this.label14);
            this.groupBox6.Controls.Add(this.cardID);
            this.groupBox6.Controls.Add(this.label13);
            this.groupBox6.Controls.Add(this.coalID);
            this.groupBox6.Controls.Add(this.label17);
            this.groupBox6.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox6.Location = new System.Drawing.Point(323, 21);
            this.groupBox6.Name = "groupBox6";
            this.groupBox6.Size = new System.Drawing.Size(286, 165);
            this.groupBox6.TabIndex = 21;
            this.groupBox6.TabStop = false;
            this.groupBox6.Text = "运行信息";
            // 
            // carNO
            // 
            this.carNO.Location = new System.Drawing.Point(94, 64);
            this.carNO.Name = "carNO";
            this.carNO.Size = new System.Drawing.Size(147, 29);
            this.carNO.TabIndex = 9;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(17, 74);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(66, 19);
            this.label14.TabIndex = 8;
            this.label14.Text = "车号：";
            // 
            // cardID
            // 
            this.cardID.Location = new System.Drawing.Point(94, 26);
            this.cardID.Name = "cardID";
            this.cardID.Size = new System.Drawing.Size(147, 29);
            this.cardID.TabIndex = 7;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(7, 29);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(86, 19);
            this.label13.TabIndex = 6;
            this.label13.Text = "车卡ID：";
            // 
            // coalID
            // 
            this.coalID.Location = new System.Drawing.Point(94, 112);
            this.coalID.Name = "coalID";
            this.coalID.Size = new System.Drawing.Size(147, 29);
            this.coalID.TabIndex = 13;
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(7, 115);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(86, 19);
            this.label17.TabIndex = 12;
            this.label17.Text = "矿卡ID：";
            // 
            // debugInfo
            // 
            this.debugInfo.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.debugInfo.Location = new System.Drawing.Point(12, 432);
            this.debugInfo.Multiline = true;
            this.debugInfo.Name = "debugInfo";
            this.debugInfo.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.debugInfo.Size = new System.Drawing.Size(623, 114);
            this.debugInfo.TabIndex = 27;
            this.debugInfo.WordWrap = false;
            // 
            // buttonReset
            // 
            this.buttonReset.BackColor = System.Drawing.Color.Transparent;
            this.buttonReset.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonReset.ForeColor = System.Drawing.Color.Blue;
            this.buttonReset.Location = new System.Drawing.Point(33, 370);
            this.buttonReset.Name = "buttonReset";
            this.buttonReset.Size = new System.Drawing.Size(125, 46);
            this.buttonReset.TabIndex = 28;
            this.buttonReset.Text = "系统复位";
            this.buttonReset.UseVisualStyleBackColor = false;
            this.buttonReset.Click += new System.EventHandler(this.buttonReset_Click);
            // 
            // carInForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1231, 574);
            this.Controls.Add(this.buttonReset);
            this.Controls.Add(this.debugInfo);
            this.Controls.Add(this.groupBox6);
            this.Controls.Add(this.groupBox5);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "carInForm";

            ////根据需要给配置文件增加 FormTitle，以改变form的标题 xieyt 20190607
            //Dictionary<string, string> ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            //string formTitle = "NEPT汽车自动识别";
            //if (ctlConfig.ContainsKey("FormTitle"))
            //{
            //    formTitle = ctlConfig["FormTitle"];
            //}

            //this.Text = formTitle;
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.carInForm_FormClosing);
            this.Load += new System.EventHandler(this.carInForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.LedPB)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.frontGatePB)).EndInit();
            this.groupBox1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.backGatePB)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.inRadiationPB)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.backRadiationPB)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.frontRadiationPB)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.outRadiationPB)).EndInit();
            this.groupBox4.ResumeLayout(false);
            this.groupBox4.PerformLayout();
            this.groupBox5.ResumeLayout(false);
            this.groupBox5.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.infoDataGridView)).EndInit();
            this.groupBox6.ResumeLayout(false);
            this.groupBox6.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox LedPB;
        private System.Windows.Forms.PictureBox frontGatePB;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.PictureBox backGatePB;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.PictureBox inRadiationPB;
        private System.Windows.Forms.PictureBox backRadiationPB;
        private System.Windows.Forms.PictureBox frontRadiationPB;
        private System.Windows.Forms.PictureBox outRadiationPB;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Button buttonBackDown;
        private System.Windows.Forms.Button buttonBackUp;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button buttonLedRed;
        private System.Windows.Forms.Button buttonLedGreen;
        private System.Windows.Forms.Button buttonFrontDown;
        private System.Windows.Forms.Button buttonFrontUp;
        private System.Windows.Forms.GroupBox groupBox6;
        private System.Windows.Forms.TextBox carNO;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox cardID;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.TextBox coalID;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.DataGridView infoDataGridView;
        private System.Windows.Forms.Button refreshBtn;
        private System.Windows.Forms.TextBox infoKeyText;
        private System.Windows.Forms.Label infoTimeLabel;
        private System.Windows.Forms.TextBox debugInfo;
        private System.Windows.Forms.Button buttonReset;
 
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox likeCardId;
 
        private System.Windows.Forms.Button cameraBtn;
 
    }
}