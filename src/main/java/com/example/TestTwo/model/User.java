package com.example.TestTwo.model;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class User {
    @NotBlank
    private String name;
    @Email
    private String email;
}
