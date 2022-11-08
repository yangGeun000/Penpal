package com.penpal.project.profile;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penpal.project.list.CountryList;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.FavoriteList;
import com.penpal.project.list.FavoriteListRepository;
import com.penpal.project.list.LanguageList;
import com.penpal.project.list.LanguageListRepository;
import com.penpal.project.list.LocationList;
import com.penpal.project.list.LocationListRepository;
import com.penpal.project.list.SnsList;
import com.penpal.project.list.SnsListRepository;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
@Slf4j
public class profileController {

	private final ProfileService profileService;

	private final MemberService memberService;
	private final LocationListRepository locationListRepository;
	private final CountryListRepository countryListRepository;
	private final SnsListRepository snsListRepository;
	private final FavoriteListRepository favoriteListRepository;
	private final LanguageListRepository languageListRepository;
	
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


    @GetMapping("/profile/create")
    public String profileCreate(ProfileForm profileForm) {
    	System.out.println("profile get");
    	return "profile/user_profile_form";
    }
    
    // 프로필 생성 일부 반영
    @PostMapping("/profile/create")
    public String profileCreate(@Valid ProfileForm profileForm, BindingResult bindingResult, Principal principal) {
    	
    	Member member = this.memberService.getMember(principal.getName());
    	LocationList location = locationListRepository.findByName(profileForm.getLocation());
    	CountryList country = countryListRepository.findByName(profileForm.getCountry());
    	
    	if (bindingResult.hasErrors()){
    		System.out.println("form error");
    		return "profile/user_profile_form";
    	}
    	System.out.println("form post");
    	this.profileService.create(
    			profileForm.getNickname(), profileForm.getGender(), 
    			Integer.parseInt(profileForm.getAge()), profileForm.getComment(),
    			member,location , country,
    			profileForm.getSns1(), profileForm.getSns2(), profileForm.getSns3(),
    			profileForm.getFavorite1(), profileForm.getFavorite2(), profileForm.getFavorite3(),
    			profileForm.getLanguage1(), profileForm.getLanguage2(), profileForm.getLanguage3()
    			);
    	
    	return "redirect:/";
    }

    // 프로필 상세 보기
    @RequestMapping(value = "/profile/{id}")
    public String userProfile(Model model, @PathVariable("id") Integer id){
    	Profile profile = this.profileService.getProfile(id);
        model.addAttribute("profile", profile);

        return "profile/user_profile";	//user_profile폼으로 이동
    }
   
    @GetMapping("/profile/modify")
    public String profileModify(ProfileForm profileForm, Principal principal) {
    	Member member = this.memberService.getMember(principal.getName());
    	Profile profile = member.getProfile();
    	profileForm.setNickname(profile.getNickname());
    	profileForm.setGender(profile.getGender());
    	profileForm.setAge(Integer.toString(profile.getAge()));
    	profileForm.setComment(profile.getComment());
    	profileForm.setLocation(profile.getLocation().getName());
    	profileForm.setCountry(profile.getCountry().getName());
    	profileForm.setSns1(profile.getSns1());
    	profileForm.setSns2(profile.getSns2());
    	profileForm.setSns3(profile.getSns3());
    	profileForm.setFavorite1(profile.getFavorite1());
    	profileForm.setFavorite2(profile.getFavorite2());
    	profileForm.setFavorite3(profile.getFavorite3());
    	profileForm.setLanguage1(profile.getLanguage1());
    	profileForm.setLanguage2(profile.getLanguage2());
    	profileForm.setLanguage3(profile.getLanguage3());
    
    	return "profile/user_profile_form";
    }
    
   @PostMapping("/profile/modify")
   public String profileModify(@Valid ProfileForm profileForm, BindingResult bindingResult, Principal principal){
	   Member member = this.memberService.getMember(principal.getName());
   	LocationList location = locationListRepository.findByName(profileForm.getLocation());
   	CountryList country = countryListRepository.findByName(profileForm.getCountry());
   	
   	if (bindingResult.hasErrors()){
   		System.out.println("form error");
   		return "profile/user_profile_form";
   	}
   	System.out.println("form post");
   	this.profileService.modify(
   			profileForm.getNickname(), profileForm.getGender(), 
   			Integer.parseInt(profileForm.getAge()), profileForm.getComment(),
   			member.getProfile(),location , country,
   			profileForm.getSns1(), profileForm.getSns2(), profileForm.getSns3(),
   			profileForm.getFavorite1(), profileForm.getFavorite2(), profileForm.getFavorite3(),
   			profileForm.getLanguage1(), profileForm.getLanguage2(), profileForm.getLanguage3()
   			);
   	
   	return "redirect:/";
   } 
   
   @ModelAttribute("sns")
	public List<SnsList> snsList() {
		List<SnsList> snsList = this.snsListRepository.findAll();
		return snsList;
	}
   
   @ModelAttribute("favorite")
	public List<FavoriteList> favoriteList() {
		List<FavoriteList> favoriteList = this.favoriteListRepository.findAll();
		return favoriteList;
	}
   
   @ModelAttribute("language")
	public List<LanguageList> languageList() {
		List<LanguageList> languageList = this.languageListRepository.findAll();
		return languageList;
	}
}
