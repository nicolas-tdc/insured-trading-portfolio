package com.insurancebanking.platform.policy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.insurancebanking.platform.policy.model.PolicyType;

class PolicyTypeResponseTest {

    @Test
    void testPolicyTypeResponse() {
        PolicyType policyType = PolicyType.LOSS_PROTECTION;
        PolicyTypeResponse response = PolicyTypeResponse.from(policyType);

        assertEquals(policyType.name(), response.code());
        assertEquals(policyType.getFormattedName(), response.displayName());
    }
}