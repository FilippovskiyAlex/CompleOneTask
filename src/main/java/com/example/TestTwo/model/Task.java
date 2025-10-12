package com.example.TestTwo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.HashMap;
import java.util.Map;

@Data
public class Task {
    @NotBlank
    private String name;

    @Valid
    private Map<String, User> workers = new HashMap<>();

    @Valid
    private Map<String, Comment> comments = new HashMap<>();

    @Valid
    private Tag tag;

    @Valid
    private Status status;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotBlank
    private String dateStart;

    private String dateEnd;
}
