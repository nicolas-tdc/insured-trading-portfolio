package com.insurancebanking.platform.account.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;

/**
 * AccountResponseTest
 *
 * Unit test class for AccountResponse DTO.
 * Verifies that the static from() method correctly maps all fields from
 * an Account entity and list of policy numbers into an AccountResponse object.
 */
class AccountResponseTest {

    /**
     * Tests the from() method of AccountResponse.
     * Creates a sample Account and list of policy numbers,
     * then asserts all fields are correctly copied to the response DTO.
     */
    @Test
    void from_shouldMapAllFieldsCorrectly() {
        Account account = Account.builder()
            .accountStatus(AccountStatus.ACTIVE)
            .accountType(AccountType.CHECKING)
            .accountNumber("ACC999")
            .balance(BigDecimal.valueOf(123.45))
            .currencyCode("EUR")
            .build();

        List<String> policies = List.of("POL123", "POL456");

        AccountResponse response = AccountResponse.from(account, policies);

        assertEquals(account.getId(), response.id());
        assertEquals(account.getAccountStatus().name(), response.statusCode());
        assertEquals(account.getAccountStatus().getFormattedName(), response.statusDisplayName());
        assertEquals(account.getAccountType().name(), response.typeCode());
        assertEquals(account.getAccountType().getFormattedName(), response.typeDisplayName());
        assertEquals(account.getAccountNumber(), response.accountNumber());
        assertEquals(account.getBalance(), response.balance());
        assertEquals("EUR", response.currencyCode());
        assertEquals("€", response.currencySymbol());
        assertEquals(2, response.currencyFractionDigits());
        assertEquals(policies, response.policies());
    }
}
