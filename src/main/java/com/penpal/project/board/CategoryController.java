package com.penpal.project.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penpal.project.list.CategoryList;
import com.penpal.project.list.CategoryListRepository;
import com.penpal.project.list.CountryList;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.LocationList;
import com.penpal.project.list.LocationListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController{

	private final BoardService boardService;
    private final CategoryListRepository categoryListRepository;
    private final LocationListRepository locationListRepository;
    private final CountryListRepository countryListRepository;
//	@RequestMapping("/FREE")
//	public String categoryFree(Model model) {
//		
//		return "category/category_FREE";
//	}
	@RequestMapping("/FREE")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);

        return "category/category_FREE";
    }
    @ModelAttribute("category")
    public List<CategoryList> categoryList() {
        List<CategoryList> categoryLists = categoryListRepository.findAll();
        System.out.println(categoryLists);

        return categoryLists;
    }

    @ModelAttribute("location")
    public List<LocationList> locationList() {
        List<LocationList> locationLists = locationListRepository.findAll();
        System.out.println(locationLists);

        return locationLists;

    }

    @ModelAttribute("country")
    public List<CountryList> countryList() {
        List<CountryList> countryLists = countryListRepository.findAll();
        System.out.println(countryLists);

        return countryLists;
    }
}
