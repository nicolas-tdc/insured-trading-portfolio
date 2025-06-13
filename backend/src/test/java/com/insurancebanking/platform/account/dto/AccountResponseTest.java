package com.insurancebanking.platform.account.dto;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

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

        AccountResponse response = AccountResponse.from(account);

        assertEquals(account.getId(), response.id());
        assertEquals("EUR", response.currencyCode());
        assertEquals("€", response.currencySymbol());
        assertEquals(2, response.currencyFractionDigits());
    }
}
