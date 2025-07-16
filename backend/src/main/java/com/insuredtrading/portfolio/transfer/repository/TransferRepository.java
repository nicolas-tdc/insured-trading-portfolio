package com.insuredtrading.portfolio.transfer.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insuredtrading.portfolio.transfer.model.Transfer;

/**
 * Repository interface for managing Transfer entities.
 * Provides methods to find transfers by source or target account IDs
 * and to check existence by transfer number.
 */
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    /**
     * Finds all transfers where the given account is the source.
     *
     * @param accountId the source account ID
     * @return list of transfers with the specified source account
     */
    List<Transfer> findBySourceAccount_Id(UUID accountId);

    /**
     * Finds all transfers where the given account is the target.
     *
     * @param accountId the target account ID
     * @return list of transfers with the specified target account
     */
    List<Transfer> findByTargetAccount_Id(UUID accountId);

    /**
     * Checks if a transfer with the specified transfer number exists.
     *
     * @param transferNumber the transfer number to check
     * @return true if a transfer with the given number exists, false otherwise
     */
    boolean existsByTransferNumber(String transferNumber);
}
