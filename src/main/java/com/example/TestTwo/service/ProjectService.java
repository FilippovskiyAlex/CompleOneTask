package com.example.TestTwo.service;

import com.example.TestTwo.entity.ProjectEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.model.ProjectDto;
import com.example.TestTwo.repository.ProjectRepository;
import com.example.TestTwo.repository.TaskRepository;
import com.example.TestTwo.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final MappingUtils mapping;

    public ProjectEntity getProject(String name) {
        return projectRepository.findByName(name);
    }

    @Transactional
    public ProjectEntity addProject(ProjectDto dto) {
        List<TaskEntity> tasks = new ArrayList<>();
        for (String nameTask : dto.getTasks()){
            TaskEntity task = taskRepository.findByName(nameTask);
            if (task != null) {
                tasks.add(task);
            }
        }
        ProjectEntity entity = mapping.toEntity(dto, tasks);
        return projectRepository.save(entity);
    }
    @Transactional
    public void removeProject(String name) {
        projectRepository.deleteByName(name);
    }
    @Transactional
    public ProjectEntity updateProject(String name, ProjectDto dto){
        ProjectEntity existing = projectRepository.findByName(name);
        List<TaskEntity> tasks = new ArrayList<>();
        for (String nameTask : dto.getTasks()){
            TaskEntity task = taskRepository.findByName(nameTask);
            if (task != null) {
                tasks.add(task);
            }
        }
        ProjectEntity updates = mapping.toEntity(dto, tasks);

        if (existing == null) {
            return null;
        }
        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            existing.setDescription(updates.getDescription());
        }
        if (updates.getStatus() != null) {
            existing.setStatus(updates.getStatus());
        }
        if (updates.getTasks() != null){
            existing.getTasks().clear();
            existing.getTasks().addAll(updates.getTasks());
        }
        return existing;
    }
}
