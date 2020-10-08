package net.nextgen.emerald.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.nextgen.emerald.service.EnrolleeNotFoundException;

@ControllerAdvice
public class EnrolleeNotFoundAdvice {

    @ExceptionHandler (EnrolleeNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onEnrolleeNotFoundException (EnrolleeNotFoundException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add (new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
        return error;
    }
}
