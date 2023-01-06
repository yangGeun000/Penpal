package com.penpal.project;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.chat.MessageService;
import com.penpal.project.member.MemberService;
import com.penpal.project.profile.Profile;
import com.penpal.project.profile.ProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final MemberService memberService;
	private final MessageService messageService;
	private final ProfileService profileService;
	
    @RequestMapping("/")
    public String index(Model model){
    	long memberCount = this.memberService.memberCount();
    	long onlineMemberCount = this.memberService.onlineMemberCount();
    	long messageCount = this.messageService.messageCount();
    	List<Profile> recentProfile = this.profileService.recentProfile();
    	
    	model.addAttribute("memberCount", memberCount);
    	model.addAttribute("onlineMemberCount", onlineMemberCount);
    	model.addAttribute("messageCount", messageCount);
    	model.addAttribute("recentProfile", recentProfile);
    	
        return "index";
    }
    
    // by 장유란, 아래 users매핑 profileController로 이사

}
