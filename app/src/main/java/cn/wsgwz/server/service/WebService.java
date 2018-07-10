package cn.wsgwz.server.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;


import com.eatbeancar.user.myapplication.LLog;

import org.eclipse.jetty.server.AsyncNCSARequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;

import cn.wsgwz.server.Config;
import cn.wsgwz.server.IMyAidlInterface;
import cn.wsgwz.server.R;
import cn.wsgwz.server.servlet.ExecServlet;
import cn.wsgwz.server.servlet.LoginServlet;

public class WebService extends Service {
    private static final String TAG = WebService.class.getSimpleName();

    private static final int NOTIFY_FOREGROUND_ID = 1;

    public static final String EXTRA_KEY_STATE = "EXTRA_KEY_STATE";

    private Server server;

    private Thread thread;

    private boolean isStart;

    @Override
    public void onCreate() {
        super.onCreate();
        LLog.Companion.d(TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LLog.Companion.d(TAG, "onDestroy()");
        finish();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean state = intent.getBooleanExtra(EXTRA_KEY_STATE, false);
        LLog.Companion.d(TAG, "onStartCommand(Intent intent, int flags, int startId)"+state);
        if (state) {
            if (!isStart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Notification.Builder builder = new Notification.Builder(this, Config.MAIN_NOTIFICATION_CHANNEL_ID);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setTicker(getString(R.string.run_web_server));
                    builder.setContentTitle(getString(R.string.app_name));
                    builder.setContentText(getString(R.string.web_server));
                    Notification notification = builder.build();
                    startForeground(NOTIFY_FOREGROUND_ID, notification);
                }
                run();
            }

        }else {
            finish();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void run() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    run0();
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
            }
        });
        thread.start();
    }


    private void finish() {
        LLog.Companion.d(TAG,"finish()");
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            server = null;
        }
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        isStart = false;
        stopForeground(true);
    }

    private void run0() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");
        server = new Server(8080);
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new ExecServlet()), "/exec");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(Environment.getExternalStorageDirectory().getPath());
        resourceHandler.setStylesheet("");



        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);

        server.start();
        isStart = true;
        server.join();
        isStart = false;
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

    private IBinder iBinder = new IMyAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public boolean isStart() {
            return isStart;
        }
    };

}
