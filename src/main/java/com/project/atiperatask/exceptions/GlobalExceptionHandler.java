package com.project.atiperatask.exceptions;

import com.project.atiperatask.models.ErrorResp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResp userNotFoundException(UserNotFoundException e) {
        return new ErrorResp(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
