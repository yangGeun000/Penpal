package com.penpal.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    
    @RequestMapping("//")
    public String index(Model model){
        model.addAttribute("index", "index");
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("login", "login");
        return "login";
    }
    
    @RequestMapping("/sign_up")
    public String signup(Model model){
        model.addAttribute("sign_up", "sign_up");
        return "signup";
    }


    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "Test Message";
    }
}
