package com.example.codingbatapp.repository;

import com.example.codingbatapp.entity.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PLRepository extends JpaRepository<ProgrammingLanguage, Integer> {
    boolean existsByName(String language);
}
