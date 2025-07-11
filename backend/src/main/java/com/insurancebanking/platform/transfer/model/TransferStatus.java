package com.insurancebanking.platform.transfer.model;

public enum TransferStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    REJECTED("Rejected");

    private final String formattedName;

    TransferStatus(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}