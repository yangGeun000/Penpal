package com.penpal.project.friend;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.member.Member;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer>{
    ArrayList<FriendRequest> findBySend(Member send);
    FriendRequest findBySendAndReceive(Member send, Member Receive);

}
