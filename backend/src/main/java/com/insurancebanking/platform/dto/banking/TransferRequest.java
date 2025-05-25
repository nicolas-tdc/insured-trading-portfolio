package com.insurancebanking.platform.dto.banking;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequest {
    // Fields
    @NotNull(message = "Source account is required")
    private String sourceAccountId;

    @NotNull(message = "Target account is required")
    private String targetAccountNumber;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    // Getters
    public String getSourceAccountId() { return sourceAccountId; }
    public String getTargetAccountNumber() { return targetAccountNumber; }
    public BigDecimal getAmount() { return amount; }

    // Setters
    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }
    public void setTargetAccountNumber(String targetAccountNumber) { this.targetAccountNumber = targetAccountNumber; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}