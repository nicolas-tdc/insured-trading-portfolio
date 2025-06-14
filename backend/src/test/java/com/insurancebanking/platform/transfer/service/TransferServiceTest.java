package com.insurancebanking.platform.transfer.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.transfer.dto.TransferRequest;
import com.insurancebanking.platform.transfer.exception.TransferCreationException;
import com.insurancebanking.platform.transfer.exception.TransferValidationException;
import com.insurancebanking.platform.transfer.model.Transfer;
import com.insurancebanking.platform.transfer.model.TransferStatus;
import com.insurancebanking.platform.transfer.repository.TransferRepository;

public class TransferServiceTest {

    @Mock private BaseEntityService baseEntityService;
    @Mock private TransferRepository transferRepository;
    @Mock private AccountService accountService;
    @Mock private AccountRepository accountRepository;

    @InjectMocks private TransferService transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Account mockAccount(UUID id, BigDecimal balance, String currency, AccountStatus status) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(balance);
        account.setCurrencyCode(currency);
        account.setAccountStatus(status);
        return account;
    }

    @Test
    void createTransfer_shouldSucceed_whenValid() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String targetAccountNumber = "ACC987";

        TransferRequest request = new TransferRequest(
            sourceId,
            targetAccountNumber,
            BigDecimal.valueOf(100),
            "Test transfer"
        );

        Account source = mockAccount(sourceId, BigDecimal.valueOf(500), "USD", AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), BigDecimal.valueOf(300), "USD", AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getUserAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("TRF123");
        when(transferRepository.existsByTransferNumber("TRF123")).thenReturn(false);
        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transfer result = transferService.createTransfer(request, userId);

        assertThat(result).isNotNull();
        assertThat(result.getTransferStatus()).isEqualTo(TransferStatus.COMPLETED);
        assertThat(result.getTransferNumber()).isEqualTo("TRF123");
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(result.getDescription()).isEqualTo("Test transfer");

        assertThat(source.getBalance()).isEqualTo(BigDecimal.valueOf(400));
        assertThat(target.getBalance()).isEqualTo(BigDecimal.valueOf(400));
    }

    @Test
    void createTransfer_shouldFail_whenSourceInactive() {
        UUID userId = UUID.randomUUID();
        TransferRequest request = new TransferRequest(UUID.randomUUID(), "ACC999", BigDecimal.TEN, "Invalid");

        Account source = mockAccount(request.sourceAccountId(), BigDecimal.valueOf(100), "USD", AccountStatus.INACTIVE);
        Account target = mockAccount(UUID.randomUUID(), BigDecimal.valueOf(200), "USD", AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getUserAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source account is not active");
    }

    @Test
    void createTransfer_shouldFail_whenTargetInactive() {
        UUID userId = UUID.randomUUID();
        TransferRequest request = new TransferRequest(UUID.randomUUID(), "ACC999", BigDecimal.TEN, "Invalid");

        Account source = mockAccount(request.sourceAccountId(), BigDecimal.valueOf(100), "USD", AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), BigDecimal.valueOf(200), "USD", AccountStatus.INACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getUserAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Target account is not active");
    }

    @Test
    void createTransfer_shouldFail_whenInsufficientBalance() {
        UUID userId = UUID.randomUUID();
        TransferRequest request = new TransferRequest(UUID.randomUUID(), "ACC123", BigDecimal.valueOf(1000), "Overdraw");

        Account source = mockAccount(request.sourceAccountId(), BigDecimal.valueOf(200), "USD", AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), BigDecimal.valueOf(300), "USD", AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getUserAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Insufficient balance");
    }

    @Test
    void createTransfer_shouldFail_whenSameAccount() {
        UUID userId = UUID.randomUUID();
        UUID accId = UUID.randomUUID();

        TransferRequest request = new TransferRequest(accId, "SELF", BigDecimal.TEN, "Self-transfer");

        Account account = mockAccount(accId, BigDecimal.valueOf(100), "USD", AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(accId, userId)).thenReturn(account);
        when(accountService.getUserAccountByAccountNumber("SELF")).thenReturn(account);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source and target accounts must be different");
    }

    @Test
    void createTransfer_shouldFail_whenCurrencyMismatch() {
        UUID userId = UUID.randomUUID();
        TransferRequest request = new TransferRequest(UUID.randomUUID(), "ACC987", BigDecimal.TEN, "Currency mismatch");

        Account source = mockAccount(request.sourceAccountId(), BigDecimal.valueOf(500), "USD", AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), BigDecimal.valueOf(100), "EUR", AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getUserAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Currency mismatch");
    }

    @Test
    void createTransfer_shouldThrow_whenTransferNumberExhausted() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String targetAccountNumber = "ACCFAIL";

        TransferRequest request = new TransferRequest(
            sourceId,
            targetAccountNumber,
            BigDecimal.valueOf(100),
            "Retry fail"
        );

        Account source = mockAccount(sourceId, BigDecimal.valueOf(500), "USD", AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), BigDecimal.valueOf(300), "USD", AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getUserAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("DUPLICATE");
        when(transferRepository.existsByTransferNumber("DUPLICATE")).thenReturn(true);

        // simulate all retries returning duplicates
        for (int i = 0; i < 10; i++) {
            when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("DUPLICATE");
        }

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferCreationException.class)
            .hasMessageContaining("Failed to generate unique transfer number");
    }
}
