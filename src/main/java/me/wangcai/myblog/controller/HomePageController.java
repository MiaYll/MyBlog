package me.wangcai.myblog.controller;

import me.wangcai.myblog.Manager.WordManager;
import me.wangcai.myblog.config.FileConfig;
import me.wangcai.myblog.model.UserBean;
import me.wangcai.myblog.model.WordBean;
import me.wangcai.myblog.utils.ColorUtil;
import me.wangcai.myblog.utils.TimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
public class HomePageController {
    @RequestMapping("/")
    public String visit(Model model, HttpServletRequest req, HttpServletResponse res){
        UserBean user = (UserBean) req.getSession().getAttribute("user");
        if(user != null){
            model.addAttribute("signin","<a href='/logout'>" + user.getName() +"</a>");
            model.addAttribute("send","<span class='fa fa-send-o'></span>");
        }else{
            model.addAttribute("signin","        <a href='login'>登录</a>" +
                    "        <a href='/reg'>注册</a>");
            model.addAttribute("send","<span id='unLogin' class='fa fa-send-o'></span>");
        }

        String str = "<div class='word' style='float:%s;width:%d%%;height:%dpx;background-color: #%s;'>" +
                "        <div class='backimage' style='background-image: url(%s);'></div>" +
                "        <p class='title'>%s</p>" +
                "        <p class='content'>%s</p>" +
                "        <p class='data fa fa-clock-o'>%s</p>" +
                "    </div>";
        String words = "";
        int i =0;
        boolean twoLine = true;
        for (WordBean wordBean : WordManager.getWordManager().getWordList()) {
            if(i % 2 == 0){
                twoLine = !twoLine;
            }
            i ++;
            String isFloat = i % 2 != 0 ? "left" : "right";
            int height = i % 2 != 0 ? 350 : 400;
            int width = i % 2 != 0 ? 50 : 45;
            if(twoLine){
                height = i % 2 != 0 ? 400 : 350;
                width = i % 2 != 0 ? 45 : 50;
            }
            words += String.format(str,isFloat,width,height, ColorUtil.getRandomColor(), "/upload/images/" +  wordBean.getImageUrl(),wordBean.getTitle(),wordBean.getContent(), TimeUtil.getYear(wordBean.getTime()));
        }
        model.addAttribute("words", words);
        return "index";
    }
}
