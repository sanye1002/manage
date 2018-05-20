package com.sanye.manage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 下午 6:40
 * 当前时间
 */

public class GetTimeUtil {
    public static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
    public static String getMonth(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(new Date());
    }
}
