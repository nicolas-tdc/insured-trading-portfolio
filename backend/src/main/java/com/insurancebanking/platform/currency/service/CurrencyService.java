package com.insurancebanking.platform.currency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.insurancebanking.platform.currency.model.Currency;
import com.insurancebanking.platform.currency.repository.CurrencyRepository;

public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
}
