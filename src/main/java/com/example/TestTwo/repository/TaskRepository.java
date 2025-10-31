package com.example.TestTwo.repository;

import com.example.TestTwo.entity.ProjectEntity;
import com.example.TestTwo.entity.TagEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    TaskEntity findByName(String name);

    void deleteByName(String name);

    List<TaskEntity> findByTagAndStatusAndProject(TagEntity tag, Status status, ProjectEntity project);

    List<TaskEntity> findByProject(ProjectEntity project);
}
