package com.insurancebanking.platform.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.User;
import com.insurancebanking.platform.repository.AccountRepository;
import com.insurancebanking.platform.repository.UserRepository;

@Service
@Transactional
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUser_Id(user.getId());
    }

    public Account getAccountById(String accountId, UUID userId) {
        return accountRepository.findById(java.util.UUID.fromString(accountId))
                .filter(a -> a.getUser().getId().equals(userId))
                .orElse(null);
    }

    public Account getAccountByAccountNumber(String accountNumber, UUID userId) {
        return accountRepository.findByAccountNumber(accountNumber)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElse(null);
    }

public Account createAccountForUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Account account = Account.builder()
                .user(user)
                .accountNumber(UUID.randomUUID().toString().substring(0, 12).toUpperCase())
                .type("CHECKING")
                .balance(BigDecimal.valueOf(10000.0))
                .build();

        return accountRepository.save(account);
    }
}