package com.example.TestTwo.entity;

import com.example.TestTwo.model.enums.ApplicationUserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "application_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationUser {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;

    private boolean isAccountExpired;

    private boolean isAccountLocked;

    private boolean isCredentialsExpired;

    private boolean isDisabled;
}
