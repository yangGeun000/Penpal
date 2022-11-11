package com.penpal.project.chat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Member sender;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime sendDate;

	@ManyToOne
	@JsonBackReference
	private Room room;
	
}
