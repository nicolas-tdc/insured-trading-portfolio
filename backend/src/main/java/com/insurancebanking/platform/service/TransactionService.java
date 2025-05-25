package com.insurancebanking.platform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.dto.common.MessageResponse;
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

    public ResponseEntity<MessageResponse> checkTransfer(
        Account sourceAccount,
        Account targetAccount,
        BigDecimal amount
        ) {

        if (sourceAccount == null) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                "Invalid source account"));
        }
        
        if (targetAccount == null) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                "Invalid target account"));
        }

        // Ensure source and target accounts are different
        if (sourceAccount.getId().equals(targetAccount.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                "Source and target accounts must be different"));
        }

        // Ensure amount is positive
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                "Amount must be positive"));
        }

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                "Insufficient balance"));
        }

        return ResponseEntity.ok(new MessageResponse("Transfer successful"));
    }

    public Transaction recordTransfer(Account sourceAccount, Account targetAccount, BigDecimal amount, String description) {
        // Calculate new balances
        BigDecimal newFromBalance = sourceAccount.getBalance().subtract(amount);
        BigDecimal newToBalance = targetAccount.getBalance().add(amount);

        // Create transactions
        Transaction transaction = Transaction.builder()
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .amount(amount.negate())
                .type("TRANSFER")
                .description(description)
                .balanceAfter(newFromBalance)
                .build();

        // Save both transactions
        transactionRepository.save(transaction);

        // Update account balances
        sourceAccount.setBalance(newFromBalance);
        targetAccount.setBalance(newToBalance);

        // Save updated accounts
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        return transaction;
    }

    public List<Transaction> getAccountTransactions(Account account) {
        UUID accountId = account.getId();
        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(transactionRepository.findByTargetAccount_Id(accountId));
        transactions.addAll(transactionRepository.findBySourceAccount_Id(accountId));

        return transactions;
    }
}