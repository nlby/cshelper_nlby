package com.hhu.cshelper.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/** Snackbar封装工具类
 * @name  SnackbarUtils
 * @description  Snackbar封装工具类
 * @author  nlby
 * @date  2020/4/29
 */
public class SnackbarUtils {


    public static void show(View view) {
        show(view, null);
    }

    public static void show(Context context, String msg) {
        show(((Activity) context), msg);
    }

    public static void show(Activity activity, String msg) {
        show(activity.getWindow().getDecorView(), msg);
    }


    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

}
