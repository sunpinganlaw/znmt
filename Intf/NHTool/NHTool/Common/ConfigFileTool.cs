using System;
using System.Collections.Generic;
using System.Text;
using System.Data.OleDb;
using System.Data;
using System.Collections;
using HaiGrang.Package.OpcNetApiChs.DaNet;
using System.Runtime.InteropServices;
using NHTool.Common;
using System.IO;

namespace NHTool
{
    public class ConfigTool
    {
        //=====================================================公共方法：读取ini文件，文件放在exe同级目录下的config中======================================================

        /// <summary>
        /// 获取本地配置文件接点信息
        /// </summary>
        /// <param name="section">上级接点</param>
        /// <param name="name">当前接点</param>
        /// <returns></returns>
        public static string getConfigFileValue(string section, string name)
        {
            String FileName = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase + "etc\\set.ini";
            StringBuilder temp = new StringBuilder(1000);
            ApiHelper.GetPrivateProfileString(section, name, "", temp, 1000, FileName);
            return temp.ToString();
        }


        /// <summary>
        /// 获取管控配置文件信息
        /// </summary>
        /// <param name="section"></param>
        /// <param name="name"></param>
        /// <param name="filename"></param>
        /// <returns></returns>
        public static string getConfigFileValue(string section, string name, string filename)
        {
            String FileName = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase + "INI\\" + filename + ".ini";
            StringBuilder temp = new StringBuilder(1000);
            ApiHelper.GetPrivateProfileString(section, name, "", temp, 1000, FileName);
            return temp.ToString();
        }
        /// <summary>
        /// 写本地配置文件ini
        /// </summary>
        /// <param name="section">上级节点</param>
        /// <param name="name">当前节点</param>
        /// <param name="itemvalue">Value</param>
        public static void WritePerConfigure(string section, string name, string itemvalue)
        {
            String FileName = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase + "etc\\set.ini";
            ApiHelper.WritePrivateProfileString(section, name, itemvalue, FileName);
        }
        public class ApiHelper
        {
            [DllImport("kernel32")]
            public static extern int GetPrivateProfileString(string section, string key, string def, StringBuilder retVal, int size, string filePath);

            [DllImport("kernel32")]
            public static extern long WritePrivateProfileString(string section, string key, string val, string filePath);
        }

        /// <summary>
        /// 获得Ini配置文件的值
        /// </summary>
        /// <param name="sectionname">节点名</param>
        /// <param name="key">key值</param>
        /// <returns>key值对应的Value</returns>
        public static string GetValueFromIni(string sectionname, string key)
        {
            string value = ConfigTool.getConfigFileValue(sectionname, key);
            return value;

        }

        //========== 公共方法：读取excel，返回Dictionary<String, Point> ，内含columns和rows两个key，类型分别为DataColumnCollection和DataRowCollection=======================

