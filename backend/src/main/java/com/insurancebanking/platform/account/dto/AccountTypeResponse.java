package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.AccountType;

public record AccountTypeResponse(
    String code,
    String displayName
) {
    public static AccountTypeResponse from(AccountType accountType) {
        return new AccountTypeResponse(
            accountType.name(),
            accountType.getFormattedName()
        );
    }
}
