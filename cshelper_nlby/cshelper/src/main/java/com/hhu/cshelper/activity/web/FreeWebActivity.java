package com.hhu.cshelper.activity.web;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;

import android.view.DragEvent;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import com.google.android.material.navigation.NavigationView;
import com.hhu.cshelper.ExitApplication;
import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.system.SettingActivity;
import com.hhu.cshelper.activity.system.UserInfoActivity;
import com.hhu.cshelper.adapter.CustomPagerAdapter;
import com.hhu.cshelper.effect.LoopTransformer;
import com.hhu.cshelper.model.repository.RemoteRepository;
import com.hhu.cshelper.ui.activity.MainActivity;
import com.hhu.cshelper.utils.ProgressUtils;
import com.hhu.cshelper.utils.SnackbarUtils;
import com.hhu.cshelper.utils.ThemeManager;
import com.hhu.cshelper.utils.ToastUtils;

import java.util.ArrayList;
/** 自由模式
 * @name  FreeWebActivity
 * @description  自由模式
 * @author  nlby
 * @date  2020/4/29
 */
public class FreeWebActivity extends MutiViewWebActivity implements View.OnClickListener {
    private View v1;
    private View v2;
    private View v3;
    private View v4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.getInstance().init(this); // 获得主题管理器
        setContentView(R.layout.activity_web_free);
        context = this;
        activity = this;  // 同步数据的方法参数
        initToolBar();
        initNavLeft();
        initNavRight();
        initViewPager();
    }



/**       页面主要内容配置    start    **/
    /**
     *  ViewPager相关的配置
     */
    private void initViewPager() {
        //获得视图对象，加入视图列表，标题加入标题列表
        viewList = new ArrayList<>();
        titleList = new ArrayList<>();
        v1 = View.inflate(context, R.layout.viewpager_child, null);
        v2 = View.inflate(context, R.layout.viewpager_child, null);
        v3 = View.inflate(context, R.layout.viewpager_child, null);
        v4 = View.inflate(context, R.layout.viewpager_child, null);
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        viewList.add(v4);
        titleList.add("首页");
        titleList.add("工具箱");
        titleList.add("github");
        titleList.add("学校");
        //设置页面内容
        setView();
        //创建设置适配器
        vp = findViewById(R.id.viewPager);
        customPagerAdapter = new CustomPagerAdapter(viewList, titleList);
        vp.setAdapter(customPagerAdapter);
        //设置viewpager相关的事件监听
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView);
                wv.reload();
            }
            @Override
            public void onPageSelected(int position) {
                wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView);
                wv.reload();
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView);
                wv.reload();
            }
        });
        // 设置页卡切换的动画效果
        vp.setPageTransformer(false, new LoopTransformer());
    }

    /**
     *  进行ViewPager配置时进行调用，用于进行每个页卡的WebView的配置
     */
    private void setView() {
        initWeb(v1, "file:///android_asset/nav/index.html");
        initWeb(v2, "http://tool.mkblog.cn/");
        initWeb(v3, "https://github.com/");
//        initSurfaceView();
        initWeb(v4, "http://www.hhu.edu.cn/");
    }

    private void initSurfaceView() {
        SurfaceView surfaceView = v4.findViewById(R.id.surfaceView);
        /**通过android.view.SurfaceView 获得 android.view.SurfaceHolder 实例*/
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        /**添加回调*/
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource("https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo/60_24e01d9734b6b1cb51a9148ead8940c0.mp4");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDisplay(surfaceHolder);
            }
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });
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



/**       页面底部菜单配置    start    **/
    /**
     *  底部菜单功能实现： 添加页卡、收藏、删除页卡
     */
    @Override
    public void onClick(View view) {
        wv = customPagerAdapter.getPrimaryItem().findViewById(R.id.webView);
        switch (view.getId()) {
            case R.id.btn_add_page: {
                showAddPageDialog();
                  break;
            }
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
            case R.id.btn_delete_page: {
                int index = viewList.indexOf(customPagerAdapter.getPrimaryItem());
                titleList.remove(index);
                System.out.println(index + "----------------------");
                viewList.remove(index);
                System.out.println(viewList.size() + "----------------------");
                customPagerAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     *  显示添加页卡输入框
     */
    public void showAddPageDialog() {

        new MaterialDialog.Builder(this)
                .title("添加页卡")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 200, R.color.colorAccent)
                .input("分类名称", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.equals("")) {
                            ToastUtils.show(context, "内容不能为空！");
                        } else {
                            ToastUtils.show(context, "新增页卡名为" + input);
                            titleList.add(input.toString());
                            View customView = View.inflate(context, R.layout.viewpager_child, null);
                            initWeb(customView, "https://jxiaoc.github.io/animeMusic/demo.html");
                            viewList.add(customView);
                            customPagerAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .positiveText("确定")
                .show();
    }
/**       页面底部菜单配置    end    **/



/**       其他功能配置    start    **/

/**       其他功能配置    end    **/

}
