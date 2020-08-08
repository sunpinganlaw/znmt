using System.Windows.Forms;
namespace NHTool.Forms.SelectMineMachine
{
    partial class SelectMineMachineForm
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
            System.Environment.Exit(0);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.mineInfoDataGridView = new System.Windows.Forms.DataGridView();
            this.Column4 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column3 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column2 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.forecast_id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.flowId = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.carID = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.groupBox6 = new System.Windows.Forms.GroupBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.buttonFrontUp = new System.Windows.Forms.Button();
            this.trainNo = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.ticketQty = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.ticketNo = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.button2 = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.mineInfoDataGridView)).BeginInit();
            this.groupBox6.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // mineInfoDataGridView
            // 
            this.mineInfoDataGridView.AllowUserToAddRows = false;
            this.mineInfoDataGridView.ColumnHeadersHeight = 40;
            this.mineInfoDataGridView.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Column4,
            this.Column3,
            this.Column1,
            this.Column2,
            this.forecast_id});
            this.mineInfoDataGridView.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.mineInfoDataGridView.Location = new System.Drawing.Point(13, 126);
            this.mineInfoDataGridView.MultiSelect = false;
            this.mineInfoDataGridView.Name = "mineInfoDataGridView";
            this.mineInfoDataGridView.RowHeadersWidth = 50;
            this.mineInfoDataGridView.RowTemplate.Height = 23;
            this.mineInfoDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.mineInfoDataGridView.Size = new System.Drawing.Size(960, 800);
            this.mineInfoDataGridView.TabIndex = 0;
            this.mineInfoDataGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.selectOneRow);
            // 
            // Column4
            // 
            this.Column4.HeaderText = "煤种";
            this.Column4.Name = "Column4";
            this.Column4.Width = 70;
            // 
            // Column3
            // 
            this.Column3.HeaderText = "矿点";
            this.Column3.Name = "Column3";
            this.Column3.Width = 230;
            // 
            // Column1
            // 
            this.Column1.HeaderText = "供应商";
            this.Column1.Name = "Column1";
            this.Column1.Width = 300;
            // 
            // Column2
            // 
            this.Column2.HeaderText = "运输单位";
            this.Column2.Name = "Column2";
            this.Column2.Width = 300;
            // 
            // forecast_id
            // 
            this.forecast_id.HeaderText = "Column5";
            this.forecast_id.Name = "forecast_id";
            this.forecast_id.Width = 5;
            // 
            // flowId
            // 
            this.flowId.BackColor = System.Drawing.SystemColors.Control;
            this.flowId.Location = new System.Drawing.Point(127, 78);
            this.flowId.Name = "flowId";
            this.flowId.ReadOnly = true;
            this.flowId.Size = new System.Drawing.Size(114, 29);
            this.flowId.TabIndex = 9;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(10, 83);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(85, 19);
            this.label14.TabIndex = 8;
            this.label14.Text = "通道号：";
            // 
            // carID
            // 
            this.carID.BackColor = System.Drawing.SystemColors.Control;
            this.carID.Location = new System.Drawing.Point(127, 28);
            this.carID.Name = "carID";
            this.carID.Size = new System.Drawing.Size(114, 29);
            this.carID.TabIndex = 7;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(7, 31);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(85, 19);
            this.label13.TabIndex = 6;
            this.label13.Text = "车牌号：";
            // 
            // groupBox6
            // 
            this.groupBox6.Controls.Add(this.flowId);
            this.groupBox6.Controls.Add(this.label14);
            this.groupBox6.Controls.Add(this.carID);
            this.groupBox6.Controls.Add(this.label13);
            this.groupBox6.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox6.Location = new System.Drawing.Point(187, 1);
            this.groupBox6.Name = "groupBox6";
            this.groupBox6.Size = new System.Drawing.Size(255, 119);
            this.groupBox6.TabIndex = 22;
            this.groupBox6.TabStop = false;
            this.groupBox6.Text = "车辆信息";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.buttonFrontUp);
            this.groupBox1.Controls.Add(this.trainNo);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.ticketQty);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.ticketNo);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox1.Location = new System.Drawing.Point(451, 1);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(519, 119);
            this.groupBox1.TabIndex = 23;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "录入信息";
            // 
            // buttonFrontUp
            // 
            this.buttonFrontUp.BackColor = System.Drawing.Color.Transparent;
            this.buttonFrontUp.Font = new System.Drawing.Font("Tahoma", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonFrontUp.ForeColor = System.Drawing.Color.Black;
            this.buttonFrontUp.Location = new System.Drawing.Point(292, 58);
            this.buttonFrontUp.Name = "buttonFrontUp";
            this.buttonFrontUp.Size = new System.Drawing.Size(209, 55);
            this.buttonFrontUp.TabIndex = 12;
            this.buttonFrontUp.Text = "确认录入";
            this.buttonFrontUp.UseVisualStyleBackColor = false;
            this.buttonFrontUp.Click += new System.EventHandler(this.buttonFrontUp_Click);
            // 
            // trainNo
            // 
            this.trainNo.Location = new System.Drawing.Point(367, 21);
            this.trainNo.Name = "trainNo";
            this.trainNo.Size = new System.Drawing.Size(134, 29);
            this.trainNo.TabIndex = 11;
            this.trainNo.MouseClick += new System.Windows.Forms.MouseEventHandler(this.TextTrainNo_GotFocus);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(238, 27);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(123, 19);
            this.label3.TabIndex = 10;
            this.label3.Text = "火车车厢号：";
            // 
            // ticketQty
            // 
            this.ticketQty.Location = new System.Drawing.Point(114, 71);
            this.ticketQty.Name = "ticketQty";
            this.ticketQty.Size = new System.Drawing.Size(121, 29);
            this.ticketQty.TabIndex = 9;
            this.ticketQty.MouseClick += new System.Windows.Forms.MouseEventHandler(this.TextTickQty_GotFocus);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(11, 76);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(76, 19);
            this.label1.TabIndex = 8;
            this.label1.Text = "票重 ：";
            // 
            // ticketNo
            // 
            this.ticketNo.Location = new System.Drawing.Point(114, 23);
            this.ticketNo.Name = "ticketNo";
            this.ticketNo.Size = new System.Drawing.Size(121, 29);
            this.ticketNo.TabIndex = 7;
            this.ticketNo.MouseClick += new System.Windows.Forms.MouseEventHandler(this.TextTickNo_GotFocus);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(6, 28);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(85, 19);
            this.label2.TabIndex = 6;
            this.label2.Text = "票单号：";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.button2);
            this.groupBox2.Controls.Add(this.button1);
            this.groupBox2.Font = new System.Drawing.Font("宋体", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.groupBox2.Location = new System.Drawing.Point(13, 1);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(148, 119);
            this.groupBox2.TabIndex = 24;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "ئۇيغۇر تىلى/汉语";
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(6, 73);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(136, 36);
            this.button2.TabIndex = 1;
            this.button2.Text = "汉语";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(6, 28);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(136, 36);
            this.button1.TabIndex = 0;
            this.button1.Text = "ئۇيغۇر تىلى";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // SelectMineMachineForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(987, 882);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.groupBox6);
            this.Controls.Add(this.mineInfoDataGridView);
            this.Name = "SelectMineMachineForm";
            this.Text = "SelectMineMachineForm";
            this.Load += new System.EventHandler(this.formLoad);
            ((System.ComponentModel.ISupportInitialize)(this.mineInfoDataGridView)).EndInit();
            this.groupBox6.ResumeLayout(false);
            this.groupBox6.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView mineInfoDataGridView;
        private System.Windows.Forms.TextBox flowId;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox carID;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.GroupBox groupBox6;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.TextBox ticketQty;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox ticketNo;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox trainNo;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button buttonFrontUp;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column4;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column3;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column2;
        private System.Windows.Forms.DataGridViewTextBoxColumn forecast_id;
    }
}