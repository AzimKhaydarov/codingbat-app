package com.example.codingbatapp.service;

import com.example.codingbatapp.entity.ProgrammingLanguage;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.repository.PLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PLService {
    @Autowired
    PLRepository plRepository;
    public ApiResponse addPL(ProgrammingLanguage pl){
        boolean existsByName = plRepository.existsByName(pl.getName());
        if(existsByName) return new ApiResponse("Current programming language already exists!", false);
        ProgrammingLanguage pl1 = new ProgrammingLanguage();
        pl1.setName(pl.getName());
        plRepository.save(pl1);
        return new ApiResponse("New programming language added successfully!", true);
    }
    public ApiResponse editPL(ProgrammingLanguage pl, Integer id){
        Optional<ProgrammingLanguage> optional = plRepository.findById(id);
        if(!optional.isPresent()) return new ApiResponse("Current programming language not found!", false);
        boolean existsByName = plRepository.existsByName(pl.getName());
        if(existsByName) return new ApiResponse("Current programming language already exists!", false);
        ProgrammingLanguage pl1 = new ProgrammingLanguage();
        pl1.setName(pl.getName());
        plRepository.save(pl1);
        return new ApiResponse("Programming language edited successfully!", true);
    }
    public List<ProgrammingLanguage>getLanguages(){
        List<ProgrammingLanguage> languageList = plRepository.findAll();
        return languageList;
    }
    public ApiResponse getPL( Integer id){
        Optional<ProgrammingLanguage> optional = plRepository.findById(id);
        if(!optional.isPresent()) return new ApiResponse("Current programming language not found!", false);
        ProgrammingLanguage programmingLanguage = optional.get();
        return new ApiResponse("Selected programming language: " + programmingLanguage, true);
    }
    public ApiResponse deletePL(Integer id){
        Optional<ProgrammingLanguage> optional = plRepository.findById(id);
        if(!optional.isPresent()) return new ApiResponse("Current programming language not found!", false);
        plRepository.delete(optional.get());
        return new ApiResponse("PL deleted successfully!", true);
    }
}
