package com.insuredtrading.portfolio.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AccountRequest
 *
 * DTO representing the request payload to create a new account.
 * Includes validation to ensure required fields are provided.
 */
public record AccountRequest(

    /**
     * Type code identifying which type of account to create.
     * Must not be null.
     */
    @NotNull(message = "Account type is required")
    String typeCode,

    /**
     * ISO currency code for the account.
     * Must not be blank.
     */
    @NotBlank(message = "Currency code is required")
    String currencyCode

) {}
