package com.penpal.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.penpal.project.member.Member;
import com.penpal.project.member.MemberRepository;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSessionListener implements HttpSessionListener {

	private final MemberRepository memberRepository;

	// by 안준언, 세션 생성 이벤트 발생시 자동 실행 , 세션 유지 시간 10분 설정
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(600);
        log.info("sessionCreated/ 세션 생성");
        log.info("session time -> {}", httpSessionEvent.getSession().getMaxInactiveInterval());
        
    }

    // by 안준언, 세션 만료 이벤트 발생시 자동 실행, 세션 만료시 친구의 접속 상태를 false로 수정
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("sessionDestroyed -> {}", httpSessionEvent.getSession().getAttribute("memberId"));
        Optional<Member> mb = this.memberRepository.findByMemberId((String)httpSessionEvent.getSession().getAttribute("memberId"));
        Member member = mb.get();
        member.setConn(false);
        this.memberRepository.save(member);

    }

}