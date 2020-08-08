using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NHTool.Common
{
    //矿卡信息
    public class CoalCardInfo
    {
        //矿卡ID
        public string mineCardId { get; set; }
        //矿点
        public string mineNo { get; set; }
        //矿名
        public string mineName { get; set; }
        //供应商
        public string vendorNo { get; set; }
        //票重
        public string ticketQty { get; set; }
        //车厢号,公铁联运的时候，火车车厢号
        public string trainNo { get; set; }
        //运输单位
        public string carrierNo { get; set; }
        //票号
        public string ticketNo { get; set; }
        //煤种
        public string coalNo { get; set; }
    }
}
