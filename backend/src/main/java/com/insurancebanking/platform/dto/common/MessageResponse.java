package com.insurancebanking.platform.dto.common;

public class MessageResponse {
    // Constructors
    public MessageResponse() {}
    public MessageResponse(String message) {
        this.message = message;
    }

    // Fields
    private String message;

    // Getters
    public String getMessage() { return message; }

    // Setters
    public void setMessage(String message) { this.message = message; }
}