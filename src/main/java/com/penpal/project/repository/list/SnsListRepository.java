package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.SnsList;

public interface SnsListRepository extends JpaRepository<SnsList, Integer>{

	SnsList findByName(String name);

}
