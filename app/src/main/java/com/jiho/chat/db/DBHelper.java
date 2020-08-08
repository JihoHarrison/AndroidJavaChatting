package com.jiho.chat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
* 내부 DB 접속 클래스
* 대화내용같은 자료들은 내부 DB에 저장되게 구현
*/
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "androidDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE friend_list (email varchar(50) not null,name	varchar(10) not null, primary key(email));");
        db.execSQL("CREATE TABLE messages (fromUser varchar(50) not null,toUser varchar(50) not null,message varchar(100) not null);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS friend_list");
        db.execSQL("DROP TABLE IF EXISTS messages");
        onCreate(db);
    }
}
