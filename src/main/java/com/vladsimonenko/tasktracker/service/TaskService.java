package com.vladsimonenko.tasktracker.service;

import com.vladsimonenko.tasktracker.model.Task;

import java.util.List;

public interface TaskService {
    Task getById(Long taskId);

    Task update(Task task);

    Task updateSomePartsOfTask(Task task);

    List<Task> getAllByUserId(Long userId);

    void delete(Long id);

    Task create(Task task, Long userId);

}
