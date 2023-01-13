package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.Sns;

public interface SnsRepository extends JpaRepository<Sns, Integer>{

	Sns findByName(String name);

}
