package com.example.TestTwo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Project {
    @NotBlank
    private String name;
    @Valid
    private Map<String, Task> tasks = new HashMap<>();
    @NotBlank
    private String description;
    @Valid
    private Status status;
}
