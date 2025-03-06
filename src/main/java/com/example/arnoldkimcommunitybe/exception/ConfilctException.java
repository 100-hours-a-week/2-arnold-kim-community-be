package com.example.arnoldkimcommunitybe.exception;

import java.util.Map;

public class ConfilctException extends RuntimeException {
    private final Map<String, String> errorDetails;

    public ConfilctException(String message, Map<String, String> errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }
}
