package com.example.TestTwo.service;

import com.example.TestTwo.entity.CommentEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.entity.UserEntity;
import com.example.TestTwo.model.CommentDto;
import com.example.TestTwo.model.UserDto;
import com.example.TestTwo.repository.CommentRepository;
import com.example.TestTwo.repository.TaskRepository;
import com.example.TestTwo.repository.UserRepository;
import com.example.TestTwo.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final MappingUtils mapping;

    public CommentEntity getComment(String author, String task, String date)
    {
        UserEntity Author = userRepository.findByName(author);
        TaskEntity Task = taskRepository.findByName(task);
        LocalDate Date = LocalDate.parse(date);
        return commentRepository.findByTaskAndAuthorAndCreatedAt(Task, Author, Date);
    }

    @Transactional
    public CommentEntity addComment(CommentDto dto) {
        UserEntity author = userRepository.findByName(dto.getAuthor());
        TaskEntity task = taskRepository.findByName(dto.getTask());
        CommentEntity entity = mapping.toEntity(dto, author, task);
        return commentRepository.save(entity);
    }

    @Transactional
    public void removeComment(String author, String task, String date) {
        UserEntity Author = userRepository.findByName(author);
        TaskEntity Task = taskRepository.findByName(task);
        LocalDate Date = LocalDate.parse(date);
        commentRepository.deleteByTaskAndAuthorAndCreatedAt(Task, Author, Date);
    }

    @Transactional
    public CommentEntity updateComment(String author, String task, String date, CommentDto dto){
        UserEntity Author = userRepository.findByName(author);
        TaskEntity Task = taskRepository.findByName(task);
        LocalDate Date = LocalDate.parse(date);
        CommentEntity existing = commentRepository.findByTaskAndAuthorAndCreatedAt(Task, Author, Date);
        UserEntity author_updates = userRepository.findByName(dto.getAuthor());
        TaskEntity task_updates = taskRepository.findByName(dto.getTask());
        CommentEntity updates = mapping.toEntity(dto, author_updates, task_updates);
        if (updates.getTask() != null){
            existing.setTask(updates.getTask());
        }
        if (updates.getAuthor() != null){
            existing.setAuthor(updates.getAuthor());
        }
        if (updates.getDescription() != null){
            existing.setDescription(updates.getDescription());
        }
        if (updates.getCreatedAt() != null){
            existing.setCreatedAt(updates.getCreatedAt());
        }
        return commentRepository.save(existing);
    }
}
