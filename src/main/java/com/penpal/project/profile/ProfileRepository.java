package com.penpal.project.profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.member.Member;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{
    Page<Profile> findAll(Specification<Profile> spec, Pageable pageable);
    Profile findByMember(Member member);

}
