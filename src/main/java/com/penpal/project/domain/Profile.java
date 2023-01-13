package com.penpal.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.penpal.project.domain.list.Country;
import com.penpal.project.domain.list.Location;
import com.penpal.project.dto.ProfileForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=60)
	private String nickname;
	
	// 성별 int -> String 변환
	private String gender;

	private int age;
	
	private String url;
	
	@OneToOne
	@JsonBackReference
	private Member member;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private Country country;
	
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

	public void modify(ProfileForm profileForm){
		this.setNickname(profileForm.getNickname());
		this.setGender(profileForm.getGender());
		this.setAge(profileForm.getAge());
		this.setComment(profileForm.getComment());
		this.setSns1(profileForm.getSns1());
		this.setSns2(profileForm.getSns2());
		this.setSns3(profileForm.getSns3());
		this.setFavorite1(profileForm.getFavorite1());
		this.setFavorite2(profileForm.getFavorite2());
		this.setFavorite3(profileForm.getFavorite3());
		this.setLanguage1(profileForm.getLanguage1());
		this.setLanguage2(profileForm.getLanguage2());
		this.setLanguage3(profileForm.getLanguage3());
	}
}
