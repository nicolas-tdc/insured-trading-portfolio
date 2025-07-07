package com.insurancebanking.platform.account.service;

import java.math.BigDecimal;
import java.util.List;
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

@Service
@Transactional
public class AccountService {

    private static final String ACCOUNT_PREFIX = "ACC";
    private static final int MAX_TRIES = 10;

    private final BaseEntityService baseEntityService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CurrencyService currencyService;

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

    public List<AccountType> getAccountTypes() {
        return List.of(AccountType.values());
    }

    public List<Account> getUserAccounts(UUID userId) {
        return accountRepository.findByUser_IdOrderByCreatedAtAsc(userId);
    }

    public Account getUserAccountById(UUID accountId, UUID userId) {
        return accountRepository.findById(accountId)
            .filter(a -> a.getUser().getId().equals(userId))
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new AccountNumberNotFoundException(accountNumber));
    }

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
            .accountType(request.accountType())
            .currencyCode(currency)
            .balance(BigDecimal.ZERO)
            .build();

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        for (int i = 0; i < MAX_TRIES; i++) {
            String candidate = baseEntityService.generateEntityPublicIdentifier(ACCOUNT_PREFIX);
            if (!accountRepository.existsByAccountNumber(candidate)) {
                return candidate;
            }
        }
        throw new AccountNumberGenerationException(MAX_TRIES);
    }
}
