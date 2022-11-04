package com.penpal.project.list;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryListRepository extends JpaRepository<CountryList, Integer>{
    CountryList findByName(String name);


}
