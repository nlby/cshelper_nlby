package com.hhu.cshelper.activity.code;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.hhu.cshelper.R;
import com.hhu.cshelper.common.Constants;
import com.hhu.cshelper.model.repository.LocalRepository;

/** 代码列表页
 * @name  CodeShowActivity
 * @description  代码列表页
 * @author  nlby
 * @date  2020/4/29
 */
public class CodeShowActivity extends ListActivity {
    private  static final int ACTIVITY_CREATE = 0;
    private static  final int ACTIVITY_EDIT = 1;

    private static final int INSERT_ID = Menu.FIRST;
    private static  final int DELETE_ID = Menu.FIRST + 1;

    private Cursor cursor;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_show);
        context = this;

        renderListView();
    }

    // 渲染列表页面
    private void renderListView() {
        cursor = LocalRepository.getInstance().getAllCodes();
        startManagingCursor(cursor);


        String[] from = new String[]{"TITLE", "CREATE_AT"};
        int[] to = new int[]{R.id.text, R.id.created};
        SimpleCursorAdapter codes = new SimpleCursorAdapter(context, R.layout.activity_code_list, cursor, from, to);
        setListAdapter(codes);
    }

    // 选项菜单 添加记录和删除记录功能实现
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, "添加记录");
        menu.add(0, DELETE_ID, 0, "删除记录");
        return true;
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID: {
                createCode();
                return true;
            }
            case DELETE_ID: { // 这个作用不大
                LocalRepository.getInstance().deleteCodeById(getListView().getSelectedItemId());
                renderListView();
                return true;
            }
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void createCode() {
        Intent intent = new Intent(context, CodeEditActivity.class);
        startActivity(intent);
    }

    // 列表元素单击打开对应编辑页面
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = cursor;
        c.moveToPosition(position);
        Intent intent = new Intent(context, CodeEditActivity.class);
        intent.putExtra("_id", id);
        intent.putExtra("TITLE", c.getString(c.getColumnIndexOrThrow("TITLE")));
        intent.putExtra("BODY", c.getString(c.getColumnIndexOrThrow("BODY")));
        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    // 回退至列表页面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        renderListView();
    }



}

















