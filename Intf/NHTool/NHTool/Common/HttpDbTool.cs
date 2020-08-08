using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json.Linq;
using System.Windows.Forms;

namespace NHTool.Common
{
    /**   
      * 功能    : 通过http接口,访问管控数据库的存储过程或查询config_sql中语句。
      *           通过两个方法直接调用，返回均为JObject，同java中的结构相同。
      *           HttpDbTool httpDbTool = new HttpDbTool();
      *           调用存储过程，httpDbTool.invokeProc  
      *           调用查询语句，httpDbTool.invokeQry
      * 创建人  : wz 2018-11-02  
      * 修改记录:     
      */
    public class HttpDbTool
    {
        //配置文件
        private static Dictionary<String, String> ctlConfig = new Dictionary<String, String>();
        //调用管控服务的url地址
        private static String httpDbSendUrl = null;
        //http调用对象，每个实例一个，不是static
        private HttpTool httpToolSend = null;

        static HttpDbTool()
        {
            //初始化配置，获得httpDbSendUrl变量
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            if (ctlConfig.ContainsKey("httpDbSendUrl"))
            {
                httpDbSendUrl = ctlConfig["httpDbSendUrl"];
            }
            else
            {
                LogTool.WriteLogInfo(typeof(HttpDbTool), "初始化类 HttpDbTool 时，excel中参数httpDbSendUrl未正确配置！");
            }

        }

        public HttpDbTool()
        {

            if (httpToolSend == null)
            {
                httpToolSend = new HttpTool();
            }
        }

        /**
         * 基础调用
         */
        public JObject invokeDb(JObject param)
        {
            LogTool.WriteLog(typeof(HttpDbTool), "请求参数：" + param.ToString());
            string ret = httpToolSend.sendHttpMsg(param.ToString(), httpDbSendUrl);
            LogTool.WriteLog(typeof(HttpDbTool), "数据库过程调用返回：" + ret);
            if (ret != null)
            {
                return JObject.Parse(ret);
            }
            else
            {
                return new JObject();
            }
        }

        public String invokeDbRetStr(JObject param)
        {
            string ret = httpToolSend.sendHttpMsg(param.ToString(), httpDbSendUrl);
            if (ret != null)
            {
                return ret;
            }
            else
            {
                return "{}";
            }
        }

        /**
         * 调用管控中的存储过程
         * @param invokePkgName  调用存储过程名称
         * @param dataJson       调用的入参
         * @return               JObject（key=Commons.RES_CODE "0"成功，"1"失败 , key=Commons.RES_MSG:调用返回信息）
         */
        public JObject invokeProc(String invokePkgName, JObject reqParam)
        {
            //public部分，固定，只要改下调用存储过程的名称
            JObject publicPart = new JObject();

            //先清理，防止重复调用时候报错
            reqParam.Remove("InvokePkgName");
            reqParam.Add("InvokePkgName", invokePkgName);

            JObject ret = invokeDb(reqParam);

            return ret;
        }

        public String invokeProcRetStr(String invokePkgName, JObject reqParam)
        {
            //public部分，固定，只要改下调用存储过程的名称
            JObject publicPart = new JObject();
            reqParam.Add("InvokePkgName", invokePkgName);
            //reqParam.Add("type", "complexPrc");
            //reqParam.Add("public", publicPart);

            return invokeDbRetStr(reqParam);
        }

        /**
         * 调用管控中的查询语句
         * @param businessCode  调用查询语句在config_sql中的sql_key
         * @param dataJson      调用的入参
         * @return              JObject（key=Commons.RES_CODE "0"成功，"1"失败 , key=Commons.RES_MSG:调用返回信息）
         */
        public JObject invokeQry(String businessCode, JObject dataJson)
        {
            dataJson.Add("businessCode", businessCode);
            return invokeDb(dataJson);
        }

        //测试，演示使用
        public void test()
        {
            JObject dataJson = new JObject();
            dataJson.Add("begDate", "2018-11-01");
            dataJson.Add("endDate", "2018-11-02");
            //JObject ret = invokeQry("ship.qryRelayPlan", dataJson);
            JObject ret = invokeQry("user.getUsers", dataJson);
            String resCode = (String)ret[Commons.RES_CODE];
            if ("0".Equals(resCode))
            {
                MessageBox.Show("成功：" + ret.ToString());
            }
            else
            {
                MessageBox.Show("失败：" + ret.ToString());
            }
        }
    }
}