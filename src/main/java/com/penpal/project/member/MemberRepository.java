package com.penpal.project.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>{

    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findById(Integer id);
    long countByConn(boolean conn);
    List<Member> findTop5ByOrderByCreateDateDesc();
}
