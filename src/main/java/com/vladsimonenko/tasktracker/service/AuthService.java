package com.vladsimonenko.tasktracker.service;

import com.vladsimonenko.tasktracker.dto.auth.JwtRequest;
import com.vladsimonenko.tasktracker.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest jwtRequest);

    JwtResponse refresh(String refreshToken);
}
