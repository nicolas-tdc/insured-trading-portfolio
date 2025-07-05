package com.insurancebanking.platform.policy.dto;

import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;
import com.insurancebanking.platform.policy.model.PolicyStatus;

public record PolicyResponse(
    UUID id,
    PolicyStatus policyStatus,
    String accountNumber,
    String policyNumber,
    String currencyCode,
    String currencySymbol,
    int currencyFractionDigits,
    PolicyType policyType,
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
            policy.getPolicyStatus(),
            policy.getAccount().getAccountNumber(),
            policy.getPolicyNumber(),
            policyCurrencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits(),
            policy.getPolicyType(),
            policy.getPremium(),
            policy.getCoverageAmount(),
            policy.getStartDate(),
            policy.getEndDate()
        );
    }
}