package com.example.TestTwo.service;

import com.example.TestTwo.model.Project;
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

    public Project updateProject(String name, Project updates){
        Project existingProject = getProject(name);
        if (existingProject == null) {
            return null;
        }
        if (updates.getName() != null) {
            existingProject.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            existingProject.setDescription(updates.getDescription());
        }
        if (updates.getStatus() != null) {
            existingProject.setStatus(updates.getStatus());
        }
        return existingProject;
    }
}
