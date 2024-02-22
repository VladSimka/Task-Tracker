package com.vladsimonenko.tasktracker.security.expression;

import com.vladsimonenko.tasktracker.model.Role;
import com.vladsimonenko.tasktracker.security.JwtEntity;
import com.vladsimonenko.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.vladsimonenko.tasktracker.model.Role.ROLE_ADMIN;

@Component("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    private final UserService userService;

    public boolean canAccessUser(Long userId) {
        JwtEntity user = getPrincipal();
        Long id = user.getId();

        return id.equals(userId) || hasAnyRole(ROLE_ADMIN);
    }


    public boolean canAccessTask(Long taskId) {
        JwtEntity user = getPrincipal();
        Long userId = user.getId();

        return userService.isTaskOwner(userId, taskId) || hasAnyRole(ROLE_ADMIN);
    }

    private boolean hasAnyRole(Role... roles) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return Arrays.stream(roles).anyMatch(role -> authentication.getAuthorities().contains(new SimpleGrantedAuthority(role.name())));
    }


    private JwtEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }
}
