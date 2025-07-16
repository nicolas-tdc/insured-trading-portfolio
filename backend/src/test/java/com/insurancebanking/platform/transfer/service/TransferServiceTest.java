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
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.transfer.dto.TransferRequest;
import com.insurancebanking.platform.transfer.exception.TransferCreationException;
import com.insurancebanking.platform.transfer.exception.TransferValidationException;
import com.insurancebanking.platform.transfer.repository.TransferRepository;
import com.insurancebanking.platform.transfer.dto.TransferValidateExternalRequest;
import com.insurancebanking.platform.transfer.dto.TransferValidateInternalRequest;
import com.insurancebanking.platform.auth.model.User;

/**
 * Unit tests for {@link TransferService}.
 * 
 * Tests cover all main use cases including creation, retrieval, and exception handling.
 */
public class TransferServiceTest {

    @Mock private BaseEntityService baseEntityService;
    @Mock private TransferRepository transferRepository;
    @Mock private AccountService accountService;
    @Mock private AccountRepository accountRepository;

    @InjectMocks private TransferService transferService;

    /**
     * Initializes Mockito mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Mocks an account for testing.
     * 
     * @param id The account ID.
     * @param number The account number.
     * @param balance The account balance.
     * @param currency The account currency.
     * @param type The account type.
     * @param status The account status.
     * @return The mocked account.
     */
    private Account mockAccount(
        UUID id, String number, BigDecimal balance, String currency, AccountType type, AccountStatus status) {
        Account account = mock(Account.class);
        when(account.getId()).thenReturn(id);
        when(account.getAccountNumber()).thenReturn(number);
        when(account.getBalance()).thenReturn(balance);
        when(account.getCurrencyCode()).thenReturn(currency);
        when(account.getAccountType()).thenReturn(type);
        when(account.getAccountStatus()).thenReturn(status);

        return account;
    }

