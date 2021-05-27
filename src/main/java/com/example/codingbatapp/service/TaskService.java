package com.example.codingbatapp.service;

import com.example.codingbatapp.entity.Category;
import com.example.codingbatapp.entity.Task;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.TaskDto;
import com.example.codingbatapp.repository.CategoryRepository;
import com.example.codingbatapp.repository.PLRepository;
import com.example.codingbatapp.repository.StarBadgeRepository;
import com.example.codingbatapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    PLRepository plRepository;
    @Autowired
    StarBadgeRepository starBadgeRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse addTask(TaskDto taskDto) {
        boolean exists = taskRepository.existsByName(taskDto.getName());
        if (exists) return new ApiResponse("Current task already exists!", false);
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if(!optionalCategory.isPresent()) return new ApiResponse("Current category not found!", false);
        task.setCategory(optionalCategory.get());
        taskRepository.save(task);
        return new ApiResponse("New task successfully added!", true);
    }

    public ApiResponse editTask(TaskDto taskDto, Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("Current task not found!", false);
        boolean exists = taskRepository.existsByName(taskDto.getName());
        if (exists) return new ApiResponse("Current task already exists!", false);
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if(!optionalCategory.isPresent()) return new ApiResponse("Current category not found!", false);
        task.setCategory(optionalCategory.get());
        taskRepository.save(task);
        return new ApiResponse("Task successfully edited!", true);
    }

    public List<Task> getTaskList() {
        List<Task> taskList = taskRepository.findAll();
        return taskList;
    }

    public ApiResponse getTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("Current task not found!", false);
        Task task = optionalTask.get();
        return new ApiResponse("Selected task: " + task, true);
    }

    public ApiResponse deleteTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("Current task not found!", false);
        taskRepository.delete(optionalTask.get());
        return new ApiResponse("Task deleted successfully!", true);
    }
}
