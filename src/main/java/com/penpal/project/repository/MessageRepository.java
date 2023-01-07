package com.penpal.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
