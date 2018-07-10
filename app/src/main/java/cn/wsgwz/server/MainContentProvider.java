package cn.wsgwz.server;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eatbeancar.user.myapplication.LLog;


public class MainContentProvider extends ContentProvider {
    private static final String TAG = MainContentProvider.class.getSimpleName();

    private static final String AUTHORITIES = "cn.wsgwz.server.MainContentProvider";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private MainSQLiteOpenHelper mainSQLiteOpenHelper = null;
    private SQLiteDatabase sqLiteDatabase;//获取其中的数据库

    class Setting {
        public static final int CODE = 1;
        public static final String PATH = "setting";
        public static final String AUTHORITY = AUTHORITIES + "/" + PATH;
    }




    static {
        URI_MATCHER.addURI(AUTHORITIES, Setting.PATH, Setting.CODE);
    }

    @Override
    public boolean onCreate() {
        mainSQLiteOpenHelper = new MainSQLiteOpenHelper(getContext());
        sqLiteDatabase = mainSQLiteOpenHelper.getReadableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch (URI_MATCHER.match(uri)) {
            case Setting.CODE:
                //返回数据库操作的结果
                return sqLiteDatabase.query(MainSQLiteOpenHelper.SETTING_TABLE_NAME, strings, s, strings1,
                        null, null, s1);
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case Setting.CODE:
                return Setting.AUTHORITY;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        LLog.Companion.d(TAG," ==>"+URI_MATCHER.match(uri)+" "+uri.getPath());
        switch (URI_MATCHER.match(uri)) {
            case Setting.CODE: {
                long l = sqLiteDatabase.insert(MainSQLiteOpenHelper.SETTING_TABLE_NAME, null, contentValues);
                Uri uri1 = ContentUris.withAppendedId(uri, l);
                getContext().getContentResolver().notifyChange(uri1, null);
                return uri1;
            }

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int i = 0;
        switch (URI_MATCHER.match(uri)) {
            case Setting.CODE:
                i = sqLiteDatabase.delete(MainSQLiteOpenHelper.SETTING_TABLE_NAME, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return i;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int i = 0;
        switch (URI_MATCHER.match(uri)) {
            case Setting.CODE:
                i = sqLiteDatabase.update(MainSQLiteOpenHelper.SETTING_TABLE_NAME, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return i;
    }
}
