package com.penpal.project.member.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.penpal.project.list.FavoriteList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MemberFavorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private FavoriteList favorite1;
	
	@ManyToOne
	private FavoriteList favorite2;
	
	@ManyToOne
	private FavoriteList favorite3;
	
}
