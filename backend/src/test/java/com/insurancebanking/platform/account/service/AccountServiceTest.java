package com.insurancebanking.platform.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import com.insurancebanking.platform.account.dto.AccountRequest;
import com.insurancebanking.platform.account.exception.AccountNotFoundException;
import com.insurancebanking.platform.account.exception.AccountNumberGenerationException;
import com.insurancebanking.platform.account.exception.AccountNumberNotFoundException;
import com.insurancebanking.platform.account.exception.UnsupportedCurrencyException;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.currency.service.CurrencyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Unit tests for {@link AccountService}.
 * 
 * Tests cover all main use cases including creation, retrieval, and exception handling.
 */
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BaseEntityService baseEntityService;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private AccountService accountService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that all available account types are returned correctly.
     */
    @Test
    void shouldReturnAllAccountTypes() {
        // Verify that all account types are returned by the service
        List<AccountType> result = accountService.getAccountTypes();
        assertThat(result).containsExactly(AccountType.values());
    }

    /**
     * Tests successful creation of an account given valid inputs.
     */
    @Test
    void shouldCreateAccountSuccessfully() {
        // Setup test data for creating an account
        UUID userId = UUID.randomUUID();
        AccountRequest request = new AccountRequest(AccountType.SAVINGS.name(), "EUR");
        String accountNumber = "ACC123456789";

        User user = new User();
        user.setId(userId);

        // Mock dependencies behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(currencyService.isCurrencySupported("EUR")).thenReturn(true);
        when(baseEntityService.generateEntityPublicIdentifier("ACC")).thenReturn(accountNumber);
        when(accountRepository.existsByAccountNumber(accountNumber)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute the service method
        Account result = accountService.create(request, userId);

        // Assert the created account has correct values
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getCurrencyCode()).isEqualTo("EUR");
        assertThat(result.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(result.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(result.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    }

    /**
     * Tests that an exception is thrown when the user ID is not found during account creation.
     */
    @Test
    void shouldThrowIfUserNotFound() {
        // Setup to test exception when user is not found
        UUID userId = UUID.randomUUID();

        AccountRequest request = new AccountRequest(AccountType.SAVINGS.name(), "EUR");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Expect UsernameNotFoundException with specific message
        assertThatThrownBy(() -> accountService.create(request, userId))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("User not found during account creation");
    }

    /**
     * Tests that an exception is thrown when the provided currency code is not supported.
     */
    @Test
    void shouldThrowIfCurrencyNotSupported() {
        // Test exception when currency is not supported
        String currencyCode = "AFN";

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        AccountRequest request = new AccountRequest(AccountType.SAVINGS.name(), currencyCode);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(currencyService.isCurrencySupported(currencyCode)).thenReturn(false);

        // Expect UnsupportedCurrencyException with specific message
        assertThatThrownBy(() -> accountService.create(request, userId))
            .isInstanceOf(UnsupportedCurrencyException.class)
            .hasMessage("Currency " + currencyCode + " is not supported.");
    }

    /**
     * Tests that an exception is thrown if the account number cannot be generated uniquely after max attempts.
     */
    @Test
    void shouldThrowIfAccountNumberGenerationFails() {
        // Test failure scenario for unique account number generation
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        AccountRequest request = new AccountRequest(AccountType.SAVINGS.name(), "EUR");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(currencyService.isCurrencySupported("EUR")).thenReturn(true);

        // Simulate repeated duplicates for generated account numbers
        when(baseEntityService.generateEntityPublicIdentifier("ACC"))
            .thenReturn("DUPLICATE_ACC");

        when(accountRepository.existsByAccountNumber("DUPLICATE_ACC"))
            .thenReturn(true); // Always duplicates

        // Expect AccountNumberGenerationException after max attempts
        assertThatThrownBy(() -> accountService.create(request, userId))
            .isInstanceOf(AccountNumberGenerationException.class)
            .hasMessage("Failed to generate unique account number after 10 attempts.");
    }

    /**
     * Tests retrieval of a user account by its ID when it belongs to the user.
     */
    @Test
    void shouldReturnUserAccountIfExistsAndMatches() {
        // Test successful retrieval of user account by id
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);

        Account account = new Account();
        account.setId(accountId);
        account.setUser(user);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account result = accountService.getUserAccountById(accountId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(accountId);
    }

    /**
     * Tests that an exception is thrown if the account does not belong to the specified user.
     */
    @Test
    void shouldThrowIfAccountUserMismatch() {
        // Test exception if account belongs to a different user
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        User differentUser = new User();
        differentUser.setId(UUID.randomUUID());

        Account account = new Account();
        account.setId(accountId);
        account.setUser(differentUser);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Expect AccountNotFoundException when user mismatch occurs
        assertThatThrownBy(() -> accountService.getUserAccountById(accountId, userId))
            .isInstanceOf(AccountNotFoundException.class)
            .hasMessage("Account with ID " + accountId + " not found for the user.");
    }

    /**
     * Tests that an exception is thrown when the account ID does not exist.
     */
    @Test
    void shouldThrowIfAccountNotFoundById() {
        // Test exception when account ID does not exist
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Expect AccountNotFoundException when account not found
        assertThatThrownBy(() -> accountService.getUserAccountById(accountId, userId))
            .isInstanceOf(AccountNotFoundException.class)
            .hasMessage("Account with ID " + accountId + " not found for the user.");
    }

    /**
     * Tests that an exception is thrown when no account is found for the given account number.
     */
    @Test
    void shouldThrowIfAccountNumberNotFound() {
        // Test exception when account number does not exist
        String accountNumber = "INVALID123";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Expect AccountNumberNotFoundException for invalid account number
        assertThatThrownBy(() -> accountService.getAccountByAccountNumber(accountNumber))
            .isInstanceOf(AccountNumberNotFoundException.class)
            .hasMessage("Account with number " + accountNumber + " not found.");
    }

    /**
     * Tests retrieval of an account by its account number when it exists.
     */
    @Test
    void shouldReturnAccountIfAccountNumberExists() {
        // Test retrieval of account by valid account number
        String accountNumber = "VALID123";
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountByAccountNumber(accountNumber);

        assertThat(result).isNotNull();
        assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    }

    /**
     * Tests retrieval of all accounts belonging to a given user.
     */
    @Test
    void shouldReturnUserAccountsByUserId() {
        // Test retrieval of all user accounts by user ID
        UUID userId = UUID.randomUUID();
        Account acc1 = new Account();
        Account acc2 = new Account();

        when(accountRepository.findByUser_IdOrderByAccountNumberAsc(userId)).thenReturn(List.of(acc1, acc2));

        List<Account> accounts = accountService.getUserAccounts(userId);

        assertThat(accounts).hasSize(2).containsExactly(acc1, acc2);
    }
}
