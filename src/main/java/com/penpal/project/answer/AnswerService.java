package com.penpal.project.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.penpal.project.board.Board;
import com.penpal.project.board.DataNotFoundException;

import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;
    // by 장유란, 답변기능 권한 주석처리
    public Answer create(Board board, String content, Member member) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setBoard(board);
        answer.setWriter(member);   
        this.answerRepository.save(answer);
        
        return answer;
    }
    
    // by 장유란, Id로 답글 찾는 기능    
    public Answer getAnswer(Integer id) {
    	Optional<Answer> answer = this.answerRepository.findById(id);
    	if(answer.isPresent()){
    		return answer.get();
    	}else {
    		throw new DataNotFoundException("answer not found");
    	}
    }
    
    public void modify(Answer answer, String content) {
    	answer.setContent(content);
    	answer.setModifyDate(LocalDateTime.now());
    	log.info("modify Service");
    	this.answerRepository.save(answer);
    }
	
    public void delete(Answer answer) {
    	this.answerRepository.delete(answer);
    }
	

}
