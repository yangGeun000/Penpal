package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.CountryList;

public interface CountryListRepository extends JpaRepository<CountryList, Integer>{
    CountryList findByName(String name);


}
