package com.user.api.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerClass {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,String> handelInvalidException(ConstraintViolationException e){
        Map<String,String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(ex->{
            errors.put(String.valueOf(ex.getPropertyPath()),ex.getMessageTemplate());
        });

        return errors;
    }
}
