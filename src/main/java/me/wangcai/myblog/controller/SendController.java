package me.wangcai.myblog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SendController {
    @RequestMapping("/send")
    public String visit(){
        return "send";
    }
}
