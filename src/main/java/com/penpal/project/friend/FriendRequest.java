package com.penpal.project.friend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.penpal.project.member.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@ManyToOne
	@JoinColumn(name = "sendId")
	private Member send;
	
	@ManyToOne
	@JoinColumn(name = "receiveId")
	private Member receive;
	
}
