package com.example.codingbatapp.controller;

import com.example.codingbatapp.entity.User;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.UserDto;
import com.example.codingbatapp.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody UserDto userDto) {
        ApiResponse register = userService.register(userDto);
        return ResponseEntity.status(register.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(register);
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody UserDto userDto,@Valid @PathVariable Integer id) {
        ApiResponse update = userService.editUser(userDto, id);
        return ResponseEntity.status(update.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(update);
    }
    @GetMapping
    public HttpEntity<List<User>>getUserList(){
        List<User> userList = userService.getUserList();
        return ResponseEntity.ok(userList);
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse>getUser(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = userService.getUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse>deleteUser(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = userService.deleteUser(id);
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
