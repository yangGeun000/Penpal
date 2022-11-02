package com.penpal.project.member;

import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.list.LanguageList;
import com.penpal.project.list.LanguageListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final LanguageListRepository languageListRepository;
    
    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {
        return "member/signup";
    }
    
    @PostMapping("/signup")
    public String signup(
            @Valid MemberCreateForm memberCreateForm, 
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "member/signup";
        }
        
        if(!memberCreateForm.getMemberPw().equals(memberCreateForm.getMemberPw2())) {
            bindingResult.rejectValue("userPw2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "member/signup";
        }
        
        try {
            memberService.create(
                    memberCreateForm.getMemberId(), 
                    memberCreateForm.getMemberPw(), 
                    memberCreateForm.getName(), 
                    memberCreateForm.getEmail()
                    );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject(
                    "signupFailed", 
                    "이미 등록된 사용자입니다."
                    );
            return "member/signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/signup";
        }
        
        return "member/login";
    }
    
    @GetMapping("/login")
    public String login(Model model, String memberId) {
    	model.addAttribute("memberId", memberId);
        return "member/login";
    }

    
    // by 조성빈, 상단 NAVI language 기능 사용을 위해 추가
	@ModelAttribute("language")
	public List<LanguageList> languageList(){
		List<LanguageList> languageLists = languageListRepository.findAll();
		return languageLists;
	}
}
