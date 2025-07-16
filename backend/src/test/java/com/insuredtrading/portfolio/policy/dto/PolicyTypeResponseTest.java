package com.insuredtrading.portfolio.policy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.insuredtrading.portfolio.policy.model.PolicyType;

/**
 * Unit tests for {@link PolicyTypeResponse}.
 * 
 * Validates that the from method correctly converts a PolicyType enum into the PolicyTypeResponse DTO.
 */
class PolicyTypeResponseTest {

    /**
     * Tests that the code and displayName fields in PolicyTypeResponse
     * correctly reflect the corresponding PolicyType enum values.
     */
    @Test
    void testPolicyTypeResponse() {
        PolicyType policyType = PolicyType.LOSS_PROTECTION;
        PolicyTypeResponse response = PolicyTypeResponse.from(policyType);

        assertEquals(policyType.name(), response.code());
        assertEquals(policyType.getFormattedName(), response.displayName());
    }
}
