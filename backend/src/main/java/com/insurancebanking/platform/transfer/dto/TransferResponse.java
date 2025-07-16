package com.insurancebanking.platform.transfer.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.Currency;

import com.insurancebanking.platform.transfer.model.Transfer;

/**
 * DTO representing detailed information about a transfer.
 *
 * @param id                   the unique identifier of the transfer
 * @param transferNumber       the public transfer number
 * @param statusCode           the status code of the transfer (e.g., PENDING, COMPLETED)
 * @param statusDisplayName    the human-readable status name
 * @param createdAt            the timestamp when the transfer was created
 * @param amount               the amount transferred
 * @param currencyCode         the ISO currency code for the transfer amount
 * @param currencySymbol       the currency symbol corresponding to the currency code
 * @param currencyFractionDigits the number of fraction digits for the currency
 * @param description          optional description for the transfer
 * @param sourceAccountNumber  account number of the source account
 * @param sourceUserEmail      email of the user owning the source account
 * @param targetAccountNumber  account number of the target account
 * @param targetUserEmail      email of the user owning the target account
 */
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
    /**
     * Creates a TransferResponse DTO from a Transfer entity.
     *
     * @param transfer the Transfer entity to convert
     * @return a TransferResponse containing transfer details and metadata
     */
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
