package com.insuredtrading.portfolio.transfer.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for validating internal transfer requests between two user-owned accounts.
 *
 * @param sourceAccountId the UUID of the source account (must not be null)
 * @param targetAccountId the UUID of the target account (must not be null)
 */
public record TransferValidateInternalRequest (
    @NotNull(message = "Source account is required")
    UUID sourceAccountId,

    @NotNull(message = "Target account is required")
    UUID targetAccountId
) {}
