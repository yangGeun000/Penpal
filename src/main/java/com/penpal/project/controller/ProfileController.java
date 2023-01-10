package com.penpal.project.controller;

import com.penpal.project.DataNotFoundException;
import com.penpal.project.domain.Member;
import com.penpal.project.domain.Profile;
import com.penpal.project.domain.list.*;
import com.penpal.project.dto.ProfileForm;
import com.penpal.project.repository.list.*;
import com.penpal.project.service.MemberService;
import com.penpal.project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    private final MemberService memberService;
    private final LocationRepository locationListRepository;
    private final CountryRepository countryListRepository;
    private final SnsRepository snsListRepository;
    private final FavoriteRepository favoriteListRepository;
    private final LanguageRepository languageListRepository;

    @RequestMapping("")
    public String users(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "kw", defaultValue = "") String kw,
                        @RequestParam(value = "location", defaultValue = "") String location,
                        @RequestParam(value = "country", defaultValue = "") String country) {

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

    // 프로필 생성
    @PostMapping("/profile/create")
    public String profileCreate(@Valid ProfileForm profileForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            log.error("create profile error = {}", bindingResult);
            return "profile/user_profile_form";
        }

        Member member = this.memberService.getMember(principal.getName());
        Location location = locationListRepository.findByName(profileForm.getLocation());
        Country country = countryListRepository.findByName(profileForm.getCountry());

        this.profileService.create(profileForm, member, location, country);

        return "redirect:/";
    }

    // 프로필 상세 보기
    @RequestMapping(value = "/profile/{id}")
    public String userProfile(Model model, @PathVariable("id") Integer id) {
        try {
            Profile profile = this.profileService.getProfile(id);
            model.addAttribute("profile", profile);
        }catch (DataNotFoundException e){
            e.printStackTrace();
            log.error("not found profile = {}",e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("profile error = {}",e.getMessage());
        }

        return "profile/user_profile";    //user_profile폼으로 이동
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
    public String profileModify(@Valid ProfileForm profileForm, BindingResult bindingResult, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Location location = locationListRepository.findByName(profileForm.getLocation());
        Country country = countryListRepository.findByName(profileForm.getCountry());

        if (bindingResult.hasErrors()) {
            log.error("modify profile error = {}", bindingResult);
            return "profile/user_profile_form";
        }

        this.profileService.modify(profileForm, member.getProfile(), location, country);

        return "redirect:/";
    }

    // by 구양근, img 태그 이미지
    @RequestMapping("/image")
    @ResponseBody
    public Resource getImage(@RequestParam String url) throws MalformedURLException {
        return new UrlResource("file:" + url);
    }

    @ModelAttribute("sns")
    public List<Sns> snsList() {
        List<Sns> snsList = this.snsListRepository.findAll();
        return snsList;
    }

    @ModelAttribute("favorite")
    public List<Favorite> favoriteList() {
        List<Favorite> favoriteList = this.favoriteListRepository.findAll();
        return favoriteList;
    }

    @ModelAttribute("language")
    public List<Language> languageList() {
        List<Language> languageList = this.languageListRepository.findAll();
        return languageList;
    }
}
