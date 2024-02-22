package com.vladsimonenko.tasktracker.mapper;

import com.vladsimonenko.tasktracker.dto.TaskDto;
import com.vladsimonenko.tasktracker.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}
