package com.insuredtrading.portfolio.policy.model;

/**
 * PolicyStatus
 *
 * Enum representing the status of an insurance policy.
 * Each status has a human-readable formatted name for display purposes.
 */
public enum PolicyStatus {
    PENDING("Pending"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String formattedName; // Human-readable name for the status

    /**
     * Constructor assigning the formatted display name.
     *
     * @param formattedName human-readable name of the policy status
     */
    PolicyStatus(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Retrieves the formatted display name of the policy status.
     *
     * @return formatted name string
     */
    public String getFormattedName() {
        return formattedName;
    }
}
