package me.wangcai.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegController {

    @RequestMapping("/reg")
    public String perRegister(){
        return "register";
    }

}
