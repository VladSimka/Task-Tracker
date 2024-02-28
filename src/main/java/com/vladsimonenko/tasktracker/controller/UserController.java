package com.vladsimonenko.tasktracker.controller;

import com.vladsimonenko.tasktracker.dto.TaskDto;
import com.vladsimonenko.tasktracker.dto.UserDto;
import com.vladsimonenko.tasktracker.dto.validator.OnCreate;
import com.vladsimonenko.tasktracker.dto.validator.OnUpdate;
import com.vladsimonenko.tasktracker.mapper.TaskMapper;
import com.vladsimonenko.tasktracker.mapper.UserMapper;
import com.vladsimonenko.tasktracker.model.Task;
import com.vladsimonenko.tasktracker.model.User;
import com.vladsimonenko.tasktracker.service.TaskService;
import com.vladsimonenko.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public UserDto getUserById(@PathVariable("id") Long userId) {
        return userMapper.toDto(userService.getById(userId));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.delete(userId);
    }

    @PutMapping
    @PreAuthorize("@cse.canAccessUser(#userDto.id)")
    public UserDto update(
            @RequestBody
            @Validated(OnUpdate.class)
            UserDto userDto
    ) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);

        return userMapper.toDto(updatedUser);
    }

    @PatchMapping
    @PreAuthorize("@cse.canAccessUser(#userDto.id)")
    public UserDto updateSomeInfo(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateSomeInfo(user);

        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public List<TaskDto> getAllTasksByUser(@PathVariable("id") Long userId) {
        List<Task> tasks = taskService.getAllByUserId(userId);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public TaskDto createTask(@PathVariable("id") Long userId,
                              @RequestBody @Validated(OnCreate.class) TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task created = taskService.create(task, userId);

        return taskMapper.toDto(created);
    }
}
