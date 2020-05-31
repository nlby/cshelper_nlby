package com.hhu.cshelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.hhu.cshelper.R;
import com.hhu.cshelper.entity.Note;
import java.util.List;

/** 将cardview的title content date呈现出来 调整每个item的显示参数
 * @name  MainPageAdapter
 * @description 将cardview的title content date呈现出来 调整每个item的显示参数
 * @author  dyh
 * @date  2020/4/29
 */



public class MainPageAdapter extends BaseAdapter<Note, MainPageAdapter.MainPageNoteItemView> {

    private static  boolean isGridMode = true;



    public MainPageAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected MainPageNoteItemView inflateView(Context context, ViewGroup parent) {
        return new MainPageNoteItemView(context);
    }

    public void setIsGridMode(boolean isGridMode) {
        this.isGridMode = isGridMode;
    }





    public class MainPageNoteItemView  extends ConstraintLayout  implements LocateAdapter<Note> {

        public TextView tvNoteTitle,tvNoteDate;
        public TextView tvNoteContent;

        /**
         * @name  MainPageNoteItemView
         * @description 设置每个note item的显示参数
         * @param
         * @return
         * @date  2020/4/29
         */

        public MainPageNoteItemView(Context context) {
            super(context);
            View v = LayoutInflater.from(context).inflate(R.layout.note_item , this  ,  true);
            tvNoteTitle = v.findViewById(R.id.note_item_title);
            tvNoteContent = v.findViewById(R.id.note_item_content);
            tvNoteDate = v.findViewById(R.id.note_item_date);
            adjustBodyLayoutParams();
        }



        /**
         * @name  bindDatatoView
         * @description 填充note item(cardview)的内容以显示
         * @param
         * @return
         * @date 2020/4/29
         */
        @Override
        public void bindDatatoView(Note data, int position) {
            tvNoteContent.setText( data.getContent());
            tvNoteDate.setText(data.getDate());
            tvNoteTitle.setText(data.getTitle());
        }


        /**
         * @name  adjustBodyLayoutParams
         * @description 调整每一个item的形状
         * @param
         * @return
         * @date
         */

        private void adjustBodyLayoutParams(){
            ViewGroup.LayoutParams params = tvNoteContent.getLayoutParams();
            params.width = mContext.getResources().getDisplayMetrics().widthPixels;
            if (isGridMode) {
                params.height = params.width / 2;
            } else {
                params.height = params.width / 6;
            }
            tvNoteContent.setLayoutParams(params);
        }



    }

}
