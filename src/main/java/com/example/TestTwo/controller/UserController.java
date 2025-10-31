package com.example.TestTwo.controller;

import com.example.TestTwo.entity.UserEntity;
import com.example.TestTwo.model.UserDto;
import com.example.TestTwo.service.UserService;
import com.example.TestTwo.util.MappingUtils;
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
    private final MappingUtils mappingUtils;

    @GetMapping
    public ResponseEntity<UserDto> getUser(
            @RequestParam @Size(min = 3, max = 255) String name) {
        UserEntity user = userService.getUser(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mappingUtils.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(
            @Valid @RequestBody @NonNull UserDto user,
            @RequestHeader("X-USER-ID") String id) {
        return ResponseEntity
                .status(CREATED)
                .header("X-USER-ID", user.getEmail())
                .body(mappingUtils.toDto(userService.addUser(user)));
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> removeUser(
            @PathVariable String name){
        userService.removeUser(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/by-name/{name}")
    public ResponseEntity<UserDto> patchUser(
            @PathVariable String name,
            @RequestBody UserDto userUpdates) {

        UserEntity updatedUser = userService.updateUser(name, userUpdates);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mappingUtils.toDto(updatedUser));
    }
}
