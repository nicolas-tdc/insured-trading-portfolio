package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.AccountStatus;

public record AccountStatusResponse (
    String code,
    String displayName
) {
    public static AccountStatusResponse from(AccountStatus accountStatus) {
        return new AccountStatusResponse(
            accountStatus.name(),
            accountStatus.getFormattedName()
        );
    }
}