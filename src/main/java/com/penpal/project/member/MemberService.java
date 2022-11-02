package com.penpal.project.member;

import java.time.LocalDateTime;
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
   
    
    public Member create(String memberId, String memberPw, String name, String email) {
        Member user = new Member();
        // by 장유란, user* - > member*
        log.info("/ memberAdd  / " + memberId);
        user.setMemberId(memberId);
        user.setMemberPw(passwordEncoder.encode(memberPw));
        user.setName(name);
        user.setEmail(email);
        user.setCreateDate(LocalDateTime.now());
        this.memberRepository.save(user);
        
        return user;
    }
   
    public Member getMember(String memberId) {
        Optional<Member> member = this.memberRepository.findByMemberId(memberId);
        
        if(member.isPresent()) {
        	
            return member.get();
        } else {
            throw new DataNotFoundException("sitemember not found");
        }
    }
    
}
