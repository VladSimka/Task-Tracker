package com.vladsimonenko.tasktracker.config;

import com.vladsimonenko.tasktracker.security.JwtEntity;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;


@TestConfiguration
public class SecurityTestConfig {

    @Bean("userDetailsService")
    @Primary
    public UserDetailsService userDetailsService() {
        JwtEntity user1 = new JwtEntity(1L,
                "mike",
                "12345",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        JwtEntity user2 = new JwtEntity(2L,
                "ivan",
                "12345",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));


        return new InMemoryUserDetailsManager(Arrays.asList(user1, user2));
    }

}
