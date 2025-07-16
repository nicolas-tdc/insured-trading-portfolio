package com.insuredtrading.portfolio.account.model;

/**
 * AccountStatus
 *
 * Enumeration representing possible statuses of an account,
 * each with a user-friendly display name for UI representation.
 */
public enum AccountStatus {
    
    /** Account is newly created and pending activation. */
    PENDING("Pending"),
    
    /** Account is active and available for use. */
    ACTIVE("Active"),
    
    /** Account is inactive and not currently usable. */
    INACTIVE("Inactive");

    private final String formattedName;

    /**
     * Constructor assigning the display name for the status.
     *
     * @param formattedName human-readable status name
     */
    AccountStatus(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Returns the display name of the account status.
     *
     * @return formatted status name
     */
    public String getFormattedName() {
        return formattedName;
    }
}
