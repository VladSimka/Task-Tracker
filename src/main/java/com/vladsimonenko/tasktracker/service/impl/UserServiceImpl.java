package com.vladsimonenko.tasktracker.service.impl;

import com.vladsimonenko.tasktracker.event.UserCreatedEvent;
import com.vladsimonenko.tasktracker.exception.ResourceNotFoundException;
import com.vladsimonenko.tasktracker.model.User;
import com.vladsimonenko.tasktracker.repository.UserRepository;
import com.vladsimonenko.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

import static com.vladsimonenko.tasktracker.event.KafkaTopics.USER_CREATED_EVENTS_TOPIC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

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

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .build();

        CompletableFuture<SendResult<Long, UserCreatedEvent>> send =
                kafkaTemplate.send(USER_CREATED_EVENTS_TOPIC.getName(), saved.getId(), userCreatedEvent);

        send.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Failed to send message: {}", throwable.getMessage());
            } else {
                log.info("Message send successfully: {}", result.getRecordMetadata());
            }
        });

        return saved;
    }


}
