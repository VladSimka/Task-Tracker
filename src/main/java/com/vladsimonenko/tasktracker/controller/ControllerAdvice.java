package com.vladsimonenko.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String a(Exception e) {
        return "ТЫ НЕ МОЖЕШЬ ЭТОГО СДЕЛАТЬ! " + e.getMessage();
    }
}
