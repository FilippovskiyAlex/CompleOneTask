package com.example.TestTwo.model;

import com.example.TestTwo.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TaskDto {
    @NotBlank
    private String name;

    private List<String> workers = new ArrayList<>();

    private Map<String, String> comments = new HashMap<>();

    private String tag;

    private Status status;

    private String project;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotBlank
    private String dateStart;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private String dateEnd;
}
