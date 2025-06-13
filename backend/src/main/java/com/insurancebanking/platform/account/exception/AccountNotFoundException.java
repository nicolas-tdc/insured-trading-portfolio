package com.insurancebanking.platform.account.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(UUID accountId) {
        super("Account with ID " + accountId + " not found for the user.");
    }
}
