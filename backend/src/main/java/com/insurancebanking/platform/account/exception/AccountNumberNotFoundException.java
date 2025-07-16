package com.insurancebanking.platform.account.exception;

/**
 * AccountNumberNotFoundException
 *
 * Exception thrown when an account with the specified account number
 * cannot be found in the system.
 */
public class AccountNumberNotFoundException extends RuntimeException {

    /**
     * Constructs a new AccountNumberNotFoundException with a message
     * indicating the missing account number.
     *
     * @param accountNumber the account number that was not found
     */
    public AccountNumberNotFoundException(String accountNumber) {
        super("Account with number " + accountNumber + " not found.");
    }
}
