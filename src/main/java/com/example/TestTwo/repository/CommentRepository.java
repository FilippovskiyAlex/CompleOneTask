package com.example.TestTwo.repository;

import com.example.TestTwo.entity.CommentEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

    List<CommentEntity> findByAuthor(UserEntity author);

    CommentEntity findByTaskAndAuthorAndCreatedAt(TaskEntity task, UserEntity author, LocalDate createdAt);

    void deleteByTaskAndAuthorAndCreatedAt(TaskEntity task, UserEntity author, LocalDate date);
}
