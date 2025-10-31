package com.example.TestTwo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ProjectDto {
    @NotBlank
    private String name;

    private List<String> tasks  = new ArrayList<>();

    private String description;

    private Status status;
}
