package com.insurancebanking.platform.core.dto;

/**
 * Simple DTO class for returning message responses in API calls.
 * Typically used to wrap a single message string for success or error responses.
 */
public class MessageResponse {

    private String message;

    /**
     * Default constructor.
     */
    public MessageResponse() {}

    /**
     * Constructs a MessageResponse with the given message.
     * 
     * @param message the message content
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     * 
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * 
     * @param message the message string to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
