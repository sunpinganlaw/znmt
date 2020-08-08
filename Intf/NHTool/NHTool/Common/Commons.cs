using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json.Linq;

namespace NHTool.Common
{
    public class Commons
    {
        public static String SRC_CODE_PROD = "999";
        public static String SRC_CODE_SIMU = "998";
        public static String RES_CODE = "resCode";
        public static String RES_MSG = "resMsg";
        public static String SUCC = "succ";
        public static String ERR = "err";
        public static String BACK_TIME = "backTime";//返回时间
        public static int ERR_WEIGHT = -999999;

        /// <summary>
        /// 当前卸煤沟状态 0空闲  1在等待去卸煤  2在卸煤
        /// </summary>
        public static String DITCH_IDLE = "0"; //空闲
        public static String DITCH_WAITING = "1"; //在等待去卸煤
        public static String DITCH_UNLOADING = "2";//在卸煤

        /// <summary>
        /// 默认句柄常量
        /// </summary>
        public const UInt32 pwd = 0x6789;
        /// <summary>
        /// 默认流程点
        /// </summary>
        public const STEP defaultErrAnimationState = STEP.IDLE;

        /// <summary>
        /// 内存数据错误常量
        /// </summary>
        public const string deviceInfoErrFlag = "-999";

        /// <summary>
        /// //地感
        /// </summary>
        public const String groundSense = "groundSense";//地感
        /// <summary>
        /// //地感2
        /// </summary>
        public const String groundSense2 = "groundSense2";//地感2
        /// <summary>
        /// 卸煤沟龙门架前面的红外对射
        /// </summary>
        public const String lmjRadiation = "lmjRadiation";
        /// <summary>
        /// 入口红外对射
        /// </summary>
        public const String inRadiation = "inRadiation";
        /// <summary>
        /// 出口处红外对射
        /// </summary>
        public const String outRadiation = "outRadiation";
        public const String outRadiationB = "outRadiationB";
        public const String outRadiationC = "outRadiationC";
        /// <summary>
        /// 汽车前端红外对射
        /// </summary>
        public const String frontRadiation = "frontRadiation";
        /// <summary>
        /// 汽车尾部红外对射
        /// </summary>
        public const String backRadiation = "backRadiation";
        /// <summary>
        /// 汽车前方道闸抬起
        /// </summary>
        public const String frontGateUpOut = "frontGateUpOut";
        /// <summary>
        /// 汽车前方道闸落下
        /// </summary>
        public const String frontGateDownOut = "frontGateDownOut";
        /// <summary>
        /// 汽车后方道闸抬起
        /// </summary>
        public const String backGateUpOut = "backGateUpOut";
        /// <summary>
        /// 汽车后方道闸落下
        /// </summary>
        public const String backGateDownOut = "backGateDownOut";
        /// <summary>
        /// 红灯输出
        /// </summary>
        public const String lightRedOut = "lightRedOut";
        /// <summary>
        /// 绿灯输出
        /// </summary>
        public const String lightGreenOut = "lightGreenOut";
        /// <summary>
        /// 衡器重量
        /// </summary>
        public const String carWeight = "carWeight";


        /// <summary>
        /// 写modbus输出ON
        /// </summary>
        public static string False = Boolean.FalseString;

        /// <summary>
        /// 写modbus输出OFF
        /// </summary>
        public static string True = Boolean.TrueString;

        /// <summary>
        ///道闸，红绿灯信号ON
        /// </summary>
        public const string signalON = "1";

        /// <summary>
        /// 道闸，红绿灯信号OFF
        /// </summary>
        public const string signalOFF = "0";








        public enum deviceState : int
        {

            INITIAL = 1,
            START = 2,
            STOP = 3,
            ERROR = 4

        }

        public enum modbusType : int
        {

            COIL_STATUS = 1,
            INPUT_STATUS = 2,
            HOLDING_REGISTER = 3,
            INPUT_REGISTER = 4

        }



        public enum STEP : int
        {
            IDLE = 1,
            CAR_IN = 2,
            CAR_ReadyWeight = 3,
            CAR_GetCarNo = 4,
            CAR_Weighting = 5,
            CAR_WeightFinish = 6,
            CAR_OUT = 7,
            CAR_WaitForParking = 8,
            CAR_WaitSample = 9,
            CAR_SampleConfirm = 10,
            CAR_Sampling = 11,
            CAR_SampleFinish = 12,
            CAR_InFinish = 13,
            CAR_OutFinish = 14,
            CAR_Finish = 15,
            CAR_GetCoalNo = 16,
            CAR_CheckCard = 17,
            CAR_ScanCarLength = 18,
            CAR_WaitScanCarLength = 19,
            CAR_SendSampleCommand = 20,
            CAR_WaitSelectStep = 21,
            CAR_GetMineCard = 22,

