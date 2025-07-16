package com.insuredtrading.portfolio.policy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insuredtrading.portfolio.policy.model.Policy;

/**
 * PolicyRepository
 *
 * Repository interface for managing Policy entities.
 * Extends JpaRepository to provide CRUD operations on policies identified by UUID.
 * Defines custom queries to:
 * - Find policies by user ID ordered by policy number ascending
 * - Find policies by account ID ordered by policy number ascending
 * - Check existence of a policy by its unique policy number
 */
public interface PolicyRepository extends JpaRepository<Policy, UUID> {

    /**
     * Retrieves a list of policies belonging to a specific user,
     * ordered by policy number in ascending order.
     *
     * @param userId UUID of the user
     * @return List of Policy entities for the user
     */
    List<Policy> findByUser_IdOrderByPolicyNumberAsc(UUID userId);

    /**
     * Retrieves a list of policies linked to a specific account,
     * ordered by policy number in ascending order.
     *
     * @param accountId UUID of the account
     * @return List of Policy entities linked to the account
     */
    List<Policy> findByAccount_IdOrderByPolicyNumberAsc(UUID accountId);

    /**
     * Checks if a policy exists with the given policy number.
     *
     * @param policyNumber the unique policy number to check
     * @return true if a policy with the policyNumber exists, false otherwise
     */
    boolean existsByPolicyNumber(String policyNumber);
}
