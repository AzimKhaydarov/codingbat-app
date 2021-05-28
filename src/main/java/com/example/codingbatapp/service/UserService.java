package com.example.codingbatapp.service;

import com.example.codingbatapp.entity.StarBadge;
import com.example.codingbatapp.entity.Task;
import com.example.codingbatapp.entity.User;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.UserDto;
import com.example.codingbatapp.repository.StarBadgeRepository;
import com.example.codingbatapp.repository.TaskRepository;
import com.example.codingbatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    StarBadgeRepository starBadgeRepository;

    public ApiResponse register(UserDto userDto) {
        boolean existsByEmail = userRepository.existsByEmail(userDto.getEmail());
        if (existsByEmail) return new ApiResponse("Current user already exists!", false);
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());
        user.setFullName(user.getFullName());
        List<Integer> taskList = userDto.getTaskList();
        List<Task> available = new ArrayList<>();
        String unavailable = "";
        for (Integer taskId : taskList) {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (!optionalTask.isPresent())
                unavailable += taskId + ",";
            available.add(optionalTask.get());
        }
        if (!unavailable.isEmpty()) return new ApiResponse("Current tasks: " + unavailable + "not found!", false);
        user.setTaskList(available);
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(userDto.getStarBadgeId());
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Current star badge not found!", false);
        user.setStarBadge(optionalStarBadge.get());
        userRepository.save(user);
        return new ApiResponse("New user successfully added!", true);
    }

    public ApiResponse editUser(UserDto userDto, Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new ApiResponse("Current user not found!", false);
        boolean existsByEmail = userRepository.existsByEmail(userDto.getEmail());
        if (existsByEmail) return new ApiResponse("Current user already exists!", false);
        User user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());
        user.setFullName(user.getFullName());
        List<Integer> taskList = userDto.getTaskList();
        List<Task> available = new ArrayList<>();
        String unavailable = "";
        for (Integer taskId : taskList) {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (!optionalTask.isPresent())
                unavailable += taskId + ",";
            available.add(optionalTask.get());
        }
        if (!unavailable.isEmpty()) return new ApiResponse("Current tasks: " + unavailable + "not found!", false);
        user.setTaskList(available);
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(userDto.getStarBadgeId());
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Current star badge not found!", false);
        user.setStarBadge(optionalStarBadge.get());
        userRepository.save(user);
        return new ApiResponse("User successfully edited!", true);
    }

    public List<User> getUserList() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public ApiResponse getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new ApiResponse("Current user not found!", false);
        User user = optionalUser.get();
        return new ApiResponse("Selected user: " + user, true);
    }

    public ApiResponse deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new ApiResponse("Current user not found!", false);
        userRepository.delete(optionalUser.get());
        return new ApiResponse("User deleted successfully!", true);
    }
}
