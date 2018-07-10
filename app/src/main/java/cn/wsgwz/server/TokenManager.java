package cn.wsgwz.server;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.eatbeancar.user.myapplication.LLog;

import java.security.GeneralSecurityException;

import cn.wsgwz.server.bean.TokenUser;
import cn.wsgwz.server.room.AppDatabase;
import cn.wsgwz.server.room.Setting;
import cn.wsgwz.server.room.SettingDao;
import cn.wsgwz.server.room.User;

public class TokenManager {
    private static final String TAG = TokenManager.class.getCanonicalName();

    private static final TokenManager tokenManager = new TokenManager();

    public static final String TOKEN = "token";

    private static final String TOKEN_SPLIT_FLAG = "-";
    private String tokenKey;
    private long tokenValidTime = 60*60*1000*2;
    private Context context;

    private TokenManager(){

    }

    public static final TokenManager getInstance(){
        return tokenManager;
    }
    public static final void init(final Context context){
        tokenManager.context = context;
//        Uri uri = Uri.parse("content://" + MainContentProvider.Setting.AUTHORITY);
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(uri, new String[]{MainSQLiteOpenHelper.SETTING_TABLE_KEY_TOKEN_KEY}, null, null, null);
//        if (cursor.moveToFirst()) {
//            String s = cursor.getString(cursor.getColumnIndex(MainSQLiteOpenHelper.SETTING_TABLE_KEY_TOKEN_KEY));
////            if (s.isEmpty()) {
////                tokenManager.tokenKey = Util.getRandomString(10);
////                ContentValues contentValues = new ContentValues();
////                contentValues.put(MainSQLiteOpenHelper.SETTING_TABLE_KEY_TOKEN_KEY, tokenManager.tokenKey);
////                contentResolver.insert(uri, contentValues);
////            } else {
//                tokenManager.tokenKey = s;
//         //   }
//
//        } else {
//            tokenManager.tokenKey = Util.getRandomString(10);
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(MainSQLiteOpenHelper.SETTING_TABLE_KEY_TOKEN_KEY, tokenManager.tokenKey);
//            contentResolver.insert(uri, contentValues);
//        }
//        cursor.close();


        new Thread(new Runnable() {
            @Override
            public void run() {
                final SettingDao settingDao = AppDatabase.getDatabase(context).settingModel();
                Setting setting = settingDao.loadSettingById(0);
                if(setting==null){
                    setting = new Setting();
                    setting.tokenKey = Util.getRandomString(10);
                    settingDao.insertSetting(setting);


                    tokenManager.tokenKey = setting.tokenKey;
                }else {
                    tokenManager.tokenKey = setting.tokenKey;
                }
            }
        }).start();




    }
    public String getTokenKey() {
        return tokenKey;
    }
    public void setTokenValidTime(long tokenValidTime) {
        this.tokenValidTime = tokenValidTime;
    }


    public String createUserToken(User user){
        if(user==null||user.id==0){
            return null;
        }
        String headerToken = null;
        try {
            headerToken = AESCrypt.encrypt(tokenKey, user.id+TOKEN_SPLIT_FLAG+(System.currentTimeMillis()+tokenValidTime));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }


        AppDatabase appDatabase = AppDatabase.getDatabase(context);
        user.token = headerToken;
        appDatabase.userModel().updateUser(user);
        return headerToken;
    }

    public TokenUser parseToken(String headerToken){
        if(headerToken==null||headerToken.isEmpty()){
            return null;
        }

        String token = null;
        try {
            token = AESCrypt.decrypt(tokenKey,headerToken);
        } catch (GeneralSecurityException  e) {
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        if(TextUtils.isEmpty(token)){
            return null;
        }

        if(token.contains(TOKEN_SPLIT_FLAG)){
            String arr[] = token.split(TOKEN_SPLIT_FLAG);
            if(arr==null){
                return null;
            }else {
                String userId = arr[0];
                long validTime = 0;

                if(arr.length>1){
                    try {
                        validTime = Long.parseLong(arr[1]);
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }

                return new TokenUser(userId,validTime);

            }
        }else {
            return null;
        }

    }


}
