package com.insuredtrading.portfolio.policy.dto;

import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import com.insuredtrading.portfolio.policy.model.Policy;

/**
 * PolicyResponse
 *
 * Data Transfer Object (DTO) representing the detailed information
 * of a policy to be sent in API responses.
 */
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
    /**
     * Creates a PolicyResponse DTO from a Policy entity.
     * Converts domain model attributes into a format suitable
     * for API responses, including currency details.
     *
     * @param policy the Policy entity to convert
     * @return a new PolicyResponse containing policy details
     */
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
