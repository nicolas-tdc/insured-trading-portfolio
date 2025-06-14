package com.insurancebanking.platform.currency.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CurrencyServiceTest {
    
    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTrueIfCurrencySupported() {
        boolean result = currencyService.isCurrencySupported("EUR");
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfCurrencyNotSupported() {
        boolean result = currencyService.isCurrencySupported("AFN");
        assertThat(result).isFalse();
    }
}
