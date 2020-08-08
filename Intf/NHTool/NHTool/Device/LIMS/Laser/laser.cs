using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.Threading;

namespace NHTool.Device.LIMS.Laser
{

    class laser : Device
    {
        int deviceState;
        Dictionary<String, String> ret = new Dictionary<String, String>();
        private Thread readThread = null;
        public delegate void laserHander(Dictionary<String, String> resultDictionary);
        public event laserHander laserEvent;
        public delegate void laserMsgHander(string msg);
        private bool comBusy = false;
        /// <summary>
        ///激光器类执行过程中的消息事件(程序日志信息）
        /// </summary>
        public event laserMsgHander laserMsgEvent;


        public long m_DeviceHandle;
        public avaspec.PixelArrayType m_Lambda = new avaspec.PixelArrayType();
        public avaspec.PixelArrayType m_Spectrum = new avaspec.PixelArrayType();


        avaspec.MeasConfigType l_PrepareMeasData = new avaspec.MeasConfigType();

        public double AxisXMinimum = 0;
        public double AxisXMaximum = 0;

        /// <summary>
        /// 初始化
        /// </summary>
        /// <returns></returns>
        public Dictionary<String, String> initial()
        {
            m_DeviceHandle = avaspec.INVALID_AVS_HANDLE_VALUE;
            int l_Port = avaspec.AVS_Init(0);
            if (l_Port > 0)
            {
                ret.Add(Common.Commons.RES_CODE, "0");
                ret.Add(Common.Commons.RES_MSG, "OK");
                deviceState = (int)NHTool.Common.Commons.deviceState.INITIAL;
            }
            else
            {
                avaspec.AVS_Done();
                l_Port = avaspec.AVS_Init(-1);   //try Ethernet/USB
                if (l_Port > 0)
                {
                    ret.Add(Common.Commons.RES_CODE, "0");
                    ret.Add(Common.Commons.RES_MSG, "OK");
                    deviceState = (int)NHTool.Common.Commons.deviceState.INITIAL;

                }
                else
                {
                    ret.Add(Common.Commons.RES_CODE, "1");
                    ret.Add(Common.Commons.RES_MSG, "初始化激光器失败");
                    deviceState = (int)NHTool.Common.Commons.deviceState.INITIAL;
                    avaspec.AVS_Done();
                }
            }

            return ret;

        }


        /// <summary>
        /// 激活激光器
        /// </summary>
        /// <returns></returns>
        public Dictionary<String, String> Activate()
        {
            ret.Clear();
            uint l_Size = 0;
            int l_NrDevices = 0;
            uint l_RequiredSize = 0;
            l_NrDevices = avaspec.AVS_GetNrOfDevices();
            avaspec.AvsIdentityType[] l_Id = new avaspec.AvsIdentityType[l_NrDevices];
            l_RequiredSize = ((uint)l_NrDevices) * (uint)Marshal.SizeOf(typeof(avaspec.AvsIdentityType));

            l_Size = l_RequiredSize;
            l_NrDevices = avaspec.AVS_GetList(l_Size, ref l_RequiredSize, l_Id);
            

            avaspec.AvsIdentityType l_Active = new avaspec.AvsIdentityType();
            long l_hDevice = 0;

            l_Active.m_SerialNumber = l_Id[0].m_SerialNumber;
            l_hDevice = (long)avaspec.AVS_Activate(ref l_Active);


            if (avaspec.INVALID_AVS_HANDLE_VALUE == l_hDevice)
            {
                
                ret.Add(Common.Commons.RES_CODE, "1");
                ret.Add(Common.Commons.RES_MSG, "Error opening device" + l_Active.m_SerialNumber);
              
            }
            else
            {
                m_DeviceHandle = l_hDevice;
                if (avaspec.AVS_UseHighResAdc((IntPtr)m_DeviceHandle, true) != avaspec.ERR_SUCCESS)
                {

                    ret.Add(Common.Commons.RES_CODE, "1");
                    ret.Add(Common.Commons.RES_MSG, "Error Setting High Res mode " + l_Active.m_SerialNumber);
                }
                else
                {
                    ret.Add(Common.Commons.RES_CODE, "0");
                    ret.Add(Common.Commons.RES_MSG, "OK");
                  
                }
                
            }
            

            return ret;

        }



