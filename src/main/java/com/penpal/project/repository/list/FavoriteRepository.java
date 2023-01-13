package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{

}
