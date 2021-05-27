package com.example.codingbatapp.repository;

import com.example.codingbatapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String category_name);
}
