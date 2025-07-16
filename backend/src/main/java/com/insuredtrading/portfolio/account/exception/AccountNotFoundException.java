package com.insuredtrading.portfolio.account.exception;

import java.util.UUID;

/**
 * AccountNotFoundException
 *
 * Exception thrown when an account with the specified ID
 * is not found for the current user.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Constructs a new AccountNotFoundException with a message
     * indicating the missing account's ID.
     *
     * @param accountId ID of the account that was not found
     */
    public AccountNotFoundException(UUID accountId) {
        super("Account with ID " + accountId + " not found for the user.");
    }
}
