package com.example.codingbatapp.service;

import com.example.codingbatapp.entity.ProgrammingLanguage;
import com.example.codingbatapp.entity.StarBadge;
import com.example.codingbatapp.entity.Task;
import com.example.codingbatapp.entity.Category;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.CategoryDto;
import com.example.codingbatapp.repository.PLRepository;
import com.example.codingbatapp.repository.StarBadgeRepository;
import com.example.codingbatapp.repository.TaskRepository;
import com.example.codingbatapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PLRepository plRepository;
    @Autowired
    StarBadgeRepository starBadgeRepository;

    public ApiResponse addCategory(CategoryDto categoryDto) {
        boolean exists = categoryRepository.existsByName(categoryDto.getName());
        if (exists) return new ApiResponse("Current category already exists!", false);
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());
        category.setStarNumber(category.getStarNumber());
        List<Integer> langList = categoryDto.getPlId();
        List<ProgrammingLanguage> available = new ArrayList<>();
        String unavailable = "";
        for (Integer langId : langList) {
            Optional<ProgrammingLanguage> optional = plRepository.findById(langId);
            if (!optional.isPresent())
                unavailable += langId + ",";
            available.add(optional.get());
        }
        if (!unavailable.isEmpty()) return new ApiResponse("Current programming languages: " + unavailable + "not found!", false);
        category.setLanguageList(available);
        categoryRepository.save(category);
        return new ApiResponse("New category successfully added!", true);
    }

    public ApiResponse editCategory(CategoryDto categoryDto, Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new ApiResponse("Current category not found!", false);
        boolean exists = categoryRepository.existsByName(categoryDto.getName());
        if (exists) return new ApiResponse("Current category already exists!", false);
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());
        category.setStarNumber(category.getStarNumber());
        List<Integer> langList = categoryDto.getPlId();
        List<ProgrammingLanguage> available = new ArrayList<>();
        String unavailable = "";
        for (Integer langId : langList) {
            Optional<ProgrammingLanguage> optional = plRepository.findById(langId);
            if (!optional.isPresent())
                unavailable += langId + ",";
            available.add(optional.get());
        }
        if (!unavailable.isEmpty()) return new ApiResponse("Current programming languages: " + unavailable + "not found!", false);
        category.setLanguageList(available);
        categoryRepository.save(category);
        return new ApiResponse("Category successfully edited!", true);
    }

    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }

    public ApiResponse getCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new ApiResponse("Current category not found!", false);
        Category category = optionalCategory.get();
        return new ApiResponse("Selected category: " + category, true);
    }

    public ApiResponse deleteCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new ApiResponse("Current category not found!", false);
        categoryRepository.delete(optionalCategory.get());
        return new ApiResponse("Category deleted successfully!", true);
    }
}
