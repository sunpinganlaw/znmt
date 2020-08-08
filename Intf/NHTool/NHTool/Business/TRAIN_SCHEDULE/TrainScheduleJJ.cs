using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Device.RailScan;

namespace NHTool.Business.TRAIN_SCHEDULE
{
    class TrainScheduleJJ
    {
        private RailScanDevice railScanDevice1 = null;
        private RailScanDevice railScanDevice2 = null;
        public Dictionary<string, string> ctlConfig;

        public TrainScheduleJJ(Dictionary<string, string> ctlConfig)
        {
            this.ctlConfig = ctlConfig;
        }

        public void startSchedule()
        {
            string ComName = ctlConfig["RailScanComName"];
            string[] ComNameList = ComName.Split(',');
            string ComBualRate = ctlConfig["RailScanComBaudRate"];
            string[] ComBualRateList = ComBualRate.Split(',');
            //入厂车号识别器
            railScanDevice1 = new RailScanDevice();
            railScanDevice1.InitRailScanPortHA("income", ComNameList[0], ComBualRateList[0], "0", "8", "1");
            //翻车机前面的车号识别器
            railScanDevice2 = new RailScanDevice();
            railScanDevice2.InitRailScanPortHA("turnover", ComNameList[1], ComBualRateList[1], "0", "8", "1");
        }

    }
}
