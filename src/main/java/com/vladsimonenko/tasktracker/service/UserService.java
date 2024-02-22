package com.vladsimonenko.tasktracker.service;

import com.vladsimonenko.tasktracker.model.User;

public interface UserService {

    User getById(Long userId);

    User getByUsername(String username);

    User update(User user);

    void delete(Long id);

    User create(User user);

    User updateSomeInfo(User user);

    boolean isTaskOwner(Long userId, Long taskId);
}
