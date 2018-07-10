package cn.wsgwz.server;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.eatbeancar.user.myapplication.LLog;

import java.util.List;

import cn.wsgwz.baselibrary.BaseApplication;


public class AppBaseApplication extends BaseApplication {
    private static final String TAG = AppBaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        LLog.Companion.d(TAG, "onCreate()");


        if(isMainProcess(this)){
            UserManager.init(this);
            TokenManager.init(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 通知渠道的id
                String id = "1";
                // 用户可以看到的通知渠道的名字.
                CharSequence name = getString(R.string.app_name);
                // 用户可以看到的通知渠道的描述
                //tring description = "这是" + getString(R.string.app_name) + "的推送消息";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                // 配置通知渠道的属性
                mChannel.setDescription(getString(R.string.app_description));
                // 设置通知出现时的闪灯（如果 android 设备支持的话）
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                // 设置通知出现时的震动（如果 android 设备支持的话）
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                //最后在notificationmanager中创建该通知渠道
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

    }


    /**
     * 包名判断是否为主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getProcessName(context));
    }

    /**
     * 获取进程名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }
}
