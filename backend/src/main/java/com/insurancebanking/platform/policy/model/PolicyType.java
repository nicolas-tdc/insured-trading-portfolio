package com.insurancebanking.platform.policy.model;

import com.insurancebanking.platform.policy.exception.PolicyTypeNotFoundException;

public enum PolicyType {
    LOSS_PROTECTION("Loss Protection"),
    PERFORMANCE_SHARE("Performance Share"),
    SUBSCRIPTION_BASED("Subscription Based"),
    CAPITAL_GUARANTEE("Capital Guarantee"),
    VOLATILITY_INSURANCE("Volatility Insurance"),
    EVENT_INSURANCE("Event Insurance");

    private final String formattedName;

    PolicyType(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public static PolicyType fromCode(String code) {
        try {
            return PolicyType.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new PolicyTypeNotFoundException(code);
        }
    }
}