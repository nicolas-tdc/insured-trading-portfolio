package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record AccountResponse(
    UUID id,
    AccountStatus accountStatus,
    AccountType accountType,
    String accountNumber,
    BigDecimal balance,
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits
) {
    public static AccountResponse from(Account account) {
        String currencyCode = account.getCurrencyCode();
        Currency currency = Currency.getInstance(currencyCode);

        return new AccountResponse(
            account.getId(),
            account.getAccountStatus(),
            account.getAccountType(),
            account.getAccountNumber(),
            account.getBalance(),
            currencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits()
        );
    }

}
