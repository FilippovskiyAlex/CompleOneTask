package com.example.TestTwo.controller;

import com.example.TestTwo.entity.CommentEntity;
import com.example.TestTwo.entity.TaskEntity;
import com.example.TestTwo.model.CommentDto;
import com.example.TestTwo.model.Status;
import com.example.TestTwo.model.TaskDto;
import com.example.TestTwo.model.TaskFilterDto;
import com.example.TestTwo.service.BusinessProcessService;
import com.example.TestTwo.service.CommentService;
import com.example.TestTwo.service.UserService;
import com.example.TestTwo.util.MappingUtils;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/BusinessProcess")
@RequiredArgsConstructor
public class BusinessProcessController {

    private final CommentService commentService;
    private final UserService userService;
    private final BusinessProcessService businessProcessService;
    private final MappingUtils mappingUtils;

    // Получение группы комментариев по автору
    @GetMapping("/get-comment-by-author")
    public ResponseEntity<List<CommentDto>> getComment(
            @RequestParam @Size(min = 3, max = 255) String name) {

        List<CommentEntity> comments = businessProcessService.getCommentByAuthor(userService.getUser(name));
        if (comments == null) {
            return ResponseEntity.notFound().build();
        }
        List<CommentDto> commentsDto = new ArrayList<>();
        for (CommentEntity comment : comments){
            commentsDto.add(mappingUtils.toDto(comment));
        }
        return ResponseEntity.ok().body(commentsDto);
    }

    // Фильтрация задач по тегам, статусу и проекту (через тело запроса)
    @PostMapping("/filter-tasks")
    public ResponseEntity<List<TaskDto>> filterTasks(
            @RequestBody TaskFilterDto filterRequest) {

        List<TaskEntity> tasks = businessProcessService.getTasksByTagsAndStatus(
                filterRequest.getTags(),
                filterRequest.getStatus(),
                filterRequest.getProject()
        );

        if (tasks == null) {
            return ResponseEntity.notFound().build();
        }

        List<TaskDto> taskDtos = tasks.stream()
                .map(mappingUtils::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(taskDtos);
    }

    // Анализ длительности проекта
    @GetMapping("/project-duration")
    public ResponseEntity<String> getProjectDuration(
            @RequestParam @Size(min = 1, max = 255) String projectName) {

        try {
            String result = businessProcessService.getDurationWork(projectName);
            if (result.equals("Не был найден проект по этому названию") ||
                    result.equals("Нет задач связанных с этим проектом")) {
                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Произошла ошибка при анализе проекта: " + e.getMessage());
        }
    }

    // Перенос задач между проектами
    @PostMapping("/transfer-tasks")
    public ResponseEntity<String> transferTasks(
            @RequestParam List<String> tasksName,
            @RequestParam String projectName) {

        try {
            String result = businessProcessService.transferTasksToProject(tasksName, projectName);

            // Проверяем на ошибки
            if (result.startsWith("Не был найден проект") ||
                    result.startsWith("Список задач для переноса пуст")) {
                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Произошла ошибка при переносе задач: " + e.getMessage());
        }
    }

    // Массовое назначение тега задачам
    @PostMapping("/set-tag-to-tasks")
    public ResponseEntity<String> setTagToTasks(
            @RequestParam List<String> tasksName,
            @RequestParam String tagName) {

        try {
            String result = businessProcessService.setTagToTasks(tasksName, tagName);

            // Проверяем на ошибки
            if (result.startsWith("Не найден тег") ||
                    result.startsWith("Список задач для назначения тега пуст")) {
                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Произошла ошибка при назначении тега: " + e.getMessage());
        }
    }
}
