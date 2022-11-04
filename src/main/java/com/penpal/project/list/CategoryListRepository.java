package com.penpal.project.list;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryListRepository extends JpaRepository<CategoryList, Integer>{
    CategoryList findByName(String name);

}
