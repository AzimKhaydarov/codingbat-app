package com.example.codingbatapp.service;

import com.example.codingbatapp.entity.StarBadge;
import com.example.codingbatapp.entity.ProgrammingLanguage;
import com.example.codingbatapp.payload.ApiResponse;
import com.example.codingbatapp.payload.StarBadgeDto;
import com.example.codingbatapp.repository.StarBadgeRepository;
import com.example.codingbatapp.repository.PLRepository;
import com.example.codingbatapp.repository.StarBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StarBadgeService {
    @Autowired
    StarBadgeRepository starBadgeRepository;
    @Autowired
    PLRepository plRepository;


    public ApiResponse addStarBadge(StarBadgeDto starBadgeDto) {
        boolean exists = starBadgeRepository.existsByValue(starBadgeDto.getValue());
        if (exists) return new ApiResponse("Current starBadgeValue already exists!", false);
        StarBadge starBadge = new StarBadge();
        starBadge.setValue(starBadgeDto.getValue());
        Optional<ProgrammingLanguage> optional = plRepository.findById(starBadgeDto.getLanguageId());
        if (!optional.isPresent()) return new ApiResponse("Current  programming language not found!", false);
        starBadge.setLanguage(optional.get());
        starBadgeRepository.save(starBadge);
        return new ApiResponse("New starBadge successfully added!", true);
    }

    public ApiResponse editStarBadge(StarBadgeDto starBadgeDto, Integer id) {
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Current starBadge not found!", false);
        boolean exists = starBadgeRepository.existsByValue(starBadgeDto.getValue());
        if (exists) return new ApiResponse("Current starBadgeValue already exists!", false);
        StarBadge starBadge = new StarBadge();
        starBadge.setValue(starBadgeDto.getValue());
        Optional<ProgrammingLanguage> optional = plRepository.findById(starBadgeDto.getLanguageId());
        if (!optional.isPresent()) return new ApiResponse("Current  programming language not found!", false);
        starBadge.setLanguage(optional.get());
        starBadgeRepository.save(starBadge);
        return new ApiResponse("StarBadge successfully edited!", true);
    }

    public List<StarBadge> getStarBadgeList() {
        List<StarBadge> starBadgeList = starBadgeRepository.findAll();
        return starBadgeList;
    }

    public ApiResponse getStarBadge(Integer id) {
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Current starBadge not found!", false);
        StarBadge starBadge = optionalStarBadge.get();
        return new ApiResponse("Selected starBadge: " + starBadge, true);
    }

    public ApiResponse deleteStarBadge(Integer id) {
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Current starBadge not found!", false);
        starBadgeRepository.delete(optionalStarBadge.get());
        return new ApiResponse("StarBadge deleted successfully!", true);
    }
}
