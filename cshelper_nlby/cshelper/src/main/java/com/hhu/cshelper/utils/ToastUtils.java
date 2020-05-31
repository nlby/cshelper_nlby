package com.hhu.cshelper.utils;

import android.content.Context;
import android.widget.Toast;

/** 单例Toast
 * @name  ToastUtils
 * @description  单例Toast
 * @author  nlby
 * @date  2020/4/29
 */
public class ToastUtils {

    public static Toast mToast;

    /**
     * @name  show
     * @description  显示Toast
     * @params  [context, messageResId]
     * @return  void
     * @date  2020/4/29
     */
    public static void show(final Context context, final String message){
        if (mToast == null){
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

   /**
    * @name  show
    * @description  显示Toast
    * @params  [context, messageResId]
    * @return  void
    * @date  2020/4/29
    */
    public static void show(final Context context, final int messageResId){
        if (mToast == null){
            mToast = Toast.makeText(context, messageResId, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(messageResId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
