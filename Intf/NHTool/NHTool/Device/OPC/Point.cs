
using System.Collections.Generic;
using System.Text;

namespace NHTool
{
    public  class Point
    {
        private string totalTagName;

        public string TotalTagName
        {
            get { return totalTagName; }
            set { totalTagName = value; }
        }

               private string tagName;

        public string TagName
        {
          get { return tagName; }
          set { tagName = value; }
        }
                private string address;

        public string Address
        {
          get { return address; }
          set { address = value; }
        }
                private string dataType;

        public string DataType
        {
          get { return dataType; }
          set { dataType = value; }
        }

                private string scanRate;

        public string ScanRate
        {
          get { return scanRate; }
          set { scanRate = value; }
        }
                private string scaling;

        private string prexPath;

        public string PrexPath
        {
            get { return prexPath; }
            set { prexPath = value; }
        }

        private string description;

        public string Description
        {
            get { return description; }
            set { description = value; }
        }
        //存放的是最后寻址的key, point.GroupId + "#" + point.LogicDeviceId + "." + getExcelCellValue(dataRow, columns, 7);
        private string logicTagName;

        public string LogicTagName
        {
            get { return logicTagName; }
            set { logicTagName = value; }
        }

        private string logicDeviceId;

        public string LogicDeviceId
        {
            get { return logicDeviceId; }
            set { logicDeviceId = value; }
        }

        private string action;

        public string Action
        {
            get { return action; }
            set { action = value; }
        }

        private string updateFrequency;

        public string UpdateFrequency
        {
            get { return updateFrequency; }
            set { updateFrequency = value; }
        }

        private string groupId;
        public string GroupId
        {
            get { return groupId; }
            set { groupId = value; }
        }

        private string protocolType;
        public string ProtocolType
        {
            get { return protocolType; }
            set { protocolType = value; }
        }

        public string value { get; set; }
    }
}