    /**
     * Tests that creating a transfer fails when the source account is inactive.
     */
    @Test
    void createTransfer_shouldFail_whenSourceInactive() {
        UUID userId = UUID.randomUUID();
        String targetAccountNumber = "ACC999";

        TransferRequest request = new TransferRequest(UUID.randomUUID(), targetAccountNumber, BigDecimal.TEN, "Invalid");

        Account source = mockAccount(request.sourceAccountId(), "ACC123", BigDecimal.valueOf(100), "USD", AccountType.CHECKING, AccountStatus.INACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(200), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source account is not active");
    }

    /**
     * Tests that creating a transfer fails when the target account is inactive.
     */
    @Test
    void createTransfer_shouldFail_whenTargetInactive() {
        UUID userId = UUID.randomUUID();
        String targetAccountNumber = "ACC999";

        TransferRequest request = new TransferRequest(UUID.randomUUID(), targetAccountNumber, BigDecimal.TEN, "Invalid");

        Account source = mockAccount(request.sourceAccountId(), "ACC123", BigDecimal.valueOf(100), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(200), "USD", AccountType.CHECKING, AccountStatus.INACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Target account is not active");
    }

    /**
     * Tests that creating a transfer fails when the source account has insufficient balance.
     */
    @Test
    void createTransfer_shouldFail_whenInsufficientBalance() {
        UUID userId = UUID.randomUUID();
        String targetAccountNumber = "ACC999";

        TransferRequest request = new TransferRequest(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(1000), "Overdraw");

        Account source = mockAccount(request.sourceAccountId(), "ACC123", BigDecimal.valueOf(200), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(300), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Insufficient balance");
    }

    /**
     * Tests that creating a transfer fails when the source and target accounts have different currencies.
     */
    @Test
    void createTransfer_shouldThrow_whenCurrencyMismatch() {
        UUID userId = UUID.randomUUID();
        String targetAccountNumber = "ACC999";

        TransferRequest request = new TransferRequest(UUID.randomUUID(), targetAccountNumber, BigDecimal.TEN, "Currency mismatch");

        Account source = mockAccount(request.sourceAccountId(), "ACC123", BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(100), "EUR", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(request.sourceAccountId(), userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(request.targetAccountNumber())).thenReturn(target);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source and target accounts must have the same currency");
    }

    /**
     * Tests that creating a transfer fails when the transfer number is exhausted.
     */
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

        Account source = mockAccount(sourceId, "ACC123", BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(300), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
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

    /**
     * Tests that creating a transfer fails when the source account is inactive.
     */
    @Test
    void createTransfer_shouldThrow_whenSourceAccountInactive() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String targetAccountNumber = "ACCFAIL";

        TransferRequest request = new TransferRequest(
            sourceId,
            targetAccountNumber,
            BigDecimal.TEN,
            "Source inactive fail");

        Account source = mockAccount(sourceId, "ACC123", BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.INACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(300), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("TRFFAIL");
        when(transferRepository.existsByTransferNumber("TRFFAIL")).thenReturn(true);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source account is not active");
    }

    /**
     * Tests that creating a transfer fails when the target account is inactive.
     */
    @Test
    void createTransfer_shouldThrow_whenTargetAccountInactive() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String targetAccountNumber = "ACCFAIL";

        TransferRequest request = new TransferRequest(
            sourceId,
            targetAccountNumber,
            BigDecimal.TEN,
            "Target inactive fail");

        Account source = mockAccount(sourceId, "ACC123", BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(300), "USD", AccountType.CHECKING, AccountStatus.INACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("TRFFAIL");
        when(transferRepository.existsByTransferNumber("TRFFAIL")).thenReturn(true);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Target account is not active");
    }

    /**
     * Tests that creating a transfer fails when the source and target accounts are the same.
     */
    @Test
    void createTransfer_shouldThrow_whenSourceAndTargetAccountsAreSame() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String sourceAccountNumber = "ACCFAIL";

        TransferRequest request = new TransferRequest(
            sourceId,
            sourceAccountNumber,
            BigDecimal.TEN,
            "Same account fail");

        Account source = mockAccount(sourceId, sourceAccountNumber, BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(sourceAccountNumber)).thenReturn(source);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("TRFFAIL");
        when(transferRepository.existsByTransferNumber("TRFFAIL")).thenReturn(true);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source and target accounts must be different");
    }

    /**
     * Tests that creating a transfer fails when the amount is negative.
     */
    @Test
    void createTransfer_shouldThrow_whenAmountIsNegative() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String targetAccountNumber = "ACCFAIL";

        TransferRequest request = new TransferRequest(
            sourceId,
            targetAccountNumber,
            BigDecimal.ZERO,
            "Negative amount fail");

        Account source = mockAccount(sourceId, "ACC123", BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(300), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("TRFFAIL");
        when(transferRepository.existsByTransferNumber("TRFFAIL")).thenReturn(true);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Transfer amount must be positive");
    }

    /**
     * Tests that creating a transfer fails when the amount exceeds the source account balance.
     */
    @Test
    void createTransfer_shouldThrow_whenAmountExceedsBalance() {
        UUID userId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        String targetAccountNumber = "ACCFAIL";

        TransferRequest request = new TransferRequest(
            sourceId,
            targetAccountNumber,
            BigDecimal.valueOf(1000),
            "Amount exceeds balance fail");

        Account source = mockAccount(sourceId, "ACC123", BigDecimal.valueOf(500), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);
        Account target = mockAccount(UUID.randomUUID(), targetAccountNumber, BigDecimal.valueOf(300), "USD", AccountType.CHECKING, AccountStatus.ACTIVE);

        when(accountService.getUserAccountById(sourceId, userId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(targetAccountNumber)).thenReturn(target);
        when(baseEntityService.generateEntityPublicIdentifier("TRF")).thenReturn("TRFFAIL");
        when(transferRepository.existsByTransferNumber("TRFFAIL")).thenReturn(true);

        assertThatThrownBy(() -> transferService.createTransfer(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Insufficient balance");
    }

    /**
     * Tests that validateInternalTransferAccounts throws an exception when the source and target accounts belong to different users.
     */
    @Test
    void validateInternalTransferAccounts_shouldThrow_whenAccountsBelongToDifferentUsers() {
        UUID userId = UUID.randomUUID();
        UUID sourceAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();

        User sourceUser = mock(User.class);
        User targetUser = mock(User.class);
        Account source = mock(Account.class);
        Account target = mock(Account.class);

        // Stub getId for accounts and users
        when(source.getId()).thenReturn(sourceAccountId);
        when(target.getId()).thenReturn(targetAccountId);
        when(sourceUser.getId()).thenReturn(userId);
        when(targetUser.getId()).thenReturn(UUID.randomUUID());

        // Stub getUser
        when(source.getUser()).thenReturn(sourceUser);
        when(target.getUser()).thenReturn(targetUser);

        // Stub accountService behavior
        when(accountService.getUserAccountById(sourceAccountId, userId)).thenReturn(source);
        when(accountService.getUserAccountById(targetAccountId, userId)).thenReturn(target);

        TransferValidateInternalRequest request = new TransferValidateInternalRequest(sourceAccountId, targetAccountId);

        assertThatThrownBy(() -> transferService.validateInternalTransferAccounts(request, userId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source and target accounts must belong to the same user");
    }

    /**
     * Tests that validateExternalTransferAccounts throws an exception when the source account is not a checking account.
     */
    @Test
    void validateExternalTransferAccounts_shouldThrow_whenSourceAccountIsNotChecking() {
        UUID sourceUserId = UUID.randomUUID();
        UUID sourceAccountId = UUID.randomUUID();
        String targetAccountNumber = "ACCFAIL";

        User sourceUser = mock(User.class);
        Account source = mock(Account.class);
        Account target = mock(Account.class);

        // Stub getId for accounts and users
        when(source.getId()).thenReturn(sourceAccountId);
        when(target.getAccountNumber()).thenReturn(targetAccountNumber);
        when(sourceUser.getId()).thenReturn(sourceUserId);

        // Stub getUser
        when(source.getUser()).thenReturn(sourceUser);

        // Stub accountService behavior
        when(accountService.getUserAccountById(sourceAccountId, sourceUserId)).thenReturn(source);
        when(accountService.getAccountByAccountNumber(targetAccountNumber)).thenReturn(target);

        TransferValidateExternalRequest request = new TransferValidateExternalRequest(sourceAccountId, targetAccountNumber);

        assertThatThrownBy(() -> transferService.validateExternalTransferAccounts(request, sourceUserId))
            .isInstanceOf(TransferValidationException.class)
            .hasMessageContaining("Source account must be a checking account");
    }
}
