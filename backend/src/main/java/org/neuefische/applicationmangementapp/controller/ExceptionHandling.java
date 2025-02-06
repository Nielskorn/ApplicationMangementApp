package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice

public class ExceptionHandling {

    @ExceptionHandler(NoSuchId.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handelNoSuchId(Exception exception) {
        return new ErrorMessage(exception.getMessage(), Instant.now());
    }
}
