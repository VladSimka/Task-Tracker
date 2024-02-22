package com.vladsimonenko.tasktracker.service.impl;

import com.vladsimonenko.tasktracker.exception.ResourceNotFoundException;
import com.vladsimonenko.tasktracker.model.Task;
import com.vladsimonenko.tasktracker.repository.TaskRepository;
import com.vladsimonenko.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

import static com.vladsimonenko.tasktracker.model.Status.TO_DO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task getById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    @Transactional
    public Task update(Task task) {
        return taskRepository.save(task);
    }


    @Override
    @SneakyThrows
    @Transactional
    public Task updateSomePartsOfTask(Task task) {
        Task toUpdate = taskRepository.findById(task.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Can not update task, it is not exist"));

        Field[] fields = Task.class.getDeclaredFields();
        for (var field : fields) {
            field.setAccessible(true);
            var value = field.get(task);
            if (value != null) {
                field.set(toUpdate, value);
            }
        }

        return toUpdate;
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        if (task.getStatus() == null) {
            task.setStatus(TO_DO);
        }
        taskRepository.save(task);
        taskRepository.connectTaskToUser(task.getId(), userId);
        return task;
    }
}
