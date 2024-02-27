package com.vladsimonenko.tasktracker.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreatedEvent {
    private Long id;

    private String username;
}
