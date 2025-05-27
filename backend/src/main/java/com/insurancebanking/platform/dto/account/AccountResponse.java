package com.insurancebanking.platform.dto.account;

import java.math.BigDecimal;
import java.util.UUID;

import com.insurancebanking.platform.model.Account;

public class AccountResponse {

    // Constructors

    public AccountResponse() {}

    // Properties

    private UUID id;
    private String accountType;
    private String currency;
    private String accountNumber;
    private BigDecimal balance;
    private String status;

    // Response static builder

    public static AccountResponse from(Account account) {
        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setAccountType(account.getAccountType().getName());
        response.setCurrency(account.getCurrency().getName());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public String getAccountType() { return accountType; }
    public String getCurrency() { return currency; }
    public BigDecimal getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getStatus() { return status; }

    // Setters

    public void setId(UUID value) { this.id = value; }
    public void setAccountNumber(String value) { this.accountNumber = value; }
    public void setAccountType(String value) { this.accountType = value; }
    public void setBalance(BigDecimal value) { this.balance = value; }
    public void setCurrency(String value) { this.currency = value; }
    public void setStatus(String value) { this.status = value; }
}