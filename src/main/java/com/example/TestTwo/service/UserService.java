package com.example.TestTwo.service;

import com.example.TestTwo.entity.UserEntity;
import com.example.TestTwo.model.UserDto;
import com.example.TestTwo.repository.UserRepository;
import com.example.TestTwo.util.MappingUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MappingUtils mapping;


    public UserEntity getUser(String name) {
        return userRepository.findByName(name);
    }

    @Transactional
    public UserEntity addUser(UserDto dto) {
        UserEntity entity = mapping.toEntity(dto);
        return userRepository.save(entity);
    }

    @Transactional
    public void removeUser(String name) {
        userRepository.deleteByName(name);
    }

    @Transactional
    public UserEntity updateUser(String name, UserDto dto) {
        UserEntity existing = userRepository.findByName(name);
        UserEntity updates = mapping.toEntity(dto);
        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getEmail() != null){
            existing.setEmail(updates.getEmail());
        }
        return userRepository.save(existing);
    }

}
