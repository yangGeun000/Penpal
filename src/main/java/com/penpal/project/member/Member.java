package com.penpal.project.member;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.WrongClassException;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.penpal.project.chat.Room;
import com.penpal.project.friend.Friend;
import com.penpal.project.friend.FriendRequest;
import com.penpal.project.profile.Profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// by 장유란, user* -> member 변수명 변경
	@Column(unique = true, length = 30)
	@JsonIgnore
	private String memberId;

	@Column
	@JsonIgnore
	private String memberPw;
	
	@Column
	@JsonIgnore
	private String memberNPw;
	
	@JsonIgnore
	private String memberNPwCheck;

	@Column(unique = true, length = 60)
	private String name;

	@Column(unique = true, length = 150)
	@JsonIgnore
	private String email;
	
	@JsonIgnore
	private LocalDateTime createDate;

	// by 장유란, author -> writer 변수명 변경
	@ManyToOne
	@JsonIgnore
	private Member writer;

	// by 안준언, 현재 접속여부 구분을 위해 해당 필드 살렸습니다.
	private boolean conn;
	
	// by 구양근, 확인한 친구 요청 개수
	private int friendRequestCount;
	
	// by 구양근, 확인한 메세지 개수
	private int messageCount;

	// by 구양근, 내가 만든 대화방 리스트
	@OneToMany(mappedBy = "maker", cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<Room> makerList;

	// by 구양근, 내가 초대된 대화방 리스트
	@OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<Room> guestList;

	// by 구양근, 프로필
	@OneToOne(mappedBy = "member")
	//@JsonBackReference
	private Profile profile;
	
	// by 안준언, 친구 목록 리스트
	@OneToMany(mappedBy = "mine", cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<Friend> friendList;
	
	// by 안준언, 친구 요청 목록 리스트
	@OneToMany(mappedBy = "receive", cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<FriendRequest> friendRequestList;

	public void changePassword(String memberPw, String memberNPw, String memberNPwC) {
		if(!memberPw.equals(memberPw))
			throw new WrongClassException("1", memberPw, memberNPw);
		this.memberPw = memberNPw;
		
	}

	public void update(Member member) {
		// TODO Auto-generated method stub
		
	}

	
}
