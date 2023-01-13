package com.penpal.project.controller;

import com.penpal.project.domain.Member;
import com.penpal.project.domain.Message;
import com.penpal.project.domain.Room;
import com.penpal.project.service.MemberService;
import com.penpal.project.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final RoomService roomService;
    private final MemberService memberService;

    // by 구양근, 방을 검색하고 없으면 만들어서 반환
    @RequestMapping("/requestRoom")
    @ResponseBody
    public Room requestRoom(@RequestParam Integer makerId, @RequestParam Integer guestId) {
        Member maker = this.memberService.getMember(makerId);
        Member guest = this.memberService.getMember(guestId);
        Room room = this.roomService.requestRoom(maker, guest);
        return room;
    }

    // by 구양근, 대화방 삭제
    @RequestMapping("/deleteRoom")
    @ResponseBody
    public boolean deleteRoom(@RequestParam Integer roomId) {
        return this.roomService.deleteRoom(roomId);
    }

    // by 구양근, 개인 대화방 리스트 가져오기
    @RequestMapping("/getRoom")
    @ResponseBody
    public List<Room> getRoom(@RequestParam Integer memberId) {
        Member member = this.memberService.getMember(memberId);
        List<Room> roomList = new ArrayList<>();
        roomList.addAll(member.getMakerList());
        roomList.addAll(member.getGuestList());
        // by 구양근, 대화방의 최근시간 순으로 정렬
        roomList.sort((room1, room2) -> room2.getLastDate().compareTo(room1.getLastDate()));

        return roomList;
    }

    // by 구양근, 대화방 메세지 리스트
    @RequestMapping("/getMessage")
    @ResponseBody
    public List<Message> getMessage(@RequestParam Integer roomId, Principal principal) {
        Room room = this.roomService.getRoom(roomId);
        List<Message> messageList = room.getMessageList();
		// 확인한 사람 새 메세지 카운트 초기화
        if (room.getMaker().getMemberId().equals(principal.getName())) {
            room.setMakerCount(0);
            this.roomService.setRoom(room);
        } else {
            room.setGuestCount(0);
            this.roomService.setRoom(room);
        }
        return messageList;
    }

}
