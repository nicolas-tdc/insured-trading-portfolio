package com.insurancebanking.platform.policy.dto;

import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import com.insurancebanking.platform.policy.model.Policy;

public record PolicyResponse(
    UUID id,
    String statusCode,
    String statusDisplayName,
    String typeCode,
    String typeDisplayName,
    String accountNumber,
    String policyNumber,
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits,
    Double premium,
    Double coverageAmount,
    Instant startDate,
    Instant endDate
) {
    public static PolicyResponse from(Policy policy) {
        String policyCurrencyCode = policy.getCurrencyCode();
        Currency currency = Currency.getInstance(policyCurrencyCode);

        return new PolicyResponse(
            policy.getId(),
            policy.getPolicyStatus().name(),
            policy.getPolicyStatus().getFormattedName(),
            policy.getPolicyType().name(),
            policy.getPolicyType().getFormattedName(),
            policy.getAccount().getAccountNumber(),
            policy.getPolicyNumber(),
            policyCurrencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits(),
            policy.getPremium(),
            policy.getCoverageAmount(),
            policy.getStartDate(),
            policy.getEndDate()
        );
    }
}