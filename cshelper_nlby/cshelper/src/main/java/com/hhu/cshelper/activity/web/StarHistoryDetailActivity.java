package com.hhu.cshelper.activity.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hhu.cshelper.R;
import com.hhu.cshelper.adapter.StarHistoryDetailAdapter;
import com.hhu.cshelper.model.bean.local.BookMark;
import com.hhu.cshelper.model.bean.local.History;
import com.hhu.cshelper.model.repository.LocalRepository;
import com.hhu.cshelper.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/** 书签历史详情页
 * @name  StarHistoryDetailActivity
 * @description  书签历史详情页
 * @author  nlby
 * @date  2020/4/29
 */
public class StarHistoryDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private StarHistoryDetailAdapter starHistoryDetailAdapter;
    private List<String> titleList;
    private List<String> dateList;
    private List<Long> keyList;
    private Context context;
    private TextView textView;

    private List<BookMark> bookMarks;
    private List<History> histories;
    private boolean isStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_history_detail_list);
        context = this;
        init_data();
        recyclerView = findViewById(R.id.recyclerView);
        starHistoryDetailAdapter = new StarHistoryDetailAdapter(context, titleList, dateList) {
            @Override
            public void onBindViewHolder(final MyItemViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "";
                        if (isStar) {
                            url = bookMarks.get(position).getUrl();
                        } else {
                            url = histories.get(position).getUrl();
                        }
                        Intent intent = new Intent(context, WebSingleActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
                holder.item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Long key = 0l;
                        if (isStar) {
                            key = bookMarks.get(position).getId();
                        } else {
                            key = histories.get(position).getId();
                        }
                        if (b) {
                            keyList.add(key);
                            Toast.makeText(context, key + "被选择了", Toast.LENGTH_SHORT).show();
                        } else if(keyList.contains(key)) {
                            keyList.remove(key);
                        }
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(starHistoryDetailAdapter);

        textView = findViewById(R.id.head);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (title != null && title.length() > 0) {
            textView.setText(title);
        }
    }

    /**
     * @name  init_data
     * @description  初始化书签/历史记录详情数据
     * @params  []
     * @return  void
     * @date  2020/4/29
     */
    private void init_data() {
        keyList = new ArrayList<>();
        Intent intent = getIntent();
        isStar = intent.getBooleanExtra("isStar", true);
        if (isStar) {
            String type = intent.getStringExtra("title");
            bookMarks = LocalRepository.getInstance().getBookMarkByType(type);
            titleList = new ArrayList<>();
            dateList = new ArrayList<>();
            for (BookMark bookMark: bookMarks) {
                titleList.add(bookMark.getTitle());
                dateList.add(bookMark.getCreateAt());
            }
        } else {
            String date = intent.getStringExtra("title");
            histories = LocalRepository.getInstance().getHistoryByDate(date);
            titleList = new ArrayList<>();
            dateList = new ArrayList<>();
            for (History history: histories) {
                titleList.add(history.getTitle());
                dateList.add(history.getDayTime());
            }
        }

    }

    /**
     * @name  onClick
     * @description  删除书签/历史记录
     * @params  [view]
     * @return  void
     * @date  2020/4/29
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_btn:
                if (isStar) {
                    LocalRepository.getInstance().deleteBookMarks(keyList);
                } else {
                    LocalRepository.getInstance().deleteHistoriess(keyList);
                }
                ToastUtils.show(context, "删除" + keyList.size() + "条记录成功");
                finish();
                break;
        }
    }

}
