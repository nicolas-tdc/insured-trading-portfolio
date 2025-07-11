package com.insurancebanking.platform.transfer.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.transfer.model.Transfer;
import com.insurancebanking.platform.transfer.model.TransferStatus;

public class TransferResponseTest {

    @Test
    void from_shouldMapAllFieldsCorrectly() {
        User sourceUser = User.builder()
            .email("jane@example.com")
            .build();

        User targetUser = User.builder()
            .email("john@example.com")
            .build();

        Account sourceAccount = Account.builder()
            .user(sourceUser)
            .accountStatus(AccountStatus.ACTIVE)
            .accountType(AccountType.CHECKING)
            .accountNumber("ACC999")
            .balance(BigDecimal.valueOf(123.45))
            .currencyCode("EUR")
            .build();

        Account targetAccount = Account.builder()
            .user(targetUser)
            .accountStatus(AccountStatus.ACTIVE)
            .accountType(AccountType.CHECKING)
            .accountNumber("ACC999")
            .balance(BigDecimal.valueOf(123.45))
            .currencyCode("EUR")
            .build();

        Transfer transfer = Transfer.builder()
            .transferStatus(TransferStatus.PENDING)
            .transferNumber("TRN123")
            .amount(BigDecimal.valueOf(100))
            .currencyCode("EUR")
            .description("Test transfer")
            .sourceAccount(sourceAccount)
            .targetAccount(targetAccount)
            .build();

        TransferResponse response = TransferResponse.from(transfer);

        assertEquals(transfer.getId(), response.id());
        assertEquals(transfer.getTransferNumber(), response.transferNumber());
        assertEquals(transfer.getTransferStatus().name(), response.statusCode());
        assertEquals(transfer.getTransferStatus().getFormattedName(), response.statusDisplayName());
        assertEquals(transfer.getCreatedAt(), response.createdAt());
        assertEquals(transfer.getAmount(), response.amount());
        assertEquals(transfer.getDescription(), response.description());
        assertEquals("EUR", response.currencyCode());
        assertEquals("€", response.currencySymbol());
        assertEquals(2, response.currencyFractionDigits());
        assertEquals(transfer.getDescription(), response.description());
        assertEquals(transfer.getSourceAccount().getAccountNumber(), response.sourceAccountNumber());
        assertEquals(transfer.getSourceAccount().getUser().getEmail(), response.sourceUserEmail());
        assertEquals(transfer.getTargetAccount().getAccountNumber(), response.targetAccountNumber());
        assertEquals(transfer.getTargetAccount().getUser().getEmail(), response.targetUserEmail());
    }
}