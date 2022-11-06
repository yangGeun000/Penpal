package com.penpal.project.profile;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penpal.project.list.CountryList;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.LocationList;
import com.penpal.project.list.LocationListRepository;
import com.penpal.project.list.SnsList;
import com.penpal.project.list.SnsListRepository;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;
import com.penpal.project.member.list.MemberSns;
import com.penpal.project.member.list.MemberSnsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
@Slf4j
public class ProfileController {

	private final ProfileService profileService;

	private final MemberService memberService;
	private final LocationListRepository locationListRepository;
	private final CountryListRepository countryListRepository;
	private final SnsListRepository snsListRepository;
	private final MemberSnsRepository memberSnsRepository;
	
    @RequestMapping("")
    public String users(Model model,
    		@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "location", defaultValue = "") String location, 
			@RequestParam(value = "country", defaultValue = "") String country){
    	
        Page<Profile> paging = this.profileService.getList(page, kw, location, country);
		model.addAttribute("kw", kw);
        model.addAttribute("paging", paging);

        log.info("userSearch = kw: " + kw + " page: " + page + " location: " + location + " country: " + country);
		
		return "profile/users";
    }


    @GetMapping("/profile/create/{id}")
    public String profileCreate(ProfileForm profileForm) {
    	System.out.println("profile get");
    	return "profile/user_profile_form";
    }
    
    // 프로필 생성 일부 반영
    @PostMapping("/profile/create/{id}")
    public String profileCreate(Model model, @PathVariable("id") Integer id, 
    		@Valid ProfileForm profileForm, BindingResult bindingResult, Principal principal) {
    	
    	Member member = this.memberService.getMember(principal.getName());
    	LocationList location = locationListRepository.findByName(profileForm.getLocation());
    	CountryList country = countryListRepository.findByName(profileForm.getCountry());
    	
    	//sns1,2,3 클릭유무로 사용여부 판별
    	SnsList sns1 = snsListRepository.findByName(profileForm.getSns1());
    	SnsList sns2 = snsListRepository.findByName(profileForm.getSns2());
    	SnsList sns3 = snsListRepository.findByName(profileForm.getSns3());
    	
    	//memberSns에 sns들 입력
    	MemberSns memberSns = new MemberSns();
    	memberSns.setSns1(sns1);
    	memberSns.setSns2(sns2);
    	memberSns.setSns3(sns3);
    	memberSnsRepository.save(memberSns);
    	
    	if (bindingResult.hasErrors()){
    		System.out.println("form error");
    		return "profile/user_profile_form";
    	}
    	System.out.println("form post");
    	this.profileService.create(
    			profileForm.getNickname(), profileForm.getGender(), 
    			profileForm.getAge(), profileForm.getComment(),
    			member,location , country,
    			memberSns);
    	return "redirect:/users";
    }

    // 프로필 상세 보기
    @RequestMapping(value = "/profile/{id}")
    public String userProfile(Model model, @PathVariable("id") Integer id){
    	Profile profile = this.profileService.getProfile(id);
        model.addAttribute("profile", profile);

        return "profile/user_profile";	//user_profile폼으로 이동
    }
   
   @RequestMapping("/profile/modify/{id}")
   public String userProfileForm(){
       return "member/user_profile_form";
   } // by 조성빈, 템플릿 제작용 임시 mapping
}
