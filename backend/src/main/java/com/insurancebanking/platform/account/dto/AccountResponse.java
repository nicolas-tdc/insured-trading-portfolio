package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import java.util.List;

public record AccountResponse(
    UUID id,
    String statusCode,
    String statusDisplayName,
    String typeCode,
    String typeDisplayName,
    String accountNumber,
    BigDecimal balance,
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits,
    List<String> policies
) {
    public static AccountResponse from(Account account, List<String> policies) {
        String currencyCode = account.getCurrencyCode();
        Currency currency = Currency.getInstance(currencyCode);

        return new AccountResponse(
            account.getId(),
            account.getAccountStatus().name(),
            account.getAccountStatus().getFormattedName(),
            account.getAccountType().name(),
            account.getAccountType().getFormattedName(),
            account.getAccountNumber(),
            account.getBalance(),
            currencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits(),
            policies
        );
    }

}
