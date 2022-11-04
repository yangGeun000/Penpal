package com.penpal.project.chat;

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
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<Message> messageList;
	
}
