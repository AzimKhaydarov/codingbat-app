package com.example.codingbatapp.controller;

import com.example.codingbatapp.entity.ProgrammingLanguage;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.repository.PLRepository;
import com.example.codingbatapp.service.PLService;
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
@RequestMapping("/api/pl")
public class PLController {
    @Autowired
    PLRepository plRepository;
    @Autowired
    PLService plService;

    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody ProgrammingLanguage pl) {
        ApiResponse register = plService.addPL(pl);
        return ResponseEntity.status(register.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(register);
    }

    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody ProgrammingLanguage pl, @Valid @PathVariable Integer id) {
        ApiResponse update = plService.editPL(pl, id);
        return ResponseEntity.status(update.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(update);
    }

    @GetMapping
    public HttpEntity<List<ProgrammingLanguage>> getPLList() {
        List<ProgrammingLanguage> plList = plService.getLanguages();
        return ResponseEntity.ok(plList);
    }

    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getPL(@Valid @PathVariable Integer id) {
        ApiResponse apiResponse = plService.getPL(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deletePL(@Valid @PathVariable Integer id) {
        ApiResponse apiResponse = plService.deletePL(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
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
