using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.Threading.Tasks;

namespace NHTool.Device.LED
{
    /*******************仰邦科技大屏幕******************
     * create by  dafeige  20190512***********************
     * 这个大屏在以下项目中使用
     * 织金项目的重磅环节
     * 哈密项目的出入厂、重磅、采样等部分环节
     * ************************************************/
    public class Onbon_LED
    {
        //初始化屏幕使用相关接口
        #region 初始化屏幕使用相关接口
        //指定五代控制器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_Bx5G(uint* Bx5g);

        //指定六代控制器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_Bx6G(uint* Bx6g);

        //创建客户端，适用于固定IP通讯
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxScreenClient(uint* screen);

        //创建客户端，适用于固定IP通讯
        //screenname - 屏幕名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxScreenClient2(uint* screen, string screenname);

        //创建客户端，适用于固定IP通讯
        // screenname - 屏幕名称
        // Bx - 指定Bx系列，可以是Bx5g或者Bx6g
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxScreenClient3(uint* screen, string screenname, uint Bx);

        //创建客户端，适用于串口通讯
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxScreenRS(uint* screen);

        //创建客户端，适用于串口通讯
        // screenname - 屏幕名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxScreenRS2(uint* screen, string screenname);

        //创建客户端，适用于服务器模式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxServer(uint* server);

        //创建客户端，适用于服务器模式
        // aliasname - 名称
        // port - 端口号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxServer2(uint* server, string alisaname, ushort port);

        //创建客户端，适用于服务器模式
        // aliasname - 名称
        // port - 端口号
        // Bx - 指定控制类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxServer3(uint* server, string aliasname, ushort port, uint Bx);

        //创建监听
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxServerListener(uint* listener);

        //设置8位访问密码
        // password - 8位访问密码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ServerSetting_setAccessPassword(uint server, string password);

        //设置12位自定义ID
        // customid - 自定义ID
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ServerSetting_setCustomID(uint server, string custonid);

        //设置心跳时间间隔
        // time - 心跳时间间隔
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ServerSetting_setHeartBeatInterval(uint server, ushort time);

        //设置IP
        // ip - IP
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ServerSetting_setIP(uint server, byte* ip);

        //设置模式
        // mode - 模式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ServerSetting_setMode(uint server, byte mode);

        //设置端口
        // port - 端口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ServerSetting_setPort(uint server, ushort port);

        //创建连接相关接口

        // ledip - 控制器IP地址
        // ledport - 控制器端口号 5005
        // result - 是否为tcp通讯，true - tcp  false - udp
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenClient_connect(uint screen, string ledip, int ledport, bool result);

        //取得控制器IP地址
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenClient_getAddress(uint* ledip, uint screen);

        //取得控制器端口号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenClient_getPort(int* ledport, uint screen);

        //取得当前屏幕规格。屏幕规格在连线成功后自动从控制器上获取，若控制器未加载屏参时回复 NULL。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getProfile(uint* profile, uint screen);

        // comport - 串口号 例如：COM1
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenRS_connect(uint screen, string comport);

        // conport - 串口号 例如：COM1
        // baudrate - 波特率 例如： 9600 ,57600
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenRS_connect2(uint screen, string comport, int baudrate);

        //取得波特率
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenRS_getBaudrate(int* baudrate, uint screen);

        //取得串口号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenRS_getComPort(uint* comport, uint screen);

        //节目相关接口
        //创建节目
        // programid - 节目编号组，0-999
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramBxFile(uint* program, int programid, uint screenprofile);

        //创建节目
        // programname - 节目名称，长度为四，第一码为P，后三位为数字，例如P001
        // screenprofile - 屏幕规格 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramBxFile2(uint* program, string programname, uint screenprofile);

        //创建节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramDataBxFile(uint* programdata, string programname, uint srceenprofile);

        //创建节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramDataBxFile2(uint* programdata, string progamname, uint screenprofile, int programid);

        //锁定节目
        // programname - 节目名称
        // locked - 是否锁定 true ： 锁定  false 解锁
        // lockduration - 锁定时间 以s为单位
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramLockCmd(uint* programlock, string programname, bool locked, uint lockduration);

        //锁定节目
        // programname - 节目名称
        // locked - 是否锁定 true： 锁定  false 解锁
        // lockduration - 锁定时间 以s为单位
        // nonvolatile - 掉电保存方式 0X00：掉电不保存  0X01：掉电保存
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramLockCmd2(uint* programlock, string progamname, bool locked, uint lockduration, byte nonvolatile);

