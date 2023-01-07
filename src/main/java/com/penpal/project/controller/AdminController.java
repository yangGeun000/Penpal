package com.penpal.project.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.penpal.project.domain.Member;
import com.penpal.project.domain.Profile;
import com.penpal.project.domain.list.CategoryList;
import com.penpal.project.domain.list.CountryList;
import com.penpal.project.domain.list.FavoriteList;
import com.penpal.project.domain.list.LanguageList;
import com.penpal.project.domain.list.LocationList;
import com.penpal.project.domain.list.SnsList;
import com.penpal.project.repository.MemberRepository;
import com.penpal.project.repository.ProfileRepository;
import com.penpal.project.repository.list.CategoryListRepository;
import com.penpal.project.repository.list.CountryListRepository;
import com.penpal.project.repository.list.FavoriteListRepository;
import com.penpal.project.repository.list.LanguageListRepository;
import com.penpal.project.repository.list.LocationListRepository;
import com.penpal.project.repository.list.SnsListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	private final CategoryListRepository categoryListRepository;
	private final LocationListRepository locationListRepository;
	private final CountryListRepository countryListRepository;
	private final SnsListRepository snsListRepository;
	private final FavoriteListRepository favoriteListRepository;
	private final LanguageListRepository languageListRepository;
	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;

	@GetMapping("/admin")
	public String admin(Model model) {
		List<CategoryList> categoryList = categoryListRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		List<LocationList> locationList = locationListRepository.findAll();
		model.addAttribute("locationList", locationList);

		List<CountryList> countryList = countryListRepository.findAll();
		model.addAttribute("countryList", countryList);
		
		List<SnsList> snsList = snsListRepository.findAll();
		model.addAttribute("snsList", snsList);
		
		List<FavoriteList> favoriteList = favoriteListRepository.findAll();
		model.addAttribute("favoriteList", favoriteList);
		
		List<LanguageList> languageList = languageListRepository.findAll();
		model.addAttribute("languageList", languageList);

		List<Member> memberList = memberRepository.findAll();
		model.addAttribute("memberList", memberList);
		
		List<Profile> profileList = profileRepository.findAll();
		model.addAttribute("profileList", profileList);
		
		return "admin/admin";
	}

	@PostMapping("/admin")
	public String admin(
			String categoryName, String locationName, String countryName,
			String snsName, String favoriteName, String languageName) {
		CategoryList category = new CategoryList();
		category.setName(categoryName);
		if (categoryName != null) {
			categoryListRepository.save(category);
		}

		LocationList location = new LocationList();
		location.setName(locationName);
		if (locationName != null) {
			locationListRepository.save(location);
		}

		CountryList country = new CountryList();
		country.setName(countryName);
		if (countryName != null) {
			countryListRepository.save(country);
		}
		
		SnsList sns = new SnsList();
		sns.setName(snsName);
		if (snsName != null) {
			snsListRepository.save(sns);
		}
		
		FavoriteList favorite = new FavoriteList();
		favorite.setName(favoriteName);
		if (favoriteName != null) {
			favoriteListRepository.save(favorite);
		}
		
		LanguageList language = new LanguageList();
		language.setName(languageName);
		if (languageName != null) {
			languageListRepository.save(language);
		}
		
		return "redirect:/admin";
	}

	@GetMapping("/category/delete/{id}")
	public String categoryDelete(@PathVariable("id") CategoryList id) {
		this.categoryListRepository.delete(id);
		return "redirect:/admin";
	}

	@GetMapping("/location/delete/{id}")
	public String locationDelete(@PathVariable("id") LocationList id) {
		this.locationListRepository.delete(id);
		return "redirect:/admin";
	}

	@GetMapping("/country/delete/{id}")
	public String countryDelete(@PathVariable("id") CountryList id) {
		this.countryListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/sns/delete/{id}")
	public String snsDelete(@PathVariable("id") SnsList id) {
		this.snsListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/favorite/delete/{id}")
	public String favoriteDelete(@PathVariable("id") FavoriteList id) {
		this.favoriteListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/language/delete/{id}")
	public String languageDelete(@PathVariable("id") LanguageList id) {
		this.languageListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/member/delete/{id}")
	public String memberDelete(@PathVariable("id") Member id) {
		this.memberRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/profile/delete/{id}")
	public String profileDelete(@PathVariable("id") Profile id) {
		this.profileRepository.delete(id);
		return "redirect:/admin";
	}
}
