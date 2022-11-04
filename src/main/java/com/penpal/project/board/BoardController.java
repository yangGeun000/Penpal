package com.penpal.project.board;

import java.security.Principal;


import javax.validation.Valid;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.server.ResponseStatusException;

import com.penpal.project.answer.AnswerForm;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/community")
@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private final MemberService memberService; 

	@RequestMapping("")
	public String boardList(Model model, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "location", defaultValue = "") String location, 
			@RequestParam(value = "country", defaultValue = "") String country,
			@RequestParam(value = "category", defaultValue = "") String category) {
		Page<Board> paging = this.boardService.getList(page, kw, location, country, category); // by 장유란 board_list에서 paging,
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("selectCategory", category);		
		log.info("kw: " + kw + " page: " + page + " location: " + location + " country: " + country + " category: " + category);
		return "community/community";
	}

	@RequestMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Board board = this.boardService.getBoard(id);
		model.addAttribute("board", board);

		return "community/community_detail";
	}

	// by 장유란, 답변기능 권한 주석처리/**/
	@PreAuthorize("isAuthenticated()") // 로그인 제약
	@GetMapping("/create")
	public String boardCreate(BoardForm boardForm) {
		return "community/writeForm";
	}

	// by 장유란, 답변기능 권한 주석처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "community/writeForm";
		}
		Member member = this.memberService.getMember(principal.getName());
		this.boardService.create(boardForm.getTitle(), boardForm.getContent(), boardForm.getCategory(),
				boardForm.getLocation(), boardForm.getCountry(), member );

		return "redirect:/community"; // by 장유란, "redirect:community/community"; ==> "redirect:/community";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String boardModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal) {
		Board board = this.boardService.getBoard(id);
		// 작성자 == 수정요청자 동일한지 확인하는 기능
		 if(!board.getWriter().getMemberId().equals(principal.getName())) {
		 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		 }
		// boardForm에서 검증받은 제목 내용 가져오기
		boardForm.setTitle(board.getTitle());
		boardForm.setContent(board.getContent());
		return "community/writeForm";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String boardModify(@Valid BoardForm boardForm,
			BindingResult bindingResult, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "community/writeForm";
		}

		Board board = this.boardService.getBoard(id);
		this.boardService.modify(board, boardForm.getTitle(), boardForm.getContent(), boardForm.getCategory(),
				boardForm.getLocation(), boardForm.getCountry());

		return String.format("redirect:/community/detail/%s", id); // 수정후 돌려주는 주소 변경
	}
	
	// by 장유란, 게시글 삭제 Service에서 게시글 삭제 받은 후 redirect, 차후에 권한부여
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String boardDelete(@PathVariable("id") Integer id) {
		Board board = this.boardService.getBoard(id);
		this.boardService.delete(board);
		return "redirect:/community";
	}

}