        //节目播放的星期属性
        // mon -biy1到bit7依次代表周一到周日
        [DllImport("onbon.apo.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ProgramWeek(uint* programweek, byte mon);

        //节目中添加区域
        // area - 区域
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_addArea(uint program, uint area);

        //增加节目播放时间段
        // starthour - 开始播放小时
        // startminute - 开始播放分钟
        // startsecond - 开始播放秒
        // endhour - 结束播放小时
        // endminute - 结束播放分钟
        // endsecond - 结束播放秒
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_addPlayPeriodSetting(uint program, byte strathour, byte startminute, byte startsecond, byte endhour, byte endminute, byte endsecond);

        //取得区域总数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getAreaCount(int* areacount, uint program);

        //取得播放结束日
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getEndDay(int* endday, uint program);

        //取得结束播放月
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getEndMonth(int* endmonth, uint program);

        //取得结束播放年
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgamBxFile_getEndYear(int* endyear, uint program);

        //取得文件名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getFileName(uint program);

        //取得边框
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getFrame(uint* frame, uint program);

        //取得节目等级 0：一般  1： 优先
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getPriority(byte* programpriority, uint program);

        //取得播放重复次数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getProgramPlayTimes(int* times, uint progam);

        //取得节目播放时间长度，单位为s，当控制器上有多个节目时，会根据此设定控制节目被播放的时间长，当控制器上只有一个节目时，此设定没有效果。控制节目播放有效时间，可以利用addPlayPeriosSetting规划
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getProgramTimeSpan(int* timespan, uint program);

        //取得播放周设定
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getProgramWeek(uint* week, uint program);

        //取得开始播放日
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getStartDay(int* startday, uint program);

        //取得开始播放月
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getStartMonth(int* startmonth, uint program);

        //取得开始播放年
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_getStartYear(int* startyear, uint program);

        //设定结束播放日
        // endday - 结束播放日
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setEndDay(uint program, byte endday);

        //设定结束播放月份
        // endmonth - 结束播放月份
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setEndMonth(uint program, byte endmonth);

        //设定结束播放年
        // endyear - 结束播放年
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setEndYear(uint program, ushort endyear);

        //设定节目等级
        // priority - 节目等级 0： 一般  1： 优先
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setPriority(uint program, byte priority);

        //设定重复播放次数
        // programplaytiesm - 重复播放次数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setProgramPlayTimes(uint program, int programplaytimes);

        //设定节目播放时间长度，单位为s，0：循环播放。当控制器上有多个节目时，会根据此设定控制节目被播放的时间长
        //当控制器上只有一个节目时，此设定无效。
        //控制器节目播放有效时间，可利用addPlayPeriosSetting规划
        // programtimespan - 节目播放时间长度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setProgramTimeSpan(uint program, int programtimespan);

        //设置播放周
        // programweek - 播放周 在接口Create_ProgramWeek中创建
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setProgramWeek(uint program, uint programweek);

        //设定开始播放日
        // startday - 开始播放日
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setStartDay(uint program, byte startday);

        //设定开始播放月份
        // startmonth - 开始播放月份
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setStartMonth(uint program, byte startmonth);

        //设定开始播放年
        // startyear - 开始播放年
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramBxFile_setStartYear(uint program, ushort startyear);

        //取得当前偏移量
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramDataBxFile_getCurrentOffset(int* currentoffset, uint programdata);

        //取得文件名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramDataBxFile_getFileName(uint programdata);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramDataBxFile_reset(uint programdata, int reset);

        //全周播放
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramWeek_All(uint program);

        //全周取消播放
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ProgramWeek_Off(uint program);

        //区域相关接口
        //创建图文区域
        // x - x坐标
        // y - y坐标
        // width - 区域宽度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextCaptionBxArea(uint* textarea, int x, int y, int width, int heigth, uint screenprofile);

        //取得图文区透明度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextCaptionBxArea_getTransparency(uint* transparency, uint textarea);

        //是否显示背景
        // isbackgroundflag - 背景是否显示 true： 显示  flase :不显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextCaptionBxArea_isBackgroundFlag(bool* isbackgroundflag, uint textarea);

        //显示被背景遮罩的部分
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextCaptionBxArea_maskByBackground(uint textarea);

        //与背景重叠显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextCaptionBxArea_overlayWithBackfround(uint textarea);

        //设定是否显示背景
        // isbackgroundflag - 是否显示背景 true：显示 flase ：不显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextCaptionBxArea_setBackgroundFlag(uint textarea, bool isbackgroundflag);

        //设定透明度
        // transparency - 透明度 当该值大于等于0（不透明）小于等于100（全透明）时，以该北京区域为基准
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextCaptionBxArea_setTransparency(uint textarea, int transparency);

        //增加图片
        // filepath - 图片的路径和图片名称 例如："D:/a/图片.bmp"
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTextCaptionBxArea_addImageFile(uint* imagefile, uint textarea, string filepath);

        //加入页面
        // page - 页面
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTextCaptionBxArea_addPage(uint area, uint page);

        //增加文字内容
        // text - 文字内容
        // font - 字型
        // foreground - 文字颜色
        // background - 背景颜色
        // style - 图文区页面播放样式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTextCaptionBxArea_addText(uint* addedtext, uint textarea, string text, uint font, uint foreground, uint background, uint style);

        //增加文本
        // filepath - 纯文字文档
        // font - 字型
        // foreground - 文字颜色
        // background - 背景颜色
        // style - 图文区页面播放样式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTextCaptionBxArea_addTextFile(uint* addedtextfile, uint textarea, string filepath, uint font, uint foreground, uint background, uint style);

        // 清除所有页面
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTextCaptionBxArea_clearPages(uint textarea);

        //创建区域
        // x - x坐标
        // y - y坐标
        // width - 区域宽度
        // heigth - 区域高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxArea(uint* area, int x, int y, int width, int heigth, uint screenprofile);

        //取得字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getFont(uint* font, uint area);

        //取得边框
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getFrame(uint* frame, uint area);

        //取得高度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getHeigth(ushort* heigth, uint area);

        //取得屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getScreenProfile(uint* screenprofile, uint area);

        //取得屏幕宽度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getWidth(ushort* width, uint area);

        //取得X坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getX(ushort* X, uint area);

        //取得Y坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_getY(ushort* Y, uint area);

        //设定字型
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_setFont(uint area, uint font);

        //
        //
        //
        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxArea_validate(bool* validate, uint area, int p1, int p2, int p3);

        //创建时间区
        // x - x坐标
        // y - y坐标
        // width - 时间区宽度
        // heigth - 时间区高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DateTimeBxArea(uint* datetimearea, int x, int y, int width, int heigth, uint profile);

        //创建时间区
        // x - x坐标
        // y - y坐标
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DateTimeBxArea2(uint* datetimearea, int x, int y, uint profile);

        //创建天单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DayBxUnit(uint* dayunit, int x, int y, uint profile);

        //创建天单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        // mode - 显示内容类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DayBxUnit2(uint* dayunit, int x, int y, uint profile, int mode);

        //取得显示类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DayBxUnit_getMode(int* mode, uint dayunit);

        //设定显示类型
        // mode - 显示类型 0: 数字 1 - 30。 1: 农历初一 ~ 三十
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DayBxUnit_setMode(uint dayunit, int mode);

        //创建月单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_MonthBxUnit(uint* monthunit, int x, int y, uint profile);

        //创建月单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        // mode - 显示内容类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_MonthBxUnit2(uint* monthunit, int x, int y, uint profile, int mode);

        //取得显示类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int MonthBxUnit_getMode(int* mode, uint monthunit);

        //设定显示类型
        // mode - 显示类型 0: 数字。01 ~12。 1: 文字。一月 ~ 十二月。 2: 农历月。正月 ~ 腊月。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int MonthBxUnit_setMode(uint monthunit, int mode);

        //创建年单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_YearBxUnit(uint* yearunit, int x, int y, uint profile);

        //创建年单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        // mode - 显示内容类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_YearBxUnit2(uint* yearunit, int x, int y, uint profile, int mode);

        //取得显示类型
        [DllImport("onbon.apo.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int YearBxUnit_getMode(int* mode, uint yearunit);

        //设定显示类型
        // mode - 显示类型 0;四位西元年 1：两位西元年 2：天干地支 3：十二生肖
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int YearBxUnit_setMode(uint yearunit, int mode);

        //创建时单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_HourBxUnit(uint* hourunit, int x, int y, uint profile);

        //创建时单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        // mode - 显示内容类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_HourBxUnit2(uint* hourunit, int x, int y, uint profile, int mode);

        //取得显示类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int HourBxUnit_getMode(int* mode, uint hourunit);

        //设定显示类型
        // mode - 显示类型 0 : 24 小时制。 1 : 12 小时制。 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int HourBxUnit_setMode(uint hourunit, int mode);

        //创建分单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_MinuteBxUnit(uint* minuteunit, int x, int y, uint profile);

        //创建秒单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SecondBxUnit(uint* secondunit, int x, int y, uint profile);

        //创建周单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_WeekBxUnit(uint* weekunit, int x, int y, uint profile);

        //创建周单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        // abbreviate - 是否适用英文缩写
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_WeekBxUnit2(uint* weekunit, int x, int y, uint profile, bool abbreviate);

        //创建周单元
        // x - x坐标
        // y - y坐标
        // profile - 屏幕规格
        // lang - 显示语言
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_WeekBxUnit3(uint* weekunit, int x, int y, uint profile, int lang);

        //取得显示语言
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int WeekBxUnit_getLang(int* lang, uint weekunit);

        //设定显示语言
        // lang - 显示语言 0：中文上午下午 1： 英文AM/PM
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int WeekBxUnit_setLang(uint weekunit, int lang);

        //取得日期显示格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_getDateStyle(int* datestyle, uint datetimearea);

        //取得颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_getForeground(uint* foreground, uint datetimearea);

        //取得时间显示格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_getTimeStyle(int* timestyle, uint datetimearea);

        //取得周显示格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_getWeekStyle(int* weekstyle, uint datetimearea);

        //是否多行显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_isMultiline(bool* ismultiline, uint datetimearea);

        //设定日期显示格式  NULL表示不显示日期
        // datestyle - 日期显示格式 NULL 表示不显示日期。 0 - 农历 1 - 日期格式：MM - DD 2 - 日期格式：MM / DD 3 - 日期格式：MM月DD日 4 - 日期格式：YYYY - MM - DD 5 - 日期格式：YYYY / MM / DD 6 - 日期格式：YYYY年MM月DD日 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_setDateStyle(uint datetimearea, int datestyle);

        //设定字型
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_setFont(uint datetimearea, uint font);

        //设定颜色
        // foreground - 颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_setForeground(uint datetimearea, uint foreground);

        //设定是否多行显示
        // multiline - 多行显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_setMultiline(uint datetimearea, bool multiline);

        //设置时间显示格式  NULL表示不显示时间
        // timestyle - 时间显示格式 NULL 表示不显示时间。 0 - 时间格式：AMPM HH:MM 1 - 时间格式：HH:MM 2 - 时间格式：HH时MM分 3 - 时间格式：HH:MM AMPM 4 - 时间格式：HH:MM:SS 5 - 时间格式：HH时MM分SS秒 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_setTimeStyle(uint datetimearea, int timestyle);

        //设置想起显示格式  NULL表示不显示星期
        // weekstyle - 星期显示格式 NULL 表示不显示时间。 0: 星期格式：中文。 1: 星期格式：英文缩写。 2: 星期格式：英文。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_setWeekStyle(uint datetimearea, int weekstyle);

        //
        //
        //
        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DateTimeBxArea_validate(bool* validate, uint area, int p1, int p2, int p3);

        //创建时钟区
        // x - x坐标
        // y - y坐标
        // widt - 宽度
        // heigth - 高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ClockBxArea(uint* clockarea, int x, int y, int width, int heigth, uint screenprofile);

        //创建上午下午单元
        // x - x坐标
        // y - y坐标
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_AmPmBxUnit(uint* ampmunit, int x, int y, uint screenprofile);

        //创建上午下午单元
        // x - x 坐标
        // y - y坐标
        // screenprofile - 屏幕规格
        // lang - 显示语言 0 : 中文上下午。1 : 英文 AM/PM。 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_AmPmBxUnit2(uint* ampmunit, int x, int y, uint screenprofiel, int lang);

        //取得显示语言
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AmPmBxUnit_getLang(int* lang, uint unit);

        //设定显示语言
        // lang - 显示语言 0 : 中文上下午。1 : 英文 AM/PM
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AmPmBxUnit_setLang(uint unit, int lang);

        //创建模拟时钟单元
        // x - x坐标
        // y - y坐标
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_AnalogClockBxUnit(uint* analogclockunit, int x, int y, uint screenprofile);

        //设定时钟的大小
        // size - 时钟的大小
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AnalogClockBxUnit_setupSize(uint analogclockunit, ushort size);

        //取得开始年
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartYear(ushort* year, uint area);

        //取得开始月
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartMonth(byte* month, uint area);

        //取得开始日
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartDay(byte* day, uint area);

        //取得开始时
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartHour(byte* hour, uint area);

        //取得开始分
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartMinute(byte* minute, uint area);

        //取得开始秒
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartSecond(byte* second, uint area);

        //取得开始周
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartWeek(byte* week, uint area);

        //取得启动类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getBattleStartupMode(byte* mode, uint area);

        //取得时间差
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getTimeDifferent(ushort* timedifferent, uint area);

        //取得透明度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_getTransparency(byte* transparency, uint area);

        //是否显示背景
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_isBackgroundFlag(bool* isbackground, uint area);

        //显示背景遮罩的部分
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_maskByBackground(uint area);

        //与背景重叠显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_overlayWithBackground(uint area);

        //设定是否显示背景背景
        // isbackgroundflag - 是否显示背景
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBackgroundFlag(uint area, bool isbackgroundflag);

        //设定开始年
        // year - 开始年
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartYear(uint area, ushort year);

        //设定开始月
        // month - 开始月
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartMonth(uint area, byte month);

        //设定开始日
        // day - 开始日
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartDay(uint area, byte day);

        //设定开始时
        // hour - 开始时
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartHour(uint area, byte hour);

        //设定开始分
        // minute - 开始分
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartMinute(uint area, byte minute);

        //设定开始秒
        // second - 开始秒
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartSecond(uint area, byte second);

        //设定开始周
        // week - 开始周
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartWeek(uint area, byte week);

        //设定启动类型
        // mode - 启动类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setBattleStartupMode(uint area, byte mode);

        //设定时间差
        // timedifferent - 时间差
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setTimeDifferent(uint area, ushort timedifferent);

        //设定透明度  当该值大于等于0小于等于100时，以该背景区域为基准
        // transparency - 透明度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int AbstractTimeClockBxArea_setTransparency(uint area, byte transparency);

        //创建缺省时钟区
        // x - x坐标
        // y - y坐标
        // width - 宽度
        // heigth - 高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DefaultTimeClockBxArea(uint* area, int x, int y, int width, int heigth, uint screenprofile);

        //添加时间单元
        // unit - 时间单元
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DefaultTimeClockBxArea_addUnit(uint area, uint unit);

        //取得时间单元资讯
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DefaultTimeClockBxArea_getUnit(uint* unit, uint area, uint p1);

        //取得时间单元资讯数量
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DefaultTimeClockBxArea_getUnitSize(uint* unitszie, uint area);

        //创建计时区
        // x - x坐标
        // y - y 坐标
        // width - 计时区宽度
        // heigth - 计时区高度
        // p5 - 未知
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TimerBxArea(uint* area, int x, int y, int width, int heigth, int p5, uint screenprofile);

        //创建计时单元
        // x - 相对于计时区X坐标的X坐标
        // y - 相对于计时区Y坐标的Y坐标
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TimerBxUnit(uint* unit, int x, int y, uint screenprofile);

        //创建计时单元
        // x - 相对于计时区X坐标的X坐标
        // y - 相对于计时区Y坐标的Y坐标
        // profile - 屏幕规格
        // mode - 显示内容类型
        // counter - 计数值
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TimerBxUnit2(uint* unit, int x, int y, uint profile, int mode, int counter);

        //创建计时区格式
        // hour - 时候显示小时
        // minute - 是否显示分钟
        // second - 是否显示秒
        // millisecond - 是否显示毫秒
        // phour - 是否显示[小时]文字
        // pminute - 是否显示[分钟]文字
        // psecond - 是否显示[秒]文字
        // pmillisecond - 是否显示[毫秒]文字
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TimerBxUnitFormat(uint* format, bool hour, bool minute, bool second, bool millisecond, bool phour, bool pminute, bool psecond, bool pmillisecond);

        //获取组元
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxArea_getUnit(uint* unit, uint area);

        //获取计数值
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxUnit_getCounter(uint* counter, uint unit);

        //获取显示内容格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxUnit_getFormat(uint* format, uint unit);

        //取得秒表计算方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxUnit_getMode(int* mode, uint unit);

        //设定计数值
        // counter - 计数值  单位为毫秒
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxUnit_setCounter(uint unit, uint counter);

        //设定显示内容格式
        // format - 显示内容格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxUnit_setFormat(uint unit, uint format);

        //设定秒表计算方式
        // mode - 秒表计算方式 0：正计时累加 1：倒计时累加 2：正计时不累加 3：倒计时不累加
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TimerBxUnit_setMode(uint unit, int mode);

        //创建重整字单元
        // name - 重整单元名
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CompositeBxUnit(uint* unit, string name);

        //重整计算各单元的坐标
        // container - 原区域大小
        // x - 此单元配置X坐标
        // y - 此单元配置Y坐标
        // center - 是否居中
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_arrange(uint arrange, uint unit, int x, int y, bool center);

        //取得字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_getFont(uint font);

        //取得前景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_getForeground(uint foreground);

        //取得相对于时间区域X坐标的X坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_getX(uint x);

        //取得相对于时间区域Y坐标的Y坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_getY(uint y);

        //偏移x坐标
        // x - x坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_offsetX(uint unit, int x);

        //偏移Y坐标
        // y - Y坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_offsetY(uint unit, int y);

        //设定字型
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_setFont(uint unit, uint font);

        //设定前景色
        // foreground - 前景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CompositeBxUnit_setForeground(uint unit, uint forefround);

        //取得显示类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CommonBxUnit_getUnitMode(byte* mode, uint unit);

        //设定显示类型
        // meod - 显示类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CommonBxUnit_setUnitMode(uint unit, byte mode);

        //创建计时区
        // x - x坐标
        // y - y坐标
        // width - 计时区宽度
        // heigth - 计时区高度
        // desttime - 目标时间
        // screenprofiel - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CounterBxArea(uint* area, int x, int y, int width, int heigth, uint srceenprofile);

        //创建计时单元
        // x - 相对于计时区X坐标的X坐标
        // y - 相对于计时区Y坐标的Y坐标
        // sceenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CounterBxUnit(uint* unit, int x, int y, uint screenprofile);

        //创建计时单元
        // x - 相对于计时区X坐标的X坐标
        // y - 相对于计时区Y坐标的Y坐标
        // screenprofile - 屏幕规格
        // mode - 显示内容类型 0：正计时累加，1：倒计时累加，2：正计时不累加，3：倒计时不累加 
        // desttime - 目标时间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CounterBxUnit2(uint* unit, int x, int y, uint screenprofile, int mode, uint desttime);

        //创建计时单元格式
        // day - 是否显示天
        // hour - 是否显示时
        // minute - 是否显示分
        // second - 是否显示秒
        // pday - 是否显示[天]文字
        // phour - 是否显示[时]文字
        // pminute - 是否显示[分]文字
        // psecond - 是否显示[秒]文字
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CounterBxUnitFormat(uint* unit, bool day, bool hour, bool minute, bool second, bool pday, bool phour, bool pminute, bool psecond);

        //取得元组
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CounterBxArea_getUnit(uint* unit, uint area);

        //取得目标时间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CouterBxUnit_getDestTime(uint* desttime, uint unit);

        //取得显示内容格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CounterBxUnit_getFormat(uint* format, uint unit);

        //取得计时器计算方式
        [DllImport("onbob.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CounterBxUnit_getMode(uint* mode, uint unit);

        //设定慕白哦时间
        // desttime - 目标时间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CounterBxUnit_setDestTime(uint unit, uint desttime);

        //设定显示内容格式
        // format - 显示内容格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CounterBxUnit_setFormat(uint unit, uint format);

        //设定计时器计算方式
        // mode - 计时器计算方式 0：正计时累加，1：倒计时累加，2：正计时不累加，3：倒计时不累加
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int CounterBxUnit_SetMode(uint unit, int mode);

        //创建温度区
        // x - X坐标
        // y - Y坐标
        // width - 温度去宽度
        // heigth - 温度区高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TemperatureBxArea(uint* area, int x, int y, int width, int heigth, uint scrennprofile);

        //取得显示格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TemperatureBxArea_getDisplayUnitType(int* type, uint area);

        //设置显示格式
        // type - 显示格式 0-摄氏 1- 华氏
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TemperatureBxArea_setDisplayUnitType(uint area, int type);

        //创建湿度区
        // x - x坐标
        // y - y坐标
        // width - 宽度
        // heigth - 高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_HumidityBxArea(uint* area, int x, int y, int width, int heigth, uint screenprofile);

        //创建噪声区
        // x - X坐标
        // y - Y坐标
        // width - 区域宽度
        // heigth - 区域高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_NoiseBxArea(uint* area, int x, int y, int width, int heigth, uint screenprofile);

        //创建农历区
        // x - x坐标
        // y - y坐标
        // width - 宽度
        // heigth - 高度
        // screenprofile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_FestivalBxArea(uint* area, int x, int y, int width, int heigth, uint screenprofile);

        //创建农历单元
        // x - x相对于农历区X坐标的X坐标
        // y - y相对于农历区Y坐标的Y坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_FestivalBxUnit(uint* unit, int x, int y, uint screenprofile);

        //取得单元颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FestivalBxArea_getUnitColor(uint color);

        //设定单元颜色
        // unitcolor - 单元颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FestivalBxArea_setUnitColor(uint unit, uint unitcolor);

        //添加传感器区入口
        // alarmtype - 报警类型 0:低于临界值报警  1：高于临界值报警
        // value - 值
        // normal - 正常显示的颜色
        // alarm - 报警显示的颜色
        [DllImport("onbob.ap.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_addThreshold(uint area, int alarmtype, uint value, uint normalcolor, uint alarmcolor);

        //清除传感器入口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_clearThresholds(uint area);

        //取得修正值
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getCorrection(uint* correction, uint area);

        //取得修正极性
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getCorrectionPolar(int* polar, uint area);

        //取得浮点数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getNumberFloat(byte* number, uint area);

        //取得整数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getNumberInt(byte* number, uint area);

        //取得传感器类型  0: 温度  1： 湿度  2：噪声  0xff：全能
        [DllImport("obnon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getSensorMode(byte* mode, uint area);

        //取得传感器类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getSensorType(byte* type, uint area);

        //取得传感器单元
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getSensorUnit(byte* unit, uint area);

        //取得静态文字
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getStaticText(uint* text, uint area);

        //取得透明度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_getTransparency(uint area);

        //是否显示背景
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_isBackgroundFlag(bool* isbackground, uint area);

        //取得湿度是否显示小数位
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_isFractionPart(bool* isfractionpart, uint area);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_isSensorUnitFlag(bool* isunitflag, uint area);

        //显示背景遮罩的部分
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_maskByBackground(uint area);

        //与背景重叠显示
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_overlayWithBackground(uint area);

        //设定是否显示背景
        // isbackground - 是否显示背景
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setBackgroundFlag(uint area, bool isbackground);

        //设定修正值
        // correction - 修正值
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setCorrection(uint area, uint correction);

        //设定修正极性
        // correctionpolar - 修正极性
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setCorrectionPolar(uint area, int correctionpolar);

        //设定湿度是否显示小数位
        // fractionalpart - 是否显示小数位
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setFractionalPart(uint area, bool ispart);

        //设定浮点
        // float - 浮点
        [DllImport("onbon.ap.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setNumberFloat(uint area, byte nunberfloat);

        //设定整型
        // numberint - 整型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setNumberInt(uint area, byte numberint);

        //设定传感器类型
        // type - 传感器类型 温度：0-DS18B20; 1-SHT11; 湿度：0-SHT11; 1-AM2301; 噪声：0-AWA5636-3; 1-HS5633T; 2-AZ8921 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setSensorType(uint area, byte type);

        //设定传感器单元
        // flag - 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setSensorUnitFlag(uint area, bool flag);

        //设定静态文字
        // text - 静态文字
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setStaticText(uint area, string text);

        //设定透明度 当该值大于等0（不透明）小于等于100（全透明）时，以该背景区域为基准
        // transparency - 透明度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SensorBxArea_setTransparency(uint area, byte transparency);

        //创建二进制文本
        // width - 宽度
        // heigth - 高度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBinary(uint* binarytext, uint width, uint heigth);

        //取得背景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_getBackground(uint textbinary);

        //取得字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_getFont(uint textbinary);

        //取得前景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_getForeground(uint textbinary);

        //取得高度
        // heigth - 高度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_getHeigth(int* heigth, uint textbinary);

        //取得宽度
        // width - 宽度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_getWidth(int* width, uint textbinary);

        //设定背景色
        // background - 背景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_setBackground(uint textbinary, uint background);

        //设定字型
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_setFont(uint binarytext, uint font);

        //设定前景色
        // foreground - 前景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_setForeground(uint textbinary, uint foreground);

        //设定高度
        // heigth - 高度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_setHeigth(uint textbinary, int heigth);

        //设定款度
        // width - 宽度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBinary_setWidth(uint textbinary, int width);

        //创建文本页
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBxPage(uint* page);

        //创建文本页
        // text - 文字讯息
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBxPage2(uint* page, string text);

        //创建文本页
        // text - 文字讯息
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBxPage3(uint* page, string text, uint font);

        //创建文本页
        // text - 文字讯息
        // font - 字型
        // foreground - 文字颜色
        // backgroudn - 背景颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBxPage4(uint* page, string text, uint font, uint foreground, uint background);

        //创建文本页
        // text - 文字讯息
        // font - 字型
        // foreground - 文字颜色
        // baockground - 背景颜色
        // linebreak - 是否换行即换页
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBxPage5(uint* page, string text, uint font, uint foreground, uint background, bool linebreak);

        //创建文本页
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextBxPage6(uint* page, uint font);

        //创建图片文件页
        // filepath - 图片文档
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ImageFileBxPage(uint* page, string filepath);

        //创建纯文字档案
        // filepath - 纯文字档案
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextFileBxPage(uint* page, string filepath);

        //创建纯文字文档
        // filepath - 纯文字文档
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextFileBxPage2(uint* page, string filepath, uint font);

        //创建纯文字文档
        // filepath - 纯文字文档
        // font - 字型
        // foreground - 文字颜色
        // background - 背景颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_TextFileBxPage3(uint* page, string filepath, uint font, uint foreground, uint background);

        //取得背景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_getBackground(uint page);

        //取得字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_getFont(uint page);

        //取得文字色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxpage_getForegroud(uint page);

        //取得首尾相连间隔
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_getHeadTailInterval(uint page);

        //取得水平对齐方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_getHorizontalAlignment(uint page);

        //取得文字讯息
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_getText(uint page);

        //取得垂直对齐方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_getVerticalAlignment(uint page);

        //取得是否换行即换页
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_isLineBreak(uint page);

        //新增一行文字
        // text - 文字
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_newLine(uint* page1, uint page, string text);

        //设定背景色
        // background - 背景色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setBackgroun(uint page, uint background);

        //设定字型
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setFont(uint page, uint font);

        //设定文字色
        // foreground - 文字色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setForeground(uint page, uint foreground);

        //设定首尾相连间隔
        // headtaillnterval - 首尾相连间隔  >= 0:前后讯息间隔的像素   -2 ：前后讯息被隔离
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setHeadTaillnterval(uint page, int headtaillnterval);

        //设定水平对齐方式
        // horizontalalignment 0：居中 1：居左 2：居右
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setHorizontalAlignment(uint page, int horizontalaligment);

        //设定是否换行即换页
        // linebreak - 换行即换页
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setLineBreak(uint page, bool linebreak);

        //设定文字讯息
        // text - 文字讯息
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setText(uint page, string text);

        //设定垂直对齐方式
        // verticalalignment - 垂直对齐方式 0：居中 1：居上 2：居下
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextBxPage_setVerticalAlignment(uint page, int verticalalignment);

        //取得背景有效标记
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getValidFlag(byte* flag, uint page);

        //取得清屏方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getClearMode(byte* mode, uint page);

        //取得播放样式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getDisplayStyle(uint* style, uint page);

        //取得边框速率
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getFrameRate(byte* rate, uint page);

        //取得重复次数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getRepeatTime(byte* times, uint page);

        //取得声音标记
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getSoundFlag(byte* flag, uint page);

        //取得速度等级
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getSpeed(byte* speed, uint page);

        //取得停留时间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getStayTime(ushort* staytime, uint page);

        //取得有效长度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_getValidLen(ushort* len, uint page);

        //设置背景有效标记
        // p1 - 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setBgValidFlag(uint page, byte p1);

        //设定清屏方式
        // clearmode - 清屏方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setClearMode(uint page, byte clearmode);

        //设定播放样式
        // displaystyle - 播放样式 样式编号: 0:随机显示;1:静止显示;2:快速打出;3:向左移动;4:向左连移;5:向上移动; 6:向上连移;7:闪烁;8:飘雪;9:冒泡;10:中间移动; 11:左右移入;12:左右交叉移入;13:上下交叉移入;14:画卷闭合;15:画卷打开; 16:向左拉伸;17:向右拉伸;18:向上拉伸;19:向下拉伸;20:向左镭射; 21:向右镭射;22:向上镭射;23:向下镭射;24:左右交叉拉幕;25:上下交叉拉幕; 26:分散左拉;27:水平百叶;28:垂直百叶;29:向左拉幕;30:向右拉幕; 31:向上拉幕;32:向下拉幕;33:左右闭合;34:左右对开;35:上下闭合; 36:上下对开;37:向右移动;38:向右连移;39:向下移动;40:向下连移; 41:45度左旋;42:180度左旋;43:90度左旋;44:45度右旋;45:180度右旋; 46:90度右旋;47:菱形打开;48:菱形闭合 
        //若样式是向左连移或向上连移，stayTime 會设定為零。可在设定播放样式後再重新设定停留时间。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setDisplayStyle(uint page, uint displaystyle);

        //设定边框速率
        // rate - 边框速率
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setFrameRate(uint page, byte rate);

        //设定重复次数
        // repeattimes - 重复次数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setRepeatTime(uint page, byte times);

        //设定声音标记
        // p1
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setSoundFlag(uint page, byte p1);

        //设定速度等级
        // speed - 运行速度 0最快-63最慢
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setSpeed(uint page, uint speed);

        //设定停留时间
        // time - 停留时间 单位毫秒
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setStayTime(uint page, ushort time);

        //设定有效长度
        // len - 有效长度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxPage_setValidLen(uint page, ushort len);

        //增加图片
        // image - 图片
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ImageBxPage_addImage(uint page, uint image);

        //清除所有图片
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ImageBxPage_clearImages(uint page);

        //取得图片文件
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ImageFileBxPage_getFilePath(uint page);

        //取得换行处理方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextFileBxPage_getBreakType(uint page);

        //取得文字文档编码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextFileBxPage_getEncoding(uint page);

        //取得纯文字文档
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextFileBxPage_getFilePath(uint page);

        //设定换行处理方式
        // breaktype - 换行处理方式 0：追加 1：换行 2：换页
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextFileBxPage_setBreakType(uint page, int breaktype);

        //设定文字档案编码
        // encoding - 文字档案编码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextFileBxPage_setEncoding(uint page, string encoding);

        //设定纯文字档案
        // filepath - 纯文字档案
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int TextFileBxPage_setFilePath(uint page, string filepath);

        //创建标识文件
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_LogoBxFile(uint* file);

        //增加区域
        // area - 区域
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int LogoBxFile_addArea(uint logofile, uint area);

        //取得区域数量
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int LogoBxFile_getAreaCount(int* count, uint logofile);

        //取得文件名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int LogoBxFile_getFileName(uint logofile);

        //取得文件时间范围
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int LogoBxFile_getTimeSpan(int* span, uint logofile);

        //设置文件时间范围
        // span - 文件时间范围
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int LogoBxFile_setTimeSpan(uint logofile, int span);

        //取得单元显示内容字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_getFont(uint unit);

        //取得单元颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_getUnitColor(uint* color, uint unit);

        //取得相对于时间区X坐标的X坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_getUnitX(ushort* x, uint unit);

        //取得相对于时间区Y坐标的Y坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_getUnitY(ushort* y, uint unit);

        //设定单元显示内容字型
        // font - 字型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_setFont(uint unit, uint font);

        //设定对齐方式
        // align - 对齐方式 0:左对齐，1：居中，2：右对齐 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_setUnitAlign(uint unit, byte align);

        //设定单位颜色
        // color - 单位颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_setUnitColor(uint unit, uint color);

        //设定相对于时间区域X坐标的X坐标
        // x - x坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_setUnitX(uint unit, ushort x);

        //设定相对于时间区域Y坐标的Y坐标
        // y - y坐标
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxUnit_setUnitY(uint unit, ushort y);

        //创建文本单元
        // x - 相对于时间区域X坐标的X坐标
        // y - 相对于时间区域Y坐标的Y坐标
        // screenprofile - 屏幕规格
        // text - 文字内容
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_StringBxUnit(uint* unit, int x, int y, uint screenprofile, string text);

        //取得文字内容
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int StringBxUnit_getText(uint unit);

        //创建动态区
        // x - x坐标
        // y - y坐标
        // width - 区域宽度
        // heigth - 区域高度
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DynamicBxArea(uint* area, int x, int y, int width, int heigth, uint profile);

        //创建动态区规则
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DynamicBxAreaRule(uint* arearule);

        //新增动态区关联的异步节目。一旦关联了某个异步节目，则当改异步节目播放时允许播放该动态区
        // programid - 关联的节目编号 0 -999
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_addRelativeProgram(uint arearule, uint program);

        //取得动态区编号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_getId(int* id, uint arearule);

        //取得是否立即播放
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_getImmediatePlay(byte* immediate, uint arearule);

        //取得运行模式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_getRunMode(uint arearule);

        //取得动态区数据超时时间，单位为秒
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_getTimeout(uint arearule);

        //取得是否关联全部节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_isRelativeAllPrograms(uint arearule);

        //设定动态区编号
        // id - 动态区编号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_setId(uint arearule, int id);

        //设定是否立即播放
        // immediateplay - 是否立即播放
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_setImmediatePlay(uint arearule, byte immediateplay);

        //设定是否关联全部节目
        // relativeallprograms - 是否关联全部节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_setRelativeAllPrograms(uint arearule, bool relativeallprograms);

        //设定动态区运行模式
        // runmode - 运行模式  0：循环显示。 1：显示完成后静止显示最后一页数据。 2：循环显示，超过设定时间后数据仍未更新时不再显示。 3：循环显示，超过设定时间后数据仍未更新时显示 Logo信息。 4：循环显示，显示完最后一页后就不再显示。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_setRunMode(uint arearule, byte runmode);

        //设定动态区数据超时时间，单位为秒
        // timeout - 动态区数据超时时间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DynamicBxAreaRule_setTimeout(uint arearule, int timeout);

        //创建背景区
        // x - x坐标
        // y - y坐标
        // width - 区域宽度
        // heigth - 区域高度
        // profile - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BackgroundBxArea(uint* area, int x, int y, int width, int heigth, uint profile);

        //创建梯度背景区
        // x - x坐标
        // y - y坐标
        // width - 区域宽度
        // heigth - 区域高度
        // profiel - 屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_GradientBackgroundBxArea(uint* area, int x, int y, int width, int heigth, uint profiel);

        //和控制器通讯的相关接口
        //同步方式将节目写入控制器
        // program - 节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_writeProgram(uint screen, uint program);

        //同步方式将节目写入控制器，本方法不做任何检查
        // program - 节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_writeProgramQuickly(uint screen, uint program);

        //同步方式将节目写入控制器
        // programarrary - 节目组数组
        // programcount - 节目组数量
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_writePrograms(uint screen, uint* programarray, uint programcount);

        //更新动态区
        //返回执行结果
        // rule - 动态区播放方式
        // area - 动态区
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_writeDynamic(uint* result, uint screen, uint rule, uint area);

        //更新控制器位址，此位址用于标识控制器，不等同于TCP位址（xxx.xxx.xxx.xxx）
        // p1 - 控制器位址，2bytes。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_writeControllerAddress(uint* result, uint screen, ushort p1);

        //同步方式将屏幕参数写入控制器
        // ccf - 屏幕参数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_writeConfig(uint screen, uint ccf);

        //解除锁定节目
        //返回执行结果
        // programname - 节目名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_unlockProgram2(uint* result, uint screen, string programname);

        //解除节目锁定
        //返回执行结果
        // programid - 节目id 0-999
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_unlockProgram(uint* result, uint screen, int programid);

        //解除屏幕锁定
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_unlock(uint* result, uint screen);

        //强制开启屏幕
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_turnOn(uint* result, uint screen);

        //强制关闭节目
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_turnOff(uint* result, uint screen);

        //校正系统时钟
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_syncTime(uint* result, uint screen);

        //切换至服务器模式，执行成功后，会将当前连线切断
        //返回执行结果
        // staticsetting - 网络位址设置
        // serversetting - 服务器应用程式设置
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_switch2ServerMode(uint* result, uint screen, uint staticsetting, uint serversetting);

        //切换至一般网络模式，执行成功后，会将当前连线切断
        //返回执行结果
        // staticsetting - 网路位址设置
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_switch2ClientMode(uint* result, uint screen, uint staticsetting);

        //设定定时开关机
        //返货执行结果
        // cmd - 定时开关机，利用createTimingOnOff方法产生并设定定时开关机区间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_setupTimingOnOff(uint* result, uint screen, uint cmd);

        //设定屏幕名称
        // aliasname - 屏幕名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_setAliasName(uint screen, string aliasname);

        //修改传感器根据客制化传感器调整亮度
        //返回执行结果
        // env - 客制化传感器亮度条件
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_sensorBrightness2(uint* result, uint screen, uint env);

        //修改传感器自动调整亮度
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_sensorBrightness(uint* result, uint screen);

        //系统复位，复位后需要重新加载屏幕参数
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_reset2Factory(uint* result, uint screen);

        //查询目前控制器上的节目清单
        //返回节目清单
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_readProgramList(uint* programlist, uint screen);

        //同步方式读取控制上的档案并保存在本地
        //返回节目
        // programname - 节目名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_readProgram2(uint* progam, uint screen, string programname);

        //同步方式读取控制上的档案并保存在本地
        //返回节目
        // programid - 节目编号 0-999
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_readProgram(uint* program, uint screen, int programid);

        //读取控制器编号
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_readControllerId(uint* result, uint screen);

        //同步方式读取控制器上的屏幕参数
        //返回屏幕参数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_readConfig(uint* ccf, uint screen);

        //PING控制器
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_ping(uint* result, uint screen);

        //修改亮度。亮度值 0x00-0x0f
        //返回执行结果
        // level - 亮度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_manualBrightness(uint* result, uint screen, byte level);

        //锁定节目
        //返回执行结果
        // programname - 节目名称
        // lockduration - 锁定秒数
        // nonvolatile - 掉电保存方式。0x00：掉电不保存 0X01：掉电保存
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_lockProgram4(uint* result, uint screen, string programname, int lockduration, byte nonvolatile);

        //锁定节目
        //返回执行结果
        // programname - 节目名称
        // lockduration - 锁定秒数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_lockProgram3(uint* result, uint screen, string programname, int lockduration);

        //锁定节目
        //返回执行结果
        // progamid - 节目id 0-999
        // lockduration - 锁定秒数
        // nonvolatile - 掉电保存方式。0x00：掉电不保存 0x01：掉电保存
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_lockProgram2(uint* result, uint screen, int programid, int lockduration, byte nonvolatile);

        //锁定节目
        //返回执行结果
        // programid - 节目编号 0-999
        // lockduration - 锁定秒数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_lockProgram(uint* result, uint screen, int programid, int lockduration);

        //锁定屏幕
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_lock(uint* result, uint screen);

        //是否已经连线
        //返回连线与否
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_isConnected(bool* isconnected, uint screen);

        //取得运行模式 
        //返回运行模式 0: CLIENT 1: SERVER, 2: RS232, 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getRunMode(int* mode, uint screen);

        //取得net编号，ONBON内部唯一识别码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getNetId(uint* netid, uint screen);

        //取得控制器类型
        //返回控制器类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getControllerType(uint scrren);

        //取得控制器位址
        //返回控制器位址
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getControllerAddress(ushort* address, uint screen);

        //取得控制器
        //返回控制器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getController(uint* controller, uint screen);

        //取得屏幕名称
        //返回屏幕名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_getAlisaName(uint screen);

        //discover控制器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_discover(uint* result, uint screen);

        //删除控制器上的特定节目组
        //返回执行结果
        // programs - 控制器上的特定节目组
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_deletePrograms2(uint* result, uint screen, string programs);

        //删除控制器上的所有节目
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_deletePrograms(uint* result, uint screen);

        //删除控制器上的特定节目
        //返回执行结果
        // program - 控制器上的特定节目
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_deleteProgram2(uint* result, uint screen, string program);

        //删除控制器上的特定节目
        //返回执行结果
        // programid - 节目编号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_deleteProgram(uint* result, uint screen, int programid);

        //删除动态区
        //返回执行结果
        // p1
        // p2 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_deleteDynamic(uint* result, uint screen, byte* p1, uint p2);

        //删除所有动态区
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_deleteAllDynamic(uint* result, uint screen);

        //建立定时开关机。设定定时开关机区间后执行setupTimingOnOff将结果传送至控制器
        //返回定时开关机
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_createTimingOnOff(uint* onoff, uint screen);

        //修改根据时间调整亮度
        //返回执行结果
        // p1
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_clockBrightness(uint* result, uint screen, uint p1);

        //查询文件系统容量
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_checkMemVolumes(uint* result, uint screen);

        //查询当前固件状态
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_checkFirmware(uint* result, uint screen);

        //查询控制器状态
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_checkControllerStatus(uint* result, uint screen);

        //改变输出缓存大小。有效范围512Bytes-65KB。注意：改变缓存大小前请确认控制卡可接受上限，超过会造成控制卡运作失败。
        // buffersize - 缓存大小
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_changeOutputBuffer(uint screen, int buffersize);

        //取消定时开关机
        //返回执行结果
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_cancelTimingOnOff(uint* result, uint screen);

        //是否逾时
        //返回 true：若命令回应逾时
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxResponseCmd_isTimeout(uint cmd);

        //判断命令是否被正确执行
        //返回 true：命令被正确执行并回应对应结果。false：回应为NACK或逾时
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxResponseCmd_isOK(uint cmd);

        //回应是否为NACK
        //返回true：若回应为NACK
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxResponseCmd_isNACK(uint cmd);

        //取得错误码
        //返回错误码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxResponseCmd_getErrorType(uint cmd);



        //和控制器断开连接
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreen_disconnect(uint screen);

        //输出接口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FObject_dump(uint dump);

        //输出接口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FObject_dump(int dump);

        //输出接口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FObject_dump(bool dump);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FObject_release(uint release);

        //颜色接口
        // reb - 红
        // green - 绿
        // blue - 蓝
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_FColor(uint* foreground, byte red, byte green, byte blue);

        //创建字体
        // fontname - 字体名称，比如“宋体”
        // fontsize - 字体高度，像素点
        // italic - 是否斜体
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_FFont(uint* font, string fongname, uint fontsize, bool italic);

        //创建显示特技
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DisplayStyleFactory(uint* style);

        //其他接口
        //设定亮度条件
        // indexvalue - 半小时索引 0-47 （00：00 - 00：29）-（23：30-23：59）
        // brightness -  亮度 1-16
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxBrightnessClockEnv_setup(uint h, byte indexvalue, byte brightness);

        //设定亮度条件
        // brightnesslevel - 亮度等级 0-15
        // environment - 环境亮度值 0-65536
        // brightness - 亮度 1-16
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxBrightnessSensorEnv_setup(uint h, byte brightnesslevel, ushort environment, byte brightness);

        //得到当前传感器的亮度值
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnBrightnessValue_Data(uint* data, uint brigthness);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_Brightness(byte* brightness, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_BrightnessAdjMode(byte* mode, uint controller);

        //建立该控制器档案阅读程式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_createFileReader(uint controller);

        //建立该控制器档案写入程式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_createFileWriter(uint controller);

        //中断连线
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_disconnect(uint controller);

        //执行命令
        // cmd - 命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_execute(uint* result, uint controller, uint cmd);

        //获取BX信息
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getBx(uint* bx, uint controller);

        //取得控制器位址
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getControllerAddress(uint controller);

        //取得名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getName(uint controller);

        //取得输出缓存大小
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getOutputBuffer(uint controller);

        //取得运行模式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getRunMode(uint controller);

        //取得屏幕规格
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getScreenProfile(uint* profile, uint controller);

        //取得控制器系列咨询
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_getSeries(uint controller);

        //取得是否连线
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_isConnected(uint controller);

        //设定控制器位址，此位址用于标识控制器，不等同于TCP位址（xxx.xxx.xxx.xxx）
        // addr - 控制器位址，2bytes
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxController_setControllerAddress(uint controller, ushort addr);

        //启动
        // p1 - 位址
        // p2 - 端口号
        // p3 - 是否为TCP
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxControllerClient_connect(bool* err, uint controller, string p1, int p2, bool p3);

        //取得名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxControllerClient_getName(uint controller);

        //取得运行模式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxControllerClient_getRunMode(uint controller);

        //连线
        // comname - com名称
        // baudrate - 波特率
        // databits - 停止位元
        // parity - 同位检查
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxControllerRS_connect(uint controller, string comname, uint baudrate, byte databits, byte parity);

        //初始化
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxEnv_initial(uint env);

        //初始化
        // log4jfile - log4j配置档案
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxEnv_initial2(uint env, string log4jfile);

        //初始化
        // log4jfile - log4j配置档案
        // timeout - 通讯超时时间
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxEnv_initial3(uint env, string log4file, int timeout);

        //取得文件名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFile_getFileName(uint file);

        //取得文件类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFile_getFileType(uint file);

        //读取控制器上目前特定类型的文件清单
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_browse(uint* browse, uint file);

        //读取控制器上目前特定类型的文件清单
        // filetype - NULL表示全部的文件重类
        // dirsize - 一次最多读取多少个文件目录
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_browse2(uint* browse, uint file, uint filetype, byte dirsize);

        //同步读取控制器上的节目
        // programid - 节目编号 0-999
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgram(uint* program, uint file, int programid);

        //读取控制器上的节目并存档
        // programid - 节目编号 0-999
        // savepath - 本地存储完整的档案名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgram2(uint file, int programid, string savepath);

        //同步读取控制器上的节目
        // programfile - 节目名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgram3(uint* program, uint file, string programfile);

        //读取控制器上的节目并存档
        // programfile - 节目名称
        // savefile - 本地存储完整的档案名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgram4(uint file, string programfile, string savepath);

        //非同步读取空时期上的节目
        // programid - 节目编号 0-999
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgramAsync(uint file, int programid);

        //非同步读取控制器上的节目并存档
        // programid - 节目编号 0-999
        // savepath - 本地存储完整的档案名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgramAsync2(uint file, int programid, string savepath);

        //非同步读取控制器上的节目
        // programfile - 节目名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgramAsync3(uint file, string programfile);

        //非同步读取控制器上的节目并存档
        // programfile - 节目名称
        // savepath - 本地存储完整的档案名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgramAsync4(uint file, string programfile, string savepath);

        //同步读取控制器上的节目
        // programfile - 节目名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileReader_readProgramData(uint* program, uint file, string programfile);

        //同步将屏参写入控制器
        // ccf - 屏幕参数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxFileWrite_writeConfig(uint file, uint ccf);

        //主循环回调
        // p1 - 回调函数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServerListener_setLoopCallback(uint h, ONBON_CALLBACK p1);

        //断线回调
        // p1 - 回调函数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServerListener_setDisconnectedCallback(uint h, ONBON_CALLBACK p1);

        //连线回调
        // p1 - 回调函数
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServerListener_setConnectedCallback(uint h, ONBON_CALLBACK p1);

        //停止服务
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_stop(uint server);

        //开启服务
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_start(uint server);

        //移除监听器
        // listener - 监听器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_removeListener(uint server, uint listener);

        //服务器主循环
        // p1 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_loop(uint server, bool p1);

        //取得上线的屏幕控制程式
        // socketid - socket编号（识别编号）
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_getOnlineScreenBySocketId(uint* id, uint server, string socketid);

        //根据net编号或是GPRS的DTU编号取得先上的屏幕控制程式
        // netid - 控制器net编号或是GPRS的DTU编号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_getOnlineScreenByNetId(uint* id, uint server, string netid);

        //清除所有监听器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_clearListeners(uint server);

        //增加监听器
        // listener - 监听器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxServer_addListener(uint server, uint listener);

        //检查是否可进行工作
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenServer_isReady(bool* isready, uint server);

        //取得TCP通讯端口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenServer_getPort(ushort* port, uint server);

        //取得识别编号，等于socket编号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenServer_getId(int* id, uint server);

        //取得TCP位址
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenServer_getAddress(uint server);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenServer_accept(uint server);

        //设定双击设点阵类型
        // matrixtype - 双击色点阵类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_setMatrixType(uint screen, int matrixtype);

        //取得是否为全彩
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_isFullColor(uint screen);

        //取得屏幕宽度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_getWidth(uint screen);

        //取得双基色点阵类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_getMatrixType(uint screen);

        //取得屏幕高度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_getHeigth(uint screen);

        //取得基色类型
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_getColorType(uint screen);

        //取得转换成色码
        // color - 颜色
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_encodeColor(uint screen, uint color);

        //取得转换成颜色
        // color - 色码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_decodeColor(uint screen, uint color);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BxScreenProfile_createMessageConst(uint screen);

        //创建亮度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxBrightnessClockEnv(uint* brightness);

        //创建亮度传感器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxBrightnessSensorEnv(uint* brightnesssensor);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxEnv(uint* bxenv);

        //创建page页
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxPage(uint* page);

        //创建定时开关机命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CancelScreenTimingOnOffCmd(uint* cmd);

        //创建检查控制器状态命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CheckControllerStatusCmd(uint* cmd);

        //创建检查当前固件命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CheckCurrentFirmwareCmd(uint* cmd);

        //创建检查FPGA状态命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_CheckFPGAStatusCmd(uint* cmd);

        //创建删除动态区命令
        // isall 是否全部删除
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DeleteDynamicAreaCmd(uint* cmd, bool isall);

        //创建删除动态区命令
        // index - 动态区编号
        // size - 动态区大小
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DeleteDynamicAreaCmd2(uint* cmd, byte* index, uint size);

        //创建删除密码命令
        // secret - 旧密码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_DeleteSecretCmd(uint* cmd, byte* secret);

        //创建文件删除命令
        // ofs - 是否为节目类型档案
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_FileDeleteCmd(uint* cmd, bool ofs);

        //创建获取亮度值命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_GetBrightnessValueCmd(uint* cmd);

        //创建获取音量值命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_GetMemVolumeCmd(uint* cmd);

        //创建
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_GetTransStatusCmd(uint* cmd);

        //设定使用传感器自动检测亮度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ModifyBrightnessCmd(uint* cmd);

        //设定手动亮度
        // brightness - 亮度 0X00-0X0F
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ModifyBrightnessCmd2(uint* cmd, byte brightness);

        //设定定时亮度
        // p1 - 亮度条件
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ModifyBrightnessCmd3(uint* cmd, uint p1);

        //定制化传感器亮度。
        // env - 亮度条件
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ModifyBrightnessCmd4(uint* cmd, uint env);

        //创建快速设置地址命令
        // screenno - 屏号
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_QuicklySetAddrCmd(uint* cmd, ushort screenno);

        //创建快速设置名称命令
        // name - 名称
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_QuicklySetNameCmd(uint* cmd, string name);

        //创建读取控制器ID命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ReadControllerIDCmd(uint* cmd);

        //创建读取版权信息命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ReadCopyrightInfoCmd(uint* cmd);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ReadDirBlockCmd(uint* cmd, ushort p1);

        //
        // p1
        // p2
        // p3
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ReadFileBlockCmd(uint* cmd, bool p1, string p2, uint p3);

        //创建锁屏命令
        // p1 - 是否断电保存
        // p2 - 是否锁定
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ScreenLockCmd(uint* cmd, bool p1, bool p2);

        //创建定时开关屏命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ScreenTimingOnOffCmd(uint* cmd);

        //创建设置条码命令
        // barcode - 条码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SetBarCodeCmd(uint* cmd, byte* barcode);

        //创建设置密码命令
        // p1 - 6字节旧密码
        // p2 - 6字节新密码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SetSecretCmd(uint* cmd, byte* p1, byte* p2);

        //创建设置地址命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SetupAddressCmd(uint* cmd);

        //创建设置MAC地址命令
        // p1 - 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SetupMACCmd(uint* cmd, byte* p1);

        //创建开始读取目录命令
        // filetype - 文件类型
        // dirsize - 文件目录块大小
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_StartReadDirCmd(uint* cmd, byte filetype, byte dirsize);

        //创建开始读取文件命令
        // ofs - 是否为节目类型档案 
        // p2 - 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_StartReadFileCmd(uint* cmd, bool ofs, string p2);

        //
        // p1 - 是否开启
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SwitchOnOffScreenCmd(uint* cmd, bool p1);

        //创建系统锁定命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SystemClockCorrectCmd(uint* cmd);

        //创建系统重置命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_SystemResetCmd(uint* cmd);

        //创建更新动态区命令
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_UpdateDynamicAreaCmd(uint* cmd);

        //创建控制卡配置文件
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ControllerConfigBxFile(uint* file);

        //取得指定样式
        // styleid - 样式编号: 0:随机显示;1:静止显示;2:快速打出;3:向左移动;4:向左连移;5:向上移动; 6:向上连移;7:闪烁;8:飘雪;9:冒泡;10:中间移动; 11:左右移入;12:左右交叉移入;13:上下交叉移入;14:画卷闭合;15:画卷打开; 16:向左拉伸;17:向右拉伸;18:向上拉伸;19:向下拉伸;20:向左镭射; 21:向右镭射;22:向上镭射;23:向下镭射;24:左右交叉拉幕;25:上下交叉拉幕; 26:分散左拉;27:水平百叶;28:垂直百叶;29:向左拉幕;30:向右拉幕; 31:向上拉幕;32:向下拉幕;33:左右闭合;34:左右对开;35:上下闭合; 36:上下对开;37:向右移动;38:向右连移;39:向下移动;40:向下连移; 41:45度左旋;42:180度左旋;43:90度左旋;44:45度右旋;45:180度右旋; 46:90度右旋;47:菱形打开;48:菱形闭合 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int DisplayStyleFactory_getStyle(uint* style, uint displaystyle, int styleid);

        //创建文件格式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_FileType(uint* filetype);

        //取得边框移动步长，单位为pixel，范围 1-16
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_getFrameMoveStep(uint* movestep, uint frame);

        //取得边框显示速度 速度 1-48
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_getFrameSpeed(byte* speed, uint frame);

        //取得边框显示效果 0：闪烁。 1：顺时针转动。 2：逆时钟转动。 3：闪烁并顺时钟转动。 4：闪烁并逆时钟转动。 5：红绿交替闪烁。 6：红绿交替转动。 7：静止打出。
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_getFrameStyle(byte* style, uint frame);

        //取得边框宽度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_getFrameWdith(byte* width, uint frame);

        //取得是否显示边框
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_isFrameShow(bool* isshow, uint frame);

        //载入内建边框特效底图
        // styleindex - 内建效果编号，双基色1-18  单基色 1-14
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_loadFrameImage(byte* image, uint frame, int styleindex);

        //设置边框移动步长。单位pixel，范围1-16
        // movestep - 边框移动步长
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_setFrameMoveStep(uint frame, byte movestep);

        //设定是否显示边框
        // isshow - 是否显示边框
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_setFrameShow(uint frame, bool isshow);

        //设定边框显示速度
        // speed - 边框显示速度 1-48
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_setFrameSpeed(uint frame, byte speed);

        //设置边框显示效果
        // framestyle - 边框显示效果 0：闪烁。 1：顺时针转动。 2：逆时钟转动。 3：闪烁并顺时钟转动。 4：闪烁并逆时钟转动。 5：红绿交替闪烁。 6：红绿交替转动。 7：静止打出。 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FFrame_setFrameStyle(uint frame, byte framestyle);

        //添加需要删除的文件名
        // filename - 文件名
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FileDeleteCmd_addFileNames(uint cmd, string filename);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_ServerSetting(uint* setting);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_StaticSetting(uint* staticsetting);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FColor_A(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FColor_B(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FColor_G(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FColor_R(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_GetYear(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_GetMonth(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_GetDay(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_GetHour(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_GetMinute(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_GetSecond(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_SetYear(uint h, byte year);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_SetMonth(uint h, byte month);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_SetDay(uint h, byte day);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_SetHour(uint h, byte hour);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_SetMinuet(uint h, byte minute);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FDate_SetSecond(uint h, byte second);

        //
        // p1
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Fstrarr_add(uint h, string p1);

        //
        // p1
        // p2 
        // p3
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Fstrarr_get(uint h, uint p1, byte* p2, int p3);

        //获取字符串
        // p1
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FString_GetBuff(uint h, byte* p1);

        //获取字符串长度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int FString_Size(uint* result, uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Response_getError(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Response_isACK(uint h);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Response_isNACK(uint h);

        //得到控制器ID值
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerID_Data(uint* data, uint controller);

        //得到控制器barcode
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_BarCode(uint* barcode, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_curProgram(uint* program, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_CustomID(uint* id, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_Humidity(byte* humidity, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_Noise(ushort* noise, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_OnOffStatus(byte* status, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_ProgramLockStatus(byte* status, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_ProgramNumber(ushort* number, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcDate(byte* rtcdate, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcHour(byte* hour, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcMinute(byte* minute, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcMonth(byte* momth, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcSecond(byte* second, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcStatus(byte* status, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcWeek(byte* week, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RtcYear(byte* year, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_RunningMode(byte* mode, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int RetturnControllerStatus_ScreenLockStatus(byte* status, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_SwithMode(byte* mode, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_Temperature1(uint* temperature, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_Temperature2(uint* temperature, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnControllerStatus_TimingOnOff(byte* onoff, uint controller);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnCopyrightInfo_Data(uint* data, uint copyrightinfo);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnMemVolume_TotalMemVolume(uint* volume, uint memvolume);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_Address(ushort* address, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_Barcode(uint* barcode, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_Baudrate(uint* baudrate, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_ControllerType(ushort* type, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_CurrentBright(byte* bright, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_CurrentOnOffStatus(byte* onoff, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_Firmware(uint* firmware, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_Heigth(ushort* heigth, uint status);

        //
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ReturnPingStatus_Width(ushort* width, uint status);

        //增加定时开关机时间区段
        // onhour - 开机小时
        // onminute - 开机分钟
        // offhour - 关机小时
        // offminute - 关机分钟
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int ScreenTimingOnOffCmd_addTime(uint cmd, byte onhour, byte onminute, byte offhour, byte offminute);

        //取得控制器连接模式 0：单机直连（PC 与控制器直接连接）。 1：自动获取（DHCP）。 2：手动设置（Static IP）。 3：服务器模式（动态IP）。 
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SetupAddressCmd_getMode(int* mode, uint cmd);

        //取得服务器墨水位址组态
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SetupAddressCmd_getServerSetting(uint cmd);

        //取得静态位址组态
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SetupAddressCmd_getStaticSetting(uint cmd);

        //设置控制器连接模式
        // mode - 控制器连接模式 1： 自动获取IP（DHCP），2： 手动设置IP（static IP），3： 服务器模式（动态IP）
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SetupAddressCmd_setMode(uint cmd, byte mode);

        //设定服务器模式位址组态
        // serversetting - 服务器模式位址组态
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SetupAddressCmd_setServerSetting(uint cmd, uint serversetting);

        //设定静态位址组态
        // staticsetting - 静态位址组态
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int SetupAddressCmd_setStaticSetting(uint cmd, uint staticsetting);

        //设置网关
        // gateway - 网关
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int StaticSetting_setGateway(uint setting, byte* gateway);

        //设置IP
        // ip - ip地址
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int StaticSetting_setIP(uint setting, byte* ip);

        //设置端口
        // port - 端口
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int StaticSetting_setPort(uint setting, ushort prot);

        //设置子网掩码
        // mask - 子网掩码
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int StaticSetting_setSubNetMask(uint setting, byte* mask);

        //
        //addarea - 动态区
        // runmode - 动态区播放方式
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int UpdateDynamicAreaCmd_addArea(uint cmd, uint addarea, uint runmode);

        //创建串口控制器
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxControllerRS(uint* controller);

        //创建串口控制器
        // alias - 别名
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxControllerRS2(uint* controller, string alias);

        //创建串口控制器
        // alias - 别名
        // bx - 指定bx控制器系列
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int Create_BxControllerRS3(uint* controller, string alias, uint bx);

        //设定透明度。 1.当该值大于等于 0(不透明) 小于等于100(全透明) 时，以该背景区域为基准。 2.当该值为101时，采用如下算法：前景灰度不为0时，显示前景值，否则为背景值。 3.当该值为102时，采用如下算法：前景灰度不为0时，显示背景值，否则为前景值
        // p1 - 透明度
        [DllImport("onbon.api.dll", CallingConvention = CallingConvention.Cdecl)]
        public unsafe static extern int BackgroundBxArea_setTransparency(uint area, uint p1);

        #endregion

        //测试图文区 发送文本
        public static void SendText(string sendText, string strIp, uint fonSize)
        {
            unsafe
            {
                uint screen;
                uint program;
                uint area;
                uint page;
                uint profile;
                int nResult;
                uint font;
                nResult = Create_BxScreenClient(&screen);

                nResult = BxScreenClient_connect(screen, strIp, 5005, true);

                nResult = BxScreen_getProfile(&profile, screen);

                nResult = Create_ProgramBxFile(&program, 0, profile);

                nResult = Create_TextCaptionBxArea(&area, 0, 0, 160, 80, profile);

                nResult = Create_TextBxPage(&page);

                nResult = TextBxPage_setText(page, sendText);

                nResult = Create_FFont(&font, "宋体", fonSize, false);

                nResult = TextBxPage_setFont(page, font);

                nResult = AbstractTextCaptionBxArea_addPage(area, page);

                nResult = ProgramBxFile_addArea(program, area);

                nResult = BxScreen_writeProgram(screen, program);

                nResult = BxScreen_disconnect(screen);
            }
        }


        //测试图文区 发送文本，多一个尺寸
        public static void SendText(string sendText, string strIp, uint fonSize, int width, int height)
        {
            unsafe
            {
                uint screen;
                uint program;
                uint area;
                uint page;
                uint profile;
                int nResult;
                uint font;
                nResult = Create_BxScreenClient(&screen);

                nResult = BxScreenClient_connect(screen, strIp, 5005, true);

                nResult = BxScreen_getProfile(&profile, screen);

                nResult = Create_ProgramBxFile(&program, 0, profile);

                nResult = Create_TextCaptionBxArea(&area, 0, 0, width, height, profile);
                //nResult = Create_TextCaptionBxArea(&area, 0, 0, 64, 32, profile); //TEST

                nResult = Create_TextBxPage(&page);

                nResult = TextBxPage_setText(page, sendText);

                nResult = Create_FFont(&font, "宋体", fonSize, false);

                nResult = TextBxPage_setFont(page, font);

                nResult = AbstractTextCaptionBxArea_addPage(area, page);

                nResult = ProgramBxFile_addArea(program, area);

                nResult = BxScreen_writeProgram(screen, program);

                nResult = BxScreen_disconnect(screen);
            }
        }


        public class ONBON_CALLBACK
        {
        }
    }
}
