package com.penpal.project.dto;

import com.penpal.project.domain.Profile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@Data
public class ProfileForm {

	@NotEmpty(message="닉네임을 적어주세요")
	private String nickname;
	
	@NotEmpty(message="성별을 정해주세요")
	private String gender;
	
	@NotNull(message="나이를 입력해주세요")
	private int age;
	
	@NotEmpty(message="사는 지역을 입력해주세요")
	private String location;
	
	@NotEmpty(message="나라를 입력해주세요")
	private String country;
	
	private MultipartFile picture;

	private String sns1;
	private String sns2;
	private String sns3;

	private String favorite1;
	private String favorite2;
	private String favorite3;

	private String language1;
	private String language2;
	private String language3;
	
	private String comment;

	public Profile convertFormToProfile(){
		return Profile.builder()
				.nickname(this.getNickname())
				.age(this.getAge())
				.gender(this.getGender())
				.comment(this.getComment())
				.sns1(this.getSns1())
				.sns2(this.getSns2())
				.sns3(this.getSns3())
				.favorite1(this.getFavorite1())
				.favorite2(this.getFavorite2())
				.favorite3(this.getFavorite3())
				.language1(this.getLanguage1())
				.language2(this.getLanguage2())
				.language3(this.getLanguage3())
				.lastDate(LocalDateTime.now())
				.build();
	}

	public void applyProfileValue(Profile profile){
		this.setNickname(profile.getNickname());
		this.setGender(profile.getGender());
		this.setLocation(profile.getLocation().getName());
		this.setAge(profile.getAge());
		this.setCountry(profile.getCountry().getName());
		this.setComment(profile.getComment());
		this.setSns1(profile.getSns1());
		this.setSns2(profile.getSns2());
		this.setSns3(profile.getSns3());
		this.setFavorite1(profile.getFavorite1());
		this.setFavorite2(profile.getFavorite2());
		this.setFavorite3(profile.getFavorite3());
		this.setLanguage1(profile.getLanguage1());
		this.setLanguage2(profile.getLanguage2());
		this.setLanguage3(profile.getLanguage3());
	}
}
