package com.example.TestTwo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Tag {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
