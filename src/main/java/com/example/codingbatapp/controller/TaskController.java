package com.example.codingbatapp.controller;

import com.example.codingbatapp.entity.Task;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.TaskDto;
import com.example.codingbatapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/register")
    public HttpEntity<?> add(@Valid @RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody TaskDto taskDto,@Valid @PathVariable Integer id) {
        ApiResponse apiResponse = taskService.editTask(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<List<Task>>getTaskList(){
        List<Task> taskList = taskService.getTaskList();
        return ResponseEntity.ok(taskList);
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse>getTask(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = taskService.getTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse>deleteTask(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
