package com.penpal.project.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberSecurityService implements UserDetailsService{

    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Optional<Member> _member = this.memberRepository.findByMemberId(memberId);
        if (_member.isEmpty()) {
        	log.error("username으로 member 찾지못함, memberId => '" + memberId + "'");
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = _member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(memberId)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }
    	log.info(">> login '" + memberId + "/" + authorities + "'");
        return new User(member.getMemberId(), member.getMemberPw(), authorities);
    }
}
