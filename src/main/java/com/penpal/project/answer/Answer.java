package com.penpal.project.answer;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;

import com.penpal.project.board.Board;
import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne
	private Member writer;

	private LocalDateTime createDate;
	
	// by 장유란, 수정일시 추가	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Board board;
	
}
