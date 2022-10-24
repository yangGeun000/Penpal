package com.penpal.project.member.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.penpal.project.list.SnsList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MemberSns {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private SnsList sns1;
	
	@ManyToOne
	private SnsList sns2;
	
	@ManyToOne
	private SnsList sns3;
	
}
