package com.example.codingbatapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Integer starNumber; //kategoriyadagi tasklar soniga qarab beriladi(3 ta task =>  1ta star)

    @ManyToMany
    private List<ProgrammingLanguage> languageList;
}
