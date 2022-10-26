package com.penpal.project.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.penpal.project.board.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    /*(권한)private final BoardRepository boardRepository;*/
<<<<<<< HEAD
=======

>>>>>>> origin/dev
    // by 장유란, 답변기능 권한 주석처리
    public Answer create(Board board, String content/*, Member member*/) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setBoard(board);
<<<<<<< HEAD
=======

>>>>>>> origin/dev
        /*answer.setWriter(member);*/    
        this.answerRepository.save(answer);
        
        return answer;
    }

	

	

	
}
