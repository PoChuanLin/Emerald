package net.nextgen.emerald.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.nextgen.emerald.service.DependentNotFoundException;
import net.nextgen.emerald.service.EnrolleeNotFoundException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.getViolations().add (new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(error, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (ConstraintViolationException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse handleConstraintValidationException (ConstraintViolationException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler (EnrolleeNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse handleEnrolleeNotFoundException (EnrolleeNotFoundException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add (new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
        return error;
    }

    @ExceptionHandler (DependentNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse handleDependentNotFoundException (DependentNotFoundException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add (new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
        return error;
    }
}
