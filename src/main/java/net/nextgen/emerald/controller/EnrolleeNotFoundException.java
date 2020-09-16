package net.nextgen.emerald.controller;

public class EnrolleeNotFoundException extends RuntimeException {
    EnrolleeNotFoundException(Long id) {
        super("Could not find enrollee " + id);
    }
}
