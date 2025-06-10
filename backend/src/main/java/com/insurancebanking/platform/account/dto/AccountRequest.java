package com.insurancebanking.platform.account.dto;

import java.util.UUID;

import com.insurancebanking.platform.account.model.AccountType;

import jakarta.validation.constraints.NotNull;

public class AccountRequest {

    // Properties

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Currency is required")
    private UUID currencyId;

    // Getters

    public AccountType getAccountType() { return accountType; }
    public UUID getCurrencyId() { return currencyId; }

    // Setters

    public void setAccountType(AccountType value) { this.accountType = value; }
    public void setCurrencyId(UUID value) { this.currencyId = value; }
}
