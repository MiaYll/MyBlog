package me.wangcai.myblog.servlet;

import com.google.gson.Gson;
import me.wangcai.myblog.manager.UserManager;
import me.wangcai.myblog.model.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


@WebServlet("/userReg")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute("user") != null){
            String json = new Gson().toJson(new Response("当前已经是登录状态!", true),Response.class);
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
            success = UserManager.getUserManager().register(name,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(success) {
            String json = new Gson().toJson(new Response("注册成功!", true),Response.class);
            ServletOutputStream out = resp.getOutputStream();
            out.write(json.getBytes(StandardCharsets.UTF_8));
            out.close();
            //req.getSession().setAttribute("user",new UserBean(name,password,true));
        }else{
            String json = new Gson().toJson(new Response("该用户已经注册过了!", false),Response.class);
            ServletOutputStream out = resp.getOutputStream();
            out.write(json.getBytes(StandardCharsets.UTF_8));
            out.close();
        }
    }

}
