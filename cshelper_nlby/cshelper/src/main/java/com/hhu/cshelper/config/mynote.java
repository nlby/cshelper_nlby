package com.hhu.cshelper.config;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;



import java.lang.reflect.Method;

public class mynote {


    private static Context scontext;
    private static Handler sHandler;


    public static void init(Context context) {
        scontext = context;
        sHandler = new Handler(Looper.getMainLooper());
        //initTheme();
    }


    /*
    private static void initTheme() {

        String themeKey = getAppContext().getResources().getString(R.string.preference_key_theme);
        String themeValueNight = getAppContext().getResources().getString(R.string.theme_value_night);
        String themeValueDefault = getAppContext().getResources().getString(R.string.theme_value_default);
        if (SharedPreferencesUtil.getInstance().getString(themeKey, themeValueDefault).equals(themeValueNight)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

     */




    public static Context getAppContext() {
        return scontext;
    }



    public static Handler getMainThreadHandler() {
        return sHandler;
    }




}
