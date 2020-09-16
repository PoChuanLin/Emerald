package net.nextgen.emerald.controller;

public class DependentNotFoundException extends RuntimeException {
    DependentNotFoundException(Long id) {
        super("Could not find dependent " + id);
    }
}
