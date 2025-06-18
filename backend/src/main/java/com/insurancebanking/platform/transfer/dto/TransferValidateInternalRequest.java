package com.insurancebanking.platform.transfer.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record TransferValidateInternalRequest (
    @NotNull(message = "Source account is required")
    UUID sourceAccountId,

    @NotNull(message = "Target account is required")
    UUID targetAccountId
) {}