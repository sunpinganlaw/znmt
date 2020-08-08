using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data.OracleClient;
using System.IO;
using Newtonsoft.Json.Linq;

namespace NHTool.Common
{
    public class DataBaseTool
    {

        private string ip = null;
        private string port = null;
        private string source = null;
        private string user = null;
        private string password = null;
        //其他配置信息
        private Dictionary<String, String> dataBase_ctlConfig = new Dictionary<String, String>();
        //----------------------------------------------------------------Oracle数据库操作-----------------------------------------
        /// <summary>
        /// 获得连接
        /// </summary>
        /// <returns></returns>
        /// 
        public DataBaseTool()
        {
            dataBase_ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            ip = dataBase_ctlConfig["ORA_IP"];
            port = dataBase_ctlConfig["ORA_PORT"];
            source = dataBase_ctlConfig["ORA_SOURCE"];
            user = dataBase_ctlConfig["ORA_USER"];
            password = dataBase_ctlConfig["ORA_PSW"];
        }

        //----------------------------------------------------------------Oracle数据库操作-----------------------------------------
        /// <summary>
        /// 获得连接
        /// </summary>
        /// <returns></returns>
        /// 
        public DataBaseTool(string ip,string port,string source, string user ,string password)
        {
            
            this.ip = ip;
            this.port = port;
            this.source = source;
            this.user = user;
            this.password = password;
        }

