package com.penpal.project;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.penpal.project.list.CategoryList;
import com.penpal.project.list.CategoryListRepository;
import com.penpal.project.list.CountryList;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.LocationList;
import com.penpal.project.list.LocationListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {

	private final CategoryListRepository categoryListRepository;
	private final LocationListRepository locationListRepository;
	private final CountryListRepository countryListRepository;

	@GetMapping("/admin")
	public String admin(Model model) {
		List<CategoryList> categoryList = categoryListRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		List<LocationList> locationList = locationListRepository.findAll();
		model.addAttribute("locationList", locationList);

		List<CountryList> countryList = countryListRepository.findAll();
		model.addAttribute("countryList", countryList);

		return "admin/admin";
	}

	@PostMapping("/admin")
	public String admin(String categoryName, String locationName, String countryName) {
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
}
