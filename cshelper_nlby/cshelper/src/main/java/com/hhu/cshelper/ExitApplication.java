package com.hhu.cshelper;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**  控制程序完全退出
 * @name  ExitApplication
 * @description
 * @author  nlby
 * @date  2020/5/31
 */
public class ExitApplication extends Application {
    private List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;

    private ExitApplication() {

    }
    //单例模式中获取唯一的MyApplication实例
    public static ExitApplication getInstance() {
        if (instance == null) {
            instance = new ExitApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

}
