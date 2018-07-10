package cn.wsgwz.server.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

public class MySupportSQLiteOpenHelper implements SupportSQLiteOpenHelper {
    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {

    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        return null;
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        return null;
    }

    @Override
    public void close() {

    }
}
