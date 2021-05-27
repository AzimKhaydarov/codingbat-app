package com.example.codingbatapp.repository;

import com.example.codingbatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
}
