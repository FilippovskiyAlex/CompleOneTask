package com.example.TestTwo.controller;

import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.model.CommentDto;
import com.example.TestTwo.model.TagDto;
import com.example.TestTwo.model.TaskDto;
import com.example.TestTwo.model.UserDto;
import com.example.TestTwo.service.CommentService;
import com.example.TestTwo.service.TagService;
import com.example.TestTwo.service.TaskService;
import com.example.TestTwo.service.UserService;
import com.example.TestTwo.util.MappingUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final MappingUtils mappingUtils;

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<TaskDto> getTask(
            @RequestParam @Size(min = 3, max = 255) String name) {
        TaskEntity task = taskService.getTask(name);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mappingUtils.toDto(task));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public ResponseEntity<TaskDto> addTask(
            @Valid @RequestBody @NonNull TaskDto task,
            @RequestHeader("X-TASK-ID") String id){
        return ResponseEntity
                .status(CREATED)
                .header("X-TASK-ID", task.getName())
                .body(mappingUtils.toDto(taskService.addTask(task)));
    }

    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasAuthority('delete')")
    public ResponseEntity<Void> removeTask(
            @PathVariable String name){
        taskService.removeTask(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/by-name/{name}")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<TaskDto> patchTask(
            @PathVariable String name,
            @RequestBody TaskDto taskUpdates) {
        TaskEntity updatedTask = taskService.updateTask(name, taskUpdates);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mappingUtils.toDto(updatedTask));
    }
}
