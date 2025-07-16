package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import java.util.List;

/**
 * AccountResponse
 *
 * DTO representing detailed information about an account,
 * including its status, type, balance, currency information,
 * and associated policy numbers.
 */
public record AccountResponse(

    /**
     * Unique identifier of the account.
     */
    UUID id,

    /**
     * Code representing the current status of the account.
     */
    String statusCode,

    /**
     * Human-readable name of the account status.
     */
    String statusDisplayName,

    /**
     * Code representing the type of the account.
     */
    String typeCode,

    /**
     * Human-readable name of the account type.
     */
    String typeDisplayName,

    /**
     * Account number assigned to the account.
     */
    String accountNumber,

    /**
     * Current balance of the account.
     */
    BigDecimal balance,

    /**
     * ISO currency code of the account's currency.
     */
    String currencyCode,

    /**
     * Symbol of the account's currency.
     */
    String currencySymbol,

    /**
     * Number of fraction digits used by the currency.
     */
    int currencyFractionDigits,

    /**
     * List of policy numbers linked to this account.
     */
    List<String> policies

) {
    /**
     * Creates an AccountResponse from an Account entity and its associated policies.
     *
     * @param account  the account entity
     * @param policies list of policy numbers linked to the account
     * @return AccountResponse containing mapped data
     */
    public static AccountResponse from(Account account, List<String> policies) {
        String currencyCode = account.getCurrencyCode();
        Currency currency = Currency.getInstance(currencyCode);

        return new AccountResponse(
            account.getId(),
            account.getAccountStatus().name(),
            account.getAccountStatus().getFormattedName(),
            account.getAccountType().name(),
            account.getAccountType().getFormattedName(),
            account.getAccountNumber(),
            account.getBalance(),
            currencyCode,
            currency.getSymbol(),
            currency.getDefaultFractionDigits(),
            policies
        );
    }
}
