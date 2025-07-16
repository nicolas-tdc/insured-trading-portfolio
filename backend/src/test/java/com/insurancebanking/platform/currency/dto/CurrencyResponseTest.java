package com.insurancebanking.platform.currency.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link CurrencyResponse}.
 * 
 * Tests mapping from currency code to the response DTO including symbol and fraction digits.
 */
public class CurrencyResponseTest {
    
    /**
     * Verifies that the from method correctly maps
     * the currency code, symbol, and fraction digits.
     */
    @Test
    public void from_shouldMapAllFieldsCorrectly() {
        CurrencyResponse response = CurrencyResponse.from("EUR");
        assertThat(response.currencyCode()).isEqualTo("EUR");
        assertThat(response.currencySymbol()).isEqualTo("€");
        assertThat(response.currencyFractionDigits()).isEqualTo(2);
    }
}
