package com.insurancebanking.platform.account.model;

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
            throw new IllegalArgumentException("Invalid account type code: " + code);
        }
    }
}