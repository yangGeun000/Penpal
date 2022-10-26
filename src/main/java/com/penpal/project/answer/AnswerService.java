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

>>>>>>> c9bc248934a5d282c84d3e55d708a97deb0bf2ea
    // by 장유란, 답변기능 권한 주석처리
    public Answer create(Board board, String content/*, Member member*/) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setBoard(board);
<<<<<<< HEAD
=======

>>>>>>> c9bc248934a5d282c84d3e55d708a97deb0bf2ea
        /*answer.setWriter(member);*/    
        this.answerRepository.save(answer);
        
        return answer;
    }

	

	

	
}
