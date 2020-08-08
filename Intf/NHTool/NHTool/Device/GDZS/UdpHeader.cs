using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NHTool.Device.GDZS
{
    class UdpHeader
    {

        private String domainNum;//---域号
        private String dropNum;//站号
        private String byteOrder = "1";//字序
        private String packType = "1";//包类型
        private String packTimes;//打包时间（秒）
        private String packTimeMs;//打包时间（毫秒）
        private int nPoints;//包中实际点个数
        private String version;//版本

        public string DomainNum { get => domainNum; set => domainNum = value; }

        public string DropNum { get => dropNum; set => dropNum = value; }

        public string ByteOrder { get => byteOrder; set => byteOrder = value; }

        public string PackType { get => packType; set => packType = value; }

        public string PackTimes { get => packTimes; set => packTimes = value; }

        public string PackTimeMs { get => packTimeMs; set => packTimeMs = value; }

        public int NPoints { get => nPoints; set => nPoints = value; }

        public string Version { get => version; set => version = value; }

    }
}
