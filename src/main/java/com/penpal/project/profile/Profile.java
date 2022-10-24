package com.penpal.project.profile;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.penpal.project.list.CountryList;
import com.penpal.project.list.LocationList;
import com.penpal.project.member.Member;
import com.penpal.project.member.list.MemberFavorite;
import com.penpal.project.member.list.MemberLanguage;
import com.penpal.project.member.list.MemberSns;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=60)
	private String nickname;
	
	private int gender;
	
	private int age;
	
	@OneToOne
	private Member member;
	
	@ManyToOne
	private LocationList location;
	
	@ManyToOne
	private CountryList country;
	
	@OneToOne
	private MemberSns sns;
	
	@OneToOne
	private MemberFavorite favorite;
	
	@OneToOne
	private MemberLanguage language;
	
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	private LocalDateTime lastDate;
	
}
