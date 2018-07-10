package cn.wsgwz.baselibrary;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends MultiDexApplication{
    private static final String TAG = BaseApplication.class.getSimpleName();
    private static final List<Activity> activities = new ArrayList<>();


    public static final void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static final boolean removeActivity(Activity activity) {
        return activities.remove(activity);
    }

    public static final void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }


    protected String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }


}
