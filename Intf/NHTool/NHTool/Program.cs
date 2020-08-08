using NHTool.form.dataSendForm;
using NHTool.Forms.carInForm;
using System;
using System.Collections.Generic;
using System.Windows.Forms;
using NHTool.Forms.scadaForm;
//using NHTool.Forms.trainForm;

namespace NHTool
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            //具体启动哪些form，通过配置实现

            //Application.Run(new mainForm());//汽车自动识别相关
            Application.Run(new dataSendForm());//数据中心启动
            //Application.Run(new dataSendForm());//opc相关
            //Application.Run(new TrainForm());//火车调度相关
        }
    }
}
