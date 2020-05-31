package com.hhu.cshelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.hhu.cshelper.R;

import java.util.List;

/** 书签/历史列表显示的适配器类
 * @name  StarHistoryAdapter
 * @description  书签/历史列表显示的适配器类
 * @author  nlby
 * @date  2020/4/29
 */
public class StarHistoryAdapter extends RecyclerView.Adapter<StarHistoryAdapter.MyItemViewHolder>{

    private Context context;
    private List<String> nameList;
    private List<String> numList;

    public StarHistoryAdapter(Context context, List<String> nameList, List<String> numList) {
        this.context = context;
        this.nameList  = nameList;
        this.numList = numList;
    }

    public void setItems(List<String> nameItems, List<String> numItems){
        this.nameList = nameItems;
        this.numList = numItems;
    }

    @Override
    public StarHistoryAdapter.MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_star_history, parent, false);
        return new StarHistoryAdapter.MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarHistoryAdapter.MyItemViewHolder holder, int position) {
        holder.item_name.setText(nameList.get(position));
        holder.item_num.setText(numList.get(position));
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
        if (nameList == null)
            return 0;
        return nameList.size();
    }

    public static class MyItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public TextView item_num;

        MyItemViewHolder(View view) {
            super(view);
            item_num = view.findViewById(R.id.star_history_num);
            item_name = view.findViewById(R.id.star_history_name);
        }
    }
}
