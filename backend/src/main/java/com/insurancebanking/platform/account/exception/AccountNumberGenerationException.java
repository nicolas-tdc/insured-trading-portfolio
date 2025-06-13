package com.insurancebanking.platform.account.exception;

public class AccountNumberGenerationException extends RuntimeException {
    public AccountNumberGenerationException(int attempts) {
        super("Failed to generate unique account number after " + attempts + " attempts.");
    }
}
