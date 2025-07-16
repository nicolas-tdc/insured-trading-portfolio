package com.insuredtrading.portfolio.account.dto;

import com.insuredtrading.portfolio.account.model.AccountType;

/**
 * AccountTypeResponse
 *
 * DTO representing a supported account type on the platform.
 * Contains the technical code and the human-readable display name.
 */
public record AccountTypeResponse(

    /**
     * Code representing the type of account.
     */
    String code,

    /**
     * Human-readable name of the account type.
     */
    String displayName

) {
    /**
     * Creates an AccountTypeResponse from an AccountType enum.
     *
     * @param accountType the account type enum
     * @return AccountTypeResponse containing mapped data
     */
    public static AccountTypeResponse from(AccountType accountType) {
        return new AccountTypeResponse(
            accountType.name(),
            accountType.getFormattedName()
        );
    }
}
