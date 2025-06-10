package com.insurancebanking.platform.account.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import com.insurancebanking.platform.account.dto.AccountRequest;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.currency.model.Currency;
import com.insurancebanking.platform.currency.repository.CurrencyRepository;

@Service
@Transactional
public class AccountService {

    @Autowired
    private BaseEntityService baseEntityService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AccountType> getAccountTypes() {
        return Arrays.stream(AccountType.values()).toList();
    }

    public List<Account> getUserAccounts(UUID userId) {
        return accountRepository.findByUser_Id(userId);
    }

    public Account getUserAccountById(UUID accountId, UUID userId) {
        return accountRepository.findById(accountId)
            .filter(a -> a.getUser().getId().equals(userId))
            .orElse(null);
    }

    public Account getUserAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                                .orElse(null);
    }

    public Account create(AccountRequest request, UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User not found during account creation"));

        Currency currency = currencyRepository.findById(request.getCurrencyId())
            .orElseThrow(() -> new EntityNotFoundException(
                "Currency not found during account creation"));

        Account account = Account.builder()
            .user(user)
            .accountStatus(AccountStatus.ACTIVE)
            .accountNumber(generateAccountNumber())
            .accountType(request.getAccountType())
            .currency(currency)
            .balance(BigDecimal.valueOf(0.0))
            .build();

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        int maxTries = 10;

        for (int i = 0; i < maxTries; i++) {
            String accountNumber = baseEntityService.generateEntityPublicIdentifier("ACC");

            if (!accountRepository.existsByAccountNumber(accountNumber)) {

                return accountNumber;
            }
        }

        throw new IllegalStateException("Unable to generate unique account number after " + maxTries + " attempts.");
    }
}