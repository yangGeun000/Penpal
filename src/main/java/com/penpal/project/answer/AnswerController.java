package com.penpal.project.answer;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.board.Board;
import com.penpal.project.board.BoardService;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
@Slf4j
public class AnswerController {

	private final BoardService boardService;
	private final AnswerService answerService;
	private final MemberService memberService;

	// by 장유란, 답변기능 권한 주석처리/**/
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult, Principal principal) {
		Board board = this.boardService.getBoard(id);
		Member member = this.memberService.getMember(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("board", board);
			return "community/community_detail"; // by 장유란, community/board_detail ==> board/board_detail
		}
		this.answerService.create(board, answerForm.getContent(), member);
		log.info("answer create post" + id);
		return String.format("redirect:/community/detail/%s", id);
	}

	// by 장유란, answer_form에서 오는 정보 처리
	//@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(Model model,@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		log.info("modify post");
		Answer answer = this.answerService.getAnswer(id);
		Board board = this.boardService.getBoard(answer.getBoard().getId());
		String content = answerForm.getContent();
		this.answerService.modify(answer, content);
		model.addAttribute("content", content);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("board", board);
			model.addAttribute("answer", answer);
			return "community/community_detail"; 
		}

		return String.format("redirect:/community/detail/%s", answer.getBoard().getId());
	}

	@GetMapping("/delete/{id}")
	public String answerDelete(@PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		this.answerService.delete(answer);
		return String.format("redirect:/community/detail/%s", answer.getBoard().getId());
	}

}
