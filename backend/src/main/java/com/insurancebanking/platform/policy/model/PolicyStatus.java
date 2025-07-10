package com.insurancebanking.platform.policy.model;

public enum PolicyStatus {
    PENDING("Pending"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String formattedName;

    PolicyStatus(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}