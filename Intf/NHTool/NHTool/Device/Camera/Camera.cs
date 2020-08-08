using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.IO;
using System.Windows.Forms;

namespace NHTool.Device.Camera
{
    public class Camera
    {
        private int user_id = -1;
        private int real_handle = -1;
        private int alarm_handle = -1;
        private bool initialize = false;
        private IntPtr m_ptrRealHandle;
        private CHCNetSDK.REALDATACALLBACK m_fRealData = null;
        private CHCNetSDK.MSGCallBack callback = null;

        private CHCNetSDK.ReturnLicense returnLicense;

        public delegate void CATCH_CARNO_EVENT(string carNo);
        public event CATCH_CARNO_EVENT catchCarNo;

        #region 初始化
        public Camera() {
            initialize = CHCNetSDK.NET_DVR_Init();
            if (!initialize){
                throw new Exception("视频监控初始化失败");
            }else {
                CHCNetSDK.NET_DVR_SetLogToFile(3, "C:\\HCNetSDKLog\\", true);
            }

            
        }
        #endregion

        #region 析构
        ~Camera() {
            if (real_handle >=0) {
                CHCNetSDK.NET_DVR_StopRealPlay(real_handle);
            }
            if (user_id >= 0){
                CHCNetSDK.NET_DVR_Logout(user_id);
            }
            if (initialize){
                CHCNetSDK.NET_DVR_Cleanup();
            }
        }
        #endregion

        #region 连接
        public bool Connect(String ip,Int16 port,String userName,String password){
            if (user_id < 0) {
                CHCNetSDK.NET_DVR_DEVICEINFO_V30 DeviceInfo = new CHCNetSDK.NET_DVR_DEVICEINFO_V30();
                user_id = CHCNetSDK.NET_DVR_Login_V30(ip, port, userName, password, ref DeviceInfo);

                if (user_id < 0){
                     uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                     string str = "摄像头登录失败,错误码:" + iLastErr; 
                }else {
                    Console.WriteLine("登录成功!");
                }
            }
            return (user_id != -1);
        }
        #endregion

        #region 断开连接
        public bool DisConnect(){
            if (user_id < 0){
                return false;
            }
            if (!CHCNetSDK.NET_DVR_Logout(user_id)){
                uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                string str = "摄像头注销失败，错误码" + iLastErr;
                return false;
            }
            user_id = -1;
            return true;
        }
        #endregion

        #region 获取车牌
        public string CapturePlate(){
            CHCNetSDK.NET_DVR_PLATE_RESULT plateResult = new CHCNetSDK.NET_DVR_PLATE_RESULT();
            plateResult.pBuffer1 = Marshal.AllocHGlobal(2 * 1024 * 1024);

            CHCNetSDK.NET_DVR_MANUALSNAP struInter = new CHCNetSDK.NET_DVR_MANUALSNAP();

            String result = null;

            if (!CHCNetSDK.NET_DVR_ManualSnap(user_id, ref struInter, ref plateResult)){
                uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                string str = "获取车牌号失败,错误码:" + iLastErr;
            }else{
                result = System.Text.Encoding.GetEncoding("GBK").GetString(plateResult.struPlateInfo.sLicense).TrimEnd('\0');
                int dwPicLen = (int)plateResult.dwPicLen;

                if (dwPicLen > 0){
                    string path = result;
                    FileStream fs = new FileStream(path, FileMode.Create);
                    byte[] by = new byte[dwPicLen];
                    Marshal.Copy(plateResult.pBuffer1, by, 0, dwPicLen);
                    fs.Write(by, 0, dwPicLen);
                    fs.Close();
                }
            }
            return result;
        }
        #endregion

        #region 实时预览
        public bool StartLiveView(PictureBox RealPlayWnd){
            if (user_id == -1) {
                return false;
            }
            CHCNetSDK.NET_DVR_PREVIEWINFO previewInfo = new CHCNetSDK.NET_DVR_PREVIEWINFO();
            previewInfo.hPlayWnd = RealPlayWnd.Handle;//预览窗口
            previewInfo.lChannel = 1;//预te览的设备通道
            previewInfo.dwStreamType = 0;//码流类型：0-主码流，1-子码流，2-码流3，3-码流4，以此类推
            previewInfo.dwLinkMode = 0;//连接方式：0- TCP方式，1- UDP方式，2- 多播方式，3- RTP方式，4-RTP/RTSP，5-RSTP/HTTP 
            previewInfo.bBlocked = true; //0- 非阻塞取流，1- 阻塞取流
            previewInfo.dwDisplayBufNum = 1; //播放库播放缓冲区最大缓冲帧数
            previewInfo.byProtoType = 0;
            previewInfo.byPreviewMode = 0;

            previewInfo.hPlayWnd = RealPlayWnd.Handle;

            m_ptrRealHandle = RealPlayWnd.Handle;
            IntPtr pUser = new IntPtr();
            real_handle = CHCNetSDK.NET_DVR_RealPlay_V40(user_id, ref previewInfo, null, pUser);

            if (real_handle == -1){
                uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                string str = "启动实时预览失败,错误码:" + iLastErr;
            }
            return (real_handle != -1);
        }
        #endregion

