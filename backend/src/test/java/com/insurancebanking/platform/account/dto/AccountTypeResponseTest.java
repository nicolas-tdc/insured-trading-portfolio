package com.insurancebanking.platform.account.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.insurancebanking.platform.account.model.AccountType;

class AccountTypeResponseTest {
    @Test
    void from_shouldMapAllFieldsCorrectly() {
        AccountType accountType = AccountType.CHECKING;
        AccountTypeResponse accountTypeResponse = AccountTypeResponse.from(accountType);

        assertEquals(accountType.name(), accountTypeResponse.code());
        assertEquals(accountType.getFormattedName(), accountTypeResponse.displayName());
    }
}