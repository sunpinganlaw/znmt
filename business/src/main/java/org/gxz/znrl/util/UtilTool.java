package org.gxz.znrl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xieyt on 14-12-14.
 */
public class UtilTool {
    //获取上一天的日期 yyyymmdd
    public static String getThisDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    //获取明天的日期 yyyymmdd
    public static String getNextDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    //获取上一天的日期 yyyymmdd
    public static String getThisTruncDate() {
        return getThisDate()+" 00:00:00";
    }

    //获取上一天的日期 yyyymmdd
    public static String getThisEndDate() {
        return getThisDate()+" 23:59:59";
    }

    public static void main(String[] args){
        System.out.println("UtilTool.getThisTruncDate():"+UtilTool.getThisTruncDate());
        System.out.println("UtilTool.getThisEndDate():"+UtilTool.getThisEndDate());
    }
}
