package cn.wsgwz.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.wsgwz.server.Config;

public class MainSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = 1;



    public static final String SETTING_TABLE_NAME = "setting";
    public static final String SETTING_TABLE_KEY_TOKEN_KEY = "tokenKey";


    public MainSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql0 = "create table if not exists " + SETTING_TABLE_NAME + " ("+SETTING_TABLE_KEY_TOKEN_KEY+" text)";
        sqLiteDatabase.execSQL(sql0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql0 = "DROP TABLE IF EXISTS " + SETTING_TABLE_NAME;
        sqLiteDatabase.execSQL(sql0);
        onCreate(sqLiteDatabase);
    }

    //SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(sqliePath, null, SQLiteDatabase.OPEN_READONLY);
}
