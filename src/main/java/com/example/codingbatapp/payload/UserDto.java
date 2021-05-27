package com.example.codingbatapp.payload;

import com.example.codingbatapp.entity.StarBadge;
import com.example.codingbatapp.entity.Task;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String email;
    private String password;
    private String fullName;
    private List<Integer> taskList;
    private Integer starBadgeId;

}
