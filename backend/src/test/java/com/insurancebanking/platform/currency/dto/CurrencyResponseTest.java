package com.insurancebanking.platform.currency.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CurrencyResponseTest {
    
    @Test
    public void from_shouldMapAllFieldsCorrectly() {
        CurrencyResponse response = CurrencyResponse.from("EUR");
        assertThat(response.currencyCode()).isEqualTo("EUR");
        assertThat(response.currencySymbol()).isEqualTo("€");
        assertThat(response.currencyFractionDigits()).isEqualTo(2);
    }
}
