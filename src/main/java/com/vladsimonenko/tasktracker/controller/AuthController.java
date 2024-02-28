package com.vladsimonenko.tasktracker.controller;

import com.vladsimonenko.tasktracker.dto.UserDto;
import com.vladsimonenko.tasktracker.dto.auth.JwtRequest;
import com.vladsimonenko.tasktracker.dto.auth.JwtResponse;
import com.vladsimonenko.tasktracker.dto.validator.OnCreate;
import com.vladsimonenko.tasktracker.mapper.UserMapper;
import com.vladsimonenko.tasktracker.model.User;
import com.vladsimonenko.tasktracker.service.AuthService;
import com.vladsimonenko.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Validated JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(
            @Validated(OnCreate.class)
            @RequestBody
            UserDto userDto
    ) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
