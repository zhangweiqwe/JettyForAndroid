package cn.wsgwz.server.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;

import com.eatbeancar.user.myapplication.LLog;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;
import java.util.List;

import cn.wsgwz.server.Config;
import cn.wsgwz.server.IMyAidlInterface1;
import cn.wsgwz.server.R;

public class FTPService extends Service {
    private static final String TAG = WebService.class.getSimpleName();

    private static final int NOTIFY_FOREGROUND_ID = 2;

    public static final String EXTRA_KEY_STATE = "EXTRA_KEY_STATE";

    private FtpServer server;

    private boolean isStart;

    @Override
    public void onCreate() {
        super.onCreate();
        LLog.Companion.d(TAG, "onCreate()");
    }

    private void run() {
        FtpServerFactory serverFactory = new FtpServerFactory();

        BaseUser user = new BaseUser();
        user.setName("admin");
        user.setPassword("123456");
        user.setEnabled(true);
        user.setHomeDirectory(Environment.getExternalStorageDirectory().getPath());

        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        ListenerFactory factory = new ListenerFactory();
        // set the port of the listener

        // ↓ 就是这这里我一直以为是只会用这一个端口，导到我踩了第一个坑
        // 原来这个只是控制端口，还有个连接端口，默认是使用的20（主动模式），被动模式下使用随机的端口。
        factory.setPort(8081);
        // replace the default listener
        serverFactory.addListener("default", factory.createListener());

        try {
            serverFactory.getUserManager().save(user);
            server = serverFactory.createServer();
            server.start();
            isStart = true;
        } catch (FtpException e) {
            e.printStackTrace();
            finish();
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean state = intent.getBooleanExtra(EXTRA_KEY_STATE, false);
        LLog.Companion.d(TAG, "onStartCommand(Intent intent, int flags, int startId)" + state);
        if (state) {
            if (!isStart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Notification.Builder builder = new Notification.Builder(this, Config.MAIN_NOTIFICATION_CHANNEL_ID);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setTicker(getString(R.string.run_FTP_server));
                    builder.setContentTitle(getString(R.string.app_name));
                    builder.setContentText(getString(R.string.ftp_server));
                    Notification notification = builder.build();
                    startForeground(NOTIFY_FOREGROUND_ID, notification);
                }
                run();
            }


        } else {
            finish();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void finish() {
        LLog.Companion.d(TAG, "finish()");
        if (server != null) {
            server.stop();
            server = null;
        }
        isStart = false;
        stopForeground(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LLog.Companion.d(TAG, "onDestroy()");
        finish();
    }


    @Override
    public IBinder onBind(Intent intent) {
        LLog.Companion.d(TAG, "onBind(Intent intent)");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LLog.Companion.d(TAG, "onUnbind(Intent intent)");
        return super.onUnbind(intent);
    }

    private IBinder iBinder = new IMyAidlInterface1.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public boolean isStart() {
            return isStart;
        }
    };
}
