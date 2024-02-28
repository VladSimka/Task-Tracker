package com.vladsimonenko.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladsimonenko.tasktracker.dto.validator.OnCreate;
import com.vladsimonenko.tasktracker.dto.validator.OnUpdate;
import com.vladsimonenko.tasktracker.model.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {

    @NotNull(message = "Id mustn't be null",
            groups = OnUpdate.class)
    private Long id;

    @NotEmpty(message = "Title mustn't be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 255,
            message = "Title length should be between 3 and 255",
            groups = {OnUpdate.class, OnCreate.class})
    private String title;

    @NotEmpty(message = "Description mustn't be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 255,
            message = "Description length should be between 3 and 255",
            groups = {OnUpdate.class, OnCreate.class})
    private String description;

    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp plannedEndTime;
}
