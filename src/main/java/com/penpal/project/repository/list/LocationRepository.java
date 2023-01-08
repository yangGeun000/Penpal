package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{
    Location findByName(String name);

}
