package com.penpal.project.member;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.penpal.project.chat.Room;
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
	
	@Column(unique = true , length = 30)
	private String memberId;
	
	@Column(length=30)
	private String memberPw;
	
	@Column(unique = true, length = 60)
	private String name;
	
	@Column(unique = true, length = 150)
	private String email;
	
	// by 구양근, db에서 처리하지말고 세션을 통해서 구분하는게 좋을것 같습니다
//	private boolean conn;
	
	private LocalDateTime createDate;
	
	// by 구양근, 내가 만든 대화방 리스트  
	@OneToMany(mappedBy = "maker", cascade = CascadeType.REMOVE)
	private List<Room> makerList;
	
	// by 구양근, 내가 초대된 대화방 리스트
	@OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
	private List<Room> guestList;
	
	// by 구양근, 프로필
	@OneToOne(mappedBy = "member")
	private Profile profile;
	
}
