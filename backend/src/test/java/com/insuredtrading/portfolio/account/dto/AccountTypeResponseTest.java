package com.insuredtrading.portfolio.account.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.insuredtrading.portfolio.account.model.AccountType;

/**
 * Unit tests for AccountTypeResponse DTO.
 * Verifies that the static from() method correctly maps AccountType enum values.
 */
class AccountTypeResponseTest {

    /**
     * Tests the from() method of AccountTypeResponse.
     * Ensures that the enum name and formatted display name are mapped correctly.
     */
    @Test
    void from_shouldMapAllFieldsCorrectly() {
        AccountType accountType = AccountType.CHECKING;

        AccountTypeResponse accountTypeResponse = AccountTypeResponse.from(accountType);

        assertEquals(accountType.name(), accountTypeResponse.code());
        assertEquals(accountType.getFormattedName(), accountTypeResponse.displayName());
    }
}
