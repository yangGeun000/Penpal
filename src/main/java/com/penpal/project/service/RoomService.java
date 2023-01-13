package com.penpal.project.service;

import com.penpal.project.DataNotFoundException;
import com.penpal.project.domain.Member;
import com.penpal.project.domain.Room;
import com.penpal.project.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
        room.setLastDate(LocalDateTime.now());
        room.setMakerCount(0);
        room.setGuestCount(0);
        try {
            room = this.roomRepository.save(room);
        } catch (Exception e) {
            log.error("create room = {}", e.getMessage());
            e.printStackTrace();
        }

        return room;
    }

    // by 구양근, 대화방 삭제
    public boolean deleteRoom(Integer roomId) {
        Room room = getRoom(roomId);
        try {
            this.roomRepository.delete(room);
            return true;
        } catch (Exception e) {
            log.error("delete room = {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // by 구양근, 대화방 아이디로 찾아서 반환
    public Room getRoom(Integer roomId) {
        Optional<Room> OpRoom = this.roomRepository.findById(roomId);

        if (OpRoom.isPresent()) {
            return OpRoom.get();
        } else {
            throw new DataNotFoundException("room not found");
        }

    }

    // by 구양근, 방을 조회하고 없으면 생성해서 반환
    public Room requestRoom(Member maker, Member guest) {
        Optional<Room> OpRoom = this.roomRepository.findByRoom(maker.getId(), guest.getId());

        if (OpRoom.isPresent()) {
            return OpRoom.get();
        } else {
            return createRoom(maker, guest);
        }
    }

    public void setRoom(Room room) {
        this.roomRepository.save(room);
    }

}
