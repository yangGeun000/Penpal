package com.penpal.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.Friend;
import com.penpal.project.domain.Member;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

	List<Friend> findByMine(Member mine);
	Friend findByMineAndFriend(Member mine, Member friend);

}
