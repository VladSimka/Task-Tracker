package com.vladsimonenko.tasktracker.dto;

import com.vladsimonenko.tasktracker.model.Status;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;

    private String title;

    private String description;

    private Status status;

    private Timestamp startTime;

    private Timestamp plannedEndTime;
}
