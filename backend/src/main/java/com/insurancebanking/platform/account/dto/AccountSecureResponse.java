package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.core.service.StringFormatterService;

/**
 * AccountSecureResponse
 *
 * DTO representing sensitive account information in a secure form.
 * Contains the account number and a masked version of the user's email.
 */
public record AccountSecureResponse(

    /**
     * Account number associated with the account.
     */
    String accountNumber,

    /**
     * Masked email address of the account owner to protect user privacy.
     */
    String secureEmail

) {
    /**
     * Creates an AccountSecureResponse from an Account entity,
     * applying masking to the user's email.
     *
     * @param account the account entity
     * @return AccountSecureResponse containing masked data
     */
    public static AccountSecureResponse from(Account account) {
        return new AccountSecureResponse(
            account.getAccountNumber(),
            StringFormatterService.maskEmail(account.getUser().getEmail())
        );
    }
}
