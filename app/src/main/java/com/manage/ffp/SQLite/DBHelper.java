package com.manage.ffp.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //생성자로 관리할 DB이름과 버전정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override //DB가 새로 생성될 때 호출
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE ");
    }

    @Override //DB 업그레이드를 위해 버전이 변경될 때 호출
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(String name) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE "+name+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER, year INTEGER, mon INTEGER, day INTEGER, time INTEGER);");
        //db.execSQL("INSERT INTO "+name+" VALUES(null, 'testman', 2000, 2017, 12, 23, 1504);");
        db.close();
    }

    public String getResult(String name) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+name, null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getInt(2)
                    + "원 "
                    + cursor.getString(3)
                    + "\n";
        }

        return result;
    }

}
