package com.insuredtrading.portfolio.policy.exception;

/**
 * PolicyCreationException
 *
 * Runtime exception thrown when an error occurs during the creation
 * of a policy, such as validation failures or persistence issues.
 */
public class PolicyCreationException extends RuntimeException {

    /**
     * Constructs the exception with a specific error message.
     *
     * @param message detailed message describing the creation failure
     */
    public PolicyCreationException(String message) {
        super(message);
    }
}
