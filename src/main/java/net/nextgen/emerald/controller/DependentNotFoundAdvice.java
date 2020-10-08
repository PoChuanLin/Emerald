package net.nextgen.emerald.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.nextgen.emerald.service.DependentNotFoundException;

@ControllerAdvice
public class DependentNotFoundAdvice {

    @ExceptionHandler (DependentNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onDependentNotFoundException (DependentNotFoundException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add (new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
        return error;
    }
}
