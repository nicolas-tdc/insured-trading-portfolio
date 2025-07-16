package com.insuredtrading.portfolio.account.exception;

/**
 * AccountNumberGenerationException
 *
 * Exception thrown when the system fails to generate
 * a unique account number after multiple attempts.
 */
public class AccountNumberGenerationException extends RuntimeException {

    /**
     * Constructs a new AccountNumberGenerationException with a message
     * indicating the number of failed attempts.
     *
     * @param attempts number of attempts made to generate the account number
     */
    public AccountNumberGenerationException(int attempts) {
        super("Failed to generate unique account number after " + attempts + " attempts.");
    }
}
