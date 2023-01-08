package com.penpal.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.penpal.project.domain.Member;
import com.penpal.project.domain.list.Category;
import com.penpal.project.domain.list.Country;
import com.penpal.project.domain.list.Language;
import com.penpal.project.domain.list.Location;
import com.penpal.project.repository.list.CategoryRepository;
import com.penpal.project.repository.list.CountryRepository;
import com.penpal.project.repository.list.LanguageRepository;
import com.penpal.project.repository.list.LocationRepository;
import com.penpal.project.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class BaseController {
    private final LanguageRepository languageListRepository;
    private final LocationRepository locationListRepository;
    private final CountryRepository countryListRepository;
    private final CategoryRepository categoryListRepository;
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
	public List<Language> languageList(){
		List<Language> languageLists = languageListRepository.findAll();
		return languageLists;
	}

    @ModelAttribute("location")
	public List<Location> locationList() {
		List<Location> locationLists = locationListRepository.findAll();
		return locationLists;
	}

	@ModelAttribute("country")
	public List<Country> countryList() {
		List<Country> countryLists = countryListRepository.findAll();
		return countryLists;
	}
	
	// by 장유란, 템플릿에서 category... 요청 시 리스트를 보내주는 기능

	// model.addAttribute("category", categoryLists)를 따로 떼어놓은 기능
	@ModelAttribute("category")
	public List<Category> categoryList() {
		List<Category> categoryLists = categoryListRepository.findAll();
		return categoryLists;
	}
	
}
