package com.penpal.project;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.penpal.project.list.CategoryList;
import com.penpal.project.list.CategoryListRepository;
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
    private final CategoryListRepository categoryListRepository;
	private final MemberService memberService;
	
	// by 구양근, 로그인한 멤버 모델에 추가
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
	
	// by 장유란, 템플릿에서 category... 요청 시 리스트를 보내주는 기능

	// model.addAttribute("category", categoryLists)를 따로 떼어놓은 기능
	@ModelAttribute("category")
	public List<CategoryList> categoryList() {
		List<CategoryList> categoryLists = categoryListRepository.findAll();
		return categoryLists;
	}
	
}
