package com.insurancebanking.platform.transfer.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for validating external transfer requests where the target account is identified by account number.
 *
 * @param sourceAccountId    the UUID of the source account (must not be null)
 * @param targetAccountNumber the account number of the target account (must not be null)
 */
public record TransferValidateExternalRequest (
    @NotNull(message = "Source account is required")
    UUID sourceAccountId,

    @NotNull(message = "Target account is required")
    String targetAccountNumber
) {}
