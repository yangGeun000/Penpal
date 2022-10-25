package com.penpal.project.answer;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penpal.project.board.Board;
import com.penpal.project.board.BoardService;
import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final BoardService boardService;
    private final AnswerService answerService;
    private final MemberService memberService;
    
    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
            @PathVariable("id") Integer id,
            @Valid AnswerForm answerForm, BindingResult bindingResult, 
            Principal principal) {
        Board board = this.boardService.getBoard(id);
        Member member = this.memberService.getMember(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/board_detail";
        }
        this.answerService.create(board, answerForm.getContent(), member);

        return String.format("redirect:/board/detail/%s", id);
    }

}
