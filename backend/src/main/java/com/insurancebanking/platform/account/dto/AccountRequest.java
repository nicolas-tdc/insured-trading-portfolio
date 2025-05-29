package com.insurancebanking.platform.account.dto;

import java.util.UUID;

public class AccountRequest {

    // Properties

    private UUID accountTypeId;
    private UUID currencyId;

    // Getters

    public UUID getAccountTypeId() { return accountTypeId; }
    public UUID getCurrencyId() { return currencyId; }

    // Setters

    public void setAccountTypeId(UUID value) { this.accountTypeId = value; }
    public void setCurrencyId(UUID value) { this.currencyId = value; }
}
