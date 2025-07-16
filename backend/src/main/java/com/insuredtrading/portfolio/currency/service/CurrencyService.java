package com.insuredtrading.portfolio.currency.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insuredtrading.portfolio.currency.dto.CurrencyResponse;

/**
 * Service handling currency-related operations such as supported currency checks
 * and retrieving the list of supported currencies with details.
 */
@Service
public class CurrencyService {

    private final List<String> supportedCurrencies = List.of(
        "EUR", "USD", "GBP"
    );

    /**
     * Checks if the given currency code is supported.
     *
     * @param currencyCode the ISO 4217 currency code to check
     * @return true if the currency is supported, false otherwise
     */
    public boolean isCurrencySupported(String currencyCode) {
        return supportedCurrencies.contains(currencyCode);
    }

    /**
     * Retrieves the list of supported currencies with their details.
     *
     * @return list of CurrencyResponse representing supported currencies
     */
    public List<CurrencyResponse> getList() {
        return supportedCurrencies.stream()
            .map(CurrencyResponse::from)
            .toList();
    }
}
