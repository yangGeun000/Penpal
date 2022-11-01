package com.penpal.project.answer;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.board.Board;
import com.penpal.project.board.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	private final BoardService boardService;
	private final AnswerService answerService;
	/* private final MemberService memberService; */

	// by 장유란, 답변기능 권한 주석처리/**/
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult
			/* , Principal principal */) {
		Board board = this.boardService.getBoard(id);
		/* Member member = this.memberService.getMember(principal.getName()); */
		if (bindingResult.hasErrors()) {
			model.addAttribute("board", board);
			return "community/community_detail";	// by 장유란, community/board_detail ==> board/board_detail
		}
		this.answerService.create(board, answerForm.getContent()/* , member */);
		System.out.println("answer create post" + id);
		return String.format("redirect:/community/detail/%s", id);// by 장유란, board/detail ==> community/detail
	}
	
	// by 장유란, answer_form에서 오는 정보 처리
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
		System.out.println("modify post");
		if(bindingResult.hasErrors()) {
			System.out.println("modify post error");
			return "answer_form";	// by 장유란, 리턴 폼 위치 옮길 시 수정 필요
		}

		Answer answer = this.answerService.getAnswer(id);
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/community/detail/%s", answer.getBoard().getId());
	}

	@GetMapping("/delete/{id}")
	public String answerDelete(@PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		this.answerService.delete(answer);
        return String.format("redirect:/community/detail/%s", answer.getBoard().getId());	}
}
