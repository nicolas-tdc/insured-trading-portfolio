package com.insurancebanking.platform.policy.exception;

import java.util.UUID;

public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException(UUID policyId) {
        super("Policy not found with ID: " + policyId);
    }
}
