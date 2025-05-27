package com.insurancebanking.platform.dto.transfer;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequest {

    // Properties

    @NotNull(message = "Source account is required")
    private UUID sourceAccountId;

    @NotNull(message = "Target account is required")
    private String targetAccountNumber;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;

    // Getters

    public UUID getSourceAccountId() { return sourceAccountId; }
    public String getTargetAccountNumber() { return targetAccountNumber; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }

    // Setters

    public void setSourceAccountId(UUID value) { this.sourceAccountId = value; }
    public void setTargetAccountNumber(String value) { this.targetAccountNumber = value; }
    public void setAmount(BigDecimal value) { this.amount = value; }
    public void setDescription(String value) { this.description = value; }
}