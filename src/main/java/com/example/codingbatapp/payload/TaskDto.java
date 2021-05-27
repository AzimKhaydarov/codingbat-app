package com.example.codingbatapp.payload;

import com.example.codingbatapp.entity.Category;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class TaskDto {
    private String name;
    private String description;
    private boolean completed = false;
    private Integer categoryId;
}
