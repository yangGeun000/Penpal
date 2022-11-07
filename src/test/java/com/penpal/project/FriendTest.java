package com.penpal.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.penpal.project.friend.Friend;
import com.penpal.project.friend.FriendRepository;
import com.penpal.project.friend.FriendRequest;
import com.penpal.project.friend.FriendRequestRepository;
import com.penpal.project.friend.FriendService;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberRepository;

@SpringBootTest
public class FriendTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private FriendRepository friendRepository;
	
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	@Autowired
	private FriendService friendService;
	
//	// by 안준언, Friend 생성을 위해 참조 데이터 Member 생성
//	@Test
//	void addMember() {
//		Member mb1 = new Member();
//		mb1.setName("aaa");
//		memberRepository.save(mb1);
//		
//		Member mb2 = new Member();
//		mb2.setName("bbb");
//		memberRepository.save(mb2);
//		
//		Member mb3 = new Member();
//		mb3.setName("ccc");
//		memberRepository.save(mb3);
//		
//		Member mb4 = new Member();
//		mb4.setName("ddd");
//		memberRepository.save(mb4);
//	}
	
//	// by 안준언, Member Table 데이터 조회
//	@Test
//	void testMemberTable() {
//		List<Member> all = this.memberRepository.findAll();
//		assertEquals(4, all.size());
//		
//		Member m1 = all.get(0);
//		assertEquals("aaa", m1.getName());
//	}

	// by 안준언, 친구 요청 기능 테스트
//	@Test
//	void requestFriend() {
//		List<Member> all = this.memberRepository.findAll();
//		Member m1 = all.get(0);
//		Member m2 = all.get(1);
//		Member m3 = all.get(2);
//		Member m4 = all.get(3);
//		
//		FriendRequest fr1 = new FriendRequest();
//		fr1.setReceive(m1);
//		fr1.setSend(m2);
//		
//		FriendRequest fr2 = new FriendRequest();
//		fr2.setReceive(m1);
//		fr2.setSend(m3);
//		
//		FriendRequest fr3 = new FriendRequest();
//		fr3.setReceive(m1);
//		fr3.setSend(m4);
//		
//		this.friendRequestRepository.save(fr1);
//		this.friendRequestRepository.save(fr2);
//		this.friendRequestRepository.save(fr3);
//	}
	
//	// by 안준언, 친구 요청 목록 조회 테스트
//	@Transactional
//	@Test
//	public void test() {
//		Optional<Member> mb = this.memberRepository.findById(1);
//		Member user = mb.get();
//		List<FriendRequest> fr = user.getFriendRequestList();
//		
//		assertEquals(fr.size(), 3);
//		
//	}
	
//	// by 안준언, 친구 요청 수락 기능 테스트
//	@Test
//	public void testAgreeFriend() {
//		Optional<Member> mb1 = memberRepository.findById(1);
//		Member member1 = mb1.get();
//		
//		Optional<Member> mb2 = memberRepository.findById(2);
//		Member member2 = mb2.get();
//		
//		this.friendService.agreeFriend(member2, member1);
//
//		
//	}
	
//	// by 안준언, 친구 요청 거절 기능 테스트
//	@Test
//	public void rejectFriendRequest() {
//		Optional<FriendRequest> fr = this.friendRequestRepository.findById(2);
//		FriendRequest fRequest = fr.get();
//		this.friendService.rejectFriend(fRequest);
//		
//	}
	
//	// by 안준언, Member PK 값을 참조한 Friend 데이터 생성
//	@Test
//	void addFriend() {
//		Optional<Member> member1 = memberRepository.findById(1);
//		Member mb1 = member1.get();
//		
//		Optional<Member> member2 = memberRepository.findById(2);
//		Member mb2 = member2.get();
//		
//		Optional<Member> member3 = memberRepository.findById(3);
//		Member mb3 = member3.get();
//		
//		Optional<Member> member4 = memberRepository.findById(4);
//		Member mb4 = member4.get();
//		
//		
//		Friend fr1 = new Friend();
//		fr1.setMine(mb1);
//		fr1.setFriend(mb2);
//		friendRepository.save(fr1);
//		
//		Friend fr2 = new Friend();
//		fr2.setMine(mb1);
//		fr2.setFriend(mb3);
//		friendRepository.save(fr2);
//		
//		Friend fr3 = new Friend();
//		fr3.setMine(mb1);
//		fr3.setFriend(mb4);
//		friendRepository.save(fr3);
//		
//		Friend fr4 = new Friend();
//		fr4.setMine(mb2);
//		fr4.setFriend(mb3);
//		friendRepository.save(fr4);
//		
//		Friend fr5 = new Friend();
//		fr5.setMine(mb2);
//		fr5.setFriend(mb4);
//		friendRepository.save(fr5);
//	}
}
