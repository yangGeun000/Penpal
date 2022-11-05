package com.penpal.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
public class MainController {
	
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    // by 장유란, 아래 users매핑 profileController로 이사

}
