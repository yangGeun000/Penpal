package com.penpal.project.list;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.penpal.project.profile.Profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class LocationList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true , length = 100, name = "location_name")
	private String name;
	
	@OneToMany(mappedBy = "location")
	@JsonBackReference
	private List<Profile> profileList;
	
	public String toString() {
        return name;
    }

}
