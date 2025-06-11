package com.insurancebanking.platform.account.dto;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Currency;

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
    private String accountNumber;
    private BigDecimal balance;
    private String currencyCode;
    private String currencySymbol;
    private int currencyFractionDigits;

    // Response static builder

    public static AccountResponse from(Account account) {
        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setAccountStatus(account.getAccountStatus());
        response.setAccountType(account.getAccountType());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());

        // Currency
        String accountCurrencyCode = account.getCurrencyCode();
        Currency currency = Currency.getInstance(accountCurrencyCode);
        response.setCurrencyCode(accountCurrencyCode);
        response.setCurrencySymbol(currency.getSymbol());
        response.setCurrencyFractionDigits(currency.getDefaultFractionDigits());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public AccountStatus getAccountStatus() { return accountStatus; }
    public AccountType getAccountType() { return accountType; }
    public BigDecimal getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getCurrencyCode() { return currencyCode; }
    public String getCurrencySymbol() { return currencySymbol; }
    public int getCurrencyFractionDigits() { return currencyFractionDigits; }

    // Setters

    public void setId(UUID value) { this.id = value; }
    public void setAccountStatus(AccountStatus value) { this.accountStatus = value; }
    public void setAccountNumber(String value) { this.accountNumber = value; }
    public void setAccountType(AccountType value) { this.accountType = value; }
    public void setBalance(BigDecimal value) { this.balance = value; }
    public void setCurrencyCode(String value) { this.currencyCode = value; }
    public void setCurrencySymbol(String value) { this.currencySymbol = value; }
    public void setCurrencyFractionDigits(int value) { this.currencyFractionDigits = value; }
}