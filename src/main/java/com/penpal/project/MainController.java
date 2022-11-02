package com.penpal.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    @RequestMapping("/users")
    public String users(){
        return "member/users";
    }

    @RequestMapping("/users/profile")
    public String userProfile(){
        return "member/user_profile";
    }

}
