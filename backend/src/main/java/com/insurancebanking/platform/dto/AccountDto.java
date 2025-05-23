package com.insurancebanking.platform.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.insurancebanking.platform.model.Account;

public class AccountDto {
    private UUID id;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private String currency;
    private String status;

    public AccountDto() {}

    public static AccountDto from(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setStatus(account.getStatus());
        return dto;
    }

    // Setters
    public void setId(UUID newId) { this.id = newId; }
    public void setAccountNumber(String newAccountNumber) { this.accountNumber = newAccountNumber; }
    public void setType(String type) { this.type = type; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(String status) { this.status = status; }

    // Getters
    public UUID getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
}