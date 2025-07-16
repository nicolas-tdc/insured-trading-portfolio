package com.insurancebanking.platform.currency.dto;

import java.util.Currency;

/**
 * DTO representing currency details including code, symbol, and fraction digits.
 *
 * @param currencyCode the ISO 4217 currency code
 * @param currencySymbol the symbol associated with the currency
 * @param currencyFractionDigits the default number of fraction digits for the currency
 */
public record CurrencyResponse(
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits
) {
    /**
     * Creates a CurrencyResponse from a currency code.
     *
     * @param currencyCode the ISO 4217 currency code
     * @return a new CurrencyResponse populated from the Currency instance
     */
    public static CurrencyResponse from(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);

        return new CurrencyResponse(
            currencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits()
        );
    }
}
