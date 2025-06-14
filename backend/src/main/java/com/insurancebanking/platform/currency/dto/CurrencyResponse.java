package com.insurancebanking.platform.currency.dto;

import java.util.Currency;

public record CurrencyResponse(
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits
) {
    public static CurrencyResponse from(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);

        return new CurrencyResponse(
            currencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits()
        );
    }
}