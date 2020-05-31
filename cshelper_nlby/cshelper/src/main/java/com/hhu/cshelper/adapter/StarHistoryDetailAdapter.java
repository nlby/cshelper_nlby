package com.hhu.cshelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.hhu.cshelper.R;

import java.util.List;

/** 书签/历史详情列表的适配器类
 * @name  StarHistoryDetailAdapter
 * @description  书签/历史详情列表的适配器类
 * @author  nlby
 * @date  2020/4/29
 */
public class StarHistoryDetailAdapter extends RecyclerView.Adapter<StarHistoryDetailAdapter.MyItemViewHolder>{
    private Context context;
    private List<String> titleList;
    private List<String> dateList;

    public StarHistoryDetailAdapter(Context context, List<String> titleList, List<String> dateList) {
        this.context = context;
        this.titleList  = titleList;
        this.dateList = dateList;
    }

    public void setItems(List<String> titleItems, List<String> dateItems){
        this.titleList = titleItems;
        this.dateList = dateItems;
    }

    @Override
    public StarHistoryDetailAdapter.MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_star_history_detail, parent, false);
        return new StarHistoryDetailAdapter.MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarHistoryDetailAdapter.MyItemViewHolder holder, int position) {
        holder.item_title.setText(titleList.get(position));
        holder.item_date.setText(dateList.get(position));
        final int p = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, p + "被点击了", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (titleList == null)
            return 0;
        return titleList.size();
    }

    public static class MyItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_title;
        public TextView item_date;
        public CheckBox item_check;

        MyItemViewHolder(View view) {
            super(view);
            item_title = view.findViewById(R.id.star_history_title);
            item_date = view.findViewById(R.id.star_history_date);
            item_check = view.findViewById(R.id.delete_check);
        }
    }
}
