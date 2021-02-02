package me.wangcai.myblog.servlet;

import com.google.gson.Gson;
import me.wangcai.myblog.Manager.UserManager;
import me.wangcai.myblog.model.Response;
import me.wangcai.myblog.model.UserBean;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


@WebServlet("/userLogin")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute("user") != null){
            String json = new Gson().toJson(new Response("当前已经登录!", true),Response.class);
            ServletOutputStream out = resp.getOutputStream();
            out.write(json.getBytes(StandardCharsets.UTF_8));
            out.close();
        }
        boolean success = false;
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        try {
                success = UserManager.getUserManager().login(name,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(success) {
            String json = new Gson().toJson(new Response("登录成功!", true),Response.class);
            ServletOutputStream out = resp.getOutputStream();
            out.write(json.getBytes(StandardCharsets.UTF_8));
            out.close();
            req.getSession().setAttribute("user",new UserBean(name,password,true));
        }else{
            String json = new Gson().toJson(new Response("账号或密码错误!", false),Response.class);
            ServletOutputStream out = resp.getOutputStream();
            out.write(json.getBytes(StandardCharsets.UTF_8));
            out.close();
        }
    }

}

