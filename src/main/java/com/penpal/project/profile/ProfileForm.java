package com.penpal.project.profile;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class ProfileForm {
	
	private Integer id;
	
	@NotEmpty(message="닉네임을 적어주세요")
	private String nickname;
	
	@NotEmpty(message="성별을 정해주세요")
	private String gender;
	
	@NotEmpty(message="나이를 입력해주세요")	//숫자로 변경
	private String age;
	
	@NotEmpty(message="사는 지역을 입력해주세요")
	private String location;
	
	@NotEmpty(message="나라를 입력해주세요")
	private String country;
	
	private MultipartFile picture;
	
	//@NotEmpty(message="사용하는 sns를 입력해주세요")
	private String sns1;
	private String sns2;
	private String sns3;
	
	//@NotEmpty(message="취미를 입력해주세요")
	private String favorite1;
	private String favorite2;
	private String favorite3;
	
	//@NotEmpty(message="사용하는 언어를 입력해주세요")
	private String language1;
	private String language2;
	private String language3;
	
	private String comment;

}
