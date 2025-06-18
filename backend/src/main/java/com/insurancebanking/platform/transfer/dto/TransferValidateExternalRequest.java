package com.insurancebanking.platform.transfer.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record TransferValidateExternalRequest (
    @NotNull(message = "Source account is required")
    UUID sourceAccountId,

    @NotNull(message = "Target account is required")
    String targetAccountNumber
) {}