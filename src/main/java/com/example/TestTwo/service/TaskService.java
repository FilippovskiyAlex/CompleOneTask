package com.example.TestTwo.service;

import com.example.TestTwo.model.Task;
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

    public Task updateTask(String name, Task updates){
        Task existingTask = getTask(name);
        if (existingTask == null) {
            return null;
        }
        if (updates.getName() != null) {
            existingTask.setName(updates.getName());
        }
        if (updates.getTag() != null) {
            existingTask.setTag(updates.getTag());
        }
        if (updates.getStatus() != null) {
            existingTask.setStatus(updates.getStatus());
        }
        if (updates.getDateStart() != null) {
            existingTask.setDateStart(updates.getDateStart());
        }
        if (updates.getDateEnd() != null) {
            existingTask.setDateEnd(updates.getDateEnd());
        }
        return existingTask;
    }
}
