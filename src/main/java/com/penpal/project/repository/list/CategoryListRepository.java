package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.CategoryList;

public interface CategoryListRepository extends JpaRepository<CategoryList, Integer>{
    CategoryList findByName(String name);

}
