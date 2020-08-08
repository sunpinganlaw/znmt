using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHTool.Common;
using Newtonsoft.Json.Linq;
using System.Security.Cryptography;
using Newtonsoft.Json;

/********************************************
* create by dafeige  20190312****************
1.使用语音之前需要设置登录语音平台的的
 * 服务器IP地址iNBS_serverIp；
 * 本机的IP地址local_Ip；
 * 服务器的ID编码zoneId（登录语音服务器管理服务平台查看）
 * 登录用户名logoName：admin(默认)；
 * 密码logoPwd(admin[使用MD5加密])；
 * 直接要设置好，各个语音播报节点的Id，初始化termAddrList
2.直接调用iNBS_BroadCastContent(string str)函数实现语音播报
********************************************/
namespace NHTool.Device.Radio
{
    
    public class INBSVoiceDevice
    {
        private string iNBS_serverIp = "";
        private string local_Ip = "";
        private string accessToken = "";
        private string voiceTaskId = "";
        private string logoName = "";
        private string logoPwd = "";
        private string zoneId = "";
        private string flowId = "";
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        private Dictionary<string, string> termAddrList = new Dictionary<string, string>();

        public INBSVoiceDevice()
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            flowId = ctlConfig["currentFlowId"];
            iNBS_serverIp = ctlConfig["INBS_SERVERIP"];
            local_Ip = ctlConfig["INBS_LOCALIP"];
            logoName = ctlConfig["INBS_LOGO_NAME"];
            logoPwd = ctlConfig["INBS_LOGO_PWD"];
            zoneId = ctlConfig["INBS_ZONE_ID"];
            termAddrList = JsonConvert.DeserializeObject<Dictionary<string, string>>(ctlConfig["INBS_TERM_LIST"]);
        }

 
        public INBSVoiceDevice(string deviceNo)
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            flowId = ctlConfig["currentFlowId"];
            iNBS_serverIp = ctlConfig["INBS_SERVERIP"];
            local_Ip = ctlConfig["INBS_LOCALIP" + deviceNo];
            logoName = ctlConfig["INBS_LOGO_NAME"];
            logoPwd = ctlConfig["INBS_LOGO_PWD"];
            zoneId = ctlConfig["INBS_ZONE_ID"];
            termAddrList = JsonConvert.DeserializeObject<Dictionary<string, string>>(ctlConfig["INBS_TERM_LIST"]);
        }

 
        private bool BroadCastStrTask(string sendText, JArray addrCodeList)
        {
            bool ret = false;
            string httpCallBackStr = "";
            string taskUrl = "http://" + iNBS_serverIp + "/api/REST-API/startPaTts.do?accessToken=" + accessToken;
            HttpTool httpToolSend = new HttpTool();
            JObject txtBody = new JObject();
            txtBody.Add("name", "task-1");
            txtBody.Add("outVol", "8");
            txtBody.Add("priority", "1");
            txtBody.Add("text", sendText);
            txtBody.Add("repeatTimes", "1");
            txtBody.Add("termList", addrCodeList);
            httpCallBackStr = httpToolSend.sendHttpMsg(Convert.ToString(txtBody), taskUrl);
            if (!httpCallBackStr.Equals(""))
            {
                httpCallBackStr = httpCallBackStr.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "").Replace("\\", "");
                JObject retJObject = (JObject)JsonConvert.DeserializeObject(httpCallBackStr);
                string retCode = Commons.getJsonValue(retJObject, "retCode");
                if (retCode.Equals("0"))
                {
                    ret = true;
                    voiceTaskId = Commons.getJsonValue(retJObject, "taskId");
                }
            }
            return ret;
        }

        private bool EndBroadCastStrTask()
        {
            bool ret = false;
            if (!voiceTaskId.Equals(""))
            {
                string httpCallBackStr = "";
                string taskUrl = "http://" + iNBS_serverIp + "/api/REST-API/endPa.do?accessToken=" + accessToken;
                HttpTool httpToolSend = new HttpTool();
                JObject txtBody = new JObject();
                txtBody.Add("taskId", voiceTaskId);
                httpCallBackStr = httpToolSend.sendHttpMsg(Convert.ToString(txtBody), taskUrl);
                if (!httpCallBackStr.Equals(""))
                {
                    httpCallBackStr = httpCallBackStr.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "").Replace("\\", "");
                    JObject retJObject = (JObject)JsonConvert.DeserializeObject(httpCallBackStr);
                    string retCode = Commons.getJsonValue(retJObject, "retCode");
                    if (retCode.Equals("0"))
                    {
                        voiceTaskId = "";
                        ret = true;
                    }
                }
            }
            return ret;
        }

        private bool logoINBSServer(string logoName,string logoPwd)
        {
            bool ret = false;
            string httpCallBackStr = "";
            HttpTool httpToolSend = new HttpTool();
            string logoUrl = "http://" + iNBS_serverIp + "/api/REST-API/login.do";
            string retUrl = "http://" + local_Ip + "/REST/";
            JObject txtBody = new JObject();
            txtBody.Add("id", logoName);
            txtBody.Add("secret", "21232f297a57a5a743894a0e4a801fc3" /*getMD5passwordString(logoPwd)*/);
            txtBody.Add("callbackUrl", retUrl);
            httpCallBackStr = httpToolSend.sendHttpMsg(Convert.ToString(txtBody), logoUrl);
            if (!httpCallBackStr.Equals(""))
            {
                httpCallBackStr = httpCallBackStr.Replace("\n", "").Replace(" ", "").Replace("\t", "").Replace("\r", "").Replace("\\", "");
                JObject retJObject = (JObject)JsonConvert.DeserializeObject(httpCallBackStr);
                string retCode = Commons.getJsonValue(retJObject, "retCode");
                if (retCode.Equals("0"))
                {
                    ret = true;
                    accessToken = Commons.getJsonValue(retJObject, "accessToken");
                }
            }
            return ret;
        }

        public static string getMD5passwordString(string orgPwd)
        {
            string cl = orgPwd;
            string pwd = "";
            MD5 md5 = MD5.Create();//实例化一个md5对像
            // 加密后是一个字节类型的数组，这里要注意编码UTF8/Unicode等的选择　
            byte[] s = md5.ComputeHash(Encoding.UTF8.GetBytes(cl));
            // 通过使用循环，将字节类型的数组转换为字符串，此字符串是常规字符格式化所得
            for (int i = 0; i < s.Length; i++)
            {
                // 将得到的字符串使用十六进制类型格式。格式后的字符是小写的字母，如果使用大写（X）则格式后的字符是大写字符 

                pwd = pwd + s[i].ToString("X");

            }
            return pwd.ToLower();
        }

        public bool iNBS_BroadCastContent(string str)
        {
            bool excuRes = false;
            if (logoINBSServer(logoName, logoPwd))
            {
                JArray addrList = new JArray();
                JObject singleAddr = new JObject();
                singleAddr.Add("zoneId", zoneId);
                singleAddr.Add("termDN", termAddrList[flowId]);
                addrList.Add(singleAddr);
                if (BroadCastStrTask(str, addrList))
                {
                    if (EndBroadCastStrTask())
                    {
                        excuRes = true;
                    }
                }
            }
            return excuRes;
        }
    }
}

