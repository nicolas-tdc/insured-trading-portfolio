package com.insurancebanking.platform.policy.dto;

import java.util.UUID;

import com.insurancebanking.platform.policy.model.PolicyType;

import jakarta.validation.constraints.NotNull;

public record PolicyRequest(
    @NotNull(message = "Account ID is required")
    UUID accountId,

    @NotNull(message = "Policy type is required")
    String typeCode,

    @NotNull(message = "Coverage amount is required")
    Double coverageAmount
) {}
