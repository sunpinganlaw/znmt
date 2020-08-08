using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NHTool.Device.GDZS
{
    class DataHanderTool
    {
        public  enum RegisterOrder { LowHigh=0, HighLow=1 };

       /// <summary>
       /// byte字节转int
       /// </summary>
       /// <param name="arr">byte[]数组</param>
       /// <param name="index">起始地址</param>
       /// <param name="type">高低字节类型</param>
       /// <returns></returns>
        public static int getIntBy2(byte[] arr, int index, RegisterOrder registerOrder)
        {
            if (registerOrder == RegisterOrder.HighLow)//高位在前
            {
                return
                        (0x0000ff00 & (arr[index + 0] << 8)) |
                        (0x000000ff & arr[index + 1]);
            }
            else if (registerOrder == RegisterOrder.LowHigh)
            {

                return
                        (0x0000ff00 & (arr[index + 1] << 8)) |
                        (0x000000ff & arr[index + 0]);

            }
            else
            {
                return
                        (0x0000ff00 & (arr[index + 0] << 8)) |
                        (0x000000ff & arr[index + 1]);

            }

        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="arr"></param>
        /// <param name="index"></param>
        /// <param name="type"></param>
        /// <returns></returns>
        public static long getIntBy4(byte[] arr, int index, RegisterOrder registerOrder)
        {
            if (registerOrder == RegisterOrder.HighLow)//高位在前
            {
                return (0xff000000 & (arr[index + 0] << 24)) |
                        (0x00ff0000 & (arr[index + 1] << 16)) |
                        (0x0000ff00 & (arr[index + 2] << 8)) |
                        (0x000000ff & arr[index + 3]);
            }
            else if (registerOrder == RegisterOrder.LowHigh)
            {



                return (0xff000000 & (arr[index + 3] << 24)) |
                        (0x00ff0000 & (arr[index + 2] << 16)) |
                        (0x0000ff00 & (arr[index + 1] << 8)) |
                        (0x000000ff & arr[index + 0]);

            }
            else
            {
                return (0xff000000 & (arr[index + 0] << 24)) |
                (0x00ff0000 & (arr[index + 1] << 16)) |
                (0x0000ff00 & (arr[index + 2] << 8)) |
                (0x000000ff & arr[index + 3]);

            }


        }

        /// <summary>
        /// Converts two ModbusRegisters to Float, Registers can by swapped
        /// </summary>
        /// <param name="registers">Two Register values received from Modbus</param>
        /// <param name="registerOrder">Desired Word Order (Low Register first or High Register first</param>
        /// <returns>Connected float value</returns>
        public static float ConvertRegistersToFloat(int[] registers, RegisterOrder registerOrder)
        {
            int[] swappedRegisters = { registers[0], registers[1] };
            if (registerOrder == RegisterOrder.HighLow)
                swappedRegisters = new int[] { registers[1], registers[0] };
            return ConvertRegistersToFloat(swappedRegisters);
        }


       /// <summary>
       /// byte[]转成float
       /// </summary>
       /// <param name="arr">byte[]数组</param>
       /// <param name="index">起始地址</param>
       /// <param name="type">高低字节类型</param>
       /// <returns></returns>
        public static float getFloat(byte[] arr, int index, RegisterOrder registerOrder)
        {
            int[] intArrey = new int[2];
            intArrey[0] = getIntBy2(arr, 0, registerOrder);
            intArrey[1] = getIntBy2(arr, 2, registerOrder);

            return ConvertRegistersToFloat(intArrey, registerOrder);
        }


        public static byte[] toByteArray(int value)
        {
            byte[] result = new byte[2];
            result[1] = (byte)(value >> 8);
            result[0] = (byte)(value);
            return result;
        }


        public static byte[] toByteArrayBy2(int[] value)
        {
            byte[] lowbytes = toByteArray(value[0]);
            byte[] highbytes = toByteArray(value[1]);
            byte[] result = new byte[lowbytes.Length + highbytes.Length];
            Array.Copy(lowbytes, 0, result, 0, lowbytes.Length);
            Array.Copy(highbytes, 0, result, lowbytes.Length, highbytes.Length);
            return result;
        }


        /// <summary>
        /// Converts two ModbusRegisters to Float - Example: EasyModbus.ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(19,2))
        /// </summary>
        /// <param name="registers">Two Register values received from Modbus</param>
        /// <returns>Connected float value</returns>
        public static float ConvertRegistersToFloat(int[] registers)
        {
            if (registers.Length != 2)
                throw new ArgumentException("Input Array length invalid - Array langth must be '2'");
            int highRegister = registers[1];
            int lowRegister = registers[0];
            byte[] highRegisterBytes = BitConverter.GetBytes(highRegister);
            byte[] lowRegisterBytes = BitConverter.GetBytes(lowRegister);
            byte[] floatBytes = {
                                    lowRegisterBytes[0],
                                    lowRegisterBytes[1],
                                    highRegisterBytes[0],
                                    highRegisterBytes[1]
                                };
            return BitConverter.ToSingle(floatBytes, 0);
        }



    public static String bytesToHexString(byte[] src)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.Length <= 0)
        {
            return null;
        }
        for (int i = 0; i < src.Length; i++)
        {
            int v = src[i] & 0xFF;
            String hv = Convert.ToString(v, 16);
            if (hv.Length< 2)
            {
                stringBuilder.Append(0);
            }
            stringBuilder.Append(hv);
        }
        return stringBuilder.ToString();
    }


        /// <summary>
        /// Converts 32 Bit Value to two ModbusRegisters
        /// </summary>
        /// <param name="intValue">Int value which has to be converted into two registers</param>
        /// <returns>Register values</returns>
        public static int[] ConvertIntToRegisters(Int32 intValue)
        {
            byte[] doubleBytes = BitConverter.GetBytes(intValue);
            byte[] highRegisterBytes =
            {
                doubleBytes[2],
                doubleBytes[3],
                0,
                0
            };
            byte[] lowRegisterBytes =
            {

                doubleBytes[0],
                doubleBytes[1],
                0,
                0
            };
            int[] returnValue =
            {
                BitConverter.ToInt32(lowRegisterBytes,0),
                BitConverter.ToInt32(highRegisterBytes,0)
            };
            return returnValue;
        }

        /// <summary>
        /// Converts 32 Bit Value to two ModbusRegisters Registers - Registers can be swapped
        /// </summary>
        /// <param name="intValue">Double value which has to be converted into two registers</param>
        /// <param name="registerOrder">Desired Word Order (Low Register first or High Register first</param>
        /// <returns>Register values</returns>
        public static int[] ConvertIntToRegisters(Int32 intValue, RegisterOrder registerOrder)
        {
            int[] registerValues = ConvertIntToRegisters(intValue);
            int[] returnValue = registerValues;
            if (registerOrder == RegisterOrder.HighLow)
                returnValue = new Int32[] { registerValues[1], registerValues[0] };
            return returnValue;
        }

        /// <summary>
        /// Converts float to two ModbusRegisters - Example:  modbusClient.WriteMultipleRegisters(24, EasyModbus.ModbusClient.ConvertFloatToTwoRegisters((float)1.22));
        /// </summary>
        /// <param name="floatValue">Float value which has to be converted into two registers</param>
        /// <returns>Register values</returns>
        public static int[] ConvertFloatToRegisters(float floatValue)
        {
            byte[] floatBytes = BitConverter.GetBytes(floatValue);
            byte[] highRegisterBytes =
            {
                floatBytes[2],
                floatBytes[3],
                0,
                0
            };
            byte[] lowRegisterBytes =
            {

                floatBytes[0],
                floatBytes[1],
                0,
                0
            };
            int[] returnValue =
            {
                BitConverter.ToInt32(lowRegisterBytes,0),
                BitConverter.ToInt32(highRegisterBytes,0)
            };
            return returnValue;
        }

        /// <summary>
        /// Converts float to two ModbusRegisters Registers - Registers can be swapped
        /// </summary>
        /// <param name="floatValue">Float value which has to be converted into two registers</param>
        /// <param name="registerOrder">Desired Word Order (Low Register first or High Register first</param>
        /// <returns>Register values</returns>
        public static int[] ConvertFloatToRegisters(float floatValue, RegisterOrder registerOrder)
        {
            int[] registerValues = ConvertFloatToRegisters(floatValue);
            int[] returnValue = registerValues;
            if (registerOrder == RegisterOrder.HighLow)
                returnValue = new Int32[] { registerValues[1], registerValues[0] };
            return returnValue;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static byte[] toByteArrayInt(int value)
        {
            return BitConverter.GetBytes(value);
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static byte[] toByteArrayLong(long value)
        {
            return BitConverter.GetBytes(value);

         }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static byte[] toByteArrayDouble(double value)
        {
                return BitConverter.GetBytes(value);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static byte[] toByteArray(float value)
        {
                return BitConverter.GetBytes(value);

        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="buf"></param>
        /// <param name="registerOrder"></param>
        /// <returns></returns>
        public  static int getInt(byte[] buf, RegisterOrder registerOrder)
        {
            if (buf == null)
            {
                throw new Exception("byte array is null!");
            }
            if (buf.Length > 4)
            {
                throw new Exception("byte array size > 4 !");
            }
            int r = 0;
            if (registerOrder== RegisterOrder.HighLow)
                for (int i = buf.Length - 1; i >= 0; i--)
                {
                    r <<= 8;
                    r |= (buf[i] & 0x000000ff);
                }
            else
                for (int i = 0; i < buf.Length; i++)
                {
                    r <<= 8;
                    r |= (buf[i] & 0x000000ff);
                }
            return r;
        }

    }
}
