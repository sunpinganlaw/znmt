using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NHTool.Device
{
    public abstract class Device
    {

        abstract public Dictionary<String, String> start();

        abstract public Dictionary<String, String> stop();

        abstract public int getState();



    }
}
