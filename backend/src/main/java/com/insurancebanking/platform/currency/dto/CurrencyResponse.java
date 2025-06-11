package com.insurancebanking.platform.currency.dto;

import java.util.Currency;

public class CurrencyResponse {

    // Constructors

    public CurrencyResponse() {}

    // Properties

    private String currencyCode;
    private String currencySymbol;
    private int currencyFractionDigits;

    // Response static builder

    public static CurrencyResponse from(String currencyCode) {
        CurrencyResponse response = new CurrencyResponse();

        response.setCurrencyCode(currencyCode);
        Currency currency = Currency.getInstance(currencyCode);
        response.setCurrencySymbol(currency.getSymbol());
        response.setCurrencyFractionDigits(currency.getDefaultFractionDigits());

        return response;
    }

    // Getters

    public String getCurrencyCode() { return currencyCode; }
    public String getCurrencySymbol() { return currencySymbol; }
    public int getCurrencyFractionDigits() { return currencyFractionDigits; }

    // Setters

    public void setCurrencyCode(String value) { this.currencyCode = value; }
    public void setCurrencySymbol(String value) { this.currencySymbol = value; }
    public void setCurrencyFractionDigits(int value) { this.currencyFractionDigits = value; }
}