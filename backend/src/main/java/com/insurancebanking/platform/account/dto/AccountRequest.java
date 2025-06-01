package com.insurancebanking.platform.account.dto;

import java.util.UUID;

import com.insurancebanking.platform.account.model.AccountType;

public class AccountRequest {

    // Properties

    private AccountType accountType;
    private UUID currencyId;

    // Getters

    public AccountType getAccountType() { return accountType; }
    public UUID getCurrencyId() { return currencyId; }

    // Setters

    public void setAccountType(AccountType value) { this.accountType = value; }
    public void setCurrencyId(UUID value) { this.currencyId = value; }
}
