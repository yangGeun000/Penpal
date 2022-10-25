package com.penpal.project.board;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	// by 장유란, 검색기능 추가
	Page<Board> findAll(Pageable pageable);
	Page<Board> findAll(Specification<Board> spec,  Pageable pageable);
	
}
