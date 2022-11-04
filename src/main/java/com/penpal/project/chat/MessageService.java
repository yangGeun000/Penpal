package com.penpal.project.chat;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageService {
	
	private final MessageRepository messageRepository;
	
	// by 구양근, 메세지 생성
	public Message createMessage(Member sender, Room room, String content) {
		Message message = new Message();
		message.setSender(sender);
		message.setContent(content);
		message.setRoom(room);
		message.setSendDate(LocalDateTime.now());
		message = this.messageRepository.save(message);
		
		return message;
	}
	
}
