package net.pclin.emerald.service;

public class EnrolleeNotFoundException extends RuntimeException {
    public EnrolleeNotFoundException(Long id) {
        super("Could not find enrollee " + id);
    }
}
