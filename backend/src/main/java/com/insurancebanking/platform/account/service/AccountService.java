package com.insurancebanking.platform.account.service;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.insurancebanking.platform.transfer.model.Transfer;

@Service
@Transactional
public class AccountService {

    private static final String ACCOUNT_PREFIX = "ACC";  // Prefix for generated account numbers
    private static final int MAX_TRIES = 10;             // Max attempts to generate unique account number

    private final BaseEntityService baseEntityService;   // Service for common entity utilities
    private final AccountRepository accountRepository;   // Repository for Account persistence
    private final UserRepository userRepository;         // Repository for User lookup
    private final CurrencyService currencyService;       // Service to validate supported currencies

    /**
     * Constructor for dependency injection of required services and repositories.
     *
     * @param baseEntityService common entity service
     * @param accountRepository account data access repository
     * @param userRepository    user data access repository
     * @param currencyService   service for currency validation
     */
    public AccountService(
        BaseEntityService baseEntityService,
        AccountRepository accountRepository,
        UserRepository userRepository,
        CurrencyService currencyService
    ) {
        this.baseEntityService = baseEntityService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.currencyService = currencyService;
    }

    /**
     * Returns all supported account types defined in the system.
     *
     * @return list of account types
     */
    public List<AccountType> getAccountTypes() {
        return List.of(AccountType.values());
    }

    /**
     * Retrieves all accounts owned by the specified user,
     * ordered by account number ascending.
     *
     * @param userId UUID of the user
     * @return list of accounts belonging to the user
     */
    public List<Account> getUserAccounts(UUID userId) {
        return accountRepository.findByUser_IdOrderByAccountNumberAsc(userId);
    }

    /**
     * Retrieves a specific account by its ID, verifying it belongs to the given user.
     * Throws AccountNotFoundException if not found or user mismatch.
     *
     * @param accountId UUID of the account
     * @param userId    UUID of the user
     * @return Account entity matching the criteria
     */
    public Account getUserAccountById(UUID accountId, UUID userId) {
        return accountRepository.findById(accountId)
            .filter(a -> a.getUser().getId().equals(userId))
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    /**
     * Retrieves an account by its unique account number.
     * Throws AccountNumberNotFoundException if not found.
     *
     * @param accountNumber unique account number string
     * @return Account entity matching the number
     */
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new AccountNumberNotFoundException(accountNumber));
    }

    /**
     * Retrieves all transfers (incoming and outgoing) associated with the account,
     * sorted by creation date descending (most recent first).
     *
     * @param account account entity
     * @return list of transfers related to the account
     */
    public List<Transfer> getAccountTransfers(Account account) {
        Set<Transfer> all = new LinkedHashSet<>(account.getOutgoingTransfers());
        all.addAll(account.getIncomingTransfers());

        return all.stream()
            .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
            .toList();
    }

    /**
     * Creates a new account for the given user with the provided request details.
     * Validates currency support and generates a unique account number.
     *
     * @param request data required to create the account
     * @param userId  UUID of the user creating the account
     * @return newly created Account entity
     * @throws UnsupportedCurrencyException if currency is not supported
     * @throws UsernameNotFoundException    if user is not found
     */
    public Account create(AccountRequest request, UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found during account creation"));

        String currency = request.currencyCode();
        if (!currencyService.isCurrencySupported(currency)) {
            throw new UnsupportedCurrencyException(currency);
        }

        String accountNumber = generateAccountNumber();

        Account account = Account.builder()
            .user(user)
            .accountStatus(AccountStatus.ACTIVE)
            .accountNumber(accountNumber)
            .accountType(AccountType.fromCode(request.typeCode()))
            .currencyCode(currency)
            .balance(BigDecimal.ZERO)
            .build();

        return accountRepository.save(account);
    }

    /**
     * Saves or updates an account entity in the repository.
     *
     * @param account the account entity to save
     * @return saved account entity
     */
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Generates a unique account number with a specific prefix.
     * Attempts up to MAX_TRIES times to avoid duplicates.
     * Throws AccountNumberGenerationException if unique number cannot be generated.
     *
     * @return generated unique account number string
     * @throws AccountNumberGenerationException if max attempts exceeded
     */
    public String generateAccountNumber() {
        for (int i = 0; i < MAX_TRIES; i++) {
            String candidate = baseEntityService.generateEntityPublicIdentifier(ACCOUNT_PREFIX);
            if (!accountRepository.existsByAccountNumber(candidate)) {
                return candidate;
            }
        }
        throw new AccountNumberGenerationException(MAX_TRIES);
    }
}
