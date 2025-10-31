package com.example.TestTwo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CommentDto {
    @NotBlank
    private String description;
    @Valid
    private String author;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private String date;
    @NotBlank
    private String task;
}
