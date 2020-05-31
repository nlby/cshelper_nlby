package com.hhu.cshelper.activity.web;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;
import com.hhu.cshelper.ExitApplication;
import com.hhu.cshelper.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/** Web界面基类
 * @name  BaseWebActivity
 * @description  Web界面基类
 * @author  nlby
 * @date  2020/4/29
 */
public class BaseWebActivity extends AppCompatActivity {
    protected DrawerLayout drawer;
    protected NavigationView nav_left;
    protected NavigationView nav_right;
    protected ActionBarDrawerToggle toggle;

    protected BaseWebActivity activity;
    protected boolean isHide = true;  // 是否隐藏工具栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
    }



    /**
     *  WebView的通用配置
     */
    protected void configWebView(WebView webView, String url, WebViewClient webViewClient) {
        //加载初始页面
        webView.loadUrl(url);
        //覆盖默认通过第三方或系统浏览器打开网页的行为，使网页在WebView内打开
        webView.setWebViewClient(webViewClient);

        WebSettings settings = webView.getSettings();
        // 启用 js 功能
        settings.setJavaScriptEnabled(true);
        // 将图片调整到适合 WebView 的大小
        settings.setUseWideViewPort(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  // 注意这个配置
        settings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 9; zh-cn; Mi Note 3 Build/PKQ1.181007.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.141 Mobile Safari/537.36 XiaoMi/MiuiBrowser/11.11.17");

        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        // 支持缩放，默认为true。是下面那个的前提。
        settings.setSupportZoom(true);
        // 设置内置的缩放控件。若为false，则该 WebView 不可缩放
        settings.setBuiltInZoomControls(true);
        // 隐藏原生的缩放控件
//        settings.setDisplayZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置可以访问文件
        settings.setAllowFileAccess(true);
        // 支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 设置默认编码格式
        settings.setDefaultTextEncodingName("utf-8");
        // 本地存储
        settings.setDomStorageEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        // 资源混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }



    /**
     *  工具栏基本设置
     */
    protected void initToolBar() {
        getSupportActionBar().setTitle("CS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer); // 自定义home图标
        getSupportActionBar().hide();
    }



    /**
     *  左侧滑菜单实现
     */
    protected void initNavLeft() {
        drawer = findViewById(R.id.drawer);
        nav_left = findViewById(R.id.nav_left);
        // 设置显示随机图片 start  点击切换
        initNavLeftDrawHead();
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     *  右侧滑菜单实现
     */
    protected void initNavRight() {
        nav_right = findViewById(R.id.nav_right);
        initNavRightDrawHead();
    }


    /**
     *  同步书签数据的成功回调方法 因处理批量数据，故需计数判断
     */
    public void syncProcess(int count, int size) {}

    /**
     *  同步数据的失败回调方法
     */
    public void syncFail(Throwable e) {};


    /**
     *  设置左菜单首部布局
     */
    @SuppressWarnings("all")
    private void initNavLeftDrawHead() {
        View drawerHeader = nav_left.inflateHeaderView(R.layout.nav_left_head);
        WebView musicWeb = drawerHeader.findViewById(R.id.webView);
        configWebView(musicWeb, "https://jxiaoc.github.io/animeMusic/demo.html", new WebViewClient() {
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
        });
    }

    /**
     *  设置右菜单首部布局
     */
    @SuppressWarnings("all")
    private void initNavRightDrawHead() {
        View drawerHead = nav_right.inflateHeaderView(R.layout.nav_right_head);
        final ImageView imageView = drawerHead.findViewById(R.id.imageView);
        new AsyncTask<String, Void, String>() {
            Bitmap bitmap;
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    InputStream is = url.openStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                imageView.setImageBitmap(bitmap);
            }
        }.execute("https://acg.yanwz.cn/api.php");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<String, Void, String>() {
                    Bitmap bitmap;
                    @Override
                    protected String doInBackground(String... strings) {
                        try {
                            URL url = new URL(strings[0]);
                            InputStream is = url.openStream();
                            bitmap = BitmapFactory.decodeStream(is);
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        imageView.setImageBitmap(bitmap);
                    }
                }.execute("https://acg.yanwz.cn/api.php");
            }
        });
        // 设置显示随机图片 end
    }

    /**
     *  Web界面单例实现需要
     */
    protected void onNewIntent(Intent intent) {super.onNewIntent(intent);};


    /**
     *  处理横屏崩溃
     */
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

}
