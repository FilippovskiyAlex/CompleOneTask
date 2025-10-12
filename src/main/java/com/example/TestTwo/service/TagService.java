package com.example.TestTwo.service;


import com.example.TestTwo.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TagService {
    private final Map<String, Tag> tags = new HashMap<>();

    public Tag getTag(String name) {
        return tags.get(name);
    }

    public Tag addTag(Tag user) {
        tags.put(user.getName(), user);
        return tags.get(user.getName());
    }

    public void removeTag(String name) {
        tags.remove(name);
    }
}
