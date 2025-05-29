package com.insurancebanking.platform.core.dto;

public class MessageResponse {

    // Constructors

    public MessageResponse() {}
    public MessageResponse(String message) {
        this.message = message;
    }

    // Properties

    private String message;

    // Getters

    public String getMessage() { return message; }

    // Setters

    public void setMessage(String message) { this.message = message; }
}