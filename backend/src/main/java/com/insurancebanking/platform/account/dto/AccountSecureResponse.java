package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.core.service.StringFormatterService;

public record AccountSecureResponse(
    String accountNumber,
    String secureEmail
) {
    public static AccountSecureResponse from(Account account) {

        return new AccountSecureResponse(
            account.getAccountNumber(),
            StringFormatterService.maskEmail(account.getUser().getEmail())
        );
    }
}