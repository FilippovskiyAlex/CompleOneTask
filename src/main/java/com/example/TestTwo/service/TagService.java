package com.example.TestTwo.service;

import com.example.TestTwo.entity.TagEntity;
import com.example.TestTwo.model.TagDto;
import com.example.TestTwo.repository.TagRepository;
import com.example.TestTwo.util.MappingUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final MappingUtils mapping;

    public TagEntity getTag(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    public TagEntity addTag(TagDto dto) {
        TagEntity entity = mapping.toEntity(dto);
        return tagRepository.save(entity);
    }

    @Transactional
    public void removeTag(String name) {
        tagRepository.deleteByName(name);
    }

    @Transactional
    public TagEntity updateTag(String name, TagDto dto) {
        TagEntity existing = tagRepository.findByName(name);
        TagEntity updates = mapping.toEntity(dto);
        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            existing.setDescription(updates.getDescription());
        }
        return tagRepository.save(existing);
    }
}