package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(
    @NotNull(message = "Account type is required")
    String typeCode,

    @NotBlank(message = "Currency code is required")
    String currencyCode
) {}