        private OracleConnection GetConn_Ora()
        {
            string connectstring_ora = "Data Source=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)" + "(" + "HOST=" + ip + ")" + "(" + "PORT=" + port + "))" + "(CONNECT_DATA=(SID=" + source + ")));" + "User Id=" + user + ";" + "Password=" + password + ";";
            OracleConnection conn = new OracleConnection(connectstring_ora);
            return conn;
        }















        
        /// <summary>
        /// 同步设备状态到Oracl数据库
        /// </summary>
        /// <param name="json">JSON数组</param>
        /// <returns></returns>
        public string record_SubDeviceState(string json)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "pkg_device.sync_subdevice_state";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_syncData", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[0].Value = json;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "1000")
                    {
                          
                     
                    }
                    else
                    {
                       
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                    }

                }
                catch (Exception ex)
                {
                  
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = "err";

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }

        
        /// <summary>
        /// 调用皮带秤入库过程（船运调度用)
        /// </summary>
        /// <param name="json"></param>
        /// <returns></returns>
        public string record_PCBarrelStateInfo(string json)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_SYNC_2_ZNRL.sync_pc_barrel";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_syncData", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[0].Value = json;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "0")
                    {

                        result = v_resultcode;
                        connection.Close();
                    }
                    else
                    {
                        result = v_resultcode;
                        connection.Close();
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                    }

                }
                catch (Exception ex)
                {
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = "error";

                }
            }


            return result;
        }

        public string get_PcCommandInfo(string deviceCode, out string cmdJsonStr)
        {
            string result = "";
            cmdJsonStr = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_GET_DATA_FUNCTIONS.get_machine_cmd_info";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_machineCode", OracleType.VarChar);
                    parm[1] = new OracleParameter("v_commandJson", OracleType.VarChar, 4000);
                    parm[2] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[0].Value = deviceCode;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        cmdJsonStr = parm[1].Value.ToString();
                        result = v_resultcode;
                        connection.Close();
                    }
                    else
                    {
                        result = v_resultcode;
                        connection.Close();
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                    }

                }
                catch (Exception ex)
                {
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = "error";

                }
            }
            return result;
        }


        /// <summary>
        /// 采样before
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_attachInfo">管控下发的采样信息</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public  void  cy_before_preProcess(string v_flowId, string v_cardId, out string v_attachInfo, out string v_resCode, out string v_resMsg)
        {
            v_attachInfo = "";
            v_resCode = "";
            v_resMsg = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.cy_before_preProcess";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_attachInfo", OracleType.VarChar, 4000);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    v_attachInfo = parm[2].Value.ToString();
                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {
                       
                       
                    }
                    else
                    {
                       
                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {
                   
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }
           
        }

        /// <summary>
        /// 采样after
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_inputInfo">传入的采样结果信息</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void cy_after_process(string v_flowId, string v_cardId, string v_inputInfo, out string v_resCode, out string v_resMsg)
        {
          
            v_resCode = "";
            v_resMsg = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.cy_after_process";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_inputInfo", OracleType.VarChar, 4000);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Input;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                    parm[2].Value = v_inputInfo;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                  
                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {

                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }


        /// <summary>
        /// 称重before
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_carId">车牌号</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void cz_before_preProcess(string v_flowId, string v_cardId, out string v_carId, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
            v_carId = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.cz_before_preProcess";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_carId", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                 
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    v_carId = parm[2].Value.ToString();
                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {

                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }



        /// <summary>
        /// 宣威称重before
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_carId">车牌号</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void cz_before_preProcess_xw(string v_flowId, string v_cardId, out string v_carId, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
            v_carId = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE.cz_before_preProcess";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_carId", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    v_carId = parm[2].Value.ToString();
                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {

                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }



       /// <summary>
        /// 称重after
       /// </summary>
       /// <param name="v_flowId"流程点</param>
       /// <param name="v_cardId">卡号</param>
       /// <param name="v_realQty">重量</param>
       /// <param name="v_resCode">返回代码</param>
       /// <param name="v_resMsg">返回信息</param>
        public void cz_after_process(string v_flowId, string v_cardId, string v_realQty, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
           
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.cz_after_process";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_realQty", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Input;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                    parm[2].Value = v_realQty;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                  
                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }



        /// <summary>
        /// 宣威称重after
        /// </summary>
        /// <param name="v_flowId"流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_realQty">重量</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void cz_after_process_xw(string v_flowId, string v_cardId, string v_realQty, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";

            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE.cz_after_process";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_realQty", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Input;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                    parm[2].Value = v_realQty;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }



    /// <summary>
    /// 回皮before
    /// </summary>
    /// <param name="v_flowId">流程点</param>
    /// <param name="v_cardId">卡号</param>
    /// <param name="v_mzQty">毛重</param>
    /// <param name="v_kdQty">扣顿</param>
    /// <param name="v_carId">车号</param>
    /// <param name="v_resCode">返回代码</param>
    /// <param name="v_resMsg">返回信息</param>
        public void hp_before_preProcess(string v_flowId, string v_cardId, out string v_mzQty, out string v_kdQty,out string v_carId, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
            v_mzQty = "";
            v_kdQty = "";
            v_carId = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.hp_before_preProcess";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[7];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_mzQty", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_kdQty", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_carId", OracleType.VarChar, 255);
                    parm[5] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[6] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;
                    parm[5].Direction = ParameterDirection.Output;
                    parm[6].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_mzQty = parm[2].Value.ToString();
                    v_kdQty = parm[3].Value.ToString();
                    v_carId = parm[4].Value.ToString();

                    v_resCode = parm[5].Value.ToString();
                    v_resMsg = parm[6].Value.ToString();
                    string v_resultcode = parm[5].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[6].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }




        /// <summary>
        /// 宣威回皮before
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_mzQty">毛重</param>
        /// <param name="v_kdQty">扣顿</param>
        /// <param name="v_carId">车号</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void hp_before_preProcess_xw(string v_flowId, string v_cardId, out string v_mzQty, out string v_kdQty, out string v_carId, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
            v_mzQty = "";
            v_kdQty = "";
            v_carId = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE.hp_before_preProcess";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[7];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_mzQty", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_kdQty", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_carId", OracleType.VarChar, 255);
                    parm[5] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[6] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;
                    parm[5].Direction = ParameterDirection.Output;
                    parm[6].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_mzQty = parm[2].Value.ToString();
                    v_kdQty = parm[3].Value.ToString();
                    v_carId = parm[4].Value.ToString();

                    v_resCode = parm[5].Value.ToString();
                    v_resMsg = parm[6].Value.ToString();
                    string v_resultcode = parm[5].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[6].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }




        /// <summary>
        /// 回皮after
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_realQty">重量</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void hp_after_process(string v_flowId, string v_cardId, string v_realQty, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
         
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.hp_after_process";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_realQty", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Input;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                    parm[2].Value = v_realQty;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }


        /// <summary>
        /// 宣威回皮after
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_realQty">重量</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void hp_after_process_xw(string v_flowId, string v_cardId, string v_realQty, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";

            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE.hp_after_process";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_realQty", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Input;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
                    parm[2].Value = v_realQty;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }



        /// <summary>
        /// 出厂Before
        /// </summary>
        /// <param name="v_flowId"></param>
        /// <param name="v_cardId"></param>
        /// <param name="v_carId"></param>
        /// <param name="v_resCode"></param>
        /// <param name="v_resMsg"></param>
        public void cc_before_preProcess(string v_flowId, string v_cardId, out string v_carId, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
            v_carId = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.cc_before_preProcess";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[5];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_carId", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[4] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[4].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;
               
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_carId = parm[2].Value.ToString();
                    v_resCode = parm[3].Value.ToString();
                    v_resMsg = parm[4].Value.ToString();
                    string v_resultcode = parm[3].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[4].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }


        }


        /// <summary>
        /// 出厂After
        /// </summary>
        /// <param name="v_flowId">流程点</param>
        /// <param name="v_cardId">卡号</param>
        /// <param name="v_resCode">返回代码</param>
        /// <param name="v_resMsg">返回信息</param>
        public void cc_after_process(string v_flowId, string v_cardId, out string v_resCode, out string v_resMsg)
        {
            v_resCode = "";
            v_resMsg = "";
         
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PK_CAR_SCHEDULE_NEW.cc_after_process";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_flowId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_cardId", OracleType.VarChar, 255);
                   
                    parm[2] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                 
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;

                    parm[0].Value = v_flowId;
                    parm[1].Value = v_cardId;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                  
                    v_resCode = parm[2].Value.ToString();
                    v_resMsg = parm[3].Value.ToString();
                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "1000")
                    {

                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }


        public void sendDCSCmd(string v_json_in_clob, out string v_json_out_clob, out string v_resCode, out string v_resMsg)
        {

            v_resCode = "";
            v_resMsg = "";
            v_json_out_clob = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "pkg_device.sendDCSCmd";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_json_in_clob", OracleType.VarChar,4000);
                    parm[1] = new OracleParameter("v_json_out_clob", OracleType.Clob);

                    parm[2] = new OracleParameter("v_res_code", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_res_msg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;

                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[0].Value = v_json_in_clob;
                 
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                  
                    OracleLob tempLob = (OracleLob)parm[1].Value;

                    StreamReader streamreader = new StreamReader(tempLob, Encoding.Unicode);
                    char[] cbuffer = new char[10000];
                    int actual = 0;
                    while ((actual = streamreader.Read(cbuffer, 0, cbuffer.Length)) > 0)
                    {
                        v_json_out_clob = new string(cbuffer, 0, actual);
                    }

                    v_json_out_clob=v_json_out_clob.Replace("\n", "").Replace("\t", "").Replace("\r", "");
                    v_resCode = parm[2].Value.ToString();
                    v_resMsg = parm[3].Value.ToString();
                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "1000")
                    {
                      
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }

        }

        public bool qryCarForecastInfoList(ref JArray sqlRest)
        {
            Boolean excRes = false;
            OracleConnection connection = null;
            try
            {
                using (connection = GetConn_Ora())
                {
                    string searchsql = "select distinct" +
                                        "(select c.coal_name from coal_type c where c.coal_no = a.coal_no and rownum < 2) coal_name," +
                                        "(select c.mine_name from coal_mine c where c.mine_no = a.mine_no and rownum < 2) mine_name," +
                                        "(select vn.ven_name from vendor_info vn where vn.ven_no = a.ven_no and rownum < 2) ven_name," +
                                        "(select cc.carrier_name from carrier_info cc where cc.carrier_no = a.carrier_no and rownum < 2) carrier_name," +
                                        "a.coal_no,a.ven_no,a.carrier_no,a.mine_no,forecast_id " +
                                        "from car_forecast_info a " +
                                        "where sysdate between a.start_date and a.end_date " +
                                        "and a.is_valid = 1  and a.is_waste = 0 " +
                                        "order by coal_name,mine_name,ven_name";
                    OracleCommand cmd = new OracleCommand(searchsql, connection);
                    cmd.CommandType = CommandType.Text;
                    //大容量数据，可以进行修改，使用DataSet和DataAdapter
                    DataSet ds = new DataSet();
                    OracleDataAdapter da = new OracleDataAdapter(cmd);
                    da.SelectCommand = cmd;
                    da.Fill(ds);
                    DataTable dt = new DataTable();
                    if (ds != null && ds.Tables.Count > 0)
                        dt = ds.Tables[0];
                    if (dt != null && dt.Rows.Count > 0)
                    {
                        foreach (DataRow row in dt.Rows)
                        {
                            JObject tempJobject = new JObject();
                            tempJobject.Add("forecast_id", row["forecast_id"].ToString());
                            tempJobject.Add("coal_name", row["coal_name"].ToString());
                            tempJobject.Add("mine_name", row["mine_name"].ToString());
                            tempJobject.Add("ven_name", row["ven_name"].ToString());
                            tempJobject.Add("carrier_name", row["carrier_name"].ToString());
                            tempJobject.Add("coal_no", row["coal_no"].ToString());
                            tempJobject.Add("ven_no", row["ven_no"].ToString());
                            tempJobject.Add("carrier_no", row["carrier_no"].ToString());
                            tempJobject.Add("mine_no", row["mine_no"].ToString());
                            sqlRest.Add(tempJobject);
                        }
                    }
                    excRes = true;
                    connection.Close();
                }
            }
            catch (Exception ex)
            {
                string myStr = ex.Message;
                LogTool.WriteLog(typeof(DataBaseTool), ex.Message);
            }
            finally
            {
                connection.Close();
            }
            return excRes;
        }

        public void sendWTQCCYJCmd(string v_json_in_clob,out string v_json_out_str, out string v_resCode, out string v_resMsg)
        {
            v_json_out_str = "";
            v_resCode = "";
            v_resMsg = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "pkg_device.sendWTQCCYJCmd";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_json_in_clob", OracleType.Clob);
                    parm[1] = new OracleParameter("v_json_out_str", OracleType.VarChar, 4000);
                    parm[2] = new OracleParameter("v_res_code", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_res_msg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                  
                    parm[0].Value = v_json_in_clob;
                   
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    v_resCode = parm[2].Value.ToString();
                    v_resMsg = parm[3].Value.ToString();
                    v_json_out_str = parm[1].Value.ToString();
                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "1000")
                    {


                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                    v_resMsg = "err";
                }
                finally
                {

                    connection.Close();
                }
            }
        }
        public bool qryCarNoByCardId(string cardId, out string carId)
        {
            Boolean excRes = false;
            OracleConnection connection = null;
            carId = "1";
            try
            {
                using (connection = GetConn_Ora())
                {
                    string searchsql = "select a.car_id from rlcarmst a " +
                                        "where a.card_id =  '" + cardId + "' " +
                                          "and not exists (select * from rlrecordmstqy z " +
                                                          "where z.card_id = '" + cardId + "' " +
                                                            "and z.net_qty is null and z.record_dtm >= trunc(sysdate)-2) " +
                                          "and a.valid_sta = '1' and rownum <2";

                    OracleCommand cmd = new OracleCommand(searchsql, connection);
                    cmd.CommandType = CommandType.Text;
                    //大容量数据，可以进行修改，使用DataSet和DataAdapter
                    DataSet ds = new DataSet();
                    OracleDataAdapter da = new OracleDataAdapter(cmd);
                    da.SelectCommand = cmd;
                    da.Fill(ds);
                    DataTable dt = new DataTable();
                    if (ds != null && ds.Tables.Count > 0)
                        dt = ds.Tables[0];
                    if (dt != null && dt.Rows.Count > 0)
                    {
                        carId = dt.Rows[0]["CAR_ID"].ToString();
                        excRes = true;
                    }
                    connection.Close();
                }
            }
            catch (Exception ex)
            {
                string myStr = ex.Message;
                LogTool.WriteLog(typeof(DataBaseTool), ex.Message);
            }
            finally
            {
                connection.Close();
            }
            return excRes;
        }

        public Boolean saveCarRecord(string carRecordInfo)
        {
            Boolean result = false;
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "pk_register.p_car_add_transRec";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_jsonString", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[0].Value = carRecordInfo;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[1].Value.ToString().Trim();
                    if (v_resultcode.Equals("0"))
                    {
                        result = true;
                        connection.Close();
                    }
                    else
                    {
                        connection.Close();
                        string aa = parm[2].Value.ToString();
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                    }

                }
                catch (Exception ex)
                {
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                }
                finally
                {
                    connection.Close();
                }
            }
            return result;
        }


        /// <summary>
        /// SCADA数据中心从数据库加载业务需要用到的测点
        /// </summary>
        /// <returns></returns>
        public  Dictionary<String, Point> point_cfg()
        {
            Dictionary<String, Point> pointsConfig = new Dictionary<String, Point>();

            using (OracleConnection connection = GetConn_Ora())
            {
              
                try
                {
                    string searchsql = "select * from point_cfg where status='0'";

                    OracleCommand cmd = new OracleCommand(searchsql, connection);
                    cmd.CommandType = CommandType.Text;
                    //大容量数据，可以进行修改，使用DataSet和DataAdapter
                    DataSet ds = new DataSet();
                    OracleDataAdapter da = new OracleDataAdapter(cmd);
                    da.SelectCommand = cmd;
                    da.Fill(ds);
                    DataTable dt = new DataTable();
                    if (ds != null && ds.Tables.Count > 0)
                        dt = ds.Tables[0];
                    if (dt != null && dt.Rows.Count > 0)
                    {
                        for (int i = 0; i < dt.Rows.Count; i++)
                        {


                            Point item = new Point();
                            item.TotalTagName = dt.Rows[i]["POINT_TAG"].ToString().Trim();
                            if(dt.Rows[i]["TO_DB"].ToString().Trim().Equals("Y"))
                            {

                                item.ProtocolType = "DB";
                            }
                            pointsConfig.Add(item.TotalTagName, item);
                        }
                    }
                    connection.Close();
                    return pointsConfig;
                }
                catch (Exception ex)
                {
                    return null;
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    
                }
                finally
                {
                 
                    connection.Close();
                }
            }
            
        }

        /// <summary>
        /// 测点入库：调用PKG_POINT.POINT_2_DB，将一部分设备实时状态数据入库到device_current_status表。
        /// </summary>
        /// <param name="json">JSON数组</param>
        /// <returns></returns>
        public string point_2_db(string json)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PKG_POINT.POINT_2_DB";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_pointInfo", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[0].Value = json;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }

        /// <summary>
        /// 业务数据提取为测点：调用PKG_POINT.DB_2_POINT，提取部分业务数据作为测点写入kepserver模拟器，由iFix关联这部分测点显示在组态界面。
        /// </summary>
        /// <param name="v_pointInfo">JSON数组</param>
        /// <returns></returns>
        public string db_2_point(out string v_pointInfo)
        {           
            string result = "";
            v_pointInfo = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PKG_POINT.DB_2_POINT";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_pointInfo", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Output;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    OracleLob tempLob = (OracleLob)parm[0].Value;
                    StreamReader streamreader = new StreamReader(tempLob, Encoding.Unicode);
                    char[] cbuffer = new char[10000];
                    int actual = 0;
                    while ((actual = streamreader.Read(cbuffer, 0, cbuffer.Length)) > 0)
                    {
                        v_pointInfo = new string(cbuffer, 0, actual);

                    }
                    v_pointInfo = v_pointInfo.Replace("\n", "").Replace("\t", "").Replace("\r", "");
                    v_pointInfo = System.Text.RegularExpressions.Regex.Unescape(v_pointInfo);

                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }

        /// <summary>
        /// 提取数据库发出的命令：调用PKG_POINT.DBCMD_2_POINT，从数据库提取machin_cmd_info表触发发起的命令。
        /// </summary>
        /// <param name="v_CMDInfo">JSON数组</param>
        /// <returns></returns>
        public string dbcmd_2_point(out string v_CMDInfo,out string v_deviceInfo)
        {
            string result = "";
            v_CMDInfo = "";
            v_deviceInfo = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PKG_POINT.DBCMD_2_POINT";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_CMDInfo", OracleType.Clob);
                    parm[1] = new OracleParameter("v_deviceInfo", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);
               

                    parm[0].Direction = ParameterDirection.Output;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    OracleLob tempLob = (OracleLob)parm[0].Value;
                    StreamReader streamreader = new StreamReader(tempLob, Encoding.Unicode);
                    char[] cbuffer = new char[10000];
                    int actual = 0;
                    while ((actual = streamreader.Read(cbuffer, 0, cbuffer.Length)) > 0)
                    {
                        v_CMDInfo = new string(cbuffer, 0, actual);
                    }
                    v_CMDInfo = v_CMDInfo.Replace("\n", "").Replace("\t", "").Replace("\r", "");

                    v_deviceInfo = parm[1].Value.ToString().Replace("\n", "").Replace("\t", "").Replace("\r", "");

                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[3].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[3].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }

        /// <summary>
        /// 界面发起的入库命令：调用PKG_POINT.CMD_2_DB，将界面发起的命令入库到machin_cmd_info表。
        /// </summary>
        /// <param name="json">JSON数组</param>
        /// <returns></returns>
        public string cmd_2_db(string json)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PKG_POINT.CMD_2_DB";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_CMDInfo", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[0].Value = json;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }

        /// <summary>
        /// 命令回写：调用PKG_POINT.DBCMD_WRITEBACK，更新命令执行结果到machin_cmd_info表。
        /// </summary>
        /// <param name="v_CMDRecId">命令ID</param>
        /// <param name="v_status">执行结果状态</param>
        /// <returns></returns>
        public string dbcmd_writeback(string v_CMDRecId, string v_status)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PKG_POINT.DBCMD_WRITEBACK";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_CMDRecId", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_status", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[0].Value = v_CMDRecId;
                    parm[1].Value = v_status;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[3].Value.ToString());
                        result = parm[3].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                        result = parm[3].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }



        /// <summary>
        /// 数据中心上传采样机集样罐信息，由启动和卸样命令触发
        /// </summary>
        /// <param name="v_deviceInfo">设备信息</param>
        /// <param name="v_subDeviceInfo"></param>
        /// <returns></returns>
        public string device_info_2_db(string v_deviceInfo, string v_subDeviceInfo)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "PKG_POINT.DEVICE_INFO_2_DB";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_deviceInfo", OracleType.VarChar);
                    parm[1] = new OracleParameter("v_subDeviceInfo", OracleType.Clob);
                    parm[2] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Input;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;

                    parm[0].Value = v_deviceInfo;
                    parm[1].Value = v_subDeviceInfo;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[2].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[3].Value.ToString());
                        result = parm[3].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[3].Value.ToString());
                        result = parm[3].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }




        /// <summary>
        /// SCADA数据中心从数据库加载采样机或者制样机需要的采制样的结果信息的点位
        /// </summary>
        /// <returns></returns>
        public Dictionary<String, List<string>> GetDeviceInfo()
        {
            Dictionary<String, List<string>> DeviceInfoConfig = new Dictionary<String, List<string>>();


            using (OracleConnection connection = GetConn_Ora())
            {

                try
                {
                    string searchsql = "select * from point_cfg where rela_device is not null";

                    OracleCommand cmd = new OracleCommand(searchsql, connection);
                    cmd.CommandType = CommandType.Text;
                    //大容量数据，可以进行修改，使用DataSet和DataAdapter
                    DataSet ds = new DataSet();
                    OracleDataAdapter da = new OracleDataAdapter(cmd);
                    da.SelectCommand = cmd;
                    da.Fill(ds);
                    DataTable dt = new DataTable();
                    if (ds != null && ds.Tables.Count > 0)
                        dt = ds.Tables[0];
                    if (dt != null && dt.Rows.Count > 0)
                    {
                        for (int i = 0; i < dt.Rows.Count; i++)
                        {

                            if (DeviceInfoConfig.ContainsKey(dt.Rows[i]["RELA_DEVICE"].ToString()))
                            {
                                DeviceInfoConfig[dt.Rows[i]["RELA_DEVICE"].ToString()].Add((dt.Rows[i]["POINT_TAG"]).ToString());


                            }
                            else
                            {
                                List<string> info = new List<string>();
                                
                                DeviceInfoConfig.Add((dt.Rows[i]["RELA_DEVICE"].ToString()),info);
                                DeviceInfoConfig[dt.Rows[i]["RELA_DEVICE"].ToString()].Add((dt.Rows[i]["POINT_TAG"]).ToString());
                            }
                           
                        }
                    }
                    connection.Close();
                    return DeviceInfoConfig;
                }
                catch (Exception ex)
                {
                    return null;
                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());

                }
                finally
                {

                    connection.Close();
                }
            }

        }


        /// <summary>
        ///  宣威临时应急用的在入厂出显示矿名
        /// </summary>
        /// <returns></returns>
        public string showMineNameXW()
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "GET_LAST_MINENAME";
                    connection.Open();

                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("v_mineName", OracleType.VarChar, 255);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Output;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;

                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();

                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[0].Value.ToString());
                        result = parm[0].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();
                }
            }

            return result;
        }

        /// <summary>
        /// 测点入库：调用PKG_POINT.POINT_2_DB，将一部分设备实时状态数据入库到device_current_status表。
        /// </summary>
        /// <param name="json">JSON数组</param>
        /// <returns></returns>
        public string msg_2_db(string json)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "BUSINESS_GROUP_INTF_PCK.add_TransConta";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[3];
                    parm[0] = new OracleParameter("msgString", OracleType.Clob);
                    parm[1] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[2] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[0].Value = json;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    string v_resultcode = parm[1].Value.ToString();
                    if (v_resultcode == "0")
                    {
                        LogTool.WriteLogDebug(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }
                    else
                    {
                        LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                        result = parm[2].Value.ToString();
                    }

                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }





        /// <summary>
        /// 测点入库：调用PKG_POINT.POINT_2_DB，将一部分设备实时状态数据入库到device_current_status表。
        /// </summary>
        /// <param name="json">JSON数组</param>
        /// <returns></returns>
        public string msg_2_test(string json)
        {
            string result = "";
            using (OracleConnection connection = GetConn_Ora())
            {
                OracleCommand cmd = new OracleCommand();
                try
                {
                    cmd.Connection = connection;
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "business_group_intf_pck_spa.P_PROCESS_0X21";
                    connection.Open();
                    OracleParameter[] parm = new OracleParameter[4];
                    parm[0] = new OracleParameter("v_dataInfo", OracleType.VarChar);
                    parm[1] = new OracleParameter("v_cmdInfo", OracleType.VarChar, 3000);
                    parm[2] = new OracleParameter("v_resCode", OracleType.VarChar, 255);
                    parm[3] = new OracleParameter("v_resMsg", OracleType.VarChar, 255);

                    parm[0].Direction = ParameterDirection.Input;
                    parm[1].Direction = ParameterDirection.Output;
                    parm[2].Direction = ParameterDirection.Output;
                    parm[3].Direction = ParameterDirection.Output;
                    parm[0].Value = json;
                    for (int i = 0; i < parm.Length; i++)
                    {
                        cmd.Parameters.Add(parm[i]);
                    }
                    cmd.ExecuteNonQuery();
                    //string v_resultcode = parm[1].Value.ToString();
                    //if (v_resultcode == "0")
                    //{
                    //    LogTool.WriteLogDebug(typeof(DataBaseTool), parm[2].Value.ToString());
                    //    result = parm[2].Value.ToString();
                    //}
                    //else
                    //{
                    //    LogTool.WriteLog(typeof(DataBaseTool), parm[2].Value.ToString());
                    //    result = parm[2].Value.ToString();
                    //}
                    result = parm[1].Value.ToString();
                }
                catch (Exception ex)
                {

                    LogTool.WriteLog(typeof(DataBaseTool), ex.ToString());
                    result = ex.ToString();

                }
                finally
                {
                    connection.Close();

                }
            }


            return result;
        }



    }
}
