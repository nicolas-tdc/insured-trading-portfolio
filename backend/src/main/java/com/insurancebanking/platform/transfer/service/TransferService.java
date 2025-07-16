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
import com.insurancebanking.platform.transfer.dto.TransferValidateExternalRequest;
import com.insurancebanking.platform.transfer.dto.TransferValidateInternalRequest;
import com.insurancebanking.platform.transfer.exception.TransferCreationException;
import com.insurancebanking.platform.transfer.exception.TransferValidationException;
import com.insurancebanking.platform.transfer.model.Transfer;
import com.insurancebanking.platform.transfer.repository.TransferRepository;
import com.insurancebanking.platform.account.model.AccountStatus;
import com.insurancebanking.platform.transfer.model.TransferStatus;
import com.insurancebanking.platform.account.model.AccountType;

/**
 * Service class responsible for handling transfer-related operations.
 * Includes validation, creation, and updating accounts involved in transfers.
 */
@Service
@Transactional
public class TransferService {

    @Autowired private BaseEntityService baseEntityService;
    @Autowired private TransferRepository transferRepository;
    @Autowired private AccountService accountService;
    @Autowired private AccountRepository accountRepository;

    /**
     * Validates accounts involved in an internal transfer for a given user.
     *
     * @param request the internal transfer validation request
     * @param userId the user performing the transfer
     * @return the validated target account
     * @throws TransferValidationException if validation rules fail
     */
    public Account validateInternalTransferAccounts(TransferValidateInternalRequest request, UUID userId) {
        Account source = accountService.getUserAccountById(request.sourceAccountId(), userId);
        Account target = accountService.getUserAccountById(request.targetAccountId(), userId);

        validateInternalTransferAccountsRules(source, target);
        validateTransferAccountsRules(source, target);

        return target;
    }

    /**
     * Validates accounts involved in an external transfer for a given user.
     *
     * @param request the external transfer validation request
     * @param userId the user performing the transfer
     * @return the validated target account
     * @throws TransferValidationException if validation rules fail
     */
    public Account validateExternalTransferAccounts(TransferValidateExternalRequest request, UUID userId) {
        Account source = accountService.getUserAccountById(request.sourceAccountId(), userId);
        Account target = accountService.getAccountByAccountNumber(request.targetAccountNumber());

        validateExternalTransferAccountsRules(source, target);
        validateTransferAccountsRules(source, target);

        return target;
    }

    /**
     * Creates a transfer record and updates the involved accounts accordingly.
     *
     * @param request the transfer request containing details
     * @param userId the user performing the transfer
     * @return the completed Transfer entity
     * @throws TransferValidationException if validation fails
     * @throws TransferCreationException if transfer number generation fails
     */
    public Transfer createTransfer(TransferRequest request, UUID userId) {
        Account source = accountService.getUserAccountById(request.sourceAccountId(), userId);
        Account target = accountService.getAccountByAccountNumber(request.targetAccountNumber());

        validateTransferAccountsRules(source, target);
        validateTransferDetailsRules(request, source, target);

        String transferNumber = generateTransferNumber();

        Transfer transfer = Transfer.builder()
            .transferStatus(TransferStatus.PENDING)
            .transferNumber(transferNumber)
            .currencyCode(source.getCurrencyCode())
            .sourceAccount(source)
            .targetAccount(target)
            .amount(request.amount())
            .description(request.description())
            .build();

        transferRepository.save(transfer);

        updateAccounts(source, target, request.amount());

        // Mark transfer as completed after updating accounts
        transfer.setTransferStatus(TransferStatus.COMPLETED);
        return transferRepository.save(transfer);
    }

    /**
     * Validates general transfer account rules such as existence, status,
     * difference between accounts, and currency consistency.
     *
     * @param source the source account
     * @param target the target account
     * @throws TransferValidationException if any rule is violated
     */
    private void validateTransferAccountsRules(Account source, Account target) {
        // Accounts must exist
        if (source == null) {
            throw new TransferValidationException("Source account not found");
        }
        if (target == null) {
            throw new TransferValidationException("Target account not found");
        }
        // Accounts must be active
        if (!AccountStatus.ACTIVE.equals(source.getAccountStatus())) {
            throw new TransferValidationException("Source account is not active");
        }
        if (!AccountStatus.ACTIVE.equals(target.getAccountStatus())) {
            throw new TransferValidationException("Target account is not active");
        }
        // Accounts must be different
        if (source.getId().equals(target.getId())) {
            throw new TransferValidationException("Source and target accounts must be different");
        }
        // Accounts must use same currency
        if (!source.getCurrencyCode().equals(target.getCurrencyCode())) {
            throw new TransferValidationException("Source and target accounts must have the same currency");
        }
    }

    /**
     * Validates that internal transfer accounts belong to the same user.
     *
     * @param source the source account
     * @param target the target account
     * @throws TransferValidationException if accounts belong to different users
     */
    private void validateInternalTransferAccountsRules(Account source, Account target) {
        if (source.getUser() != target.getUser()) {
            throw new TransferValidationException("Source and target accounts must belong to the same user");
        }
    }

    /**
     * Validates that external transfer accounts belong to different users,
     * and that the source account is a checking account.
     *
     * @param source the source account
     * @param target the target account
     * @throws TransferValidationException if validation rules are violated
     */
    private void validateExternalTransferAccountsRules(Account source, Account target) {
        if (source.getUser() == target.getUser()) {
            throw new TransferValidationException("Source and target accounts must belong to different users");
        }
        if (source.getAccountType() != AccountType.CHECKING) {
            throw new TransferValidationException("Source account must be a checking account for external transfers");
        }
    }

    /**
     * Validates the transfer details such as positive amount and sufficient balance.
     *
     * @param request the transfer request details
     * @param source the source account
     * @param target the target account
     * @throws TransferValidationException if any detail rule is violated
     */
    private void validateTransferDetailsRules(TransferRequest request, Account source, Account target) {
        // Amount must be positive
        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransferValidationException("Transfer amount must be positive");
        }
        // Source account must have enough balance
        if (source.getBalance().compareTo(request.amount()) < 0) {
            throw new TransferValidationException("Insufficient balance");
        }
    }

    /**
     * Updates the balances of source and target accounts and persists them.
     *
     * @param source the source account
     * @param target the target account
     * @param amount the amount to transfer
     */
    private void updateAccounts(Account source, Account target, BigDecimal amount) {
        // Deduct amount from source balance
        source.setBalance(source.getBalance().subtract(amount));
        // Add amount to target balance
        target.setBalance(target.getBalance().add(amount));

        // Persist changes
        accountRepository.save(source);
        accountRepository.save(target);
    }

    /**
     * Generates a unique transfer number using the BaseEntityService.
     * Retries a fixed number of times to ensure uniqueness.
     *
     * @return a unique transfer number
     * @throws TransferCreationException if unable to generate a unique number
     */
    private String generateTransferNumber() {
        int maxTries = 10;
        for (int i = 0; i < maxTries; i++) {
            String transferNumber = baseEntityService.generateEntityPublicIdentifier("TRF");
            if (!transferRepository.existsByTransferNumber(transferNumber)) return transferNumber;
        }
        throw new TransferCreationException("Failed to generate unique transfer number after retries");
    }
}
