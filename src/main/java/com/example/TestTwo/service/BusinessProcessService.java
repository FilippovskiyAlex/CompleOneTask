package com.example.TestTwo.service;

import com.example.TestTwo.entity.*;
import com.example.TestTwo.model.Status;
import com.example.TestTwo.repository.CommentRepository;
import com.example.TestTwo.repository.ProjectRepository;
import com.example.TestTwo.repository.TagRepository;
import com.example.TestTwo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BusinessProcessService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;


    // 1. Выдача группы комментариев по автору
    public List<CommentEntity> getCommentByAuthor(UserEntity author) {
        return commentRepository.findByAuthor(author);
    }

    // 2. Выдача задач по тегам и статусу
    public List<TaskEntity> getTasksByTagsAndStatus(List<String> tagNames, Status status, String projectName) {
        ProjectEntity project = projectRepository.findByName(projectName);

        if (project == null) {
            return null;
        }

        List<TagEntity> tags = new ArrayList<>();
        List<TaskEntity> rezult = new ArrayList<>();
        for (String tagName : tagNames){
            TagEntity tag = tagRepository.findByName(tagName);
            if (tag != null) {
                List<TaskEntity> findOneTag = taskRepository.findByTagAndStatusAndProject(tag, status, project);
                rezult.addAll(findOneTag);
            }
        }
        return rezult;
    }

    // 3. Анализ длительности проекта
    public String getDurationWork(String nameProject) {
        ProjectEntity project = projectRepository.findByName(nameProject);
        if (project == null) {
            return "Не был найден проект по этому названию";
        }

        int cntDone = 0;
        LocalDate start = null;  // Инициализируем как null
        LocalDate end = null;    // Инициализируем как null
        boolean allTasksHaveEndDate = true;

        List<TaskEntity> tasks = taskRepository.findByProject(project);
        if (tasks == null || tasks.isEmpty()) {
            return "Нет задач связанных с этим проектом";
        }

        for (TaskEntity task : tasks) {
            if (task.getStatus() == Status.DONE) {
                cntDone++;
            }

            // Обновляем самую раннюю дату начала
            if (task.getStartDate() != null) {
                if (start == null || task.getStartDate().isBefore(start)) {
                    start = task.getStartDate();
                }
            }

            // Проверяем наличие даты окончания и обновляем самую позднюю
            if (task.getEndDate() != null) {
                if (end == null || task.getEndDate().isAfter(end)) {
                    end = task.getEndDate();
                }
            } else {
                allTasksHaveEndDate = false;
            }
        }

        String rezult = "";

        if (!allTasksHaveEndDate || start == null || end == null) {
            rezult += "Проект еще не закончен!";
        } else {
            Period period = Period.between(start, end);
            rezult += "Период: " + period.getYears() + " лет, " +
                    period.getMonths() + " месяцев, " +
                    period.getDays() + " дней";
        }

        // Защита от деления на ноль
        if (!tasks.isEmpty()) {
            double percentage = (double) cntDone / tasks.size() * 100;
            rezult += "\nПроцент выполнения: %.2f%%".formatted(percentage);
        } else {
            rezult += "\nПроцент выполнения: нет задач";
        }

        return rezult;
    }

    // 4. Перенос задач между проектами
    public String transferTasksToProject(List<String> tasksName, String projectName){
        ProjectEntity project = projectRepository.findByName(projectName);
        if (project == null) {
            return "Не был найден проект по этому названию";
        }

        if (tasksName == null || tasksName.isEmpty()) {
            return "Список задач для переноса пуст";
        }

        List<String> foundTasks = new ArrayList<>();
        List<String> notFoundTasks = new ArrayList<>();

        for (String taskName : tasksName) {
            TaskEntity task = taskRepository.findByName(taskName);
            if (task != null){
                task.setProject(project);
                taskRepository.save(task); // Сохраняем изменения
                foundTasks.add(taskName);
            } else {
                notFoundTasks.add(taskName);
            }
        }

        StringBuilder result = new StringBuilder();
        if (!foundTasks.isEmpty()) {
            result.append("Успешно перенесены задачи: ").append(String.join(", ", foundTasks));
        }
        if (!notFoundTasks.isEmpty()) {
            if (!foundTasks.isEmpty()) result.append("\n");
            result.append("Не найдены задачи: ").append(String.join(", ", notFoundTasks));
        }

        return result.toString();
    }

    // 5. Массовое заполнение тегами
    public String setTagToTasks(List<String> tasksName, String tagName) {
        TagEntity tag = tagRepository.findByName(tagName);
        if (tag == null) {
            return "Не найден тег с именем: " + tagName;
        }

        if (tasksName == null || tasksName.isEmpty()) {
            return "Список задач для назначения тега пуст";
        }

        List<String> foundTasks = new ArrayList<>();
        List<String> notFoundTasks = new ArrayList<>();

        for (String taskName : tasksName) {
            TaskEntity task = taskRepository.findByName(taskName);
            if (task != null) {
                // Устанавливаем тег для задачи
                task.setTag(tag);
                taskRepository.save(task); // Сохраняем изменения
                foundTasks.add(taskName);
            } else {
                notFoundTasks.add(taskName);
            }
        }

        StringBuilder result = new StringBuilder();
        if (!foundTasks.isEmpty()) {
            result.append("Тег '").append(tagName).append("' успешно назначен задачам: ")
                    .append(String.join(", ", foundTasks));
        }
        if (!notFoundTasks.isEmpty()) {
            if (!foundTasks.isEmpty()) result.append("\n");
            result.append("Не найдены задачи: ").append(String.join(", ", notFoundTasks));
        }

        return result.toString();
    }
}
