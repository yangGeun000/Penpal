package com.penpal.project.board;

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

import com.penpal.project.answer.Answer;
import com.penpal.project.list.CategoryList;
import com.penpal.project.list.CountryList;
import com.penpal.project.list.LocationList;
import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Board {
	// git commit --amend 최근 커밋 수정 
	// :wq commit 상세설명 저장
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne
	private LocationList location;
	
	@ManyToOne
	private CountryList country;
	
	@ManyToOne
	private CategoryList category;
	

	// by 장유란, author -> writer
	@ManyToOne
	private Member writer;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	

}
