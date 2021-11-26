package edu.cn.WuKongadminister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    //常量定义
    public static final String name = "db_rubbish.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_USERDATA1 = "create table " +
            "tb(time char(20) primary key," +
            "rubbishname varchar(20))";
    //构造函数
    public MyDBHelper(Context context) {
        super(context, name, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}