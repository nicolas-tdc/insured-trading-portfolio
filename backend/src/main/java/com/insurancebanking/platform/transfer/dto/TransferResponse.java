package com.insurancebanking.platform.transfer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.insurancebanking.platform.transfer.model.Transfer;

public class TransferResponse {

    // Constructors

    public TransferResponse() {}

    // Properties

    private UUID id;
    private String transferNumber;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private String description;
    private String sourceAccountNumber;
    private String sourceUserEmail;
    private String targetAccountNumber;
    private String targetUserEmail;

    // Response static builder

    public static TransferResponse from(Transfer transfer) {
        TransferResponse response = new TransferResponse();

        response.setId(transfer.getId());
        response.setTransferNumber(transfer.getTransferNumber());
        response.setcreatedAt(transfer.getCreatedAt());
        response.setAmount(transfer.getAmount());
        response.setDescription(transfer.getDescription());
        response.setSourceAccountNumber(transfer.getSourceAccount().getAccountNumber());
        response.setSourceUserEmail(transfer.getSourceAccount().getUser().getEmail());
        response.setTargetAccountNumber(transfer.getTargetAccount().getAccountNumber());
        response.setTargetUserEmail(transfer.getTargetAccount().getUser().getEmail());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public String getTransferNumber() { return transferNumber; }
    public LocalDateTime getcreatedAt() { return createdAt; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getSourceAccountNumber() { return sourceAccountNumber; }
    public String getSourceUserEmail() { return sourceUserEmail; }
    public String getTargetAccountNumber() { return targetAccountNumber; }
    public String getTargetUserEmail() { return targetUserEmail; }

    // Setters

    public void setId(UUID value) { this.id = value; }
    public void setTransferNumber(String value) { this.transferNumber = value; }
    public void setcreatedAt(LocalDateTime value) { this.createdAt = value; }
    public void setAmount(BigDecimal value) { this.amount = value; }
    public void setDescription(String value) { this.description = value; }
    public void setSourceAccountNumber(String value) { this.sourceAccountNumber = value; }
    public void setSourceUserEmail(String value) { this.sourceUserEmail = value; }
    public void setTargetAccountNumber(String value) { this.targetAccountNumber = value; }
    public void setTargetUserEmail(String value) { this.targetUserEmail = value; }
}