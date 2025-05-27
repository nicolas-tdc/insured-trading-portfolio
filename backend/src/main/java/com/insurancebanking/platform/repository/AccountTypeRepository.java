package com.insurancebanking.platform.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.model.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, UUID> { }