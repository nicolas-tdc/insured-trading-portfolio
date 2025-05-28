package com.insurancebanking.platform.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.model.TransferType;

public interface TransferTypeRepository extends JpaRepository<TransferType, UUID> { }