package com.insurancebanking.platform.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.Transaction;
import com.insurancebanking.platform.repository.AccountRepository;
import com.insurancebanking.platform.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    public Transaction recordTransfer(Account from, Account to, BigDecimal amount, String description) {
        // Ensure amount is positive
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        // Ensure sender has enough funds
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Calculate new balances
        BigDecimal newFromBalance = from.getBalance().subtract(amount);
        BigDecimal newToBalance = to.getBalance().add(amount);

        // Create transactions
        Transaction fromTx = Transaction.builder()
                .account(from)
                .amount(amount.negate())
                .type("TRANSFER_OUT")
                .description(description)
                .balanceAfter(newFromBalance)
                .build();

        Transaction toTx = Transaction.builder()
                .account(to)
                .amount(amount)
                .type("TRANSFER_IN")
                .description(description)
                .balanceAfter(newToBalance)
                .build();

        // Save both transactions
        transactionRepository.save(fromTx);
        transactionRepository.save(toTx);

        // Update account balances
        from.setBalance(newFromBalance);
        to.setBalance(newToBalance);

        // Save updated accounts
        accountRepository.save(from);
        accountRepository.save(to);

        return fromTx;
    }

    public List<Transaction> getAccountTransactions(Account account) {
        return transactionRepository.findByAccount_Id(account.getId());
    }
}