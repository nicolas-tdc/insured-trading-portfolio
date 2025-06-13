package com.insurancebanking.platform.account.exception;

public class UnsupportedCurrencyException extends RuntimeException {
    public UnsupportedCurrencyException(String code) {
        super("Currency " + code + " is not supported.");
    }
}
