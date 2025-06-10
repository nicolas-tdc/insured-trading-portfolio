package com.insurancebanking.platform.transfer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.transfer.dto.TransferRequest;
import com.insurancebanking.platform.transfer.model.Transfer;
import com.insurancebanking.platform.transfer.repository.TransferRepository;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.transfer.model.TransferStatus;

@Service
@Transactional
public class TransferService {

    @Autowired
    private BaseEntityService baseEntityService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    public String validate(TransferRequest request, UUID userId) {
        Account sourceAccount = getSourceAccountFromRequest(request, userId);
        Account targetAccount = getTargetAccountFromRequest(request);

        if (sourceAccount == null) {
            return "Invalid source account";
        }
        if (targetAccount == null) {
            return "Invalid target account";
        }
        if (sourceAccount.getAccountStatus() != AccountStatus.ACTIVE) {
                return "Source account is not active";
        }
        if (targetAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            return "Target account is not active";
        }
        // Ensure amount is positive
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return "Amount must be positive";
        }
        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            return "Insufficient balance";
        }
        // Ensure source and target accounts are different
        if (sourceAccount.getId().equals(targetAccount.getId())) {
            return "Source and target accounts must be different";
        }
        if (sourceAccount.getCurrency() != targetAccount.getCurrency()) {
            return "Source and target accounts must be in the same currency";
        }
        
        return null;
    }

    public Transfer create(TransferRequest request, UUID userId) {
        Account sourceAccount = getSourceAccountFromRequest(request, userId);
        Account targetAccount = getTargetAccountFromRequest(request);
        BigDecimal amount = request.getAmount();

        // Create and save transfer
        Transfer transfer = Transfer.builder()
                .transferStatus(TransferStatus.PENDING)
                .transferNumber(generateTransferNumber())
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .amount(amount)
                .description(request.getDescription())
                .build();
        transferRepository.save(transfer);

        updateAccounts(sourceAccount, targetAccount, amount);

        transfer.setTransferStatus(TransferStatus.COMPLETED);
        transferRepository.save(transfer);
        
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
        return accountService.getUserAccountById(request.getSourceAccountId(), userId);
    }

    private Account getTargetAccountFromRequest(TransferRequest request) {
        return accountService.getUserAccountByAccountNumber(request.getTargetAccountNumber());
    }

    private String generateTransferNumber() {
        int maxTries = 10;

        for (int i = 0; i < maxTries; i++) {
            String transferNumber = baseEntityService.generateEntityPublicIdentifier("TRF");

            if (!transferRepository.existsByTransferNumber(transferNumber)) {

                return transferNumber;
            }
        }

        throw new IllegalStateException("Unable to generate unique transfer number after " + maxTries + " attempts.");
    }
}