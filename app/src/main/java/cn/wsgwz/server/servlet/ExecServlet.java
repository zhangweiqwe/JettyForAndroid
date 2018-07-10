package cn.wsgwz.server.servlet;

import com.eatbeancar.user.myapplication.LLog;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.wsgwz.server.servlet.listener.ExecWebSocketListener;

@WebServlet(name = "ExecServlet", urlPatterns = { "/exec" })
public class ExecServlet extends WebSocketServlet {
    private static final String TAG = ExecServlet.class.getSimpleName();
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(60*5*1000);
        webSocketServletFactory.register(ExecWebSocketListener.class);
    }


    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExecResult execResult = new ExecResult();

        UserManager.getInstance().checkUserState(req.getHeader("token"),execResult);

        if(execResult.getCode()== Result.SUCCESS.getCode()){
            execResult.setResult("执行成功！");
        }

        resp.getWriter().print(new String(Const.GSON.toJson(execResult).getBytes(), "iso8859-1"));

    }*/
}
