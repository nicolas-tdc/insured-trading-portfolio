package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.AccountType;

import jakarta.validation.constraints.NotNull;

public class AccountRequest {

    // Properties

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Currency is required")
    private String currencyCode;

    // Getters

    public AccountType getAccountType() { return accountType; }
    public String getCurrencyCode() { return currencyCode; }

    // Setters

    public void setAccountType(AccountType value) { this.accountType = value; }
    public void setCurrencyCode(String value) { this.currencyCode = value; }
}
