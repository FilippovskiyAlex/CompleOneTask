package com.example.TestTwo.controller;


import com.example.TestTwo.entity.ApplicationUser;
import com.example.TestTwo.model.ApplicationUserDto;
import com.example.TestTwo.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ApplicationUserDto request) {
        try {
            ApplicationUser user = registrationService.registerUser(request);

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "userId", user.getId(),
                    "email", user.getEmail()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}

