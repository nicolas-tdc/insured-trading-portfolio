package com.insurancebanking.platform.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.account.dto.AccountRequest;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.model.AccountType;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.account.repository.AccountTypeRepository;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.currency.model.Currency;
import com.insurancebanking.platform.currency.repository.CurrencyRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    UserRepository userRepository;

    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
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
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        AccountType accountType = accountTypeRepository.findById(request.getAccountTypeId())
            .orElseThrow(() -> new EntityNotFoundException("Account type not found"));

        Currency currency = currencyRepository.findById(request.getCurrencyId())
            .orElseThrow(() -> new EntityNotFoundException("Currency not found"));

        Account account = Account.builder()
            .user(user)
            .accountNumber(UUID.randomUUID()
                            .toString()
                            .substring(0, 12)
                            .toUpperCase())
            .accountType(accountType)
            .currency(currency)
            .balance(BigDecimal.valueOf(0.0))
            .build();

        return accountRepository.save(account);
    }
}