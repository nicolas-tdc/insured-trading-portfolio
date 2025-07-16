package com.insuredtrading.portfolio.account.exception;

/**
 * AccountTypeNotFoundException
 *
 * Exception thrown when the specified account type
 * is not found or unsupported by the platform.
 */
public class AccountTypeNotFoundException extends RuntimeException {

    /**
     * Constructs a new AccountTypeNotFoundException with a message
     * indicating the missing account type.
     *
     * @param accountType the account type that was not found
     */
    public AccountTypeNotFoundException(String accountType) {
        super("Account type " + accountType + " not found.");
    }
}
