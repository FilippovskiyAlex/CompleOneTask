package com.example.TestTwo.service;

import com.example.TestTwo.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public User getUser(String name) {
        return users.get(name);
    }

    public User addUser(User user) {
        users.put(user.getName(), user);
        return users.get(user.getName());
    }

    public void removeUser(String name) {
        users.remove(name);
    }
}
