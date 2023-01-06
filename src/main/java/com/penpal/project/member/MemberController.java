package com.penpal.project.member;

import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
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
    
    @GetMapping("/modify")
    public String update() {
		return "member/user_info_modify";
    	
    }
    
    @PostMapping("/modify")
    public String updatePassword(@Valid Member member, BindingResult bindingResult, HttpSession session) {
    	
    	
    	if(bindingResult.hasErrors()) {
    		
    	} try {
    		if(!member.getMemberNPw().equals(member.getMemberNPwCheck())) {
    			throw new Exception("변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    		}
    		String memberid = session.getAttribute("memberId").toString();
    		member.setMemberId(memberid);
    		
    		System.out.println("test1");
			int result = memberService.updatePw(member);
			System.out.println("test2");
			System.out.println(result);
			
			
			if(result == 1) {
				return "/";
			}else {
				return "member/user_info_modify";
			}
			
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
            return "member/user_info_modify";
        }
    	
    		
    }

    @RequestMapping("/modify")
    public String infoModify(){
        return "member/user_info_modify";
    } //by 조성빈, 유저 정보(비밀번호) 수정 템플릿 작성용 임시 매핑
    
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
