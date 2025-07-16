package com.insuredtrading.portfolio.policy.exception;

import java.util.UUID;

/**
 * PolicyNotFoundException
 *
 * Runtime exception thrown when a policy with the specified ID
 * cannot be found in the system.
 */
public class PolicyNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with a message indicating
     * the policy ID that was not found.
     *
     * @param policyId the UUID of the missing policy
     */
    public PolicyNotFoundException(UUID policyId) {
        super("Policy not found with ID: " + policyId);
    }
}
