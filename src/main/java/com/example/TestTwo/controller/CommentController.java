package com.example.TestTwo.controller;

import com.example.TestTwo.entity.CommentEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.entity.UserEntity;
import com.example.TestTwo.model.CommentDto;
import com.example.TestTwo.model.TaskDto;
import com.example.TestTwo.model.UserDto;
import com.example.TestTwo.service.CommentService;
import com.example.TestTwo.service.TaskService;
import com.example.TestTwo.service.UserService;
import com.example.TestTwo.util.MappingUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final MappingUtils mappingUtils;

//    @GetMapping
//    public ResponseEntity<List<CommentDto>> getComment(
//            @RequestParam @Size(min = 3, max = 255) String name) {
//
//        List<CommentEntity> comments = commentService.getComment(userService.getUser(name));
//        if (comments == null) {
//            return ResponseEntity.notFound().build();
//        }
//        List<CommentDto> commentsDto = new ArrayList<>();
//        for (CommentEntity comment : comments){
//            commentsDto.add(mappingUtils.toDto(comment));
//        }
//        return ResponseEntity.ok().body(commentsDto);
//    }

    @GetMapping
    public ResponseEntity<CommentDto> getComment(
            @RequestParam @Size(min = 3, max = 255) String authorName,
            @RequestParam @Size(min = 3, max = 255) String taskName,
            @RequestParam String createdAt) {
        CommentEntity entity = commentService.getComment(authorName, taskName, createdAt);
        return ResponseEntity.ok().body(mappingUtils.toDto(entity));
    }


    @PostMapping
    public ResponseEntity<CommentDto> addComment(
            @Valid @RequestBody @NonNull CommentDto comment,
            @RequestHeader("X-COMMENT-ID") String id
    ) {
        commentService.addComment(comment);
        return ResponseEntity
                .status(CREATED)
                .header("X-COMMENT-ID", comment.getAuthor())
                .body(comment);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeComment(
            @RequestParam @Size(min = 3, max = 255) String authorName,
            @RequestParam @Size(min = 3, max = 255) String taskName,
            @RequestParam String createdAt
            ) {

        commentService.removeComment(authorName, taskName, createdAt);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<CommentDto> patchComment(
            @RequestBody CommentDto commentUpdates) {

        CommentEntity updatedComment = commentService.updateComment(commentUpdates.getAuthor(), commentUpdates.getTask(), commentUpdates.getDate() , commentUpdates);
        return ResponseEntity.ok(mappingUtils.toDto(updatedComment));
    }

}
