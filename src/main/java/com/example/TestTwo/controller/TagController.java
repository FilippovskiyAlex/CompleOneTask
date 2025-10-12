package com.example.TestTwo.controller;

import com.example.TestTwo.model.Tag;
import com.example.TestTwo.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Tag> getTag(
            @RequestParam @Size(min = 3, max = 255) String name) {
        Tag tag = tagService.getTag(name);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .body(tag);
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(
            @Valid @RequestBody @NonNull Tag tag,
            @RequestHeader("X-TAG-ID") String id) {
        return ResponseEntity
                .status(CREATED)
                .header("X-TAG-ID")
                .body(tagService.addTag(tag));
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeTag(
            @PathVariable String name){
        tagService.removeTag(name);
        return ResponseEntity.noContent().build();
    }
}
