package com.example.codingbatapp.controller;

import com.example.codingbatapp.entity.StarBadge;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.StarBadgeDto;
import com.example.codingbatapp.service.StarBadgeService;
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
@RequestMapping("/api/starBadge")
public class StarBadgeController {
    @Autowired
    StarBadgeService starBadgeService;

    @PostMapping("/register")
    public HttpEntity<?> add(@Valid @RequestBody StarBadgeDto starBadgeDto) {
        ApiResponse apiResponse = starBadgeService.addStarBadge(starBadgeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody StarBadgeDto starBadgeDto,@Valid @PathVariable Integer id) {
        ApiResponse apiResponse = starBadgeService.editStarBadge(starBadgeDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @GetMapping
    public HttpEntity<List<StarBadge>>getStarBadgeList(){
        List<StarBadge> starBadgeList = starBadgeService.getStarBadgeList();
        return ResponseEntity.ok(starBadgeList);
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse>getStarBadge(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = starBadgeService.getStarBadge(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse>deleteStarBadge(@Valid @PathVariable Integer id){
        ApiResponse apiResponse = starBadgeService.deleteStarBadge(id);
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
