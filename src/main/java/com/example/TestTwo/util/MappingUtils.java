package com.example.TestTwo.util;

import com.example.TestTwo.entity.*;
import com.example.TestTwo.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Component
public class MappingUtils {
    public UserDto toDto(UserEntity e) {
        UserDto dto = new UserDto();
        dto.setName(e.getName());
        dto.setEmail(e.getEmail());
        return dto;
    }

    public TagDto toDto(TagEntity e) {
        TagDto dto = new TagDto();
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        return dto;
    }

    public CommentDto toDto(CommentEntity e){
        CommentDto dto = new CommentDto();
        dto.setDescription(e.getDescription());
        dto.setAuthor(e.getAuthor().getName());
        dto.setTask(e.getTask().getName());
        dto.setDate(e.getCreatedAt().toString());
        return dto;
    }

    public TaskDto toDto(TaskEntity e){
        TaskDto dto = new TaskDto();
        dto.setName(e.getName());
        if (e.getAssignedUsers() != null){
            for (UserEntity ue : e.getAssignedUsers()){
                dto.getWorkers().add(ue.getName());
            }
        }
        if (e.getComments() != null){
            for (CommentEntity ce : e.getComments()){
                dto.getComments().put(ce.getCreatedAt().toString(), ce.getAuthor().getName());
            }
        }
        if (e.getTag() != null){
            dto.setTag(e.getTag().getName());
        }
        if (e.getStatus() != null) {
            dto.setStatus(e.getStatus());
        }
        if (e.getProject() != null) {
            dto.setProject(e.getProject().getName());
        }
        if (e.getStartDate() != null) {
            dto.setDateStart(e.getStartDate().toString());
        }
        if (e.getEndDate() != null) {
            dto.setDateEnd(e.getEndDate().toString());
        }
        return dto;
    }

    public ProjectDto toDto(ProjectEntity e){
        ProjectDto dto = new ProjectDto();
        dto.setName(e.getName());
        dto.setStatus(e.getStatus());
        dto.setDescription(e.getDescription());
        if (e.getTasks() != null){
            for (TaskEntity te : e.getTasks()){
                if (te.getName() != null) {
                    dto.getTasks().add(te.getName());
                }
            }
        }
        return dto;
    }

    public UserEntity toEntity(UserDto dto){
        UserEntity e = new UserEntity();
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        return e;
    }

    public TagEntity toEntity(TagDto dto){
        TagEntity e =new TagEntity();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        return e;
    }

    public CommentEntity toEntity(CommentDto dto, UserEntity author, TaskEntity task) {
        CommentEntity e = new CommentEntity();
        e.setDescription(dto.getDescription());
        e.setAuthor(author);
        e.setCreatedAt(LocalDate.parse(dto.getDate()));
        e.setTask(task);
        return e;
    }

    public TaskEntity toEntity(TaskDto dto, ProjectEntity project, TagEntity tag, List<UserEntity> assignedUsers, List<CommentEntity> comments) {
        TaskEntity e = new TaskEntity();
        e.setName(dto.getName());
        e.setStatus(dto.getStatus());
        e.setStartDate(LocalDate.parse(dto.getDateStart()));
        if (dto.getDateEnd() != null) {
            e.setEndDate(LocalDate.parse(dto.getDateEnd()));
        }
        e.setProject(project);
        e.setTag(tag);
        e.setAssignedUsers(assignedUsers);
        e.setComments(comments);
        return e;
    }

    public ProjectEntity toEntity(ProjectDto dto, List<TaskEntity> tasks) {
        ProjectEntity e = new ProjectEntity();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setStatus(dto.getStatus());
        e.setTasks(tasks);
        return e;
    }
}

