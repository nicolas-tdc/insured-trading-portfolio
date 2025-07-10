package com.insurancebanking.platform.account.model;

/**
 * Account status with display name for UI.
 */
public enum AccountStatus {
    PENDING("Pending"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String formattedName;

    AccountStatus(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
