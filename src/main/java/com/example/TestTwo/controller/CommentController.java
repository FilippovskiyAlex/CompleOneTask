package com.example.TestTwo.controller;

import com.example.TestTwo.model.Comment;
import com.example.TestTwo.model.Comment;
import com.example.TestTwo.model.User;
import com.example.TestTwo.service.CommentService;
import com.example.TestTwo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService сommentService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Comment> getComment(
            @RequestParam @Size(min = 3, max = 255) String name) {
        Comment comment = сommentService.getComment(userService.getUser(name));
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(
            @Valid @RequestBody @NonNull Comment comment,
            @RequestHeader("X-COMMENT-ID") String id,
            @RequestParam String author) {
        User Author = userService.getUser(author);
        if (Author == null) {
            return ResponseEntity.notFound().build();
        }
        comment.setAuthor(Author);
        сommentService.addComment(comment);
        return ResponseEntity
                .status(CREATED)
                .header("X-COMMENT-ID", comment.getAuthor().getEmail())
                .body(comment);
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeComment(
            @PathVariable String name){
        сommentService.removeComment(userService.getUser(name));
        return ResponseEntity.noContent().build();
    }
}
