package com.insurancebanking.platform.account.dto;

import java.util.UUID;

import com.insurancebanking.platform.account.model.AccountType;

public class AccountTypeResponse {
    
    // Constructors

    public AccountTypeResponse() {}

    // Properties

    UUID id;
    String name;

    // Response static builder

    public static AccountTypeResponse from(AccountType accountType) {
        AccountTypeResponse response = new AccountTypeResponse();

        response.setId(accountType.getId());
        response.setName(accountType.getName());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public String getName() { return name; }

    // Setters

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}