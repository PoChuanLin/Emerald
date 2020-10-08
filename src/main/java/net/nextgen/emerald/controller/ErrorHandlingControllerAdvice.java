package net.nextgen.emerald.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.nextgen.emerald.service.DependentNotFoundException;
import net.nextgen.emerald.service.EnrolleeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    
    @ExceptionHandler (ConstraintViolationException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException (ConstraintViolationException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.getViolations().add (new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    @ExceptionHandler (EnrolleeNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onEnrolleeNotFoundException (EnrolleeNotFoundException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add (new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
        return error;
    }

    @ExceptionHandler (DependentNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onDependentNotFoundException (DependentNotFoundException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add (new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
        return error;
    }
}
