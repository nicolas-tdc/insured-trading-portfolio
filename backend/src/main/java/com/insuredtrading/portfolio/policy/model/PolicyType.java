package com.insuredtrading.portfolio.policy.model;

import com.insuredtrading.portfolio.policy.exception.PolicyTypeNotFoundException;

/**
 * PolicyType
 *
 * Enum representing the various types of insurance policies supported by the platform.
 * Each type has a human-readable formatted name.
 * Provides a method to parse an enum from its string code with error handling.
 */
public enum PolicyType {
    LOSS_PROTECTION("Loss Protection"),
    PERFORMANCE_SHARE("Performance Share"),
    SUBSCRIPTION_BASED("Subscription Based"),
    CAPITAL_GUARANTEE("Capital Guarantee"),
    VOLATILITY_INSURANCE("Volatility Insurance"),
    EVENT_INSURANCE("Event Insurance");

    private final String formattedName; // Human-readable name for display

    /**
     * Constructor assigning the formatted display name.
     *
     * @param formattedName human-readable name of the policy type
     */
    PolicyType(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Retrieves the formatted display name of the policy type.
     *
     * @return formatted name string
     */
    public String getFormattedName() {
        return formattedName;
    }

    /**
     * Converts a string code to the corresponding PolicyType enum.
     * Throws a PolicyTypeNotFoundException if the code is invalid.
     *
     * @param code string code representing the PolicyType enum constant
     * @return matching PolicyType enum
     * @throws PolicyTypeNotFoundException if no matching PolicyType is found
     */
    public static PolicyType fromCode(String code) {
        try {
            return PolicyType.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new PolicyTypeNotFoundException(code);
        }
    }
}
