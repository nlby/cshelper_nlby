package com.hhu.cshelper.activity.code;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.BaseMVPActivity;
import com.hhu.cshelper.common.Constants;
import com.hhu.cshelper.presenter.CodePresenter;
import com.hhu.cshelper.presenter.contract.CodeContract;

/** 代码编辑页
 * @name  CodeEditActivity
 * @description  代码编辑页
 * @author  nlby
 * @date  2020/4/29
 */
public class CodeEditActivity extends BaseMVPActivity<CodeContract.Presenter>
        implements CodeContract.View, View.OnClickListener {
    private Context context;
    private EditText editText;
    private EditText editText2;
    private Long rowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_edit);
        context = this;
        attachView(bindPresenter());  // MVP 绑定 v 和 p

        editText = findViewById(R.id.title);
        editText2 = findViewById(R.id.body);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("TITLE");
            String body = extras.getString("BODY");
            rowId = extras.getLong("_id");
            if (title != null) {
                editText.setText(title);
            }
            if (body != null) {
                editText2.setText(body);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save: {
                String title = editText.getText().toString().trim();
                String body = editText2.getText().toString().trim();
                if (!title.equals("") && !body.equals("")) {
                    if (rowId != null) { // 已在数据库中的更新
                        mPresenter.updateCode(rowId, title, body);
                    } else { // 未在数据库中的新增
                        mPresenter.createCode(title, body, "1111", "1111");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("保存成功");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("标题与内容不能为空！");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                break;
            }
            case R.id.delete: {
                if (rowId != null) {
                    mPresenter.deleteCodeById(rowId);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("删除成功");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                break;
            }
        }
    }


    @Override
    protected CodeContract.Presenter bindPresenter() {
        return new CodePresenter();
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onFailure(Throwable e) {
    }



}
