package com.hhu.cshelper.activity.web;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.navigation.NavigationView;
import com.hhu.cshelper.ExitApplication;
import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.code.CodeEditActivity;
import com.hhu.cshelper.activity.code.CodeShowActivity;
import com.hhu.cshelper.adapter.CustomPagerAdapter;
import com.hhu.cshelper.model.bean.local.History;
import com.hhu.cshelper.model.repository.LocalRepository;

import java.util.List;

/** 多页卡Web界面基类
 * @name  MutiViewWebActivity
 * @description  多页卡Web界面基类
 * @author  nlby
 * @date  2020/4/29
 */
public class MutiViewWebActivity extends BaseWebActivity {

    protected Context context;
    protected ViewPager vp;
    protected List<View> viewList;
    protected List<String> titleList;
    protected WebView wv;
    protected CustomPagerAdapter customPagerAdapter;


/**       页面主要内容配置    start    **/
    /**
     *  初始化WebView内容及配置
     */
    protected void initWeb(View view, String url) {
        final WebView wv = view.findViewById(R.id.webView);
        configWebView(wv, url, new MutiViewWebActivity.CustomWebViewClient());
    }


    /**
     *  改写按键--返回的逻辑，实现页面的后退
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();
                return true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("确定退出当前模式或退出程序？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
//                        System.exit(0);
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
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *  自定义的WebView客户端 实现一些功能性的处理 包括各种事件监听
     */
    protected class CustomWebViewClient extends WebViewClient {

        // 处理自动篡改协议的情况
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
            if (!url.startsWith("http")) {
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
                wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView); // 解决了bug
                WebBackForwardList history = wv.copyBackForwardList();    // 一定要使用传入的参数view 错误 事实证明引起了bug
                nav_right.getMenu().clear();
                String info = "";
                for (int i = 0; i < history.getSize(); i++) {
                    WebHistoryItem h = history.getItemAtIndex(i);
                    System.out.println(h.getTitle() + h.getUrl());
                    info = h.getTitle();
                    if (info == null || info.length() == 0) {  // 处理没有title的情况
                        info = h.getUrl();
                    }
                    nav_right.getMenu().add(0, i, 1, info);
                }
                // 点击某条历史记录跳转至相应页面
                final WebView view1 = wv;
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

            History history = new History(view.getTitle(), view.getUrl());
            if (view.getTitle() != null && view.getTitle().length() > 0) {
                LocalRepository.getInstance().saveHistory(history);
            }

            View drawHead = nav_right.getHeaderView(0);
            EditText editText = drawHead.findViewById(R.id.nav_right_address);
            editText.setText(wv.getUrl());

        }
    }
/**       页面主要内容配置    end    **/



/**       页面顶部工具栏配置    start    **/
    /**
     *  给工具栏选项菜单设置布局及实现搜索框功能
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        // 获得菜单项中的SearchView
        SearchView searchView = (SearchView)item.getActionView();
        // 设置SearchView属性
        searchView.setQueryHint("输入关键词，百度一下");
        searchView.setSubmitButtonEnabled(true);
        // 为searchView添加事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 输入后点击回车改变文本
            public boolean onQueryTextSubmit(String query) {
                wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView);  //此处需要
                query = "https://baidu.com/s?wd=" + query;
                wv.loadUrl(query);
                return false;
            }
            @Override
            // 随着输入改变文本
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    /**
     *  工具栏选项点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 关联home和drawer
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView); // 获得当前页卡的wv
        switch (item.getItemId()){
            case R.id.action_back:
                wv.goBack();
                break;
            case R.id.action_forword:
                wv.goForward();
                break;
            case R.id.action_flush:
                wv.reload();
                break;
            case R.id.action_all:
                Intent intent = new Intent(context, CodeShowActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add:
                intent = new Intent(context, CodeEditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_exit:
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
        }
        return super.onOptionsItemSelected(item);
    }
/**       页面顶部工具栏配置    end    **/

    /**
     *  右侧侧滑菜单实现
     */
    public void initNavRight() {
        super.initNavRight();
        View drawHead = nav_right.getHeaderView(0);
        final EditText et_address = drawHead.findViewById(R.id.nav_right_address);
        Button bt_star = drawHead.findViewById(R.id.nav_right_star);
        Button bt_clear = drawHead.findViewById(R.id.nav_right_clear);
        Button bt_refresh = drawHead.findViewById(R.id.nav_right_refresh);
        Button bt_go = drawHead.findViewById(R.id.nav_right_go);
        bt_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), StarSortActivity.class);
                intent.putExtra("title", wv.getTitle());
                intent.putExtra("url", wv.getUrl());
                startActivity(intent);
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav_right.getMenu().clear();
                wv.clearHistory();
            }
        });
        bt_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.reload();
            }
        });
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.loadUrl(et_address.getText().toString().trim());
            }
        });
    }
}
