package ru.nsu.g20202.nmatus.medicalorg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = {IllegalValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> IllegalValueExceptionHandler(IllegalValueException ex) {
        return ex.getMessages();
    }
}
