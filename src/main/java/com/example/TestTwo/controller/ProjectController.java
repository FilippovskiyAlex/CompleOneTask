package com.example.TestTwo.controller;

import com.example.TestTwo.model.*;
import com.example.TestTwo.service.ProjectService;
import com.example.TestTwo.service.ProjectService;
import com.example.TestTwo.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Project> getProject(
            @RequestParam @Size(min = 3, max = 255) String name) {
        Project project = projectService.getProject(name);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(project);
    }
    @PostMapping
    public ResponseEntity<Project> addProject(
            @Valid @RequestBody @NonNull Project project,
            @RequestHeader("X-PROJECT-ID") String id,
            @RequestParam String nameTask){
        Task task = taskService.getTask(nameTask);
        if (task == null){
            return ResponseEntity.notFound().build();
        }
        project.getTasks().put(nameTask, task);
        projectService.addProject(project);
        return ResponseEntity
                .status(CREATED)
                .header("X-PROJECT-ID", project.getName())
                .body(project);
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeProject(
            @PathVariable String name){
        projectService.removeProject(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/by-name/{name}")
    public ResponseEntity<Project> patchProject(
            @PathVariable String name,
            @RequestBody Project projectUpdates) {
        Project updateProject = projectService.updateProject(name, projectUpdates);
        if (updateProject == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateProject);
    }
}
