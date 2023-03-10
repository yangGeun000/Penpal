package com.penpal.project.service;

import com.penpal.project.domain.Member;
import com.penpal.project.domain.Message;
import com.penpal.project.domain.Room;
import com.penpal.project.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageService {
	
	private final MessageRepository messageRepository;
	
	// by 구양근, 메세지 생성
	public Message createMessage(Member sender, Room room, String content, LocalDateTime sendDate) {
		Message message = new Message();
		message.setSender(sender);
		message.setContent(content);
		message.setRoom(room);
		message.setSendDate(sendDate);
		message = this.messageRepository.save(message);
		
		return message;
	}
	
	//by 구양근, 총 메세지 개수
	public long messageCount() {
		return this.messageRepository.count();
	}
	
}
