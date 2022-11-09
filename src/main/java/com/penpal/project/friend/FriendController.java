package com.penpal.project.friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FriendController {
   private final MemberService memberService;
   private final FriendService friendService;
   private final FriendRepository friendRepository;
   private final FriendRequestRepository friendRequestRepository;
   
   // by 안준언, 친구 리스트 가져오기 (임시)
   @RequestMapping("/getFriend")
   @ResponseBody
   public List<Friend> getFriend(@RequestParam HashMap<Object, Object> params){
		Member member = this.memberService.getMember(Integer.parseInt((String) params.get("memberId")));
		List<Friend> friendList = new ArrayList<>();
		friendList.addAll(member.getFriendList());
		// by 안준언, 가장 최근 접속 순으로 친구 정렬
		friendList.sort((friend1, friend2) -> 
			friend2.getFriend().getProfile().getLastDate().compareTo(friend1.getFriend().getProfile().getLastDate()));
		
		return friendList;
	}
   
   // by 안준언, 접속중인 친구 리스트 가져오기 (임시)
   @RequestMapping("/getOnlineFriend")
   @ResponseBody
   public List<Friend> getOnlineFriend(@RequestParam HashMap<Object, Object> params){
	   	Member member = this.memberService.getMember(Integer.parseInt((String) params.get("memberId")));
		List<Friend> friendList = new ArrayList<>();
		friendList.addAll(member.getFriendList());
		List<Friend> onlineFriendList = new ArrayList<>();
		
		for(int i=0; i<friendList.size(); i++) {
			if(friendList.get(i).getFriend().isConn() == true) {
				onlineFriendList.add(friendList.get(i));
			}
		}
		// by 안준언, 가장 최근 접속 순으로 친구 정렬
		onlineFriendList.sort((friend1, friend2) -> 
			friend2.getFriend().getProfile().getLastDate().compareTo(friend1.getFriend().getProfile().getLastDate()));
		return onlineFriendList;
   }
   
   // by 안준언, 내가 받은 친구 요청 리스트 가져오기 (임시)
   @RequestMapping("/getFriendRequest")
   @ResponseBody
   public List<FriendRequest> getFriendRequest(@RequestParam HashMap<Object, Object> params){
	   Member member = this.memberService.getMember(Integer.parseInt((String) params.get("memberId")));
	   List<FriendRequest> friendRequestList = new ArrayList<>();
	   friendRequestList.addAll(member.getFriendRequestList());
	   
	   return friendRequestList;
   }
   
   // by 안준언, 친구 삭제 (임시)
   @RequestMapping("/deleteFriend")
   public void deletefriend(@RequestParam HashMap<Object, Object> params) {
	   Optional<Friend> df = this.friendRepository.findById(Integer.parseInt((String) params.get("friendId")));
	   Friend friend = df.get();
	   this.friendService.deleteFriend(friend.getMine(), friend.getFriend());
	   
   }
   
   // by 안준언, 친구 요청 수락(임시)
   @RequestMapping("/agreeFriend")
   @ResponseBody
   public void agreeFriend(@RequestParam HashMap<Object, Object> params) {
	   Optional<FriendRequest> fr = this.friendRequestRepository.findById(Integer.parseInt((String) params.get("friendRequestId")));
	   FriendRequest friendRequest = fr.get();
	   this.friendService.agreeFriend(friendRequest.getSend(), friendRequest.getReceive());
	   
   }
   
   // by 안준언, 친구 요청 거절(임시)
   @RequestMapping("/rejectFriend")
   @ResponseBody
   public void rejectFriend(@RequestParam HashMap<Object, Object> params) {
	   Optional<FriendRequest> fr = this.friendRequestRepository.findById(Integer.parseInt((String) params.get("friendRequestId")));
	   FriendRequest friendRequest = fr.get();
	   this.friendService.rejectFriend(friendRequest);
   }
   
   // by 안준언, 친구 요청 보내기(임시)
   @RequestMapping("/requestFriend")
   @ResponseBody
   public void requestFriend(@RequestParam HashMap<Object, Object> params) {
	   Member mine = this.memberService.getMember(Integer.parseInt((String) params.get("mine")));
	   Member friend = this.memberService.getMember(Integer.parseInt((String) params.get("friend")));
	   this.friendService.sendRequest(mine, friend);
   }
}
