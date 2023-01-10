package com.penpal.project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Data
public class ProfileForm {

	@NotEmpty(message="닉네임을 적어주세요")
	private String nickname;
	
	@NotEmpty(message="성별을 정해주세요")
	private String gender;
	
	@NotEmpty(message="나이를 입력해주세요")
	private String age;
	
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

}
