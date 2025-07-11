package com.insurancebanking.platform.transfer.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.Currency;

import com.insurancebanking.platform.transfer.model.Transfer;

public record TransferResponse (
    UUID id,
    String transferNumber,
    String statusCode,
    String statusDisplayName,
    Instant createdAt,
    BigDecimal amount,
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits,
    String description,
    String sourceAccountNumber,
    String sourceUserEmail,
    String targetAccountNumber,
    String targetUserEmail
) {
    // Response static builder

    public static TransferResponse from(Transfer transfer) {
        String transferCurrencyCode = transfer.getCurrencyCode();
        Currency currency = Currency.getInstance(transferCurrencyCode);

        return new TransferResponse(
            transfer.getId(),
            transfer.getTransferNumber(),
            transfer.getTransferStatus().name(),
            transfer.getTransferStatus().getFormattedName(),
            transfer.getCreatedAt(),
            transfer.getAmount(),
            transferCurrencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits(),
            transfer.getDescription(),
            transfer.getSourceAccount().getAccountNumber(),
            transfer.getSourceAccount().getUser().getEmail(),
            transfer.getTargetAccount().getAccountNumber(),
            transfer.getTargetAccount().getUser().getEmail()
        );
    }
}