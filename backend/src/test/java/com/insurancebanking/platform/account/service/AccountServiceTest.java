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

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllAccountTypes() {
        List<AccountType> result = accountService.getAccountTypes();
        assertThat(result).containsExactly(AccountType.values());
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        UUID userId = UUID.randomUUID();
        AccountRequest request = new AccountRequest(AccountType.SAVINGS, "EUR");
        String accountNumber = "ACC123456789";

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(currencyService.isCurrencySupported("EUR")).thenReturn(true);
        when(baseEntityService.generateEntityPublicIdentifier("ACC")).thenReturn(accountNumber);
        when(accountRepository.existsByAccountNumber(accountNumber)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.create(request, userId);

        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getCurrencyCode()).isEqualTo("EUR");
        assertThat(result.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(result.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(result.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    }

    @Test
    void shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();

        AccountRequest request = new AccountRequest(AccountType.SAVINGS, "EUR");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.create(request, userId))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("User not found during account creation");
    }

    @Test
    void shouldThrowIfCurrencyNotSupported() {
        String currencyCode = "AFN";

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        AccountRequest request = new AccountRequest(AccountType.SAVINGS, currencyCode);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(currencyService.isCurrencySupported(currencyCode)).thenReturn(false);

        assertThatThrownBy(() -> accountService.create(request, userId))
            .isInstanceOf(UnsupportedCurrencyException.class)
            .hasMessage("Currency " + currencyCode + " is not supported.");
    }

    @Test
    void shouldThrowIfAccountNumberGenerationFails() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        AccountRequest request = new AccountRequest(AccountType.SAVINGS, "EUR");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(currencyService.isCurrencySupported("EUR")).thenReturn(true);

        when(baseEntityService.generateEntityPublicIdentifier("ACC"))
            .thenReturn("DUPLICATE_ACC");

        when(accountRepository.existsByAccountNumber("DUPLICATE_ACC"))
            .thenReturn(true); // Always duplicates

        assertThatThrownBy(() -> accountService.create(request, userId))
            .isInstanceOf(AccountNumberGenerationException.class)
            .hasMessage("Failed to generate unique account number after 10 attempts.");
    }

    @Test
    void shouldReturnUserAccountIfExistsAndMatches() {
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

    @Test
    void shouldThrowIfAccountUserMismatch() {
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        User differentUser = new User();
        differentUser.setId(UUID.randomUUID());

        Account account = new Account();
        account.setId(accountId);
        account.setUser(differentUser);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.getUserAccountById(accountId, userId))
            .isInstanceOf(AccountNotFoundException.class)
            .hasMessage("Account with ID " + accountId + " not found for the user.");
    }

    @Test
    void shouldThrowIfAccountNotFoundById() {
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getUserAccountById(accountId, userId))
            .isInstanceOf(AccountNotFoundException.class)
            .hasMessage("Account with ID " + accountId + " not found for the user.");
    }

    @Test
    void shouldThrowIfAccountNumberNotFound() {
        String accountNumber = "INVALID123";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getAccountByAccountNumber(accountNumber))
            .isInstanceOf(AccountNumberNotFoundException.class)
            .hasMessage("Account with number " + accountNumber + " not found.");
    }

    @Test
    void shouldReturnAccountIfAccountNumberExists() {
        String accountNumber = "VALID123";
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountByAccountNumber(accountNumber);

        assertThat(result).isNotNull();
        assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    }

    @Test
    void shouldReturnUserAccountsByUserId() {
        UUID userId = UUID.randomUUID();
        Account acc1 = new Account();
        Account acc2 = new Account();

        when(accountRepository.findByUser_IdOrderByCreatedAtAsc(userId)).thenReturn(List.of(acc1, acc2));

        List<Account> accounts = accountService.getUserAccounts(userId);

        assertThat(accounts).hasSize(2).containsExactly(acc1, acc2);
    }
}
