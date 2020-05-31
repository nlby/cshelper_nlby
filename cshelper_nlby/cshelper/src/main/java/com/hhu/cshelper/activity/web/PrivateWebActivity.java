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
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

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

/** 无痕模式
 * @name  PrivateWebActivity
 * @description  无痕模式
 * @author  nlby
 * @date  2020/4/29
 */
public class PrivateWebActivity extends SingleViewWebActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.getInstance().init(this); // 获得主题管理器
        setContentView(R.layout.activity_web_private);
        context = this;
        activity = this;
        initNavLeft();
        initNavRight();
        initWeb("https://baidu.com");
    }



/**       页面主要内容配置    start    **/
    /**
     *  初始化WebView内容及配置
     */
    @Override
    protected void initWeb(String url) {
        wv = findViewById(R.id.webView);
        configWebView(wv, url, new PrivateWebViewClient());
    }

    /**
     *  自定义的WebView暗色客户端 实现一些功能性的处理 包括各种事件监听 注入暗色css样式  有bug 只能处理部分页面 且速度慢
     */
    private class PrivateWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".apk")) {
                return true;
            } else if (url.startsWith("tel:")) {
                return true;
            } else if (url.startsWith("mailto:")) {
                return true;
            } else if (url.startsWith("baidu")) {
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        // 页面加载完成后，将该页面的相关信息在右侧菜单中显示
        // 此处还应将历史记录写入自定义数据库
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (nav_right != null) {
                WebBackForwardList history = view.copyBackForwardList();    // 一定要使用传入的参数view
                nav_right.getMenu().clear();
                String info = "";
                for (int i = 0; i < history.getSize(); i++) {
                    WebHistoryItem h = history.getItemAtIndex(i);
                    //                    System.out.println(h.getUrl());
                    System.out.println(h.getTitle() + h.getUrl());
                    info = h.getTitle();
                    if (info == null || info.length() == 0) {  // 处理没有title的情况
                        info = h.getUrl();
                    }
                    nav_right.getMenu().add(0, i, 1, info);
                }
                final WebView view1 = view;
                nav_right.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        WebBackForwardList history = view1.copyBackForwardList();
                        WebHistoryItem h = history.getItemAtIndex(id);
                        System.out.println("------------------" + id);
                        view1.loadUrl(h.getUrl());
                        return false;
                    }
                });
            }

            View drawHead = nav_right.getHeaderView(0);
            EditText editText = drawHead.findViewById(R.id.nav_right_address);
            editText.setText(view.getUrl());
        }
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
                        if (isHide) {
                            getSupportActionBar().show();
                            isHide = false;
                        } else {
                            getSupportActionBar().hide();
                            isHide = true;
                        }
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



/**       其他功能配置    start    **/

/**       其他功能配置    end    **/
}
