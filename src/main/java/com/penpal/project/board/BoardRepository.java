package com.penpal.project.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	// {category}/search
	@Query(   "select distinct q "
			+ "from Board q "
			+ "    left outer join Member u1 "
			+ "	       on q.writer = u1 "
			+ "where("
			+ "	   q.title like %:kw% or "
			+ "	   q.content like %:kw%) and "
			+ "	   q.location.name like %:location% and "
			+ "	   q.country.name like %:country% and "
			+ "	   q.category.name like %:category%"
			)
			Page<Board> findAllByKeywordCategory(
				@Param("kw") String kw, 
				Pageable pageable, 
				@Param("country") String country, 
				@Param("location") String location, 
				@Param("category") String category);
}

