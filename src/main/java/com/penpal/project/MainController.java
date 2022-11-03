package com.penpal.project;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penpal.project.profile.Profile;
import com.penpal.project.profile.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

	private final ProfileService profileService;
	
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    // by 장유란, 프로필 목록 넘기기
    @RequestMapping("/users")
    public String users(Model model,
    		@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "location", defaultValue = "") String location, 
			@RequestParam(value = "country", defaultValue = "") String country){
    	
        Page<Profile> paging = this.profileService.getList(page, kw, location, country);
		model.addAttribute("kw", kw);
        model.addAttribute("paging", paging);

        log.info("userSearch = kw: " + kw + " page: " + page + " location: " + location + " country: " + country);
		
        return "member/users";
    }

    @RequestMapping("/users/profile")
    public String userProfile(){
        return "member/user_profile";
    }
    
    @RequestMapping("users/profile/modify")
    public String userProfileForm(){
        return "member/user_profile_form";
    } // by 조성빈, 템플릿 제작용 임시 mapping
}
