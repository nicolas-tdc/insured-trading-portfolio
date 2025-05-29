package com.insurancebanking.platform.account.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.account.model.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, UUID> { }