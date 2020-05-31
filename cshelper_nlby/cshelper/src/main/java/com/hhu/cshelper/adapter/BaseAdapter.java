package com.hhu.cshelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


/** RecycleView Adapter的抽象基类，用来定义一些所有Adapter通用的属性方法MainPageAdapter 将会继承它
 * @name  BaseAdapter
 * @description RecycleView Adapter的抽象基类，用来定义一些所有Adapter通用的属性方法
 *              MainPageAdapter 将会继承它
 * @author  dyh
 * @date   2020/4/29
 */


public abstract class BaseAdapter<T, V extends LocateAdapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //private static final String TAG = "Mynote.BaseAdapter";
    protected Context mContext;
    protected List<T> mDatas;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public BaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    protected abstract V inflateView(Context context, ViewGroup parent);

    /**
     * @name  refreshData
     * @description 下拉swiperefreshlayout时，触发刷新note
     * @param  notes
     * @return
     * @date  2020/4/29
     */
    public void refreshData(List<T> datas) {
        if (datas == null) return;
        this.mDatas = datas;
        notifyDataSetChanged();
    }





    /**
     * @name
     * @description 设置每个item的点击事件
     * @param
     * @return
     * @date 2020/4/29
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View) inflateView(mContext, parent);
        if (mItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, (int) v.getTag());
                }
            });
        }


        if (mItemLongClickListener != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onItemLongClick(v, (int) v.getTag());
                    return true;  // 返回true，拦截onClick事件
                }
            });
        }

        return new RecyclerView.ViewHolder(itemView) {
        };
    }





    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LocateAdapter itemView = (LocateAdapter) holder.itemView;
        holder.itemView.setTag(position);
        itemView.bindDatatoView(getItemData(position) , position);
    }



    T getItemData(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }else return null;
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mItemClickListener = onItemClickListener;
    }



    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas==null ? 0 : mDatas.size();
    }


}
