package edu.cn.WuKongadminister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RubbishDAO {
    private Context context;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;

    //构造函数
    public RubbishDAO(Context context) {
        this.context = context;
    }

    //打开数据库
    public void open() throws SQLiteException {
        dbHelper = new MyDBHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    //关闭数据库
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    //添加借书信息
    public long addRubbish(Rubbish o) {
        // 创建ContentValues对象
        ContentValues values = new ContentValues();
        // 向该对象中插入键值对
        values.put("time", o.time);
        values.put("rubbishname", o.name);

        // 调用insert()方法将数据插入到数据库当中
        return db.insert("tb", null, values);
    }

    public void deleteRubbish() {
        db.execSQL("delete from tb");
    }

    //查找所有借书信息
    public ArrayList<Map<String, Object>> getAllRubbish() {
        ArrayList<Map<String, Object>> listBooks = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.query("tb", null, null, null, null, null,null);

        int resultCounts = cursor.getCount();  //记录数
        if (resultCounts == 0 ) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("time", cursor.getString(cursor.getColumnIndex("time")));
                map.put("rubbishname", cursor.getString(cursor.getColumnIndex("rubbishname")));

                listBooks.add(map);
            }
            return listBooks;
        }
    }
}