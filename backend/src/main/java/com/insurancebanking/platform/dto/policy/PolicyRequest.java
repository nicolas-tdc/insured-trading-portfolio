package com.insurancebanking.platform.dto.policy;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class PolicyRequest {

    // Properties

    @NotNull(message = "Account ID is required")
    private UUID accountId;

    @NotNull(message = "Type is required")
    private String type;

    @NotNull(message = "Coverage amount is required")
    private Double coverageAmount;

    // Getters

    public UUID getAccountId() { return accountId; }
    public String getType() { return type; }
    public Double getCoverageAmount() { return coverageAmount; }

    // Setters

    public void setAccountId(UUID accountId) { this.accountId = accountId; }
    public void setType(String type) { this.type = type; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }
}
