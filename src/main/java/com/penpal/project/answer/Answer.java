package com.penpal.project.answer;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.penpal.project.board.Board;
import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;

// 장유란 2022-10-21 게시판 답글기능
@Getter
@Setter
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member_id")
	private Integer writer;
	
	private LocalDateTime createDate;
	
	@ManyToOne
	private Board board;
	
	@ManyToOne
    private Member author;
	
}
