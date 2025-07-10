package com.insurancebanking.platform.policy.exception;

public class PolicyTypeNotFoundException extends RuntimeException {
    public PolicyTypeNotFoundException(String policyType) {
        super("Policy type " + policyType + " not found.");
    }
}