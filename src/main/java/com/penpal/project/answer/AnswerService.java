package com.penpal.project.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.penpal.project.board.Board;
import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    
    public void create(Board board, String content, Member member) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setBoard(board);
        answer.setAuthor(member);        
        this.answerRepository.save(answer);
    }
}
