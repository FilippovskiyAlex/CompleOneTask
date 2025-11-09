package com.example.TestTwo.service;

import com.example.TestTwo.entity.ApplicationUser;
import com.example.TestTwo.model.ApplicationUserDto;
import com.example.TestTwo.model.enums.ApplicationUserRole;
import com.example.TestTwo.repository.ApplicationUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {
    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public ApplicationUser registerUser(ApplicationUserDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        ApplicationUser user = ApplicationUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ApplicationUserRole.USER)
                .isAccountExpired(false)
                .isAccountLocked(false)
                .isCredentialsExpired(false)
                .isDisabled(false)
                .build();
        return userRepository.save(user);
    }

}