package com.penpal.project.friend;

import org.springframework.stereotype.Service;

import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;

    private final FriendRequestRepository friendRequestRepository;

    // by 안준언, 친구 요청 보내기
    public void sendRequest(Member mine, Member friend) {
    	Friend f1 = this.friendRepository.findByMineAndFriend(mine, friend);
    	Friend f2 = this.friendRepository.findByMineAndFriend(friend, mine);
    	
    	if (f1 == null && f2 == null) {
    		FriendRequest rf1 = this.friendRequestRepository.findBySendAndReceive(mine, friend);
    		FriendRequest rf2 = this.friendRequestRepository.findBySendAndReceive(friend, friend);
    		if(rf1 == null && rf2 == null) {
    			FriendRequest friendRequest = new FriendRequest();
    			friendRequest.setSend(mine);
    			friendRequest.setReceive(friend);
    			this.friendRequestRepository.save(friendRequest);    			
    		}
    		
    	}
    	
    }

    // by 안준언, 친구 요청 수락
    public void agreeFriend(Member send, Member receive) {
        FriendRequest request = this.friendRequestRepository.findBySendAndReceive(send, receive);
            	
        Friend addFriend1 = new Friend();
        addFriend1.setMine(request.getReceive());
        addFriend1.setFriend(request.getSend());
        this.friendRepository.save(addFriend1);
        
        Friend addFriend2 = new Friend();
        addFriend2.setMine(request.getSend());
        addFriend2.setFriend(request.getReceive());
        this.friendRepository.save(addFriend2);

        this.friendRequestRepository.delete(request);
    }

    // by 안준언, 친구 요청 거절
    public void rejectFriend(FriendRequest friendRequest) {
        this.friendRequestRepository.delete(friendRequest);
    }
    
    // by 안준언, 친구 삭제
    public void deleteFriend(Member mine, Member friend) {
    	Friend df1 = this.friendRepository.findByMineAndFriend(mine, friend);
    	Friend df2 = this.friendRepository.findByMineAndFriend(friend, mine);
        
        this.friendRepository.delete(df1);
        this.friendRepository.delete(df2);
    }
}
