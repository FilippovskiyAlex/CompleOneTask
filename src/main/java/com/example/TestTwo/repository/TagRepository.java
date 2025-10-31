package com.example.TestTwo.repository;

import com.example.TestTwo.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {
    TagEntity findByName(String name);

    void deleteByName(String name);
}
