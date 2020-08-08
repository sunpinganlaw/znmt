using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json.Linq;

namespace NHTool.Common
{
    public class StateEventArgs
    {

        public string groundSense { get; set; }

        public float carWeight { get; set; }

        public string carMzWeight { get; set; }

        public string carPzWeight { get; set; }

        public string carKdWeight { get; set; }

        public string carNetWeight { get; set; }

        public string inRadiation { get; set; }

        public string outRadiation { get; set; }

        public string frontRadiation { get; set; }

        public string backRadiation { get; set; }

        public string ledShowOut { get; set; }

        public string voiceOut { get; set; }

        public string frontGateUpOut { get; set; }

        public string frontGateDownOut { get; set; }

        public string backGateUpOut { get; set; }

        public string backGateDownOut { get; set; }

        public string lightRedOut { get; set; }

        public string lightGreenOut { get; set; }

        public string carNo { get; set; }

        public string cardID { get; set; }

        /// <summary>
        /// 矿卡ID
        /// </summary>
        public string mineCardID { get; set; }

        /// <summary>
        /// 矿名
        /// </summary>
        public string mineName { get; set; }

        /// <summary>
        /// 卸煤沟
        /// </summary>
        public string ditchCd { get; set; }

        /// <summary>
        /// 队伍序号
        /// </summary>
        public string queueOrder { get; set; }

        /// <summary>
        /// 状态机需要调用外部过程或者设备的名称
        /// </summary>
        public string actionName { get; set; }

        /// <summary>
        /// 状态机需要调用外部过程或者设备的返回值
        /// </summary>
        public string actionResultCode { get; set; }

        public string actionResultMsg { get; set; }

        //下一步执行的步骤
        public Commons.STEP nextStep { get; set; }

        //当前执行步骤
        public Commons.STEP currentStep { get; set; }

        //当前入厂通道，"1"或"2"，
        public string currentChannel { get; set; }

        //矿卡信息
        public CoalCardInfo coalCardInfo { get; set; }

        public string recordNo { get; set; }

        public string sampleCode { get; set; }

        public string sampleType { get; set; }

        public string carType { get; set; }

        public string sampleCfgPonits { get; set; }

        public string batchChgFlag { get; set; }

        public string sampleStartTime { get; set; }

        public string sampleEndTime { get; set; }

        public string bigWater { get; set; }

        public string sampleCoordNumList { get; set; }

        public JArray sampleCoordList { get; set; }

        public string SuofenInterval { get; set; }

        public string SuofenCnt { get; set; }

        public int stepExcuteCount { get; set; }

        /// <summary>
        /// 过磅类型
        /// </summary>
        public string weightType { get; set; }

        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("groundSense:" + groundSense + "\r\n");
            sb.Append("carWeight:" + carWeight.ToString() + "\r\n");
            sb.Append("inRadiation:" + inRadiation + "\r\n");
            sb.Append("outRadiation:" + outRadiation + "\r\n");
            sb.Append("frontRadiation:" + frontRadiation + "\r\n");
            sb.Append("backRadiation:" + backRadiation + "\r\n");
            sb.Append("ledShowOut:" + ledShowOut + "\r\n");
            sb.Append("voiceOut:" + voiceOut + "\r\n");
            sb.Append("frontGateUpOut:" + frontGateUpOut + "\r\n");
            sb.Append("frontGateDownOut:" + frontGateDownOut + "\r\n");
            sb.Append("backGateUpOut:" + backGateUpOut + "\r\n");
            sb.Append("backGateDownOut:" + backGateDownOut + "\r\n");
            sb.Append("lightRedOut:" + lightRedOut + "\r\n");
            sb.Append("lightGreenOut:" + lightGreenOut + "\r\n");
            sb.Append("carNo:" + carNo + "\r\n");
            sb.Append("cardID:" + cardID + "\r\n");
            sb.Append("actionName:" + actionName + "\r\n");
            sb.Append("actionResultCode:" + actionResultCode + "\r\n");
            sb.Append("actionResultMsg:" + actionResultMsg + "\r\n");
            return sb.ToString();
        }

    }

}
