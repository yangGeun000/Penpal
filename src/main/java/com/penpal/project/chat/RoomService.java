package com.penpal.project.chat;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.penpal.project.board.DataNotFoundException;
import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoomService {
	
	private final RoomRepository roomRepository;
	
	// by 구양근, 대화방 생성
	public Room createRoom(Member maker, Member guest) {
		Room room = new Room();
		room.setMaker(maker);
		room.setGuest(guest);
		room = this.roomRepository.save(room);
		log.info("room -> id : " + room.getId() + " maker : " + room.getMaker() + " guest: " + room.getGuest());
		return room;
	}
	
	// by 구양근, 대화방 아이디로 찾아서 반환
	public Room getRoom(Integer roomId) {
		Optional<Room> OpRoom = this.roomRepository.findById(roomId);
        
        if(OpRoom.isPresent()) {
            return OpRoom.get();
        } else {
            throw new DataNotFoundException("room not found");
        }
       
	}
}
