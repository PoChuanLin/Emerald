package net.nextgen.emerald.controller;

public class EnrolleeNotFoundException extends RuntimeException {
    EnrolleeNotFoundException(Long id) {
        super("Cound not find enrollee "+id);
    }
}
