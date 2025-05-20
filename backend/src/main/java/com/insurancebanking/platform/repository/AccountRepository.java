package com.insurancebanking.platform.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.model.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUser_Id(UUID userId);
}