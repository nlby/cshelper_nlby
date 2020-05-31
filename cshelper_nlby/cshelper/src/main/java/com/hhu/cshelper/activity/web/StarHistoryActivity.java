package com.hhu.cshelper.activity.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hhu.cshelper.R;
import com.hhu.cshelper.adapter.StarHistoryAdapter;
import com.hhu.cshelper.model.repository.LocalRepository;

import java.util.ArrayList;
import java.util.List;

/** 书签历史页
 * @name  StarHistoryActivity
 * @description  书签历史页
 * @author  nlby
 * @date  2020/4/29
 */
public class StarHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private StarHistoryAdapter starHistoryAdapter;
    private List<String> typeList;
    private List<String> dateList;
    private List<String> starNumList;
    private List<String> historyNumList;
    private Context context;
    public boolean isStar = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_history_list);
        context = this;
        init_data();
        recyclerView = findViewById(R.id.recyclerView);
        starHistoryAdapter = new StarHistoryAdapter(context, typeList, starNumList) {
            @Override
            public void onBindViewHolder(final MyItemViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StarHistoryDetailActivity.class);
                        intent.putExtra("title", holder.item_name.getText().toString().trim());
                        intent.putExtra("isStar", isStar);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(starHistoryAdapter);
    }

    /**
     * @name  init_data
     * @description  初始化书签/历史记录列表数据
     * @params  []
     * @return  void
     * @date  2020/4/29
     */
    private void init_data() {
        typeList = new ArrayList<>(LocalRepository.getInstance().getTypesOfBookMark());
        dateList = new ArrayList<>(LocalRepository.getInstance().getDatesOfHistory());
        starNumList = new ArrayList<>(LocalRepository.getInstance().getNumOfType(typeList));
        historyNumList = new ArrayList<>(LocalRepository.getInstance().getNumOfDate(dateList));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.star_tab:  //书签页
                isStar = true;
                setTitleStatus();
                break;
            case R.id.history_tab: //历史记录页
                isStar = false;
                setTitleStatus();
                break;
        }
    }


    /**
     * @name  setTitleStatus
     * @description  切换页面显示书签/历史记录的状态
     * @params  []
     * @return  void
     * @date  2020/4/29
     */
    private void setTitleStatus() {
        if (isStar) {
            //设置书签状态
            starHistoryAdapter.setItems(typeList, starNumList);
        } else {
            //设置历史记录状态
           starHistoryAdapter.setItems(dateList, historyNumList);
        }
        starHistoryAdapter.notifyDataSetChanged();
    }
}
