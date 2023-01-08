package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>{
    Country findByName(String name);


}
