using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EasyModbus;
using NHTool.Common;
using System.Windows.Forms;
using System.Threading;

namespace NHTool.Device.Modbus
{
   public class Modbus:Device
    {
         int deviceState;
         Dictionary<String, String> ret = new Dictionary<String, String>();
         ModbusClient clinet = null;
         Dictionary<String, String> ctlConfig = new Dictionary<String, String>();
         public delegate void modbusDeviceHander(Dictionary<String, String> resultDictionary);
         public event modbusDeviceHander modbusDeviceEvent;
         private Thread readThread=null;
         const int startAdress = 0;
         const int readNummber = 24;
         private string IP;
         private string PORT;
         private byte UNIT;

        private readonly object modbusLock = new object();

        public  Dictionary<String, String> initial(string modbusIP, string modbusPort,byte unit=0x01)
        {
             this.IP = modbusIP;
             this.PORT = modbusPort;
             this.UNIT = unit;
             ret.Clear();
             ret.Add(Common.Commons.RES_CODE, "0");
             ret.Add(Common.Commons.RES_MSG, "OK");
             deviceState = (int)NHTool.Common.Commons.deviceState.INITIAL;
             clinet = new ModbusClient();
             try
             {
                 clinet.Connect("192.168.90.151", int.Parse("502"));
                 clinet.UnitIdentifier = unit;

             }catch(Exception e)
             {
                 ret.Clear();
                 ret.Add(Common.Commons.RES_CODE, "1");
                 ret.Add(Common.Commons.RES_MSG, e.ToString());
                 return ret;
             }
             return ret; 
        }

        public override Dictionary<String, String>  start()
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


        public override Dictionary<String, String> stop()
        {

            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");
            deviceState = (int)NHTool.Common.Commons.deviceState.STOP;

           if(clinet!=null)
           {
               clinet.Disconnect();

           }

           return ret;
            
        }


        public Dictionary<String, String> getInfo(string key, Commons.modbusType type)
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "1");
            ret.Add(Common.Commons.RES_MSG, "OK");
            Dictionary<String, String> resultDictionary = new Dictionary<string, string>();
             bool[] resultBoolen=null;
             int[] resultInt = null;
            if (clinet != null && clinet.Connected)
            {
                try
                {
                    //lock (modbusLock)
                    //{
                        switch (type)
                        {
                            
                            case Commons.modbusType.COIL_STATUS:
                                resultBoolen = clinet.ReadCoils(int.Parse(key), 1);
                                resultDictionary.Add(key, resultBoolen[0].ToString());
                                break;

                            case Commons.modbusType.INPUT_REGISTER:
                                resultInt = clinet.ReadInputRegisters(int.Parse(key), 1);
                                resultDictionary.Add(key, resultInt[0].ToString());
                                break;

                            case Commons.modbusType.INPUT_STATUS:
                                resultBoolen = clinet.ReadDiscreteInputs(int.Parse(key), 1);
                                resultDictionary.Add(key, resultBoolen[0].ToString());
                                break;


                            case Commons.modbusType.HOLDING_REGISTER:
                                resultInt = clinet.ReadHoldingRegisters(int.Parse(key), 1);
                                resultDictionary.Add(key, resultInt[0].ToString());
                                break;
                                
                        }

                    //}

                   
                  

                }
                catch (Exception e)
                {
                    LogTool.WriteLog(typeof(Modbus), e);
                    ret.Clear();
                    ret.Add(Common.Commons.RES_CODE, "1");
                    ret.Add(Common.Commons.RES_MSG, e.ToString());
                }


            }
            else
            {
                ret.Clear();
                ret.Add(Common.Commons.RES_CODE, "1");
                ret.Add(Common.Commons.RES_MSG, "modbus无法访问");

            }

            return ret;
        }



        public  Dictionary<String, String> setCommand(string key,string value ,Commons.modbusType  type)
        {
            ret.Clear();
            ret.Add(Common.Commons.RES_CODE, "0");
            ret.Add(Common.Commons.RES_MSG, "OK");

            if(clinet!=null && clinet.Connected)
            {
                try{

                    lock (modbusLock)
                    {

                        switch (type)
                        {


                            case Commons.modbusType.COIL_STATUS:
                                clinet.WriteSingleCoil(int.Parse(key) - 1, Boolean.Parse(value));
                                break;

                            case Commons.modbusType.INPUT_REGISTER:

                                ret.Clear();
                                ret.Add(Common.Commons.RES_CODE, "1");
                                ret.Add(Common.Commons.RES_MSG, "功能码不支持写操作");
                                break;

                            case Commons.modbusType.INPUT_STATUS:

                                ret.Clear();
                                ret.Add(Common.Commons.RES_CODE, "1");
                                ret.Add(Common.Commons.RES_MSG, "功能码不支持写操作");
                                break;

                            case Commons.modbusType.HOLDING_REGISTER:

                                clinet.WriteSingleRegister(int.Parse(key) - 1, int.Parse(value));
                                break;



                        }

                    }



                }
                catch(Exception e){


                    LogTool.WriteLog(typeof(Modbus), e); 
                    ret.Clear();
                    ret.Add(Common.Commons.RES_CODE, "1");
                    ret.Add(Common.Commons.RES_MSG,e.ToString());
                }
                

            }
            else
            {
                ret.Clear();
                ret.Add(Common.Commons.RES_CODE, "1");
                ret.Add(Common.Commons.RES_MSG, "modbus无法访问");

            }

            return ret;

        }
      

        public override int getState()
        {
            return deviceState;
        }


        private void readThreadWork()
        {

            while(true)
            {

                //lock (modbusLock)
                //{
                    if (clinet != null && clinet.Connected)
                    {
                        try
                        {
                            bool[] result = clinet.ReadCoils(startAdress, readNummber);
                            Dictionary<String, String> resultDictionary = new Dictionary<string, string>();
                            for (int i = 1; i <= result.Length; i++)
                            {
                                resultDictionary.Add(i.ToString(), result[i - 1].ToString());

                            }
                            if (modbusDeviceEvent != null)
                            {
                                modbusDeviceEvent(resultDictionary);
                            }

                        }
                        catch (Exception e)
                        {
                            ret.Clear();
                            ret.Add(Common.Commons.RES_CODE, "1");
                            ret.Add(Common.Commons.RES_MSG, e.ToString());
                            modbusDeviceEvent(ret);
                            LogTool.WriteLog(typeof(Modbus), e);

                            try
                            {
                                clinet.Disconnect();
                                System.Threading.Thread.Sleep(20000);
                                clinet = null;
                               

                            }
                            catch (Exception ex)
                            {
                                clinet = null;
                                LogTool.WriteLog(typeof(Modbus), "reConect:err--->" + ex);

                            }


                        }

                    }
                    else
                    {
                        if (clinet == null)
                        {

                            clinet = new ModbusClient();
                        }
                        try
                        {
                            System.Threading.Thread.Sleep(2000);
                            clinet.Connect(this.IP, int.Parse(this.PORT));
                            clinet.UnitIdentifier = this.UNIT;

                        }
                        catch (Exception ex)
                        {
                            clinet = null;
                            LogTool.WriteLog(typeof(Modbus), "reConect:err--->" + ex);
                            System.Threading.Thread.Sleep(20000);


                        }
                    }


                //}
                
               System.Threading.Thread.Sleep(500);
            }

        }
    }
}
