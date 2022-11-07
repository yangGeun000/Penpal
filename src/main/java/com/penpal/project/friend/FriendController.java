package com.penpal.project.friend;

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

@RequiredArgsConstructor
@Controller
public class FriendController {
   private final MemberService memberService;
   
   // by 안준언, 친구 리스트 가져오기 (임시)
   @RequestMapping("/getFriend")
   @ResponseBody
   public List<Friend> getFriend(@RequestParam HashMap<Object, Object> params){
		Member member = this.memberService.getMember(Integer.parseInt((String) params.get("memberId")));
		List<Friend> friendList = new ArrayList<>();
		friendList.addAll(member.getFriendList());
		
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
}