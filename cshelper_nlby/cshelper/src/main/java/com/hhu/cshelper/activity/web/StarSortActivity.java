package com.hhu.cshelper.activity.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.BaseMVPActivity;
import com.hhu.cshelper.adapter.StarSortAdapter;
import com.hhu.cshelper.model.bean.local.BookMark;
import com.hhu.cshelper.presenter.BookMarkPresenter;
import com.hhu.cshelper.presenter.contract.BookMarkContract;
import com.hhu.cshelper.utils.ToastUtils;


import java.util.ArrayList;
import java.util.List;

/** 添加书签页
 * @name  StarSortActivity
 * @description  添加书签页
 * @author  nlby
 * @date  2020/4/29
 */
public class StarSortActivity extends BaseMVPActivity<BookMarkContract.Presenter>
        implements BookMarkContract.View, View.OnClickListener {


    private RecyclerView recyclerView;
    private StarSortAdapter starSortAdapter;
    private List<String> dataList;
    private Context context;

    private List<String> typeList;

    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_sort_list);
        context = this;
        attachView(bindPresenter());
        init_data();

        recyclerView = findViewById(R.id.recyclerView);
        starSortAdapter = new StarSortAdapter(context, dataList) {
            @Override
            public void onBindViewHolder(MyItemViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final String s = holder.item_name.getText().toString();
                holder.item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            typeList.add(s);
                            Toast.makeText(context, s + "被选择了", Toast.LENGTH_SHORT).show();
                        } else if(typeList.contains(s)) {
                            typeList.remove(s);
                        }
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(starSortAdapter);



    }


    /**
     * @name  init_data
     * @description  初始化书签分类数据
     * @params  []
     * @return  void
     * @date  2020/4/29
     */
    private void init_data() {
        dataList = new ArrayList<>(mPresenter.getTypesOfBookMark());
        typeList = new ArrayList<>();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                showSortDialog();
                break;
            case R.id.star_btn:
                String s = "";
                for (String t : typeList) {
                    s = s + t + " ";
                    BookMark bookMark = null;
                    if (title != null && title.length() > 0) {
                        bookMark = new BookMark(title, url, t);
                        mPresenter.saveBookMark(bookMark);
                    }
                }
                ToastUtils.show(context, s);
                finish();
                break;
        }
    }


    /**
     * @name  showSortDialog
     * @description  显示添加分类输入框
     * @params  []
     * @return  void
     * @date  2020/4/29
     */
    public void showSortDialog() {

        new MaterialDialog.Builder(this)
                .title("添加书签分类")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 200, R.color.colorAccent)
                .input("分类名称", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.equals("")) {
                            ToastUtils.show(context, "内容不能为空！");
                        } else {
                            ToastUtils.show(context, "新增类型为" + input);
                            dataList.add(input.toString());
                            starSortAdapter.notifyItemInserted(starSortAdapter.getItemCount());
                        }
                    }
                })
                .positiveText("确定")
                .show();
    }




    @Override
    protected BookMarkContract.Presenter bindPresenter() {
        return new BookMarkPresenter();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Throwable e) {

    }

}
