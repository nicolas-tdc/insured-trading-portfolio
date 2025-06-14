package com.insurancebanking.platform.transfer.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.transfer.dto.TransferRequest;
import com.insurancebanking.platform.transfer.exception.TransferCreationException;
import com.insurancebanking.platform.transfer.exception.TransferValidationException;
import com.insurancebanking.platform.transfer.model.Transfer;
import com.insurancebanking.platform.transfer.repository.TransferRepository;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.transfer.model.TransferStatus;

@Service
@Transactional
public class TransferService {

    @Autowired private BaseEntityService baseEntityService;
    @Autowired private TransferRepository transferRepository;
    @Autowired private AccountService accountService;
    @Autowired private AccountRepository accountRepository;

    public Transfer createTransfer(TransferRequest request, UUID userId) {
        Account source = getSourceAccount(request, userId);
        Account target = getTargetAccount(request);

        validateTransferRules(request, source, target);

        String transferNumber = generateTransferNumber();

        Transfer transfer = Transfer.builder()
            .transferStatus(TransferStatus.PENDING)
            .transferNumber(transferNumber)
            .currencyCode(source.getCurrencyCode())
            .sourceAccount(source)
            .targetAccount(target)
            .amount(request.getAmount())
            .description(request.getDescription())
            .build();

        transferRepository.save(transfer);

        updateAccounts(source, target, request.getAmount());

        transfer.setTransferStatus(TransferStatus.COMPLETED);
        return transferRepository.save(transfer);
    }

    private void validateTransferRules(TransferRequest request, Account source, Account target) {
        if (source == null) {
            throw new TransferValidationException("Invalid source account");
        }
        if (target == null) {
            throw new TransferValidationException("Invalid target account");
        }

        if (!AccountStatus.ACTIVE.equals(source.getAccountStatus())) {
            throw new TransferValidationException("Source account is not active");
        }

        if (!AccountStatus.ACTIVE.equals(target.getAccountStatus())) {
            throw new TransferValidationException("Target account is not active");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransferValidationException("Transfer amount must be positive");
        }

        if (source.getBalance().compareTo(request.getAmount()) < 0) {
            throw new TransferValidationException("Insufficient balance");
        }

        if (source.getId().equals(target.getId())) {
            throw new TransferValidationException("Source and target accounts must be different");
        }

        if (!source.getCurrencyCode().equals(target.getCurrencyCode())) {
            throw new TransferValidationException("Currency mismatch between source and target accounts");
        }
    }

    private Account getSourceAccount(TransferRequest request, UUID userId) {
        return accountService.getUserAccountById(request.getSourceAccountId(), userId);
    }

    private Account getTargetAccount(TransferRequest request) {
        return accountService.getUserAccountByAccountNumber(request.getTargetAccountNumber());
    }

    private void updateAccounts(Account source, Account target, BigDecimal amount) {
        source.setBalance(source.getBalance().subtract(amount));
        target.setBalance(target.getBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(target);
    }

    private String generateTransferNumber() {
        int maxTries = 10;
        for (int i = 0; i < maxTries; i++) {
            String transferNumber = baseEntityService.generateEntityPublicIdentifier("TRF");
            if (!transferRepository.existsByTransferNumber(transferNumber)) return transferNumber;
        }
        throw new TransferCreationException("Failed to generate unique transfer number after retries");
    }
}
