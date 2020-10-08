package net.nextgen.emerald.controller;

import lombok.Value;

@Value
public class Violation {
    private final String fieldName;
    private final String message;
}
