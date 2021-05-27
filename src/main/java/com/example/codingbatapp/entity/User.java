package com.example.codingbatapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Email(message = "Email is mandatory!")
    @Column(unique = true)
    @NotNull(message = "Field must be filled!")
    private String email;

    @NotNull(message = "Field must be filled!")
    @NotBlank(message = "Password is mandatory!")
    private String password;
    @NotNull(message = "Field must be filled!")
    @NotBlank(message = "Full name is mandatory!")
    private String fullName;

    @OneToMany
    private List<Task> taskList;

    @ManyToOne
    private StarBadge starBadge;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }
}
