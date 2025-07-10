package com.insurancebanking.platform.account.model;

import com.insurancebanking.platform.account.exception.AccountTypeNotFoundException;

public enum AccountType {
    CHECKING("Checking"),
    SAVINGS("Savings"),
    TRADING("Trading");

    private final String formattedName;

    AccountType(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public static AccountType fromCode(String code) {
        try {
            return AccountType.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new AccountTypeNotFoundException(code);
        }
    }
}