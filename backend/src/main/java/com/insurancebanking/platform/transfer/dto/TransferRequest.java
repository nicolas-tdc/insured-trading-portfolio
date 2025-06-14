package com.insurancebanking.platform.transfer.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequest (
    @NotNull(message = "Source account is required")
    UUID sourceAccountId,

    @NotNull(message = "Target account is required")
    String targetAccountNumber,

    @Positive(message = "Amount must be positive")
    BigDecimal amount,

    String description
) {}