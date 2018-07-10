package cn.wsgwz.server.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ashokvarma.sqlitemanager.SqliteDataRetriever;

public class RoomSqliteDataRetriever implements SqliteDataRetriever {
    SupportSQLiteOpenHelper mSqliteHelper;
    SupportSQLiteDatabase mSQLiteDatabase;

    public RoomSqliteDataRetriever(SupportSQLiteOpenHelper sqliteHelper) {
        mSqliteHelper = sqliteHelper;
    }

    @Override
    public Cursor rawQuery(@NonNull String query, String[] selectionArgs) {
        mSQLiteDatabase = mSqliteHelper.getWritableDatabase();
        return mSQLiteDatabase.query(query, selectionArgs);
    }

    @Override
    public String getDatabaseName() {
        return mSqliteHelper.getDatabaseName();
    }

    @Override
    public void freeResources() {
        // not good practice to open multiple database connections and close every time
    }
}