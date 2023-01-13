package com.penpal.project.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.penpal.project.DataNotFoundException;
import com.penpal.project.domain.Member;
import com.penpal.project.dto.UpdateMemberForm;
import com.penpal.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    public Member create(String memberId, String memberPw, String name, String email) {
        Member user = new Member();
        // by 장유란, user* - > member*
        log.info("/ memberAdd  / " + memberId);
        user.setMemberId(memberId);
        user.setMemberPw(passwordEncoder.encode(memberPw));
        user.setName(name);
        user.setEmail(email);
        user.setCreateDate(LocalDateTime.now());
        user.setFriendRequestCount(0);
        user.setMessageCount(0);
        this.memberRepository.save(user);
        
        return user;
    }
    
    //by 구양근, 멤버정보 수정
    public boolean updateMember(UpdateMemberForm updateMemberForm, Member member) throws Exception{
    	// 현재 비밀번호가 일치한다면 수정
    	if(passwordEncoder.matches(updateMemberForm.getCurrentPw(), member.getMemberPw())) {
    		member.setMemberPw(passwordEncoder.encode(updateMemberForm.getNewPw()));
    		this.memberRepository.save(member);
    		return true;
    	}
    	return false;
    }
    
    public void saveMember(Member member) {
    	this.memberRepository.save(member);
    }
    
    public Member getMember(String memberId) {
        Optional<Member> member = this.memberRepository.findByMemberId(memberId);
        
        if(member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("sitemember not found");
        }
    }
    
    public Member getMember(Integer Id) {
        Optional<Member> member = this.memberRepository.findById(Id);
        
        if(member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("sitemember not found");
        }
    }
    
    // by 구양근, 총 멤버 수
    public long memberCount() {
    	return this.memberRepository.count();
    }
    
    // by 구양근, 온라인 중인 멤버 수
    public long onlineMemberCount() {
    	return this.memberRepository.countByConn(true);
    }
  
}
