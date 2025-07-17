package com.insuredtrading.portfolio.auth.exception;

/**
 * Exception thrown when attempting to register with an email that is already in use.
 *
 * @param email the email address that caused the conflict
 */
public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super(email);
    }
}
