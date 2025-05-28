package com.insurancebanking.platform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.dto.transfer.TransferRequest;
import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.Transfer;
import com.insurancebanking.platform.repository.AccountRepository;
import com.insurancebanking.platform.repository.TransferRepository;

@Service
@Transactional
public class TransferService {

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    public List<Transfer> getTransferTypes() {
        return transferRepository.findAll();
    }

    public String validate(TransferRequest request, UUID userId) {
        Account sourceAccount = getSourceAccountFromRequest(request, userId);
        Account targetAccount = getTargetAccountFromRequest(request);

        if (sourceAccount == null) {
            return "Invalid source account";
        }
        if (targetAccount == null) {
            return "Invalid target account";
        }
        // Ensure source and target accounts are different
        if (sourceAccount.getId().equals(targetAccount.getId())) {
            return "Source and target accounts must be different";
        }
        // Ensure amount is positive
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return "Amount must be positive";
        }
        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            return "Insufficient balance";
        }
        
        return null;
    }

    public Transfer create(TransferRequest request, UUID userId) {
        Account sourceAccount = getSourceAccountFromRequest(request, userId);
        Account targetAccount = getTargetAccountFromRequest(request);
        BigDecimal amount = request.getAmount();

        // Create and save transfer
        Transfer transfer = Transfer.builder()
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .amount(amount)
                .type("TRANSFER")
                .description(request.getDescription())
                .build();
        transferRepository.save(transfer);

        updateAccounts(sourceAccount, targetAccount, amount);

        return transfer;
    }

    public List<Transfer> getAccountTransfers(Account account) {
        UUID accountId = account.getId();
        List<Transfer> transfers = new ArrayList<>();

        // Merge incoming and outgoing account transfers
        transfers.addAll(transferRepository.findByTargetAccount_Id(accountId));
        transfers.addAll(transferRepository.findBySourceAccount_Id(accountId));

        return transfers;
    }

    private void updateAccounts(
        Account sourceAccount, Account targetAccount, BigDecimal amount) {
        // Update account balances
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        // Save updated accounts
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
    }

    private Account getSourceAccountFromRequest(TransferRequest request, UUID userId) {
        return accountService.getUserAccountById(request.getSourceAccountFromRequestId(), userId);
    }

    private Account getTargetAccountFromRequest(TransferRequest request) {
        return accountService.getUserAccountByAccountNumber(request.getTargetAccountFromRequestNumber());
    }
}