package com.hhu.cshelper;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hhu.cshelper.activity.web.FreeWebActivity;
import com.hhu.cshelper.config.mynote;

/** 定制的程序入口类
 * @name  CustomApplication
 * @description  定制的程序入口类
 * @author  nlby
 * @date  2020/4/29
 */
public class CustomApplication extends Application {
    public static CustomApplication application;
    private static Context context;
    private Intent intent;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        mynote.init(this);

//        SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
//        if (sharedPreferences.getString("username", "").equals("")) {
//            intent = new Intent(context, LoginSignActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else {  // 仅为演示效果，实际不会跳转至登录页
//            intent = new Intent(context, LoginSignActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }

//        SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
//        if (!sharedPreferences.getString("username", "").equals("")) {
//            intent = new Intent(context, FreeWebActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
