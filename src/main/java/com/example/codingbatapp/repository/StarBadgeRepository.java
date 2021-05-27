package com.example.codingbatapp.repository;

import com.example.codingbatapp.entity.StarBadge;
import com.example.codingbatapp.entity.enums.StarBadgeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarBadgeRepository extends JpaRepository<StarBadge, Integer> {
    boolean existsByValue(StarBadgeValue value);
}
