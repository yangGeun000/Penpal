package com.penpal.project.list;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.penpal.project.board.Board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class CategoryList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true , length = 100, name = "category_name")
	private String name;
	
	@OneToMany(mappedBy = "category")
    private List<Board> boardList;
	
	public String toString() {
	    return name;
	}
	
}
