using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.RFID.Net;
using System.Windows.Forms;
using NHTool.Common;
using Newtonsoft.Json.Linq;
using System.Threading;

namespace NHTool.Device.SXLYRfid
{
    public class SxlyRfidDevice
    {
        public delegate void SXLY_RFID_READER_EVENT(string retStr, string dataType);
        public event SXLY_RFID_READER_EVENT Sxly_Rfid_Reader;

        public JObject areaTypeList = new JObject();
        private string deviceIp = "";
        private UInt16 devicePort = 0;
        public Dictionary<string, string> ctlConfig = new Dictionary<string, string>();
        public RFIDNetReader readerObj = new RFIDNetReader();

        public SxlyRfidDevice()
        {
            ctlConfig = ConfigTool.initOpcCtlConfigExcel("NHConfigs.xls", "ctrlConfig");
            deviceIp = ctlConfig["SXLYRfidIP"];
            devicePort = (UInt16)Convert.ToUInt32(ctlConfig["SXLYRfidPort"]);

            areaTypeList.Add("保留区", "0");
            areaTypeList.Add("EPC", "1");
            areaTypeList.Add("TID", "3");
            areaTypeList.Add("用户区", "3");
        }

        public Boolean ConnectDevice()
        {
            Boolean connectResult = false;
            if (CheckEquipNumber() == false)
            {
                return false;
            }
            try
            {
                connectResult = readerObj.ConnectDevice(deviceIp, devicePort);
            }
            catch (FormatException ex)
            {
                MessageBox.Show("连接失败，请检查是否已经和RFID设备连接：" + ex.Message);
            }
            catch (System.Net.Sockets.SocketException ex)
            {
                MessageBox.Show("连接失败，请检查是否已经和RFID设备连接：" + ex.Message);
            }
            return connectResult;
        }

        public void DisConnectDevice()
        {
            readerObj.DisConnectDevice();
        }

        public Boolean ReadSelectedUsedData(string areaType, string beginStr, string readLen,string readType)
        {
            byte[] bReadBytes = null;
            byte iLength = 0;
            byte iReadAddress = 0;
            byte iReadMemBank = 0;
            UInt16 pbEquipNum = 0;
            Boolean excuResult = false;
            if (CheckEquipNumber() == false)
            {
                return excuResult;
            }

            try
            {
                iLength = byte.Parse(readLen);
                iReadAddress = byte.Parse(beginStr);
                iReadMemBank = byte.Parse(areaType);
                pbEquipNum = GetEquipNumber("FFFF");
            }
            catch (Exception)
            {
                LogTool.WriteLog(typeof(SxlyRfidDevice), "读取失败，请检查参数");
            }

            try
            {
                bReadBytes = readerObj.ReadTagData(pbEquipNum, iReadMemBank, iReadAddress, iLength);
            }
            catch (System.Net.Sockets.SocketException ex)
            {
                LogTool.WriteLog(typeof(SxlyRfidDevice), ex.Message);
                DisConnectDevice();
                Thread.Sleep(1000);
                ConnectDevice();
            }

            if (bReadBytes != null)
            {
                if (bReadBytes.Length == 1)
                {
                    LogTool.WriteLog(typeof(SxlyRfidDevice), "读不到标签");
                }
                else
                {
                    //长度不为1则说明读取成功
                    StringBuilder strBuffer = new StringBuilder();
                    int iIndex = 0;
                    for (iIndex = 0; iIndex < iLength * 2; iIndex++)
                    {
                        strBuffer.Append(bReadBytes[iIndex].ToString("X2"));
                        strBuffer.Append("  ");
                    }
                    //显示数据
                    excuResult = true;
                    Sxly_Rfid_Reader(strBuffer.ToString(), readType);
                  
                }
            }
            else
            {
                LogTool.WriteLog(typeof(SxlyRfidDevice), "读取标签失败Fail Code:" + readerObj.GetLastErrorCode().ToString());
            }
            return excuResult;
        }

        public Boolean WriteSelectedUsedData(string areaType, string beginStr, string writeLen, string writeData)
        {
            byte bWriteAddress = 0;
            byte bWriteMemBank = 0;
            byte bWriteLength = 0;
            UInt16 equipNum = 0;
            String strWriteData;
            Boolean excuResult = false;
            try
            {
                bWriteAddress = byte.Parse(beginStr);
                bWriteMemBank = byte.Parse(areaType);
                bWriteLength = byte.Parse(writeLen);
                equipNum = GetEquipNumber("FFFF");
                strWriteData = writeData.Trim();
                if (strWriteData.Length != bWriteLength * 4)
                {
                    LogTool.WriteLog(typeof(SxlyRfidDevice),  "你输入的数据长度错误");
                    return excuResult;
                }
            }
            catch (Exception)
            {
                LogTool.WriteLog(typeof(SxlyRfidDevice), "请检查您输入的参数");
                return excuResult;
            }

            byte[] wByte = new byte[bWriteLength * 2];
            String str;
            for (int bIndex = 0; bIndex < bWriteLength * 2; bIndex++)
            {
                str = strWriteData.Substring(bIndex * 2, 2);
                wByte[bIndex] = Convert.ToByte(str, 16);
            }
            Boolean bWriteResultFlag = false;

            try
            {
                bWriteResultFlag = readerObj.WriteTagData(equipNum, bWriteMemBank, bWriteAddress, bWriteLength, wByte);
            }
            catch (System.Net.Sockets.SocketException ex)
            {
                LogTool.WriteLog(typeof(SxlyRfidDevice), "写入失败:" + ex.Message);
                DisConnectDevice();
                Thread.Sleep(1000);
                ConnectDevice();
            }

            if (bWriteResultFlag == true)
            {
                excuResult = true;
                LogTool.WriteLog(typeof(SxlyRfidDevice),"写入" + strWriteData + "成功");
            }
            else
            {
                LogTool.WriteLog(typeof(SxlyRfidDevice), "写入失败：Fail Code:" + readerObj.GetLastErrorCode());
            }
            return excuResult;
        }

        private UInt16 GetEquipNumber(string strEquipNum)
        {
            UInt16 iEquipNum = 0;
            for (int iIndex = 0; iIndex < 4; ++iIndex)
            {
                iEquipNum <<= 4;

                byte b = Convert.ToByte(strEquipNum.Substring(iIndex, 1), 16);
                iEquipNum += b;
            }
            return iEquipNum;
        }

        private Boolean CheckEquipNumber()
        {
            if (!deviceIp.Equals("") && devicePort > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
