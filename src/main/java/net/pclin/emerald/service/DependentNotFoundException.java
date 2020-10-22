package net.pclin.emerald.service;

public class DependentNotFoundException extends RuntimeException {
    public DependentNotFoundException(Long id) {
        super("Could not find dependent " + id);
    }
}
