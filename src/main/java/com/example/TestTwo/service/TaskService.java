package com.example.TestTwo.service;

import com.example.TestTwo.model.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {
    private final Map<String, Task> tasks = new HashMap<>();

    public Task getTask(String name) {
        return tasks.get(name);
    }

    public Task addTask(Task task) {
        tasks.put(task.getName(), task);
        return tasks.get(task.getName());
    }

    public void removeTask(String name) {
        tasks.remove(name);
    }
}
