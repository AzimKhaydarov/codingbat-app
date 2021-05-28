package com.example.codingbatapp.payload;

import com.example.codingbatapp.entity.enums.StarBadgeValue;
import lombok.Data;

@Data
public class StarBadgeDto {

    private StarBadgeValue value;
    private Integer languageId;
}
