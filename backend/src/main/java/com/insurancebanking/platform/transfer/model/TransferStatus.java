package com.insurancebanking.platform.transfer.model;

/**
 * Enumeration representing the possible statuses of a transfer.
 */
public enum TransferStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    REJECTED("Rejected");

    private final String formattedName;

    TransferStatus(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Returns the formatted, user-friendly name of the transfer status.
     *
     * @return formatted name as String
     */
    public String getFormattedName() {
        return formattedName;
    }
}
