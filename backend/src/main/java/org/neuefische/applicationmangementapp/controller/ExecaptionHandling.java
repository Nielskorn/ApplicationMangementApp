package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.execaptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.ErrorMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice

public class ExecaptionHandling {
    @ExceptionHandler(NoSuchId.class)
    public ErrorMessage handelNoSuchId(Exception exception){
        return new ErrorMessage(exception.getMessage(), Instant.now());
    }
}
