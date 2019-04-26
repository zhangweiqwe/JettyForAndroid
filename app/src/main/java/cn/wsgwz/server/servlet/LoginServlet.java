package cn.wsgwz.server.servlet;

import android.text.TextUtils;

import com.eatbeancar.user.myapplication.LLog;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.internet.ContentType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.wsgwz.server.Const;
import cn.wsgwz.server.TokenManager;
import cn.wsgwz.server.UserManager;
import cn.wsgwz.server.result.LoginResult;
import cn.wsgwz.server.result.Result;
import cn.wsgwz.server.room.AppDatabase;
import cn.wsgwz.server.room.User;

public class LoginServlet extends HttpServlet {
    private static final String TAG = LoginServlet.class.getSimpleName();

  /*  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginResult loginResult = new LoginResult();
        StringBuilder sb = new StringBuilder();
        byte[] buf = new byte[1024];
        InputStream in = req.getInputStream();
        int len;
        while ((len=in.read(buf))!=-1){
            sb.append(new String(buf,0,len));
        }

        LLog.Companion.d(TAG,sb.toString());
        User reqUser = Const.GSON.fromJson(sb.toString(),User.class);


        if(reqUser!=null&&!TextUtils.isEmpty(reqUser.password)){
            User user = UserManager.getInstance().getUserById(reqUser.id);
            if(user!=null&&user.password.equals(reqUser.password)){
                user.token = TokenManager.getInstance().createUserToken(user);
                loginResult.setCode(Result.SUCCESS.getCode());
                loginResult.setMsg(Result.SUCCESS.getMsgs()[1]);
                loginResult.setToken(user.token);
            }else {
                loginResult.setCode(Result.ERROR.getCode());
                loginResult.setMsg(Result.ERROR.getMsgs()[3]);
            }
        }else {
            loginResult.setCode(Result.ERROR.getCode());
            loginResult.setMsg(Result.ERROR.getMsgs()[1]);
        }


        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().print(new String(Const.GSON.toJson(loginResult).getBytes(), "utf-8"));
        resp.getWriter().flush();
        resp.getWriter().close();

    }*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String password = req.getParameter("password");

        LLog.Companion.d(TAG,id+"\t"+password);
        LoginResult loginResult = new LoginResult();

        User reqUser = new User();
        reqUser.id = id;
        reqUser.password = password;


        if(reqUser!=null&&!TextUtils.isEmpty(reqUser.password)){
            User user = UserManager.getInstance().getUserById(reqUser.id);
            if(user!=null&&user.password.equals(reqUser.password)){
                user.token = TokenManager.getInstance().createUserToken(user);
                loginResult.setCode(Result.SUCCESS.getCode());
                loginResult.setMsg(Result.SUCCESS.getMsgs()[1]);
                loginResult.setToken(user.token);
            }else {
                loginResult.setCode(Result.ERROR.getCode());
                loginResult.setMsg(Result.ERROR.getMsgs()[3]);
            }
        }else {
            loginResult.setCode(Result.ERROR.getCode());
            loginResult.setMsg(Result.ERROR.getMsgs()[1]);
        }


        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().print(new String(Const.GSON.toJson(loginResult).getBytes(), "utf-8"));
        resp.getWriter().flush();
        resp.getWriter().close();

    }
}
