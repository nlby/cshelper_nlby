package com.hhu.cshelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 创建数据库
 * @name  DatabaseHelper
 * @description 创建数据库
 * @author  dyh
 * @date 2020/4/29
 */

public class DatabaseHelper extends SQLiteOpenHelper {




    public static final String DATABASE_NAME = "mynote";
    public static final String ALL_NOTE_TABLE_NAME = "all_notes";
    public static final String CREATE_NOTE_TABLE = "create table " + ALL_NOTE_TABLE_NAME
            + " (_id integer primary key autoincrement, title text, content text,"
            + " date varchar(10), address text, timestamp float,lastmodify float, iswasted integer)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}