        /// <summary>
        /// 激活激光器准备测量,给各个光谱仪的测试参数赋值
        /// </summary>
        /// <returns></returns>
        public Dictionary<String, String> PrepareMeasure(avaspec.MeasConfigType user_PrepareMeasData)
        {
            ret.Clear();

            this.l_PrepareMeasData = user_PrepareMeasData;
            int l_Res = (int)avaspec.AVS_PrepareMeasure((IntPtr)m_DeviceHandle, ref l_PrepareMeasData);
            if (avaspec.ERR_SUCCESS != l_Res)
            {
               
                ret.Add(Common.Commons.RES_CODE, "1");
                ret.Add(Common.Commons.RES_MSG, "Error " + l_Res.ToString());
            }
            else
            {

                ret.Add(Common.Commons.RES_CODE, "0");
                ret.Add(Common.Commons.RES_MSG, "OK");
            }

            return ret;
        }



        /// <summary>
        /// 获取各个光谱仪波长数组数据（横坐标）
        /// </summary>
        /// <returns></returns>
        public Dictionary<String, String> GetLambda()
        {
            ret.Clear();
            
            if (avaspec.ERR_SUCCESS == (int)avaspec.AVS_GetLambda((IntPtr)m_DeviceHandle, ref m_Lambda))
            {
                AxisXMinimum= m_Lambda.Value[l_PrepareMeasData.m_StartPixel];
                AxisXMaximum = m_Lambda.Value[l_PrepareMeasData.m_StopPixel];

                ret.Add(Common.Commons.RES_CODE, "0");
                ret.Add(Common.Commons.RES_MSG, "OK");

            }
            else
            {

                ret.Add(Common.Commons.RES_CODE, "1");
                ret.Add(Common.Commons.RES_MSG, "失败："+ "获取各个光谱仪波长数组数据（横坐标）");
            }
           

            return ret;
        }


        /// <summary>
        /// 控制激光器开始测量
        /// </summary>
        /// <param name="Handle">调用该方法窗口的句柄</param>
        /// <returns></returns>
        public Dictionary<String, String> Measure(IntPtr Handle)
        {
            ret.Clear();
            int l_Res = (int)avaspec.AVS_Measure((IntPtr)m_DeviceHandle, Handle, 1);
            if (avaspec.ERR_SUCCESS != l_Res)
            {
                switch (l_Res)
                {
                    case avaspec.ERR_INVALID_PARAMETER:
                        
                        ret.Add(Common.Commons.RES_CODE, "1");
                        ret.Add(Common.Commons.RES_MSG, "Measure失败：" + "Meas.Status: invalid parameter");
                        break;
                    default:
                       
                        ret.Add(Common.Commons.RES_CODE, "1");
                        ret.Add(Common.Commons.RES_MSG, "Measure失败：" + "Meas.Status: start failed, code:" + l_Res.ToString());
                        break;
                }
            }
            else
            {
                ret.Add(Common.Commons.RES_CODE, "0");
                ret.Add(Common.Commons.RES_MSG, "OK");
            }

            return ret;
        }




        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override Dictionary<String, String> start()
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");

            deviceState = (int)NHTool.Common.Commons.deviceState.START;

            readThread = new Thread(readThreadWork);
            readThread.IsBackground = true;
            readThread.Start();
            return ret;

        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override Dictionary<String, String> stop()
        {
          
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            deviceState = (int)NHTool.Common.Commons.deviceState.STOP;
            return ret;

        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override int getState()
        {
            return deviceState;
        }



        /// <summary>
        /// 
        /// </summary>
        private void readThreadWork()
        {

            while (true)
            {


                System.Threading.Thread.Sleep(5000);
            }

        }


    }
}
