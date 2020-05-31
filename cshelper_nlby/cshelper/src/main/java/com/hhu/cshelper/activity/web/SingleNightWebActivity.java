package com.hhu.cshelper.activity.web;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import com.google.android.material.navigation.NavigationView;
import com.hhu.cshelper.ExitApplication;
import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.system.SettingActivity;
import com.hhu.cshelper.activity.system.UserInfoActivity;
import com.hhu.cshelper.model.repository.RemoteRepository;
import com.hhu.cshelper.ui.activity.MainActivity;
import com.hhu.cshelper.utils.ProgressUtils;
import com.hhu.cshelper.utils.SnackbarUtils;
import com.hhu.cshelper.utils.ThemeManager;
import com.hhu.cshelper.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;

/** 夜间模式
 * @name  SingleNightWebActivity
 * @description  夜间模式
 * @author  nlby
 * @date  2020/4/29
 */
public class SingleNightWebActivity extends SingleViewWebActivity implements View.OnClickListener {
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.getInstance().init(this); // 获得主题管理器
        setTheme(R.style.Theme_AppCompat_NoActionBar); // 设置无工具栏（暗色）主题
        setContentView(R.layout.activity_web_singlenight);
        context = this;
        activity = this;
        code = initCode(); // 读取css文件
        initNavLeft();
        initNavRight();
        initWeb("https://baidu.com"); // 初始化WebView
    }



/**       页面主要内容配置    start    **/
    /**
     *  初始化WebView内容及配置
     */
    @Override
    protected void initWeb(String url) {
        wv = findViewById(R.id.webView);
        configWebView(wv, url, new SingleNightWebActivity.NightWebViewClient());
    }

    /**
     *  自定义的WebView暗色客户端 实现一些功能性的处理 包括各种事件监听 注入暗色css样式  有bug 只能处理部分页面 且速度慢
     */
    private class NightWebViewClient extends CustomWebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            wv.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var style = document.createElement('style');" + "style.type = 'text/css';" + "style.innerHTML = window.atob('" + code + "');" + "parent.appendChild(style)" + "})();");
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            wv.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var style = document.createElement('style');" + "style.type = 'text/css';" + "style.innerHTML = window.atob('" + code + "');" + "parent.appendChild(style)" + "})();");
        }
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            wv.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var style = document.createElement('style');" + "style.type = 'text/css';" + "style.innerHTML = window.atob('" + code + "');" + "parent.appendChild(style)" + "})();");
        }
    }

    /**
     *  读取css样式文件
     */
    private String initCode() {
        InputStream is = context.getResources().openRawResource(R.raw.night);
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[is.available()];
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Base64.encodeToString(buffer, Base64.NO_WRAP);

    }
/**       页面主要内容配置    end    **/



/**       页面侧滑菜单配置    start    **/
    /**
     *  左侧侧滑菜单实现 每个web界面都需要单独设置
     */
    @Override
    public void initNavLeft() {
        super.initNavLeft();
        nav_left.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_user: {
                        intent = new Intent(getBaseContext(), UserInfoActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_info:
                        intent = new Intent(getBaseContext(), StarHistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_sync: {
                        ProgressUtils.show(context, "正在上传书签...");
                        SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
                        String userId = sharedPreferences.getString("userId", "");
                        // 注意此处调用的作用是，批量发送请求，但请求都是异步的，因此无法在此处直接进行响应判断，而是应该在响应的回调方法中调用该界面的回调方法
                        // 因此特意在父类中添加了一个全局变量 activity 作为参数以使其可以进行回调
                        RemoteRepository.getInstance().syncBookMark(userId, activity);
                        break;
                    }
                    case R.id.nav_note:
                        // ToastUtils.show(context, "笔记功能尚未实现");
                        intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_sync_note: {
                        ProgressUtils.show(context, "正在上传笔记...");
                        SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
                        String userId = sharedPreferences.getString("userId", "");
                        // 注意此处调用的作用是，批量发送请求，但请求都是异步的，因此无法在此处直接进行响应判断，而是应该在响应的回调方法中调用该界面的回调方法
                        // 因此特意在父类中添加了一个全局变量 activity 作为参数以使其可以进行回调
                        RemoteRepository.getInstance().syncNote(userId, activity);
                        break;
                    }
                    case R.id.nav_free: {
                        intent = new Intent(getBaseContext(), FreeWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_tab: {
                        intent = new Intent(getBaseContext(), TabWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_night: {
                        intent = new Intent(getBaseContext(), SingleNightWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_full: {
                        intent = new Intent(getBaseContext(), FullWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_private: {
                        intent = new Intent(getBaseContext(), PrivateWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_toggle: {
                        ToastUtils.show(context, "当前模式无工具栏");
                        break;
                    }
                    case R.id.nav_resp: {
                        intent = new Intent(getBaseContext(), CodeWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_community: {
                        intent = new Intent(getBaseContext(), BBSWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_data: {
                        intent = new Intent(getBaseContext(), DataWebActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_setting: {
                        intent = new Intent(getBaseContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_exit:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("确定要退出程序？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                ExitApplication.getInstance().exit();
                                System.exit(0);
                                finish();
                            }
                        });
                        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                        break;
                    default:
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /**
     *  同步书签/笔记数据的成功回调方法 因处理批量数据，故需计数判断
     */
    @Override
    public void syncProcess(int count, int size) {
        super.syncProcess(count, size);
        if (count == size) {  // 当响应成功的请求数与发送时的请求数一致时，同步成功
            ProgressUtils.dismiss();
            RemoteRepository.getInstance().setCount(0);  // 注意此处必须置0 否则影响下次上传操作
            SnackbarUtils.show(context, "同步成功");
        }
    }

    /**
     *  同步数据的失败回调方法
     */
    @Override
    public void syncFail(Throwable e) {
        super.syncFail(e);
        ProgressUtils.dismiss();
        if (e.getMessage() != null && e.getMessage().length() > 0) {
            SnackbarUtils.show(context,e.getMessage());
        } else {
            SnackbarUtils.show(context,"响应失败，请重试");
        }
    }
/**       页面侧滑菜单配置    end    **/



/**       页面底部菜单配置    start    **/
    /**
     *  底部菜单功能实现： 待定
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forward: {
                wv.goForward();
                break;
            }
            case R.id.btn_star: {
                Intent intent = new Intent(context, StarSortActivity.class);
                intent.putExtra("title", wv.getTitle());
                intent.putExtra("url", wv.getUrl());
                startActivity(intent);
                break;
            }
            case R.id.btn_refresh: {
                wv.reload();
                break;
            }
        }
    }
/**       页面底部菜单配置    end    **/



/**       其他功能配置    start    **/

/**       其他功能配置    end    **/
}
