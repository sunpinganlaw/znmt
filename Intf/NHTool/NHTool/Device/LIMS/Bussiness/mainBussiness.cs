using System;
using System.Collections.Generic;
using NHTool.Device.LIMS.EnergyMeter;
using NHTool.Common;

namespace NHTool.Device.LIMS.Bussiness
{
    class mainBussiness
    {

        public static energyMeter energy = new energyMeter();
       static Dictionary<String, String> ret = new Dictionary<String, String>();

        public mainBussiness()
        {

            string msg = "";
            initiDevie(out msg);

        }
        
        /// <summary>
        /// 初始化设备
        /// </summary>
        /// <param name="msg"></param>
        /// <returns></returns>
        public static bool initiDevie(out string msg)
        {
            bool result = true;
            msg = "设备初始化成功";
            try
            {
                ret = energy.initial(ConfigTool.GetValueFromIni("EnergyMeter", "name"), ConfigTool.GetValueFromIni("EnergyMeter", "baud"), ConfigTool.GetValueFromIni("EnergyMeter", "par"), ConfigTool.GetValueFromIni("EnergyMeter", "dBits"), ConfigTool.GetValueFromIni("EnergyMeter", "sBits"));
                if (!ret[Common.Commons.RES_CODE].Equals("0"))
                {
                 
                    msg = ret[Commons.RES_MSG];
                    LogTool.WriteLog(typeof(mainBussiness), msg);
                    return false;
                }
                
            }
            catch(Exception ex)
            {
             
                msg = "初始化能量计失败";
                LogTool.WriteLog(typeof(mainBussiness), ex);
                return false;
            }
           
            ///-----todo--------

            
            return result;
        }


    }

    

}
