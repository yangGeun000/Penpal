package com.penpal.project.chat;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Member maker;
	
	@ManyToOne
	private Member guest;
	
	private int makerCount;
	
	private int guestCount;
	
	private LocalDateTime lastDate;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	private List<Message> messageList;
	
}
