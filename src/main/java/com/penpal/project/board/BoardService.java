package com.penpal.project.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    
   
    public Page<Board> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
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
        System.out.println("no1");
        
        this.boardRepository.save(board);
    }



}
