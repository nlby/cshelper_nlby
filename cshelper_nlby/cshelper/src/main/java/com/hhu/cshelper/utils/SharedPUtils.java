package com.hhu.cshelper.utils;

import android.content.Context;
import android.content.SharedPreferences;


/** 主题切换辅助类
 * @name  SharedPUtils
 * @description  主题切换辅助类
 * @author  nlby
 * @date  2020/4/29
 */
public class SharedPUtils {

    public final static String USER_INFOR = "userInfo";
    public final static String USER_SETTING = "userSetting";



    /**
     * 获取当前用户主题
     */
    public static String getCurrentTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        if (sp != null)
            return sp.getString("theme", "酷炫黑");
        return null;
    }

    /**
     * 获取当前用户主题
     */
    public static void setCurrentTheme(Context context, String theme) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (editor != null) {
            editor.putString("theme", theme);
            editor.commit();
        }
    }

    /**
     * 判断是否第一次进入APP
     * @param context
     * @return
     */
    public static boolean isFirstStart(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        boolean isFirst= sp.getBoolean("first", true);
        //第一次则修改记录
        if(isFirst)
            sp.edit().putBoolean("first", false).commit();
        return isFirst;
    }
}
