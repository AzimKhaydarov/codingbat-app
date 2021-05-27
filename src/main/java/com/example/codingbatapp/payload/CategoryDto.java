package com.example.codingbatapp.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CategoryDto {
    @NotBlank(message = "name is mandatory!")
    private String name;
    private String description;
    private int starNumber;
    private List<Integer> plId;
}
