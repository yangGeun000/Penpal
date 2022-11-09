package com.penpal.project.member;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    
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
            bindingResult.rejectValue("memberPw2", "passwordInCorrect", 
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
    public String login() {
        return "member/login";
    }

    @RequestMapping("/modify")
    public String infoModify(){
        return "member/user_info_modify";
    } //by 조성빈, 유저 정보(비밀번호) 수정 템플릿 작성용 임시 매핑
}
