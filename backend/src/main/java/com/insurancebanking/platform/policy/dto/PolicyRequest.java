package com.insurancebanking.platform.policy.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * PolicyRequest
 *
 * Data Transfer Object (DTO) for incoming requests to create a new policy.
 * Validates required fields such as account ID, policy type code, and coverage amount.
 */
public record PolicyRequest(
    @NotNull(message = "Account ID is required")
    UUID accountId,

    @NotNull(message = "Policy type is required")
    String typeCode,

    @NotNull(message = "Coverage amount is required")
    Double coverageAmount
) {}
