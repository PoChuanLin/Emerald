package net.pclin.emerald.controller;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}
