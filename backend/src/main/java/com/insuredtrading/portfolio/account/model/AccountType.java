package com.insuredtrading.portfolio.account.model;

import com.insuredtrading.portfolio.account.exception.AccountTypeNotFoundException;

/**
 * AccountType
 *
 * Enumeration of supported account types within the platform,
 * each with a user-friendly display name.
 */
public enum AccountType {
    
    /** Checking account type */
    CHECKING("Checking"),
    
    /** Savings account type */
    SAVINGS("Savings"),
    
    /** Trading account type */
    TRADING("Trading");

    private final String formattedName;

    /**
     * Constructor to assign display name for the account type.
     *
     * @param formattedName human-readable name of the account type
     */
    AccountType(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Gets the formatted display name of the account type.
     *
     * @return display name string
     */
    public String getFormattedName() {
        return formattedName;
    }

    /**
     * Converts a string code to the corresponding AccountType enum.
     * Throws AccountTypeNotFoundException if no matching type exists.
     *
     * @param code string representation of the account type
     * @return AccountType enum matching the code
     * @throws AccountTypeNotFoundException if code is invalid
     */
    public static AccountType fromCode(String code) {
        try {
            return AccountType.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new AccountTypeNotFoundException(code);
        }
    }
}
