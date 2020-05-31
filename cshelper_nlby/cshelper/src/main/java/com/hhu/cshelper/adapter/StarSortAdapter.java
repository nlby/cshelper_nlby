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

/** 显示书签分类列表的适配器类
 * @name  StarSortAdapter
 * @description  显示书签分类列表的适配器类
 * @author  nlby
 * @date  2020/4/29
 */
public class StarSortAdapter extends RecyclerView.Adapter<StarSortAdapter.MyItemViewHolder> {

    private Context context;
    private List<String> dataList;

    public StarSortAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList  = dataList;
    }

    public void setItems(List<String> items){
        this.dataList=items;
    }

    @Override
    public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_star_sort, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyItemViewHolder holder, int position) {
        holder.item_name.setText(dataList.get(position));

        final int p = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, p + "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
//        final String s = holder.item_name.getText().toString();
//        holder.item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    Toast.makeText(context, s + "被选择了", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;
        return dataList.size();
    }

    public static class MyItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public CheckBox item_check;

        MyItemViewHolder(View view) {
            super(view);
            item_check = view.findViewById(R.id.star_type_check);
            item_name = view.findViewById(R.id.star_type_name);
        }
    }
}
