package me.wangcai.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String perLogin(){
        return "login";
    }

    @RequestMapping("logout")
    public String  logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
