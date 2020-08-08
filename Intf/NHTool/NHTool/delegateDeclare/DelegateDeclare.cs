using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
namespace NHTool.delegateDeclare
{
    //代理，通过代理来实现代码的最大程度的共用
    public delegate JObject HttpProcessHandler(String httpStr);
}
