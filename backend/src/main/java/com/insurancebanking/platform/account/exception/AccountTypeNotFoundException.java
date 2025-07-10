package com.insurancebanking.platform.account.exception;

public class AccountTypeNotFoundException extends RuntimeException {
    public AccountTypeNotFoundException(String accountType) {
        super("Account type " + accountType + " not found.");
    }
}