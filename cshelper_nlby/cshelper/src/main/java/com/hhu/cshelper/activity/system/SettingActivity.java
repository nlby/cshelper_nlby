package com.hhu.cshelper.activity.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.web.FreeWebActivity;
import com.hhu.cshelper.utils.GlideCacheUtil;
import com.hhu.cshelper.utils.ThemeManager;
import com.hhu.cshelper.utils.ToastUtils;

/** 设置页
 * @name  SettingActivity
 * @description  设置页
 * @author  nlby
 * @date  2020/4/29
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.getInstance().init(this); // 获得主题管理器
        setContentView(R.layout.activity_setting);
        context = this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_theme:
                showUpdateThemeDialog();
                break;
            case R.id.setting_clear:
                showCacheDialog();
                break;
            case R.id.setting_about:
                Intent intent = new Intent(context, AboutPageActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    /**
     *  显示修改主题色 Dialog
     */
    protected void showUpdateThemeDialog() {
        final String[] themes = ThemeManager.getInstance().getThemes();
        new MaterialDialog.Builder(context)
                .title("选择主题")
                .titleGravity(GravityEnum.CENTER)
                .items(themes)
                .negativeText("取消")
                .itemsCallback((new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        ThemeManager.getInstance().setTheme(SettingActivity.this, themes[position]);
                    }
                }))
                .show();
    }

    /**
     * 显示清除缓存对话框
     */
    public void showCacheDialog() {

        new MaterialDialog.Builder(context)
                .title("清除缓存(图片），该操作不建议使用")
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    GlideCacheUtil.getInstance().clearImageDiskCache(context);
                    ToastUtils.show(context, "清除成功");
                })
                .negativeText("取消")
                .show();
    }
}
