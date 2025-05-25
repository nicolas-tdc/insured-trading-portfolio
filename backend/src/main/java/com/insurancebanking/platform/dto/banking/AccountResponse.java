package com.insurancebanking.platform.dto.banking;

import java.math.BigDecimal;
import java.util.UUID;

import com.insurancebanking.platform.model.Account;

public class AccountResponse {
    // Constructors
    public AccountResponse() {}

    // Fields
    private UUID id;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private String currency;
    private String status;

    // Response static builder
    public static AccountResponse from(Account account) {
        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setType(account.getType());
        response.setBalance(account.getBalance());
        response.setCurrency(account.getCurrency());
        response.setStatus(account.getStatus());

        return response;
    }

    // Getters
    public UUID getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }

    // Setters
    public void setId(UUID newId) { this.id = newId; }
    public void setAccountNumber(String newAccountNumber) { this.accountNumber = newAccountNumber; }
    public void setType(String type) { this.type = type; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(String status) { this.status = status; }
}