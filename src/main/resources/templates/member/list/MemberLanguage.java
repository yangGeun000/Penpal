package com.penpal.project.member.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.penpal.project.list.LanguageList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MemberLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private LanguageList language1;
	
	@ManyToOne
	private LanguageList language2;
	
	@ManyToOne
	private LanguageList language3;
	
}
