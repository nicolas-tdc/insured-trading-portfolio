package com.insuredtrading.portfolio.policy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.insuredtrading.portfolio.account.model.Account;
import com.insuredtrading.portfolio.account.model.AccountStatus;
import com.insuredtrading.portfolio.account.model.AccountType;
import com.insuredtrading.portfolio.policy.model.Policy;
import com.insuredtrading.portfolio.policy.model.PolicyType;

/**
 * Unit tests for {@link PolicyResponse}.
 * 
 * Tests that the from method correctly maps all relevant fields from Policy to PolicyResponse DTO.
 */
public class PolicyResponseTest {

    /**
     * Verifies mapping of all fields including
     * policy status, type, currency details, and coverage amount.
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

        Policy policy = Policy.builder()
            .policyType(PolicyType.LOSS_PROTECTION)
            .coverageAmount(Double.valueOf(1000))
            .currencyCode("EUR")
            .premium(Double.valueOf(100))
            .account(account)
            .build();

        PolicyResponse response = PolicyResponse.from(policy);

        assertEquals(policy.getId(), response.id());
        assertEquals(policy.getPolicyStatus().name(), response.statusCode());
        assertEquals(policy.getPolicyStatus().getFormattedName(), response.statusDisplayName());
        assertEquals(policy.getPolicyType().name(), response.typeCode());
        assertEquals(policy.getPolicyType().getFormattedName(), response.typeDisplayName());
        assertEquals("EUR", response.currencyCode());
        assertEquals("€", response.currencySymbol());
        assertEquals(2, response.currencyFractionDigits());
        assertEquals(Double.valueOf(1000), response.coverageAmount());
    }
}
