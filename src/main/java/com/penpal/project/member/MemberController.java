package com.penpal.project.member;

import java.security.Principal;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
@Slf4j
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
    
    @GetMapping("/modify")
    public String update(UpdateMemberForm updateMemberForm) {
		return "member/user_info_modify";
    	
    }
    
    //by 구양근, 멤버 정보 수정
    @PostMapping("/modify")
    public String updateMember(@Valid UpdateMemberForm updateMemberForm, BindingResult bindingResult, Principal principal) {
    	
    	if(bindingResult.hasErrors()) {
    		log.error("updateMemberError = {}",bindingResult);
    		return "member/user_info_modify";
    	} 
    	try {
    		Member member = this.memberService.getMember(principal.getName());
    		boolean check = this.memberService.updateMember(updateMemberForm, member);
    		if(!check) {
    			log.error("현재 비밀번호가 일치하지 않습니다.");
    			bindingResult.rejectValue("currentPw", "NotEmpty.currentPassword","현재 비밀번호가 일치하지 않습니다.");
    			return "member/user_info_modify";
    		}
    		return "redirect:/";
    	}catch(Exception e){
    		e.printStackTrace();
            bindingResult.reject("updateMemberFailed", e.getMessage());
            return "member/user_info_modify";
    	}

    }
    
    // by 구양근, 확인한 메세지, 친구요청 저장 
 	@RequestMapping("/setCount")
 	@ResponseBody
 	public void setCount(@RequestParam HashMap<Object, Object> params, Principal principal){
 		Member member = this.memberService.getMember(principal.getName());
 		member.setMessageCount(Integer.parseInt((String) params.get("checkMessage")));
 		member.setFriendRequestCount(Integer.parseInt((String) params.get("checkFriend")));
 		this.memberService.saveMember(member);
 	}
}
