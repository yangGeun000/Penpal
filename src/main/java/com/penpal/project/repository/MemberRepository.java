package com.penpal.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findById(Integer id);
    long countByConn(boolean conn);
}
