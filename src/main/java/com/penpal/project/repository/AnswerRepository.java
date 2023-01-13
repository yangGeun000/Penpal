package com.penpal.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}
