package com.example.TestTwo.controller;

import com.example.TestTwo.entity.TagEntity;
import com.example.TestTwo.model.TagDto;
import com.example.TestTwo.service.TagService;
import com.example.TestTwo.util.MappingUtils;
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
    private final MappingUtils mappingUtils;

    @GetMapping
    public ResponseEntity<TagDto> getTag(
            @RequestParam @Size(min = 3, max = 255) String name) {
        TagEntity tag = tagService.getTag(name);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .body(mappingUtils.toDto(tag));
    }

    @PostMapping
    public ResponseEntity<TagDto> addTag(
            @Valid @RequestBody @NonNull TagDto tag,
            @RequestHeader("X-TAG-ID") String id) {
        return ResponseEntity
                .status(CREATED)
                .header("X-TAG-ID")
                .body(mappingUtils.toDto(tagService.addTag(tag)));
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeTag(
            @PathVariable String name){
        tagService.removeTag(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/by-name/{name}")
    public ResponseEntity<TagDto> patchTag(
            @PathVariable String name,
            @RequestBody TagDto userUpdates) {

        TagEntity updatedTag = tagService.updateTag(name, userUpdates);
        if (updatedTag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mappingUtils.toDto(updatedTag));
    }
}
