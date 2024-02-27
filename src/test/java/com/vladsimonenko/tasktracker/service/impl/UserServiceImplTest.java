package com.vladsimonenko.tasktracker.service.impl;

import com.vladsimonenko.tasktracker.exception.ResourceNotFoundException;
import com.vladsimonenko.tasktracker.model.User;
import com.vladsimonenko.tasktracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final Long ID = 1L;
    private static final String USERNAME = "vlad";
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void getById() {
        User expected = new User();
        expected.setId(ID);

        Mockito.doReturn(Optional.of(expected))
                .when(userRepository).findById(ID);
        User actual = userService.getById(ID);

        Mockito.verify(userRepository).findById(ID);
        assertEquals(expected, actual);
    }

    @Test
    void getByNonExistingId() {
        Mockito.doReturn(Optional.empty())
                .when(userRepository).findById(ID);

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(ID));
        Mockito.verify(userRepository).findById(ID);
    }

    @Test
    void getByUsername() {
        User expected = new User();
        expected.setUsername(USERNAME);

        Mockito.doReturn(Optional.of(expected))
                .when(userRepository).findByUsername(USERNAME);
        User actual = userService.getByUsername(USERNAME);

        Mockito.verify(userRepository).findByUsername(USERNAME);
        assertEquals(expected, actual);
    }

    @Test
    void getByNonExistingUsername() {
        Mockito.doReturn(Optional.empty())
                .when(userRepository).findByUsername(USERNAME);

        assertThrows(ResourceNotFoundException.class, () -> userService.getByUsername(USERNAME));
        Mockito.verify(userRepository).findByUsername(USERNAME);
    }
}
