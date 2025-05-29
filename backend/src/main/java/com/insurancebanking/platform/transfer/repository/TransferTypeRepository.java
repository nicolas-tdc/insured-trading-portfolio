package com.insurancebanking.platform.transfer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.transfer.model.TransferType;

public interface TransferTypeRepository extends JpaRepository<TransferType, UUID> { }