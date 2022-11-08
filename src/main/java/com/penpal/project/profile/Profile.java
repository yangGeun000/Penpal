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
	
	// 성별 int -> String(varchar(20)) 변환
	private String gender;
	
	// 성별 int -> Integer 변환
	private int age;
	
	@OneToOne
	private Member member;
	
	@ManyToOne
	private LocationList location;
	
	@ManyToOne
	private CountryList country;
	
	private String sns1;
	private String sns2;
	private String sns3;
	
	private String favorite1;
	private String favorite2;
	private String favorite3;
	
	private String language1;
	private String language2;
	private String language3;
	
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	private LocalDateTime lastDate;
	
}
