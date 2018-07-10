package cn.wsgwz.server;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.eatbeancar.user.myapplication.LLog;

import cn.wsgwz.server.bean.TokenUser;
import cn.wsgwz.server.result.BaseResult;
import cn.wsgwz.server.result.Result;
import cn.wsgwz.server.room.AppDatabase;
import cn.wsgwz.server.room.User;


public class UserManager {
    private static final String TAG = UserManager.class.getSimpleName();

    private Context context;
    private static final UserManager userManager = new UserManager();


    private UserManager() {
    }

    public Context getContext() {
        return context;
    }

    public static final void init(Context context) {


        userManager.context = context;
    }

    public static UserManager getInstance() {
        return userManager;
    }


    public User getUserById(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }

        int idI = 0;
        try {
            idI = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (idI != 0) {
            return getUserById(idI);

        }

        return null;
    }

    public User getUserById(int id) {
        if (id == 0) {
            return null;
        }
        User user = null;

        AppDatabase appDatabase = AppDatabase.getDatabase(context);

        user = appDatabase.userModel().loadUserById(id);

        /*User user = null;


        Uri uri = Uri.parse("content://" + MainContentProvider.User.AUTHORITY);
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(uri, new String[]{MainSQLiteOpenHelper.USER_TABLE_KEY_PASSWORD, MainSQLiteOpenHelper.USER_TABLE_KEY_NAME, MainSQLiteOpenHelper.USER_TABLE_KEY_TAG}, MainSQLiteOpenHelper.USER_TABLE_KEY_ID + "=?", new String[]{id}, null);

        if (cursor.moveToNext()) {
            user = new User();
            user.setPassword(cursor.getString(cursor.getColumnIndex(MainSQLiteOpenHelper.USER_TABLE_KEY_PASSWORD)));
            user.setName(cursor.getString(cursor.getColumnIndex(MainSQLiteOpenHelper.USER_TABLE_KEY_NAME)));
            user.setTag(cursor.getString(cursor.getColumnIndex(MainSQLiteOpenHelper.USER_TABLE_KEY_TAG)));
        }
        cursor.close();*/
        return user;
    }


    public void checkUserState(String headerToken, BaseResult baseResult) {
        if(TextUtils.isEmpty(headerToken)){
            baseResult.setCode(Result.ERROR.getCode());
            baseResult.setMsg(Result.ERROR.getMsgs()[4]);
            return;
        }
     /*   BaseResult baseResult = null;

        try {
            baseResult = (BaseResult) getClass().getClassLoader().loadClass(cls.getCanonicalName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/


        TokenUser tokenUser = TokenManager.getInstance().parseToken(headerToken);

        if (tokenUser != null) {
            String userId = tokenUser.getUserId();
            long validTime = tokenUser.getValidTime();
            if (getUserById(userId) != null) {
                if (validTime >= System.currentTimeMillis()) {
                    User user = AppDatabase.getDatabase(context).userModel().loadUserById(userId);

                    if (user == null) {
                        baseResult.setCode(Result.ERROR.getCode());
                        baseResult.setMsg(Result.ERROR.getMsgs()[2]);
                    } else {
                        if (headerToken.equals(user.token)) {
                            baseResult.setCode(Result.SUCCESS.getCode());
                            baseResult.setMsg(Result.SUCCESS.getMsgs()[0]);
                        } else {
                            baseResult.setCode(Result.ERROR.getCode());
                            baseResult.setMsg(Result.ERROR.getMsgs()[2]);
                        }
                    }

                } else {
                    baseResult.setCode(Result.ERROR.getCode());
                    baseResult.setMsg(Result.ERROR.getMsgs()[2]);
                }
            } else {
                baseResult.setCode(Result.ERROR.getCode());
                baseResult.setMsg(Result.ERROR.getMsgs()[2]);
            }

        } else {
            baseResult.setCode(Result.ERROR.getCode());
            baseResult.setMsg(Result.ERROR.getMsgs()[2]);
        }


    }


}
