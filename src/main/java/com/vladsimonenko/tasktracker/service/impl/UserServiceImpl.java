package com.vladsimonenko.tasktracker.service.impl;

import com.vladsimonenko.tasktracker.exception.ResourceNotFoundException;
import com.vladsimonenko.tasktracker.model.User;
import com.vladsimonenko.tasktracker.repository.UserRepository;
import com.vladsimonenko.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public User updateSomeInfo(User user) {
        User toUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Can not update, no such user"));

        Field[] fields = User.class.getDeclaredFields();

        for (var field : fields) {
            field.setAccessible(true);
            var value = field.get(user);
            if (value != null) {
                field.set(toUpdate, value);
            }
        }
        toUpdate.setPassword(passwordEncoder.encode(toUpdate.getPassword()));
        return toUpdate;
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User create(User user) {
        User saved = userRepository.save(user);
        saved.setPassword(passwordEncoder.encode(saved.getPassword()));
        return saved;
    }


}
