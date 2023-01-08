package com.penpal.project.repository.list;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penpal.project.domain.list.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Category findByName(String name);

}
