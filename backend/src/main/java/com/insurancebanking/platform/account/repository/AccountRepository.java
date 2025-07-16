package com.insurancebanking.platform.account.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurancebanking.platform.account.model.Account;

/**
 * AccountRepository
 *
 * Spring Data JPA repository interface for Account entity.
 * Provides database access methods to retrieve and check accounts.
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {

    /**
     * Finds all accounts belonging to a specific user,
     * ordered by account number in ascending order.
     *
     * @param userId UUID of the user
     * @return list of accounts for the user
     */
    List<Account> findByUser_IdOrderByAccountNumberAsc(UUID userId);

    /**
     * Finds an account by its unique account number.
     *
     * @param accountNumber unique account number string
     * @return optional account matching the account number
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Checks if an account exists with the specified account number.
     *
     * @param accountNumber account number to check for existence
     * @return true if account number exists, false otherwise
     */
    boolean existsByAccountNumber(String accountNumber);
}
