package com.insurancebanking.platform.policy.dto;

import com.insurancebanking.platform.policy.model.PolicyType;

public record PolicyTypeResponse(
    String code,
    String displayName
) {
    public static PolicyTypeResponse from(PolicyType policyType) {
        return new PolicyTypeResponse(
            policyType.name(),
            policyType.getFormattedName()
        );
    }
}