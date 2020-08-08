using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
namespace NHTool.Common
{
    class Json
    {
        //同时参考 OPCTool中
        public static void testParseJson()
        {
            //定义一个JSON字符串   
            string jsonText = "[{'a':'aaa','b':'bbb','c':'ccc'},{'a':'aaa2','b':'bbb2','c':'ccc2'}]";
            //反序列化JSON字符串  
            JArray jarray = (JArray)JsonConvert.DeserializeObject(jsonText);
            //将反序列化的JSON字符串转换成对象  
            JObject jobject = (JObject)jarray[1];
            //读取对象中的各项值  
            Console.WriteLine(jobject["a"]);
            Console.WriteLine(jarray[1]["a"]);  
        }

        public static void testGenJson()
        {
            JArray jsonArray = new JArray();
            JObject json = new JObject();
            json.Add("key", "value");
            jsonArray.Add(json);
            Console.WriteLine(jsonArray.ToString());
        }
    }
}
