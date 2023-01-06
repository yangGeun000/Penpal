package com.penpal.project;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.penpal.project.member.Member;
import com.penpal.project.member.MemberRepository;
import com.penpal.project.profile.Profile;
import com.penpal.project.profile.ProfileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final MemberRepository mr;
	private final ProfileRepository pr;

	// by 안준언, 로그인한 세션으로 멤버의 접속 상태를 true로 변경
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		System.out.println("authentication:: " + authentication.getName());
		httpServletRequest.getSession().setAttribute("memberId", authentication.getName());
		System.out.println(httpServletRequest.getSession().getAttribute("memberId"));
		Optional<Member> mb = mr.findByMemberId(httpServletRequest.getSession().getAttribute("memberId").toString());
		Member member = mb.get();
		member.setConn(true);
		mr.save(member);

		Profile profile = pr.findByMember(member);
		if (profile != null) {
			profile.setLastDate(LocalDateTime.now());
			pr.save(profile);
		}

		response.sendRedirect("/");

	}
}