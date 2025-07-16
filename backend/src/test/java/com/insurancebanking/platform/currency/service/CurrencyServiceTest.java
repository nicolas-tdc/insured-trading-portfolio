package com.insurancebanking.platform.currency.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for {@link CurrencyService}.
 * 
 * Tests verifying the currency support checking functionality.
 */
public class CurrencyServiceTest {
    
    @InjectMocks
    private CurrencyService currencyService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifies that isCurrencySupported returns true for supported currencies.
     */
    @Test
    public void shouldReturnTrueIfCurrencySupported() {
        boolean result = currencyService.isCurrencySupported("EUR");
        assertThat(result).isTrue();
    }

    /**
     * Verifies that isCurrencySupported returns false for unsupported currencies.
     */
    @Test
    public void shouldReturnFalseIfCurrencyNotSupported() {
        boolean result = currencyService.isCurrencySupported("AFN");
        assertThat(result).isFalse();
    }
}
