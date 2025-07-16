package com.insuredtrading.portfolio.policy.exception;

/**
 * PolicyTypeNotFoundException
 *
 * Runtime exception thrown when an invalid or unknown policy type code
 * is encountered during policy processing or lookup.
 */
public class PolicyTypeNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with a message indicating
     * which policy type code was not found.
     *
     * @param policyType the invalid policy type code
     */
    public PolicyTypeNotFoundException(String policyType) {
        super("Policy type " + policyType + " not found.");
    }
}
