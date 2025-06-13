package com.insurancebanking.platform.account.exception;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException(String accountNumber) {
        super("Account with number " + accountNumber + " not found.");
    }
}
