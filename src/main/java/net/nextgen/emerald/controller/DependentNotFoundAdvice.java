package net.nextgen.emerald.controller;

import net.nextgen.emerald.service.DependentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DependentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DependentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String dependentNotFoundHandler(DependentNotFoundException ex) {
        return ex.getMessage();
    }
}
