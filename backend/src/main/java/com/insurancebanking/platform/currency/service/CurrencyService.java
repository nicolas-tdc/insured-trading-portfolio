package com.insurancebanking.platform.currency.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insurancebanking.platform.currency.dto.CurrencyResponse;

@Service
public class CurrencyService {

    private final List<String> supportedCurrencies = List.of(
        "EUR", "USD", "GBP"
    );

    public List<CurrencyResponse> getList() {
        return supportedCurrencies.stream()
            .map(CurrencyResponse::from)
            .toList();
    }
}