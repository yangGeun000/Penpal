package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.LocationList;

public interface LocationListRepository extends JpaRepository<LocationList, Integer>{
    LocationList findByName(String name);

}
