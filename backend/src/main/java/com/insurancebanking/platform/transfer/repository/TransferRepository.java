package com.insurancebanking.platform.transfer.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.transfer.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    List<Transfer> findBySourceAccount_Id(UUID accountId);
    List<Transfer> findByTargetAccount_Id(UUID accountId);
}