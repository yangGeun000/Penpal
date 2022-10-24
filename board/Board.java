package com.penpal.project.board;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.penpal.project.answer.Answer;
import com.penpal.project.list.CategoryList;
import com.penpal.project.list.CountryList;
import com.penpal.project.list.LocationList;
import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;

// 장유란 2022-10-21 게시판 기능
@Getter
@Setter
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne(targetEntity = LocationList.class)
	@JoinColumn(name = "location_name")
	private LocationList location;
	
	@ManyToOne(targetEntity = CountryList.class)
	@JoinColumn(name = "country_name")
	private CountryList country;
	
	@ManyToOne(targetEntity = CategoryList.class)
	@JoinColumn(name = "category_name")
	private CategoryList category;
	
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member_id")
	private Integer writer;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@ManyToOne
    private Member author;

	
}
