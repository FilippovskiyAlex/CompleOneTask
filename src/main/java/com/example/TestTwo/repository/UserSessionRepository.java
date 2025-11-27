package com.example.TestTwo.repository;

import com.example.TestTwo.entity.ApplicationUser;
import com.example.TestTwo.entity.UserSession;
import com.example.TestTwo.model.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findById(UUID id);
    Optional<UserSession> findByRefreshToken(String refreshToken);
    Optional<UserSession> findByRefreshTokenAndStatus(String refreshToken, SessionStatus status);
    List<UserSession> findByUserAndStatus(ApplicationUser user, SessionStatus status);
    void deleteByExpiresAtBefore(Instant expiredTime);
}
