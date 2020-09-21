package net.nextgen.emerald.controller;

import net.nextgen.emerald.service.EnrolleeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EnrolleeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EnrolleeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String enrolleeNotFoundHandler(EnrolleeNotFoundException ex) {
        return ex.getMessage();
    }
}
