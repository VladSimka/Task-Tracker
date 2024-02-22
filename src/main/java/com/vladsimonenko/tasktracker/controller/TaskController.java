package com.vladsimonenko.tasktracker.controller;

import com.vladsimonenko.tasktracker.dto.TaskDto;
import com.vladsimonenko.tasktracker.mapper.TaskMapper;
import com.vladsimonenko.tasktracker.model.Task;
import com.vladsimonenko.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@cse.canAccessTask(#taskId)")
    public TaskDto getTaskById(@PathVariable("id") Long taskId) {
        Task task = taskService.getById(taskId);
        return taskMapper.toDto(task);
    }

    @PutMapping
    @PreAuthorize("@cse.canAccessTask(#taskDto.id)")
    public TaskDto update(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @PatchMapping
    @PreAuthorize("@cse.canAccessTask(#taskDto.id)")
    public TaskDto updateSomeParts(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.updateSomePartsOfTask(task);
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@cse.canAccessTask(#taskId)")
    public void deleteById(@PathVariable("id") Long taskId) {
        taskService.delete(taskId);
    }

}
