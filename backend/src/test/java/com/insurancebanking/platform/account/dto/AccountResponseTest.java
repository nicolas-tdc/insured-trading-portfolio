package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountResponseTest {

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
        assertEquals(account.getAccountStatus(), response.accountStatus());
        assertEquals(account.getAccountType(), response.accountType());
        assertEquals(account.getAccountNumber(), response.accountNumber());
        assertEquals(account.getBalance(), response.balance());
        assertEquals("EUR", response.currencyCode());
        assertEquals("€", response.currencySymbol());
        assertEquals(2, response.currencyFractionDigits());
        assertEquals(policies, response.policies());
    }
}