        //注意文件路径中不能有中文,就放在程序的当前文件夹下
        //TODO:都遍历一下， string tableName = schemaTable.Rows[0][2].ToString().Trim();
        //返回的Dictionary<String,Object>为：key=rows value=DataRowCollection ; key=columns, value=DataColumnCollection
        private static Dictionary<String, Object> getExcelRet(string fileName, String sheetName, string path = "")
        {
           
            Console.WriteLine(path + "\\" + fileName);
            string strConn = "Provider=Microsoft.Ace.OleDb.12.0;" + "data source=" + path + "\\" + fileName + ";Extended Properties='Excel 12.0; HDR=Yes; IMEX=1'";
            //string strConn = "Provider=Microsoft.Ace.OleDb.12.0;" + "data source=d:\\testKepServerConfig\\" + fileName + ";Extended Properties='Excel 12.0; HDR=Yes; IMEX=1'";
            Dictionary<String, Object> ret = new Dictionary<String, Object>();
            OleDbConnection excelConnction = null;
            string strExcel = "";
            OleDbDataAdapter excelCommand = null;
            DataSet dataSet = null;
            try
            {
                excelConnction = new OleDbConnection(strConn);
                excelConnction.Open();
                System.Data.DataTable schemaTable = excelConnction.GetOleDbSchemaTable(OleDbSchemaGuid.Tables, new Object[] { null, null, null, "TABLE" });
                //获取Excel中所有Sheet表的信息

                  sheetName = schemaTable.Rows[0][2].ToString().Trim();
                strExcel = "select * from [" + sheetName + "]";
                excelCommand = new OleDbDataAdapter(strExcel, strConn);
                dataSet = new System.Data.DataSet();
                excelCommand.Fill(dataSet, sheetName);
                ret.Add("rows", dataSet.Tables[sheetName].Rows);
                ret.Add("columns", dataSet.Tables[sheetName].Columns);

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            finally
            {
                if (excelCommand != null)
                {
                    excelCommand.Dispose();
                }
                if (dataSet != null)
                {
                    dataSet.Dispose();
                }
                if (excelConnction != null)
                {
                    excelConnction.Close();
                }
            }
            return ret;
        }

        private static String getExcelCellValue(DataRow dataRow, DataColumnCollection columns, int columnIndex)
        {
            Object obj = dataRow[columns[columnIndex]];
            if (obj != null)
            {
                return obj.ToString().Trim();
            }
            else
            {
                return "";
            }
        }


        /// <summary>
        /// 读txt文件
        /// </summary>
        /// <param name="path">文件路径</param>
        /// <returns>txt文件内容</returns>
        public static List<string> Read(string path)
        {
            StreamReader sr=null;
            try
            {
                sr = new StreamReader(path, Encoding.UTF8);
                List<string> fileBuf = new List<string>();
                string line;
                while (!sr.EndOfStream)
                {
                    line = sr.ReadLine();
                    fileBuf.Add(line);
                    Console.WriteLine(line.ToString());
                }
                sr.Close();//关闭读取流文件
                return fileBuf;
            }
            catch (Exception e)
            {
                sr.Close();//关闭读取流文件
                Console.WriteLine(DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "读取" + path + "文件出错：\r\n" + e.ToString());
                return null;
                //Console.WriteLine(e.Message);
            }
        }


        /// <summary>
        /// 写数据到txt
        /// </summary>
        /// <param name="path">写入的文件路劲</param>
        /// <param name="buff">写入的内容</param>
        public static void Write(string path, List<string> buff)
        {
            try
            {
                FileStream fs = new FileStream(path, FileMode.Create);
                StreamWriter sw = new StreamWriter(fs);
                foreach (string line in buff)
                {
                    //开始写入
                    sw.WriteLine(line);
                }
                //清空缓冲区
                sw.Flush();
                //关闭流
                sw.Close();
                fs.Close();
            }
            catch (Exception e)
            {
                throw e;
            }
        }


        //================================================================下面为具体的读取配置方法==========================================================================
        public static Dictionary<String, Point> initOpcPointsConfigExcel( string fileName, string sheetName, string path = "")
        {
            Dictionary<String, Point> pointsConfig = new Dictionary<String, Point>();
            Dictionary<String, Object> excelRet = getExcelRet(fileName, sheetName);
            try
            {
                if (excelRet.ContainsKey("rows") && excelRet.ContainsKey("columns"))
                {
                    DataRowCollection rows = (DataRowCollection)excelRet["rows"];
                    DataColumnCollection columns = (DataColumnCollection)excelRet["columns"];

                    for (int i = 0; i < rows.Count; i++)//注意与上面的MY_N_AIT要对上哦
                    {
                        DataRow dataRow = rows[i];
                        Point point = new Point();
                        point.GroupId = getExcelCellValue(dataRow, columns, 11);
                        point.TagName = getExcelCellValue(dataRow, columns, 0);//
                        point.Address = getExcelCellValue(dataRow, columns, 1);//
                        point.DataType = getExcelCellValue(dataRow, columns, 2);//
                        point.ScanRate = getExcelCellValue(dataRow, columns, 5);//
                        point.PrexPath = getExcelCellValue(dataRow, columns, 6);//
                        point.TotalTagName = point.PrexPath + "." + point.GroupId + "#" + point.TagName;
                        point.LogicDeviceId = getExcelCellValue(dataRow, columns, 8);
                        //存放的是最后寻址的key
                        point.LogicTagName = point.GroupId + "#" + point.LogicDeviceId + "_" + getExcelCellValue(dataRow, columns, 7);
                        point.ProtocolType = getExcelCellValue(dataRow, columns, 9);
                        point.UpdateFrequency = getExcelCellValue(dataRow, columns, 10);
                        if (point.UpdateFrequency == null || point.UpdateFrequency.Length < 1)
                        {
                            point.UpdateFrequency = "normal";
                        }
                        point.Description = getExcelCellValue(dataRow, columns, 21);
                        //Console.WriteLine(i + "row," + "0 column," + point.TagName + "," + point.Address + "," + point.DataType + "," + point.ScanRate + "," + point.LogicTagName + "," + point.LogicDeviceId + "," + point.Action + "," + point.UpdateFrequency + "," + point.Description + "," + point.TotalTagName);
                        pointsConfig.Remove(point.LogicTagName);
                        pointsConfig.Add(point.LogicTagName, point);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return pointsConfig;
        }


        public static List<TRANSMST> readTransmstExcel(string fileName, string sheetName, string path = "")
        {
            if (path=="")
            {
                path = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase;

            }
            List<TRANSMST> msgs = new List<TRANSMST> ();
            Dictionary<String, Object> excelRet = getExcelRet(fileName, sheetName,path);
            try
            {
                if (excelRet.ContainsKey("rows") && excelRet.ContainsKey("columns"))
                {
                    DataRowCollection rows = (DataRowCollection)excelRet["rows"];
                    DataColumnCollection columns = (DataColumnCollection)excelRet["columns"];

                    for (int i = 0; i < rows.Count; i++)//注意与上面的MY_N_AIT要对上哦
                    {
                        DataRow dataRow = rows[i];
                        TRANSMST msg = new TRANSMST();
                        msg.shipName = getExcelCellValue(dataRow, columns, 0);
                        msg.fixShipNo = getExcelCellValue(dataRow, columns, 1);
                        msg.carId = getExcelCellValue(dataRow, columns, 2);
                        msg.billNo = getExcelCellValue(dataRow, columns, 3);
                        if (getExcelCellValue(dataRow, columns, 4).Equals("内贸") && getExcelCellValue(dataRow, columns, 5).Equals("出口"))
                        {

                            msg.tradeType = "3";

                        }
                        if (getExcelCellValue(dataRow, columns, 4).Equals("内贸") && getExcelCellValue(dataRow, columns, 5).Equals("进口"))
                        {
                            msg.tradeType = "2";


                        }
                        if (getExcelCellValue(dataRow, columns, 4).Equals("外贸") && getExcelCellValue(dataRow, columns, 5).Equals("出口"))
                        {

                            msg.tradeType = "1";

                        }
                        if (getExcelCellValue(dataRow, columns, 4).Equals("外贸") && getExcelCellValue(dataRow, columns, 5).Equals("进口"))
                        {

                            msg.tradeType = "0";

                        }
                        msg.areaCode = getExcelCellValue(dataRow, columns, 6);
                        msgs.Add(msg);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return msgs;
        }

        public static List<TRANSMST> readTransmstTxt(string fileName, string sheetName, string path = "")
        {
            if (path == "")
            {
                path = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase;

            }
            List<TRANSMST> msgs = new List<TRANSMST>();

            try
            {
                List<string> strlist= Read(path + "\\" + fileName);
                if (strlist != null && strlist.Count > 0)
                {
                    foreach (var item in strlist)
                    {
                        string[] msgStr = item.Substring(1, item.Length - 3).Split(';');
                        TRANSMST msg = new TRANSMST();
                        msg.shipName = msgStr[0];
                        msg.fixShipNo = msgStr[1];
                        msg.carId = msgStr[2];
                        msg.billNo = msgStr[3];
                        if (msgStr[4].Equals("内贸") && msgStr[5].Equals("出口"))
                        {

                            msg.tradeType = "3";

                        }
                        if (msgStr[4].Equals("内贸") && msgStr[5].Equals("进口"))
                        {
                            msg.tradeType = "2";


                        }
                        if (msgStr[4].Equals("外贸") && msgStr[5].Equals("出口"))
                        {

                            msg.tradeType = "1";

                        }
                        if (msgStr[4].Equals("外贸") && msgStr[5].Equals("进口"))
                        {

                            msg.tradeType = "0";

                        }
                        msg.areaCode = msgStr[6];
                        msgs.Add(msg);

                    }



                }

            }
            catch (Exception ex)
            {

                Console.WriteLine(ex.Message);


            }



            return msgs;

        }



        public static Dictionary<String, String> initOpcCtlConfigExcel(string fileName, string sheetName)
        {
            Dictionary<String, Object> excelRet = getExcelRet(fileName, sheetName);
            Dictionary<String, String> ret = new Dictionary<String, String>();
            try
            {
                if (excelRet.ContainsKey("rows") && excelRet.ContainsKey("columns"))
                {
                    DataRowCollection rows = (DataRowCollection)excelRet["rows"];
                    DataColumnCollection columns = (DataColumnCollection)excelRet["columns"];

                    for (int i = 0; i < rows.Count; i++)//注意与上面的MY_N_AIT要对上哦
                    {
                        var key = getExcelCellValue(rows[i], columns, 0);
                        var value = getExcelCellValue(rows[i], columns, 1);
                        if (key != null && value != null)
                        {
                            ret.Remove(key);
                            ret.Add(key, value);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            return ret;
        }
    }


   
}
