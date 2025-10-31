package com.example.TestTwo.repository;

import com.example.TestTwo.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    ProjectEntity findByName(String name);

    void deleteByName(String name);
}
