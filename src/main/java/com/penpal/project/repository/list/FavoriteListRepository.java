package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.FavoriteList;

public interface FavoriteListRepository extends JpaRepository<FavoriteList, Integer>{

}
