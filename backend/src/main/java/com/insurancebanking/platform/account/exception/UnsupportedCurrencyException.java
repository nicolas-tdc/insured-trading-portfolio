package com.insurancebanking.platform.account.exception;

/**
 * UnsupportedCurrencyException
 *
 * Exception thrown when the specified currency code
 * is not supported by the platform.
 */
public class UnsupportedCurrencyException extends RuntimeException {

    /**
     * Constructs a new UnsupportedCurrencyException with a message
     * indicating the unsupported currency code.
     *
     * @param code the currency code that is unsupported
     */
    public UnsupportedCurrencyException(String code) {
        super("Currency " + code + " is not supported.");
    }
}
