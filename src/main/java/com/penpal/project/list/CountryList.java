package com.penpal.project.list;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CountryList {

	@Id
	private String name;
	
}
