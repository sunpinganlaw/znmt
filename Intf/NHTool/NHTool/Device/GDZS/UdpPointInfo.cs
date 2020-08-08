using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NHTool.Device.GDZS
{
    class UdpPointInfo
    {
       
        private String sourceNumber;

        private String deviceCode;
        private String status1; 
        private String status2;
        private String value = null;
        private String type;
        private byte[] bytes = new byte[12];
        private double lastStateReadTime;
        private String sourceName;
        
        /// <summary>
        /// 点名
        /// </summary>
        public string SourceNumber { get => sourceNumber; set => sourceNumber = value; }
        /// <summary>
        /// 点编号--为了发送别系统用
        /// </summary>
        public string DeviceCode { get => deviceCode; set => deviceCode = value; }
        /// <summary>
        /// 状态字 1
        /// </summary>
        public string Status1 { get => status1; set => status1 = value; }
        /// <summary>
        /// 状态字 2
        /// </summary>
        public string Status2 { get => status2; set => status2 = value; }
        /// <summary>
        /// 实际值
        /// </summary>
        public string Value { get => value; set => this.value = value; }
        /// <summary>
        /// 测点类型模拟点（AP）：4 个字节的浮点数，代表该模拟点的数值。开关点（DP）：4 个字节的整数，0 或 1。打包点（GP）：2 个无符号短整数，前一个代表数值，后一个是强制标志
        /// </summary>
        public string Type { get => type; set => type = value; }
        /// <summary>
        /// 原始byte数组，其实长度是固定的12个字节
        /// </summary>
        public byte[] Bytes { get => bytes; set => bytes = value; }
        /// <summary>
        /// 
        /// </summary>
        public double LastStateReadTime { get => lastStateReadTime; set => lastStateReadTime = value; }
        /// <summary>
        /// 原始DCS中的测点名
        /// </summary>
        public string SourceName { get => sourceName; set => sourceName = value; }

        
        /// <summary>
        /// 字节数组转化
        /// </summary>
        /// <param name="bytes"></param>
        public void setBytes(byte[] bytes)
        {

            Array.Copy(bytes, 0, this.bytes, 0, bytes.Length);
            byte[] status1Byte = new byte[2];
            byte[] status2Byte = new byte[2];
            byte[] valueByte = new byte[4];

            Array.Copy(this.bytes, 4, status1Byte, 0, 2);
            Array.Copy(this.bytes, 6, status2Byte, 0, 2);
            Array.Copy(this.bytes, 8, valueByte, 0, 4);


            CalcValue(valueByte);
        }



        private void CalcValue(byte[] value)
        {
            switch (type)
            {
                case "AP":

                    //this.value = DataHanderTool.getFloat(value, 0, DataHanderTool.RegisterOrder.HighLow).ToString();
                    this.value = BitConverter.ToSingle(value, 0).ToString();

                    break;
                case "DP":

                    //this.value = DataHanderTool.getIntBy4(value, 0, DataHanderTool.RegisterOrder.HighLow).ToString();
                    this.value = BitConverter.ToUInt32(value, 0).ToString();

                    break;
                case "GP":

                    this.value = "";

                    break;


            }

            LastStateReadTime = DateTime.Now.Millisecond;

        }


        public override String ToString()
        {
            
            StringBuilder sb = new StringBuilder();
            sb.Append("(class = " + "" + "; ");
            sb.Append("deviceCode = " + deviceCode + "; ");
            sb.Append("sourceNumber = " + sourceNumber + "; ");
            sb.Append("status1 = " + status1 + "; ");
            sb.Append("status2 = " + status2 + "; ");
            sb.Append("value = " + value + "; ");
            sb.Append("type = " + type + "; ");
            sb.Append("sourceName = " + sourceName + "; ");
            return sb.ToString();
        }


    }
}
