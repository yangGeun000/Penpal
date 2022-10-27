package com.penpal.project;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.list.CountryList;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.LanguageList;
import com.penpal.project.list.LanguageListRepository;
import com.penpal.project.list.LocationList;
import com.penpal.project.list.LocationListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final LanguageListRepository languageListRepository;
    private final LocationListRepository locationListRepository;
    private final CountryListRepository countryListRepository;
    
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

    @RequestMapping("/users/profile")
    public String userProfile(Model model){
        model.addAttribute("profile", "profile");
        return "member/user_profile";
    }




    // by 조성빈, 상단 NAVI language 기능 사용을 위해 추가
	@ModelAttribute("language")
	public List<LanguageList> languageList(){
		List<LanguageList> languageLists = languageListRepository.findAll();
		return languageLists;
	}

    @ModelAttribute("location")
	public List<LocationList> locationList() {
		List<LocationList> locationLists = locationListRepository.findAll();
		return locationLists;
	}

	@ModelAttribute("country")
	public List<CountryList> countryList() {
		List<CountryList> countryLists = countryListRepository.findAll();
		return countryLists;
	}

}
