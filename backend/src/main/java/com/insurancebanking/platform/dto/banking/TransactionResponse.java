package com.insurancebanking.platform.dto.banking;

import java.math.BigDecimal;
import java.util.UUID;

import com.insurancebanking.platform.model.Transaction;

public class TransactionResponse {
    // Constructors
    public TransactionResponse() {}

    // Fields
    private UUID id;
    private BigDecimal amount;
    private String type;
    private String description;
    private BigDecimal balanceAfter;
    private String sourceAccountNumber;
    private String sourceUserEmail;
    private String targetAccountNumber;
    private String targetUserEmail;

    // Response static builder
    public static TransactionResponse from(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setDescription(transaction.getDescription());
        response.setBalanceAfter(transaction.getBalanceAfter());
        response.setSourceAccountNumber(transaction.getSourceAccount().getAccountNumber());
        response.setSourceUserEmail(transaction.getSourceAccount().getUser().getEmail());
        response.setTargetAccountNumber(transaction.getTargetAccount().getAccountNumber());
        response.setTargetUserEmail(transaction.getTargetAccount().getUser().getEmail());

        return response;
    }

    // Getters
    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public String getSourceAccountNumber() { return sourceAccountNumber; }
    public String getSourceUserEmail() { return sourceUserEmail; }
    public String getTargetAccountNumber() { return targetAccountNumber; }
    public String getTargetUserEmail() { return targetUserEmail; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }
    public void setSourceAccountNumber(String sourceAccountNumber) { this.sourceAccountNumber = sourceAccountNumber; }
    public void setSourceUserEmail(String sourceUserEmail) { this.sourceUserEmail = sourceUserEmail; }
    public void setTargetAccountNumber(String targetAccountNumber) { this.targetAccountNumber = targetAccountNumber; }
    public void setTargetUserEmail(String targetUserEmail) { this.targetUserEmail = targetUserEmail; }
}