package com.insurancebanking.platform.account.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;

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