            CAR_WAITING_UNLOAD = 23,
            CAR_UNLOADING = 24,
            CAR_FINISH_UNLOAD = 25
        }

        public enum modbusPoint : int
        {
            groundSense = 1,
            inRadiation = 2,
            outRadiation = 3,
            frontRadiation = 4,
            backRadiation = 5,
            frontGateUpOut = 17,
            frontGateDownOut = 18,
            backGateUpOut = 19,
            backGateDownOut = 20,
            lightRedOut = 21,
            lightGreenOut = 22
        }

        //对Dictionary封装的方法
        public static string getDcValue(IDictionary<String, String> dc, String key)
        {
            if (dc.ContainsKey(key))
            {
                return dc[key];
            }
            else
            {
                return null;
            }
        }

        //0成功，其他为异常，通常 1异常，也可以定义其他的errorCode
        public static string getDcResCode(Dictionary<String, String> dc)
        {
            if (dc.ContainsKey(RES_CODE))
            {
                return dc[RES_CODE];
            }
            else
            {
                return null;
            }
        }

        public static string getDcResMsg(Dictionary<String, String> dc)
        {
            if (dc.ContainsKey(RES_MSG))
            {
                return dc[RES_MSG];
            }
            else
            {
                return null;
            }
        }

        public static Dictionary<String, String> putDcValue(ref Dictionary<String, String> dc, String key, String value)
        {
            if (dc.ContainsKey(key))
            {
                dc.Remove(key);

            }
            dc.Add(key, value);
            return dc;
        }

        public static Dictionary<String, String> putDicResCodeMsg(ref Dictionary<String, String> dc, String resCode, String resMsg)
        {
            if (dc.ContainsKey(RES_CODE))
            {
                dc.Remove(RES_CODE);

            }
            dc.Add(RES_CODE, resCode);

            if (dc.ContainsKey(RES_MSG))
            {
                dc.Remove(RES_MSG);

            }
            dc.Add(RES_MSG, resMsg);

            return dc;
        }

        public static Dictionary<String, String> createDictionary(String resCode, String resMsg)
        {
            Dictionary<String, String> dc = new Dictionary<String, String>();
            dc.Add(Common.Commons.RES_CODE, resCode);
            dc.Add(Common.Commons.RES_MSG, resMsg);
            return dc;
        }

        /**
         * 获取Json对象中的值
         */
        public static string getJsonValue(JObject josn, String key)
        {
            if (josn[key] != null)
            {
                return (string)josn[key];
            }
            else
            {
                return "";
            }
        }

        public static void putJsonResCodeMsg(ref JObject json, String code, String message)
        {
            if (json != null)
            {
                json.Remove(Commons.RES_CODE);
                json.Add(Commons.RES_CODE, code);

                json.Remove(Commons.RES_MSG);
                json.Add(Commons.RES_MSG, message);
            }
        }

        public static Boolean isNullStr(string str)
        {
            if (str == null)
            {
                return true;
            }
            if (str.Length <= 0)
            {
                return true;
            }
            return false;
        }

        public static void putDictionaryData(ref System.Collections.Concurrent.ConcurrentDictionary<string, string> dc, string key, string value)
        {
            if (dc.ContainsKey(key))
            {
                dc[key] = value;
            }
            else
            {
                dc.TryAdd(key, value);
            }
        }

        /// <summary>
        /// 将车牌号转换成中文数字,zhoul,20190418,解决语音库，车牌数字无法正确播报的问题
        /// </summary>
        /// <param name="orgCarNo"></param>
        /// <returns></returns>
        public static string changeCarNo(string orgCarNo)
        {
            try
            {
                int temp;
                string newValue = "";
                string[] upper = new string[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };


                foreach (char c in orgCarNo)
                {
                    temp = int.Parse(((byte)c).ToString());
                    if (temp >= 48 && temp <= 57)
                    {
                        newValue += upper[int.Parse(c.ToString())];

                    }
                    else
                    {
                        newValue += c.ToString();
                    }
                }
                return newValue;
            }
            catch (Exception e)
            {
                return orgCarNo;
            }
        }
    }

    public class TRANSMST
    {
        public string carId { get; set; }
        public string carType { get; set; }
        public string tradeType { get; set; }
        public string shipName { get; set; }
        public string billNo { get; set; }
        public string fixShipNo { get; set; }
        public string areaCode { get; set; }
    }
}
