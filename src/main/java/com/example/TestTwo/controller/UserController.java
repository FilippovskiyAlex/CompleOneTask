package com.example.TestTwo.controller;

import com.example.TestTwo.model.User;
import com.example.TestTwo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(
            @RequestParam @Size(min = 3, max = 255) String name) {
        User user = userService.getUser(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<User> addUser(
            @Valid @RequestBody @NonNull User user,
            @RequestHeader("X-USER-ID") String id) {
        return ResponseEntity
                .status(CREATED)
                .header("X-USER-ID", user.getEmail())
                .body(userService.addUser(user));
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeUser(
            @PathVariable String name){
        userService.removeUser(name);
        return ResponseEntity.noContent().build();
    }

}
