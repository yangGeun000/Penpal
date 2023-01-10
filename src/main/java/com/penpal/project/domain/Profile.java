package com.penpal.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.penpal.project.domain.list.Country;
import com.penpal.project.domain.list.Location;
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
	
	// 성별 int -> String(varchar(20)) 변환
	private String gender;
	
	// 성별 int -> Integer 변환
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
	
}
