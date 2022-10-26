package com.penpal.project;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.list.LanguageList;
import com.penpal.project.list.LanguageListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final LanguageListRepository languageListRepository;
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("index", "index");
        return "index";
    }
    
    @RequestMapping("/users")
    public String users(Model model){
        model.addAttribute("users", "users");
        return "member/users";
    }

    // by 조성빈, 상단 NAVI language 기능 사용을 위해 추가
	@ModelAttribute("language")
	public List<LanguageList> languageList(){
		List<LanguageList> languageLists = languageListRepository.findAll();
		return languageLists;
	}

}
