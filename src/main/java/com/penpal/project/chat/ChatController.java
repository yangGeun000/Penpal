package com.penpal.project.chat;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
	
	private final RoomService roomService;
	private final MemberService memberService;
	
	// by 구양근, 방을 검색하고 없으면 만들어서 반환
	@RequestMapping("/requestRoom")
	@ResponseBody
	public  Room requestRoom(@RequestParam HashMap<Object, Object> params){
		Member maker = this.memberService.getMember(Integer.parseInt((String) params.get("makerId")));
		Member guest = this.memberService.getMember(Integer.parseInt((String) params.get("guestId")));
		Room room = this.roomService.requestRoom(maker, guest);
		return room;
	}
	
	// by 구양근, 대화방 메세지 리스트 
		@RequestMapping("/deleteRoom")
		@ResponseBody
		public  void deleteRoom(@RequestParam HashMap<Object, Object> params){
			this.roomService.deleteRoom(Integer.parseInt((String) params.get("roomId")));
		}
	
	// by 구양근, 개인 대화방 리스트 가져오기
	@RequestMapping("/getRoom")
	@ResponseBody
	public  List<Room> getRoom(@RequestParam HashMap<Object, Object> params){
		Member member = this.memberService.getMember(Integer.parseInt((String) params.get("memberId")));
		List<Room> roomList = new ArrayList<>();
		roomList.addAll(member.getMakerList());
		roomList.addAll(member.getGuestList());
		// by 구양근, 대화방의 최근시간 순으로 정렬
		roomList.sort((room1,room2) -> room2.getLastDate().compareTo(room1.getLastDate()));
		
		return roomList;
	}
	
	// by 구양근, 대화방 메세지 리스트 
	@RequestMapping("/getMessage")
	@ResponseBody
	public  List<Message> getMessage(@RequestParam HashMap<Object, Object> params, Principal principal){
		Room room = this.roomService.getRoom(Integer.parseInt((String) params.get("roomId")));
		List<Message> messageList = room.getMessageList();
		if(room.getMaker().getMemberId().equals(principal.getName())){
			room.setMakerCount(0);
			this.roomService.setRoom(room);
		}
		else {
			room.setGuestCount(0);
			this.roomService.setRoom(room);
		}
		return messageList;
	}
	
}
