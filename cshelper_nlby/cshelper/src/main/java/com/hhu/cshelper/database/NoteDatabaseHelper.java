package com.hhu.cshelper.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hhu.cshelper.config.mynote;
import com.hhu.cshelper.entity.Note;

import java.util.ArrayList;
import java.util.List;

/** 数据库的增删改查
 * @name  NoteDatabaseHelper
 * @description 数据库的增删改查
 * @author  dyh
 * @date  2020/4/29
 */

public class NoteDatabaseHelper {


    private static NoteDatabaseHelper notedatabasehelper;
    private final DatabaseHelper databasehelper;

    public static NoteDatabaseHelper getInstance() {
        if (notedatabasehelper == null) {
            synchronized (NoteDatabaseHelper.class) {
                if (notedatabasehelper == null) {
                    notedatabasehelper = new NoteDatabaseHelper(mynote.getAppContext());
                }
            }
        }
        return notedatabasehelper;
    }

    private NoteDatabaseHelper(Context appContext){
        databasehelper = new DatabaseHelper(appContext);
    }


    /**
     * @name  insert
     * @description 增加操作 将note加入到数据库中
     * @param
     * @return
     * @date  2020/4/29
     */

    public void insert(Note note){
        String table = DatabaseHelper.ALL_NOTE_TABLE_NAME;
        String sql = "insert into " + table;
        sql += "(_id, title, content, date, address, timestamp,lastmodify) values(null, ?,?, ?, ?, ?, ?)";
        SQLiteDatabase sqlite = databasehelper.getWritableDatabase();
        sqlite.execSQL(sql, new String[]{note.getTitle(), note.getContent(), note.getDate(),
                note.getAddress(), note.getTimestamp() + "", note.getLastModify() + ""});
//        System.out.println(note.getTimestamp() + "-------");  测试timestamp
        sqlite.close();
    }

    /**
     * @name  delete
     * @description 根据id删除note
     * @param
     * @return
     * @date  2020/4/29
     */
    public void delete(int id){
        String table = DatabaseHelper.ALL_NOTE_TABLE_NAME;
        SQLiteDatabase sqlite = databasehelper.getWritableDatabase();
        String sql = ("delete from " + table + " where _id=?");
        sqlite.execSQL(sql, new Integer[]{id});
        sqlite.close();
    }


    /**
     * @name  update
     * @description 将已有的note进行更新内容
     * @param
     * @return
     * @date  2020/4/19
     */
    public void update(Note note) {
        String table = DatabaseHelper.ALL_NOTE_TABLE_NAME;
        SQLiteDatabase sqlite = databasehelper.getWritableDatabase();
        String sql = ("update " + table +
                " set title=?, content=?, date=?, address=?, timestamp=?, lastmodify=? where _id=?");
//        System.out.println(note.getTimestamp() + "-------");   测试timestamp
        sqlite.execSQL(sql,
                new String[]{note.getTitle(), note.getContent(), note.getDate(), note.getAddress(),
                        note.getTimestamp() + "", note.getLastModify() + "",  note.getId() + ""});
        sqlite.close();
    }


    /**
     * @name  query
     * @description 查询符合条件的notes
     * @param
     * @return
     * @date  2020/4/29
     */
    public List<Note> query(String where) {
        String table = DatabaseHelper.ALL_NOTE_TABLE_NAME;
        SQLiteDatabase sqlite = databasehelper.getReadableDatabase();
        ArrayList<Note> data = new ArrayList<>();
        Cursor cursor = sqlite.rawQuery("select * from " + table + where, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Note Note = new Note();
            Note.setId(cursor.getInt(0));
            Note.setTitle(cursor.getString(1));
            Note.setContent(cursor.getString(2));
            Note.setDate(cursor.getString(3));
            Note.setAddress(cursor.getString(4));
            Note.setTimestamp(cursor.getLong(5));
            Note.setLastModify(cursor.getLong(6));
            data.add(Note);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return data;
    }

    /**
     * @name  save
     * @description 通过id查询，如果存在就更新，否则插入
     * @param
     * @return
     * @date  2020/4/29
     */
    public void save(Note data) {
        String table = DatabaseHelper.ALL_NOTE_TABLE_NAME;
        List<Note> datas = query( " where _id=" + data.getId());
        if (datas != null && !datas.isEmpty()) {  //如果有了就更新
            update(data);
        } else {
            insert(data);
        }
        //insert(data);

    }


    /**
     * @name  getAllNotes
     * @description 获得所有的notes
     * @param
     * @return
     * @date  2020/4/29
     */
    public List<Note> getAllNotes() {
        return  query("");
    }

    public List<Note> getNotesByPattern(String pattern) {
        String whereCondition = " where title like '%" + pattern + "%' or " +
                "content like '%" + pattern + "%'";
        return query(whereCondition);
    }
}
