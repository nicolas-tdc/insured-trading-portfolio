package com.insuredtrading.portfolio.transfer.exception;

/**
 * Exception thrown when transfer creation fails due to internal errors.
 */
public class TransferCreationException extends RuntimeException {

    /**
     * Constructs a new TransferCreationException with the specified detail message.
     *
     * @param message the detail message
     */
    public TransferCreationException(String message) {
        super(message);
    }
}
