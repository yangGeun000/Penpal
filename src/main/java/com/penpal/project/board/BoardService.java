package com.penpal.project.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.penpal.project.answer.Answer;
import com.penpal.project.list.CategoryListRepository;
import com.penpal.project.list.CountryListRepository;
import com.penpal.project.list.LocationListRepository;
import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final CategoryListRepository categoryListRepository;
	private final LocationListRepository locationListRepository;
	private final CountryListRepository countryListRepository;

	// by 장유란, 검색기능(Specification 예시, 참고용(실사용x)
	//			BoardRepository의 findAllByKeyword의 쿼리와 유사기능
//	private Specification<Board> search(String kw) {
//        return new Specification<>() {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public Predicate toPredicate(Root<Board> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                query.distinct(true);// select distinct기능
//                Join<Board, Member> u1 = q.join("writer", JoinType.LEFT);
//                Join<Board, Answer> a = q.join("answerList", JoinType.LEFT);
//                Join<Answer, Member> u2 = a.join("writer", JoinType.LEFT);
//                return cb.or(cb.like(q.get("title"), "%" + kw + "%"), // 제목 
//                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
//                        cb.like(u1.get("memberId"), "%" + kw + "%"),    // 질문 작성자 
//                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
//                        cb.like(u2.get("memberId"), "%" + kw + "%"));   // 답변 작성자 
//            }
//		};
//	}

	public Page<Board> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts));
		// (참고용)Specification<Board>  spec = search(kw);
		return this.boardRepository.findAllByKeyword(kw, pageable);
	}

	public Board getBoard(Integer id) {
		Optional<Board> board = this.boardRepository.findById(id);
		if (board.isPresent()) {
			return board.get();
		} else {
			throw new DataNotFoundException("board not found");
		}
	}

	public void create(String title, String content, String category, String location, String country, Member member) {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setCategory(categoryListRepository.findByName(category));
		board.setLocation(locationListRepository.findByName(location));
		board.setCountry(countryListRepository.findByName(country));
		board.setCreateDate(LocalDateTime.now());
		board.setWriter(member);

		this.boardRepository.save(board);
	}
	
	// by 장유란, 수정분 받아와서 저장
	public void modify(Board board, String title, String content) {
		board.setTitle(title);
		board.setContent(content);
		board.setModifyDate(LocalDateTime.now());
		this.boardRepository.save(board);
	}

}
