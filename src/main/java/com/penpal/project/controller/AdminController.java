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
import com.penpal.project.domain.list.Category;
import com.penpal.project.domain.list.Country;
import com.penpal.project.domain.list.Favorite;
import com.penpal.project.domain.list.Language;
import com.penpal.project.domain.list.Location;
import com.penpal.project.domain.list.Sns;
import com.penpal.project.repository.MemberRepository;
import com.penpal.project.repository.ProfileRepository;
import com.penpal.project.repository.list.CategoryRepository;
import com.penpal.project.repository.list.CountryRepository;
import com.penpal.project.repository.list.FavoriteRepository;
import com.penpal.project.repository.list.LanguageRepository;
import com.penpal.project.repository.list.LocationRepository;
import com.penpal.project.repository.list.SnsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	private final CategoryRepository categoryListRepository;
	private final LocationRepository locationListRepository;
	private final CountryRepository countryListRepository;
	private final SnsRepository snsListRepository;
	private final FavoriteRepository favoriteListRepository;
	private final LanguageRepository languageListRepository;
	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;

	@GetMapping("/admin")
	public String admin(Model model) {
		List<Category> categoryList = categoryListRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		List<Location> locationList = locationListRepository.findAll();
		model.addAttribute("locationList", locationList);

		List<Country> countryList = countryListRepository.findAll();
		model.addAttribute("countryList", countryList);
		
		List<Sns> snsList = snsListRepository.findAll();
		model.addAttribute("snsList", snsList);
		
		List<Favorite> favoriteList = favoriteListRepository.findAll();
		model.addAttribute("favoriteList", favoriteList);
		
		List<Language> languageList = languageListRepository.findAll();
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
		Category category = new Category();
		category.setName(categoryName);
		if (categoryName != null) {
			categoryListRepository.save(category);
		}

		Location location = new Location();
		location.setName(locationName);
		if (locationName != null) {
			locationListRepository.save(location);
		}

		Country country = new Country();
		country.setName(countryName);
		if (countryName != null) {
			countryListRepository.save(country);
		}
		
		Sns sns = new Sns();
		sns.setName(snsName);
		if (snsName != null) {
			snsListRepository.save(sns);
		}
		
		Favorite favorite = new Favorite();
		favorite.setName(favoriteName);
		if (favoriteName != null) {
			favoriteListRepository.save(favorite);
		}
		
		Language language = new Language();
		language.setName(languageName);
		if (languageName != null) {
			languageListRepository.save(language);
		}
		
		return "redirect:/admin";
	}

	@GetMapping("/category/delete/{id}")
	public String categoryDelete(@PathVariable("id") Category id) {
		this.categoryListRepository.delete(id);
		return "redirect:/admin";
	}

	@GetMapping("/location/delete/{id}")
	public String locationDelete(@PathVariable("id") Location id) {
		this.locationListRepository.delete(id);
		return "redirect:/admin";
	}

	@GetMapping("/country/delete/{id}")
	public String countryDelete(@PathVariable("id") Country id) {
		this.countryListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/sns/delete/{id}")
	public String snsDelete(@PathVariable("id") Sns id) {
		this.snsListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/favorite/delete/{id}")
	public String favoriteDelete(@PathVariable("id") Favorite id) {
		this.favoriteListRepository.delete(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/language/delete/{id}")
	public String languageDelete(@PathVariable("id") Language id) {
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
