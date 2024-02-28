package com.vladsimonenko.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladsimonenko.tasktracker.dto.validator.OnCreate;
import com.vladsimonenko.tasktracker.dto.validator.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotNull(message = "Id mustn't be null",
            groups = OnUpdate.class)
    private Long id;

    @NotEmpty(message = "Username mustn't be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 255,
            message = "Username length should be between 3 and 255",
            groups = {OnUpdate.class, OnCreate.class})
    private String username;

    @NotEmpty(message = "Password mustn't be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 255,
            message = "Password length should be between 3 and 255",
            groups = {OnUpdate.class, OnCreate.class})
    @JsonProperty(access = WRITE_ONLY)
    private String password;
}
