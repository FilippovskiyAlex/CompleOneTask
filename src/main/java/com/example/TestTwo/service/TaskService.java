package com.example.TestTwo.service;

import com.example.TestTwo.entity.*;
import com.example.TestTwo.model.TaskDto;
import com.example.TestTwo.repository.*;
import com.example.TestTwo.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MappingUtils mapping;

    public TaskEntity getTask(String name) {
        return taskRepository.findByName(name);
    }

    @Transactional
    public TaskEntity addTask(TaskDto dto) {
        List<UserEntity> workers = new ArrayList<>();
        for (String name : dto.getWorkers()){
            UserEntity user = userRepository.findByName(name);
            if (user != null) {
                workers.add(user);
            }
        }
        ProjectEntity project = null;
        TagEntity tag = null;
        if (dto.getProject() != null){
            project = projectRepository.findByName(dto.getProject());
        }
        if (dto.getTag() != null) {
            tag = tagRepository.findByName(dto.getTag());
        }

        TaskEntity entity  = mapping.toEntity(dto, project, tag, workers, null);
        return taskRepository.save(entity);
    }
    @Transactional
    public void removeTask(String name) {
        taskRepository.deleteByName(name);
    }
    @Transactional
    public TaskEntity updateTask(String name, TaskDto dto){
        TaskEntity existing = taskRepository.findByName(name);
        // Сбор всех полей
        List<UserEntity> workers = new ArrayList<>();
        for (String worker : dto.getWorkers()){
            UserEntity user = userRepository.findByName(worker);
            if (user != null) {
                workers.add(user);
            }
        }
        List<CommentEntity> comments = new ArrayList<>();
        for (String Date : dto.getComments().keySet()){
            UserEntity user = userRepository.findByName(dto.getComments().get(Date));
            LocalDate date = LocalDate.parse(Date);
            CommentEntity comment = commentRepository.findByTaskAndAuthorAndCreatedAt(
                    existing, user, date);
            comments.add(comment);
        }
        ProjectEntity project = projectRepository.findByName(dto.getProject());
        TagEntity tag = tagRepository.findByName(dto.getTag());
        // Преобразование к Entity всех полей
        TaskEntity updates = mapping.toEntity(dto, project, tag, workers, comments);
        if (existing == null) { return null; }

        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            existing.setDescription(updates.getDescription());
        }
        if (updates.getStatus() != null) {
            existing.setStatus(updates.getStatus());
        }
        if (updates.getStartDate() != null) {
            existing.setStartDate(updates.getStartDate());
        }
        if (updates.getAssignedUsers() != null) {
            existing.getAssignedUsers().clear();
            existing.getAssignedUsers().addAll(updates.getAssignedUsers());
        }
        if (updates.getComments() != null) {
            existing.getComments().clear();
            existing.getComments().addAll(updates.getComments());
        }
        if (updates.getEndDate() != null) {
            existing.setEndDate(updates.getEndDate());
        }
        if (updates.getTag() != null) {
            existing.setTag(updates.getTag());
        }
         return taskRepository.save(existing);
    }
}
