package com.example.TestTwo.controller;

import com.example.TestTwo.entity.ProjectEntity;
import com.example.TestTwo.entity.TagEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.model.*;
import com.example.TestTwo.service.ProjectService;
import com.example.TestTwo.service.TaskService;
import com.example.TestTwo.util.MappingUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final MappingUtils mappingUtils;

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<ProjectDto> getProject(
            @RequestParam @Size(min = 3, max = 255) String name) {
        ProjectEntity project = projectService.getProject(name);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mappingUtils.toDto(project));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public ResponseEntity<ProjectDto> addProject(
            @Valid @RequestBody @NonNull ProjectDto dto,
            @RequestHeader("X-PROJECT-ID") String id){
        return ResponseEntity
                .status(CREATED)
                .header("X-PROJECT-ID", dto.getName())
                .body(mappingUtils.toDto(projectService.addProject(dto)));
    }

    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasAuthority('delete')")
    public ResponseEntity<Void> removeProject(
            @PathVariable String name){
        projectService.removeProject(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/by-name/{name}")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<ProjectDto> patchProject(
            @PathVariable String name,
            @RequestBody ProjectDto projectUpdates) {
        ProjectEntity updateProject = projectService.updateProject(name, projectUpdates);
        if (updateProject == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mappingUtils.toDto(updateProject));
    }
}
