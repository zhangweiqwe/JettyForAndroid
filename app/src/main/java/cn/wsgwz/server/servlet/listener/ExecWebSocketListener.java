package cn.wsgwz.server.servlet.listener;

import com.eatbeancar.user.myapplication.LLog;

import org.eclipse.jetty.deploy.App;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

import cn.wsgwz.server.TokenManager;
import cn.wsgwz.server.UserManager;
import cn.wsgwz.server.result.Result;
import cn.wsgwz.server.room.AppDatabase;

public class ExecWebSocketListener implements WebSocketListener {
    private static final String TAG = ExecWebSocketListener.class.getSimpleName();
    private Session session;
    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        if (session.isOpen()) {
            try {
                session.getRemote().sendString(session.getRemoteAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //发送String
    @Override
    public void onWebSocketText(String message) {
        LLog.Companion.d(TAG,"onWebSocketText");


        if (session.isOpen()) {

            AppDatabase appDatabase = AppDatabase.getDatabase(UserManager.getInstance().getContext());
            appDatabase.getOpenHelper().getWritableDatabase();
            session.getRemote().sendString("echo msg->m"+message , null);
        }
    }
    //发送byte[]
    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        LLog.Companion.d(TAG,"onWebSocketBinary");
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        LLog.Companion.d(TAG,"Error->" + cause.getMessage());
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        LLog.Companion.d(TAG,"onWebSocketClose");
        this.session = null;
    }

}
