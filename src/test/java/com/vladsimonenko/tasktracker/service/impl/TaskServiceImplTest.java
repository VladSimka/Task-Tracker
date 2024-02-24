package com.vladsimonenko.tasktracker.service.impl;


import com.vladsimonenko.tasktracker.exception.ResourceNotFoundException;
import com.vladsimonenko.tasktracker.model.Task;
import com.vladsimonenko.tasktracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.vladsimonenko.tasktracker.model.Status.IN_PROCESS;
import static com.vladsimonenko.tasktracker.model.Status.TO_DO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    private static final Long ID = 1L;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getById() {
        Task expected = new Task();
        expected.setId(ID);

        Mockito.doReturn(Optional.of(expected))
                .when(taskRepository).findById(ID);
        Task actual = taskService.getById(ID);

        Mockito.verify(taskRepository).findById(ID);
        assertEquals(expected, actual);
    }

    @Test
    void getByNonExistingId() {
        Mockito.doReturn(Optional.empty())
                .when(taskRepository).findById(ID);

        assertThrows(ResourceNotFoundException.class, () -> taskService.getById(ID));
        Mockito.verify(taskRepository).findById(ID);
    }


    @Test
    void update() {
        Task expected = new Task();
        expected.setId(ID);
        expected.setStatus(TO_DO);
        expected.setDescription("simple task");

        Mockito.doReturn(expected)
                .when(taskRepository).save(expected);
        Task actual = taskService.update(expected);


        Mockito.verify(taskRepository).save(expected);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }


    @Test
    void deleteById() {
        taskService.delete(ID);

        Mockito.verify(taskRepository).deleteById(ID);
    }

    @Test
    void create() {
        Task expected = new Task();
        expected.setStatus(IN_PROCESS);
        expected.setTitle("my task");
        expected.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        expected.setPlannedEndTime(Timestamp.valueOf(LocalDateTime.now()));
        expected.setDescription("simple task");

        Mockito.doAnswer(invocationOnMock -> {
                    Task savedTask = invocationOnMock.getArgument(0);
                    savedTask.setId(ID);
                    return savedTask;
                })
                .when(taskRepository).save(expected);
        Task actual = taskService.create(expected, ID);

        Mockito.verify(taskRepository).save(expected);
        Mockito.verify(taskRepository).connectTaskToUser(ID, ID);
        assertNotNull(actual.getId());
    }

    @Test
    void createWhenStatusIsNull() {
        Task expected = new Task();
        expected.setTitle("my task");
        expected.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        expected.setPlannedEndTime(Timestamp.valueOf(LocalDateTime.now()));
        expected.setDescription("simple task");

        Mockito.doAnswer(invocationOnMock -> {
                    Task savedTask = invocationOnMock.getArgument(0);
                    savedTask.setId(ID);
                    return savedTask;
                })
                .when(taskRepository).save(expected);
        Task actual = taskService.create(expected, ID);

        Mockito.verify(taskRepository).save(expected);
        Mockito.verify(taskRepository).connectTaskToUser(ID, ID);
        assertNotNull(actual.getId());
        assertEquals(actual.getStatus(), TO_DO);
    }
}
