package com.penpal.project.domain;

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

import com.penpal.project.domain.list.Category;
import com.penpal.project.domain.list.Country;
import com.penpal.project.domain.list.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 20)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private Country country;
	
	@ManyToOne
	private Category category;
	
	// by 장유란, author -> writer
	@ManyToOne
	private Member writer;
	
	private LocalDateTime createDate;
	
	// by 장유란, 수정일시
	private LocalDateTime modifyDate;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
}
