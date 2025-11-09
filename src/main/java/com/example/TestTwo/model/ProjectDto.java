package com.example.TestTwo.model;

import com.example.TestTwo.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    @NotBlank
    private String name;

    private List<String> tasks  = new ArrayList<>();

    private String description;

    private Status status;
}
