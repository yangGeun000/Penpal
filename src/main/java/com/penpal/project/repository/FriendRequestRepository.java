package com.penpal.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.FriendRequest;
import com.penpal.project.domain.Member;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer>{

    List<FriendRequest> findBySend(Member send);
    FriendRequest findBySendAndReceive(Member send, Member Receive);

}
