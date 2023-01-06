package com.penpal.project.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.penpal.project.board.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
	private Member member;
   
    
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
    
    public int updatePw(Member member) {
    	try{
    		Member user =  (this.memberRepository.findByMemberId(member.getMemberId()).get());
    //		if(!passwordEncoder.encode(member.getMemberPw()).equals(user.getMemberPw())) {
    //			return -1;
    //		}
    		
    		user.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
    		this.memberRepository.save(user);
    		
    		return 1;
    	}catch (Exception e) {
    		System.out.println("test3");
			return -1;
		}
    }
    
    public void update(Member member) {
    	
    	
    	
    	this.memberRepository.save(member);
    	log.info("이거 되냐?");
    }
    
    public void setMember(Member member) {
    	this.member = member;
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
