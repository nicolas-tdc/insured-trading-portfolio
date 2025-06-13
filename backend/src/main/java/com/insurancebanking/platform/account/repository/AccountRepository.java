package com.insurancebanking.platform.account.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.account.model.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByUser_Id(UUID userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}