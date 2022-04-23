package com.example.recipes.config;

import com.example.recipes.model.error.BaseExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String NEW_RECIPE_PATH = "/api/recipe/new";
    public static final String BAD_REQUEST_ERROR = "Bad Request";

    @ExceptionHandler
    ResponseEntity<BaseExceptionBody> handleConstraintViolationException(ConstraintViolationException exception) {
        BaseExceptionBody baseExceptionBody = new BaseExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST_ERROR,
                NEW_RECIPE_PATH
        );
        return new ResponseEntity<>(baseExceptionBody, HttpStatus.BAD_REQUEST);
    }
}
