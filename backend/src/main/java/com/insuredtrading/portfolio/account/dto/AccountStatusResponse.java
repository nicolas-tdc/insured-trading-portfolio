package com.insuredtrading.portfolio.account.dto;

import com.insuredtrading.portfolio.account.model.AccountStatus;

/**
 * AccountStatusResponse
 *
 * DTO representing a possible status of an account on the platform.
 * Contains the technical code and the human-readable display name.
 */
public record AccountStatusResponse (

    /**
     * Code representing the account status.
     */
    String code,

    /**
     * Human-readable name of the account status.
     */
    String displayName

) {
    /**
     * Creates an AccountStatusResponse from an AccountStatus enum.
     *
     * @param accountStatus the account status enum
     * @return AccountStatusResponse containing mapped data
     */
    public static AccountStatusResponse from(AccountStatus accountStatus) {
        return new AccountStatusResponse(
            accountStatus.name(),
            accountStatus.getFormattedName()
        );
    }
}
