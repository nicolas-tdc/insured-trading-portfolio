package com.insurancebanking.platform.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.account.model.AccountStatus;

public class AccountResponse {

    // Constructors

    public AccountResponse() {}

    // Properties

    private UUID id;
    private AccountStatus accountStatus;
    private AccountType accountType;
    private String currency;
    private String accountNumber;
    private BigDecimal balance;

    // Response static builder

    public static AccountResponse from(Account account) {
        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setAccountStatus(account.getAccountStatus());
        response.setAccountType(account.getAccountType());
        response.setCurrency(account.getCurrency().getName());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public AccountStatus getAccountStatus() { return accountStatus; }
    public AccountType getAccountType() { return accountType; }
    public String getCurrency() { return currency; }
    public BigDecimal getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }

    // Setters

    public void setId(UUID value) { this.id = value; }
    public void setAccountStatus(AccountStatus value) { this.accountStatus = value; }
    public void setAccountNumber(String value) { this.accountNumber = value; }
    public void setAccountType(AccountType value) { this.accountType = value; }
    public void setBalance(BigDecimal value) { this.balance = value; }
    public void setCurrency(String value) { this.currency = value; }
}