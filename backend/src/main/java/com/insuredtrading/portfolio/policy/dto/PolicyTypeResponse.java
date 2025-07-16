package com.insuredtrading.portfolio.policy.dto;

import com.insuredtrading.portfolio.policy.model.PolicyType;

/**
 * PolicyTypeResponse
 *
 * Data Transfer Object (DTO) representing a policy type
 * with its code and user-friendly display name.
 */
public record PolicyTypeResponse(
    String code,
    String displayName
) {
    /**
     * Creates a PolicyTypeResponse DTO from a PolicyType enum instance.
     *
     * @param policyType the PolicyType enum to convert
     * @return a new PolicyTypeResponse containing the code and formatted name
     */
    public static PolicyTypeResponse from(PolicyType policyType) {
        return new PolicyTypeResponse(
            policyType.name(),
            policyType.getFormattedName()
        );
    }
}
