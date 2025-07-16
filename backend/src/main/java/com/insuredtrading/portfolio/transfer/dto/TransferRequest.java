package com.insuredtrading.portfolio.transfer.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO representing a request to create a transfer between accounts.
 *
 * @param sourceAccountId     UUID of the source account from which funds will be withdrawn; must not be null.
 * @param targetAccountNumber Account number of the target account to receive funds; must not be null.
 * @param amount              Amount to transfer; must be positive.
 * @param description         Optional textual description for the transfer.
 */
public record TransferRequest (
    @NotNull(message = "Source account is required")
    UUID sourceAccountId,

    @NotNull(message = "Target account is required")
    String targetAccountNumber,

    @Positive(message = "Amount must be positive")
    BigDecimal amount,

    String description
) {}
