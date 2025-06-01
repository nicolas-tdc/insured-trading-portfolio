package com.insurancebanking.platform.policy.dto;

import java.util.UUID;

import com.insurancebanking.platform.policy.model.PolicyType;

import jakarta.validation.constraints.NotNull;

public class PolicyRequest {

    // Properties

    @NotNull(message = "Account ID is required")
    private UUID accountId;

    @NotNull(message = "Type is required")
    private PolicyType policyType;

    @NotNull(message = "Coverage amount is required")
    private Double coverageAmount;

    // Getters

    public UUID getAccountId() { return accountId; }
    public PolicyType getPolicyType() { return policyType; }
    public Double getCoverageAmount() { return coverageAmount; }

    // Setters

    public void setAccountId(UUID value) { this.accountId = value; }
    public void setPolicyType(PolicyType value) { this.policyType = value; }
    public void setCoverageAmount(Double value) { this.coverageAmount = value; }
}
