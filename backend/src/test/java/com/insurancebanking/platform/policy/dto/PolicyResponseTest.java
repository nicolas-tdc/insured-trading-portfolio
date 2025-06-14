package com.insurancebanking.platform.policy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;

public class PolicyResponseTest {

    @Test
    void from_shouldMapAllFieldsCorrectly() {
        Account account = Account.builder()
            .accountStatus(AccountStatus.ACTIVE)
            .accountType(AccountType.CHECKING)
            .accountNumber("ACC999")
            .balance(BigDecimal.valueOf(123.45))
            .currencyCode("EUR")
            .build();

        Policy policy = Policy.builder()
            .policyType(PolicyType.AUTO)
            .coverageAmount(Double.valueOf(1000))
            .currencyCode("EUR")
            .premium(Double.valueOf(100))
            .account(account)
            .build();

        PolicyResponse response = PolicyResponse.from(policy);

        assertEquals(policy.getId(), response.id());
        assertEquals("EUR", response.currencyCode());
        assertEquals("€", response.currencySymbol());
        assertEquals(2, response.currencyFractionDigits());
        assertEquals(PolicyType.AUTO, response.policyType());
        assertEquals(Double.valueOf(1000), response.coverageAmount());
    }
}