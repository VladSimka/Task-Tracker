package com.vladsimonenko.tasktracker.mapper;

import com.vladsimonenko.tasktracker.dto.UserDto;
import com.vladsimonenko.tasktracker.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
