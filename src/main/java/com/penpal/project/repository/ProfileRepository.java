package com.penpal.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.Member;
import com.penpal.project.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{
    Page<Profile> findAll(Specification<Profile> spec, Pageable pageable);
    Profile findByMember(Member member);
    List<Profile> findTop5ByOrderByIdDesc();
}
