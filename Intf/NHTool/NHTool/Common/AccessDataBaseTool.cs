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

///
namespace NHTool.Common
{
    public class AccessDataBaseTool
    {
        //需要安装 AccessDatabaseEngine.exe(64位)
        private string connStr;// connStr = @"Provider = Microsoft.Ace.OLEDB.12.0;Data Source = E:\TEST.accdb";
        public AccessDataBaseTool(string connStr)
        {
            this.connStr = connStr;
        }
        

        public OleDbConnection GetConn()
        {

            OleDbConnection tempconn = new OleDbConnection(connStr);

            MessageBox.Show(tempconn.DataSource);

            tempconn.Open();

            MessageBox.Show(tempconn.State.ToString());

            return (tempconn);

        }


        /*
        public static void Main(string[] args)
        {
            string cardId="abcdef";
            string weight="88.9";
            string deviceNo = "W2";
            OleDbParameter[] param = { new OleDbParameter("@cardId", cardId), new OleDbParameter("@weight", weight), new OleDbParameter("@deviceNo", deviceNo) };

            string sql = "insert into car_weight_local(card_id,weight,device_no,weight_time) values(@cardId,@weight,@deviceNo,Now())";
            //方法调用
            ExecuteNonQuery(sql, param);
        }*/




        /// <summary>

        /// 执行增加、删除、修改指令

        /// </summary>

        /// <param name="sql">增加、删除、修改的sql语句</param>

        /// <param name="param">sql语句的参数</param>

        /// <returns></returns>

        public int ExecuteNonQuery(string sql, params OleDbParameter[] param)

        {

            using (OleDbConnection conn = new OleDbConnection(connStr))

            {

                using (OleDbCommand cmd = new OleDbCommand(sql,conn))

                {

                    if (param != null)

                    {

                        cmd.Parameters.AddRange(param);

                    }

                    conn.Open();

                    return(cmd.ExecuteNonQuery());

                }

            }

        }

 

        /// <summary>

        /// 执行查询指令，获取返回的首行首列的值

        /// </summary>

        /// <param name="sql">查询sql语句</param>

        /// <param name="param">sql语句的参数</param>

        /// <returns></returns>

        public object ExecuteScalar(string sql, params OleDbParameter[] param)

        {

            using (OleDbConnection conn = new OleDbConnection(connStr))

            {

                using (OleDbCommand cmd = new OleDbCommand(sql, conn))

                {

                    if (param != null)

                    {

                        cmd.Parameters.AddRange(param);

                    }

                    conn.Open();

                    return (cmd.ExecuteScalar());

                }

            }

        }

 

        /// <summary>

        /// 执行查询指令，获取返回的datareader

        /// </summary>

        /// <param name="sql">查询sql语句</param>

        /// <param name="param">sql语句的参数</param>

        /// <returns></returns>

        public OleDbDataReader ExecuteReader(string sql, params OleDbParameter[] param)

        {

            OleDbConnection conn = new OleDbConnection(connStr);

            OleDbCommand cmd = conn.CreateCommand();

            cmd.CommandText = sql;

            cmd.CommandType = CommandType.Text;

            if (param != null)

            {

                cmd.Parameters.AddRange(param);

            }

            conn.Open();

            return (cmd.ExecuteReader(CommandBehavior.CloseConnection));
        }

 

        /// <summary>

        /// 执行查询指令，获取返回datatable

        /// </summary>

        /// <param name="sql">查询sql语句</param>

        /// <param name="param">sql语句的参数</param>

        /// <returns></returns>

        public DataTable ExecuteDatable(string sql, params OleDbParameter[] param)

        {

            using (OleDbConnection conn = new OleDbConnection(connStr))

            {

                using (OleDbCommand cmd = new OleDbCommand(sql, conn))

                {

                    if (param != null)

                    {

                        cmd.Parameters.AddRange(param);

                    }

                    DataTable dt = new DataTable();
                    OleDbDataAdapter sda = new OleDbDataAdapter(cmd);
                    sda.Fill(dt);
                    return (dt);
                }
            }
        }

    

    }
}