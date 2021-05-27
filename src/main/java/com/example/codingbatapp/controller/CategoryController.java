package com.example.codingbatapp.controller;

import com.example.codingbatapp.entity.Category;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.CategoryDto;
import com.example.codingbatapp.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/register")
    public HttpEntity<?> add(@Valid @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody CategoryDto categoryDto,@Valid @PathVariable Integer id) {
        ApiResponse apiResponse = categoryService.editCategory(categoryDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<List<Category>>getCategoryList(){
        List<Category> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse>getCategory(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = categoryService.getCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse>deleteCategory(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = categoryService.deleteCategory(id);
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
