package com.penpal.project.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	// by 장유란, 검색기능 추가
	Page<Board> findAll(Pageable pageable);
	Page<Board> findAll(Specification<Board> spec, Pageable pageable);
	
	@Query(   "select distinct q "
			+ "from Board q "
			+ "    left outer join Member u1 "
			+ "	       on q.writer = u1 "
			+ "    left outer join Answer a "
			+ "        on a.board = q "
			+ "    left outer join Member u2 "
			+ "        on a.writer = u2 "
			+ "where"
			+ "	   q.title like %:kw% or "
			+ "	   q.content like %:kw% or "
			+ "	   u1.memberId like %:kw% or "
			+ "	   a.content like %:kw% or "
			+ "	   u2.memberId like %:kw%"
			)
	Page<Board> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	
	@Query(   "select distinct q "
			+ "from Board q "
			+ "    left outer join Member u1 "
			+ "	       on q.writer = u1 "
			+ "    left outer join Answer a "
			+ "        on a.board = q "
			+ "    left outer join Member u2 "
			+ "        on a.writer = u2 "
			+ "where"
			+ "	   (q.title like %:kw% or "
			+ "	   q.content like %:kw% or "
			+ "	   u1.memberId like %:kw% or "
			+ "	   a.content like %:kw% or "
			+ "	   u2.memberId like %:kw%) and "
			+ "	   q.location.name like %:location% and "
			+ "	   q.country.name like %:country% "
			)
	Page<Board> findAllByKeyword(
			@Param("kw") String kw, 
			Pageable pageable, 
			@Param("location") String location, 
			@Param("country") String country);
	
	@Query(   "select distinct q "
			+ "from Board q "
			+ "    left outer join Member u1 "
			+ "	       on q.writer = u1 "
			+ "    left outer join Answer a "
			+ "        on a.board = q "
			+ "    left outer join Member u2 "
			+ "        on a.writer = u2 "
			+ "where"
			+ "	   (q.title like %:kw% or "
			+ "	   q.content like %:kw% or "
			+ "	   u1.memberId like %:kw% or "
			+ "	   a.content like %:kw% or "
			+ "	   u2.memberId like %:kw%) and "
			+ "	   q.country.name like %:country% "
			)
	Page<Board> findAllByKeyword(
			@Param("kw") String kw, 
			Pageable pageable, 
			@Param("country") String country);
	
	@Query(   "select distinct q "
			+ "from Board q "
			+ "    left outer join Member u1 "
			+ "	       on q.writer = u1 "
			+ "    left outer join Answer a "
			+ "        on a.board = q "
			+ "    left outer join Member u2 "
			+ "        on a.writer = u2 "
			+ "where"
			+ "	   (q.title like %:kw% or "
			+ "	   q.content like %:kw% or "
			+ "	   u1.memberId like %:kw% or "
			+ "	   a.content like %:kw% or "
			+ "	   u2.memberId like %:kw%) and "
			+ "	   q.location.name like %:location%"
			)
	Page<Board> findAllByKeywordLocatuin(
			@Param("kw") String kw, 
			Pageable pageable, 
			@Param("location") String location);
	
	@Query(   "select distinct q "
			+ "from Board q "
			+ "    left outer join Member u1 "
			+ "	       on q.writer = u1 "
			+ "    left outer join Answer a "
			+ "        on a.board = q "
			+ "    left outer join Member u2 "
			+ "        on a.writer = u2 "
			+ "where"
			+ "	   (q.title like %:kw% or "
			+ "	   q.content like %:kw% or "
			+ "	   u1.memberId like %:kw% or "
			+ "	   a.content like %:kw% or "
			+ "	   u2.memberId like %:kw%) and "
			+ "	   q.category.name like %:category%"
			)
	Page<Board> findAllByKeywordCategory(
			@Param("kw") String kw, 
			Pageable pageable, 
			@Param("category") String category);
}
