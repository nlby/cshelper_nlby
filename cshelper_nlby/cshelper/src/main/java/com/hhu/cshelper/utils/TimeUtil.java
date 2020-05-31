package com.hhu.cshelper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 设置日期显示格式的模板
 * @name  TimeUtil
 * @description 设置日期显示格式的模板
 * @author  dyh
 * @date  2020/4/29
 */
public class TimeUtil {


    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    // nlby:  获取时间字符串 上传使用
    public static String convert(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
    }
}