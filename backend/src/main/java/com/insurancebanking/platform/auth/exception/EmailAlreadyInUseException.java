package com.insurancebanking.platform.auth.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email " + email + " is already in use.");
    }
}
