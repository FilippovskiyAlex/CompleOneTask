package com.example.TestTwo.controller;

import com.example.TestTwo.model.Comment;
import com.example.TestTwo.model.Tag;
import com.example.TestTwo.model.Task;
import com.example.TestTwo.model.User;
import com.example.TestTwo.service.CommentService;
import com.example.TestTwo.service.TagService;
import com.example.TestTwo.service.TaskService;
import com.example.TestTwo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final CommentService сommentService;
    private final UserService userService;
    private final TagService tagService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Task> getTask(
            @RequestParam @Size(min = 3, max = 255) String name) {
        Task task = taskService.getTask(name);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(task);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(
            @Valid @RequestBody @NonNull Task task,
            @RequestHeader("X-TASK-ID") String id,
            @RequestParam String worker,
            @RequestParam String author,
            @RequestParam String tag){
        User Author = userService.getUser(author);
        User Worker = userService.getUser(worker);
        Comment Comment = сommentService.getComment(Author);
        Tag Tag = tagService.getTag(tag);
        if (Author == null || Worker == null || Tag == null || Comment == null){
            return ResponseEntity.notFound().build();
        }
        task.setTag(Tag);
        task.getWorkers().put(worker, Worker);
        task.getComments().put(author, Comment);
        taskService.addTask(task);
        return ResponseEntity
                .status(CREATED)
                .header("X-TASK-ID", task.getName())
                .body(task);
    }
    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeTask(
            @PathVariable String name){
        taskService.removeTask(name);
        return ResponseEntity.noContent().build();
    }
}
