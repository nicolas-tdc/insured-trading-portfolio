package com.insurancebanking.platform.transfer.exception;

/**
 * Exception thrown when a transfer validation fails.
 */
public class TransferValidationException extends RuntimeException {
    
    /**
     * Constructs a new TransferValidationException with the specified detail message.
     *
     * @param message the detail message explaining the validation failure
     */
    public TransferValidationException(String message) {
        super(message);
    }
}