        #region 关闭预览
        public bool StopLiveView(){
            if (user_id == -1 || real_handle == -1) {
                return false;
            }

            if (!CHCNetSDK.NET_DVR_StopRealPlay(real_handle)) {
                uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                string str = "关闭实时预览失败,错误码:" + iLastErr;
                return false;
            }
            
            real_handle = -1;
            return true;
        }
        #endregion

        #region 车牌自动获取
        public bool StartAlarm(CHCNetSDK.ReturnLicense returnLicense) {
            
            if (user_id == -1){
                return false;
            }

            CHCNetSDK.NET_DVR_SETUPALARM_PARAM struAlarmParam = new CHCNetSDK.NET_DVR_SETUPALARM_PARAM();
            struAlarmParam.dwSize = (uint)Marshal.SizeOf(struAlarmParam);
            struAlarmParam.byLevel = 1; //0- 一级布防,1- 二级布防
            struAlarmParam.byAlarmInfoType = 1;//智能交通设备有效，新报警信息类型
            struAlarmParam.byFaceAlarmDetection = 1;//1-人脸侦测

            alarm_handle = CHCNetSDK.NET_DVR_SetupAlarmChan_V41(user_id, ref struAlarmParam);

            if (alarm_handle < 0)
            {
                uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                String strErr = "布防失败，错误号：" + iLastErr;
            }
            else {
                callback = new CHCNetSDK.MSGCallBack(MsgCallback);
                CHCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(callback, IntPtr.Zero);
                this.returnLicense = returnLicense;
            }

            return (alarm_handle!=-1);
        }

        public bool StopAlarm() {
            if (alarm_handle < 0) {
                return true;
            }

            if (!CHCNetSDK.NET_DVR_CloseAlarmChan_V30(user_id)) {
                uint iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                String strErr = "撤防失败，错误号：" + iLastErr; //撤防失败，输出错误号
                return false;
            }

            alarm_handle = -1;
            return true;
        }

        public bool MsgCallback(int lCommand, ref CHCNetSDK.NET_DVR_ALARMER pAlarmer, IntPtr pAlarmInfo, uint dwBufLen, IntPtr pUser){
            //通过lCommand来判断接收到的报警信息类型，不同的lCommand对应不同的pAlarmInfo内容
            Console.WriteLine(lCommand.ToString());
            AlarmMessageHandle(lCommand, ref pAlarmer, pAlarmInfo, dwBufLen, pUser);
            return true; //回调函数需要有返回，表示正常接收到数据
        }

        public void AlarmMessageHandle(int lCommand, ref CHCNetSDK.NET_DVR_ALARMER pAlarmer, IntPtr pAlarmInfo, uint dwBufLen, IntPtr pUser)
        {
            //通过lCommand来判断接收到的报警信息类型，不同的lCommand对应不同的pAlarmInfo内容
            switch (lCommand)
            {
                case CHCNetSDK.COMM_ITS_PLATE_RESULT://交通抓拍结果上传(新报警信息类型)
                    ProcessCommAlarm_ITSPlate(ref pAlarmer, pAlarmInfo, dwBufLen, pUser);
                    break;
                default:
                    break;
            }
        }

        private void ProcessCommAlarm_ITSPlate(ref CHCNetSDK.NET_DVR_ALARMER pAlarmer, IntPtr pAlarmInfo, uint dwBufLen, IntPtr pUser)
        {
            CHCNetSDK.NET_ITS_PLATE_RESULT struITSPlateResult = new CHCNetSDK.NET_ITS_PLATE_RESULT();
            uint dwSize = (uint)Marshal.SizeOf(struITSPlateResult);

            struITSPlateResult = (CHCNetSDK.NET_ITS_PLATE_RESULT)Marshal.PtrToStructure(pAlarmInfo, typeof(CHCNetSDK.NET_ITS_PLATE_RESULT));

            string sLicense = System.Text.Encoding.GetEncoding("GBK").GetString(struITSPlateResult.struPlateInfo.sLicense).TrimEnd('\0');

            if (this.returnLicense != null) {
                this.returnLicense.Invoke(sLicense);
            }
        }
        #endregion
    }
}
