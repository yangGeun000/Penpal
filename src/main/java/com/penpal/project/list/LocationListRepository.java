package com.penpal.project.list;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationListRepository extends JpaRepository<LocationList, Integer>{
    LocationList findByName(String name);

}
