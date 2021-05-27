package com.example.codingbatapp.repository;

import com.example.codingbatapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByName(String name);
}
