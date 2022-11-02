package com.penpal.project;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.penpal.project.list.CountryList;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.LanguageList;
import com.penpal.project.list.LanguageListRepository;
import com.penpal.project.list.LocationList;
import com.penpal.project.list.LocationListRepository;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class BaseController {
    private final LanguageListRepository languageListRepository;
    private final LocationListRepository locationListRepository;
    private final CountryListRepository countryListRepository;
	private final MemberService memberService;
	
	@ModelAttribute("loginMember")
	public Member getLoginMember(Principal principal) {
		if(principal != null) {
			Member loginMember = this.memberService.getMember(principal.getName());
			return loginMember;
		}
		return null;
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
