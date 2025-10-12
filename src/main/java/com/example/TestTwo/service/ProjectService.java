package com.example.TestTwo.service;

import com.example.TestTwo.model.Project;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectService {
    private final Map<String, Project> projects = new HashMap<>();

    public Project getProject(String name) {
        return projects.get(name);
    }

    public Project addProject(Project project) {
        projects.put(project.getName(), project);
        return projects.get(project.getName());
    }

    public void removeProject(String name) {
        projects.remove(name);
    }
}
