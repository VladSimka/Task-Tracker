package com.vladsimonenko.tasktracker.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Username must not be null")
    private String password;
}
