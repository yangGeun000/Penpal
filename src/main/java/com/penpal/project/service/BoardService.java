package com.penpal.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.penpal.project.DataNotFoundException;
import com.penpal.project.domain.Board;
import com.penpal.project.domain.Member;
import com.penpal.project.repository.BoardRepository;
import com.penpal.project.repository.list.CategoryRepository;
import com.penpal.project.repository.list.CountryRepository;
import com.penpal.project.repository.list.LocationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final CategoryRepository categoryListRepository;
	private final LocationRepository locationListRepository;
	private final CountryRepository countryListRepository;

	// by 장유란, 검색기능

	public Page<Board> getList(int page, String kw, String location, String country, String category) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts));
		Page<Board> searchList = this.boardRepository.findAllByKeywordCategory(kw, pageable, country, location, category);

		return searchList;
	}

	public Board getBoard(Integer id) {
		Optional<Board> board = this.boardRepository.findById(id);
		if (board.isPresent()) {
			return board.get();
		} else {
			throw new DataNotFoundException("board not found");
		}
	}

	// by 장유란, 답변기능 권한 주석처리/**/
	public void create(String title, String content, String category, String location,
			String country, Member member ) {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setCategory(categoryListRepository.findByName(category));
		board.setLocation(locationListRepository.findByName(location));
		board.setCountry(countryListRepository.findByName(country));
		board.setCategory(categoryListRepository.findByName(category));
		board.setLocation(locationListRepository.findByName(location));
		board.setCountry(countryListRepository.findByName(country));
		board.setCreateDate(LocalDateTime.now());
		board.setWriter(member);

		this.boardRepository.save(board);
	}

	// by 장유란, 수정분 받아와서 저장
	public void modify(Board board, String title, String content, String category, String location, String country) {
		board.setTitle(title);
		board.setContent(content);
		board.setModifyDate(LocalDateTime.now());
		board.setCategory(categoryListRepository.findByName(category));
		board.setLocation(locationListRepository.findByName(location));
		board.setCountry(countryListRepository.findByName(country));
		this.boardRepository.save(board);
	}

	// by 장유란, 게시글 삭제
	public void delete(Board board) {
		this.boardRepository.delete(board);
	}

}
