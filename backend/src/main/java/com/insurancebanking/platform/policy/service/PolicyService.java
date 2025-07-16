package com.insurancebanking.platform.policy.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.exception.PolicyCreationException;
import com.insurancebanking.platform.policy.exception.PolicyNotFoundException;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyStatus;
import com.insurancebanking.platform.policy.model.PolicyType;
import com.insurancebanking.platform.policy.repository.PolicyRepository;

import lombok.RequiredArgsConstructor;

/**
 * PolicyService
 *
 * Service layer managing insurance policies for users and their accounts.
 * Supports operations including:
 * - Retrieving policies for a user
 * - Fetching a specific user policy by ID
 * - Listing available policy types
 * - Getting policy numbers linked to an account
 * - Creating new policies with automatic policy number generation and premium calculation
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PolicyService {

    private final BaseEntityService baseEntityService; // Utility service for generating unique IDs
    private final AccountService accountService;       // Service to access account-related data
    private final PolicyRepository policyRepository;   // Repository managing Policy persistence
    private final UserRepository userRepository;       // Repository for user entity access

    /**
     * Retrieves all policies belonging to a specific user,
     * ordered by policy number ascending.
     *
     * @param userId UUID of the user
     * @return List of Policy entities for the user
     */
    public List<Policy> getUserPolicies(UUID userId) {
        return policyRepository.findByUser_IdOrderByPolicyNumberAsc(userId);
    }

    /**
     * Retrieves a single policy by its ID, ensuring it belongs to the specified user.
     *
     * @param policyId UUID of the policy
     * @param userId   UUID of the user requesting the policy
     * @return Policy entity if found and owned by user
     * @throws PolicyNotFoundException if no matching policy is found or not owned by user
     */
    public Policy getUserPolicyById(UUID policyId, UUID userId) {
        return policyRepository.findById(policyId)
                .filter(p -> p.getUser().getId().equals(userId))
                .orElseThrow(() -> new PolicyNotFoundException(policyId));
    }

    /**
     * Retrieves all available policy types supported by the platform.
     *
     * @return List of PolicyType enums
     */
    public List<PolicyType> getPolicyTypes() {
        return List.of(PolicyType.values());
    }

    /**
     * Gets all policy numbers associated with a specific account,
     * ordered ascending.
     *
     * @param accountId UUID of the account
     * @return List of policy number strings linked to the account
     */
    public List<String> getAccountPoliciesNumbers(UUID accountId) {
        return policyRepository.findByAccount_IdOrderByPolicyNumberAsc(accountId)
            .stream()
            .map(Policy::getPolicyNumber)
            .toList();
    }

    /**
     * Creates a new policy based on the provided request and associates it with the given user.
     * Generates a unique policy number and calculates the premium according to the policy type and coverage.
     *
     * @param request PolicyRequest DTO containing creation data
     * @param userId  UUID of the user creating the policy
     * @return The saved Policy entity
     * @throws PolicyCreationException if user is not found or unique policy number generation fails
     */
    public Policy create(PolicyRequest request, UUID userId) {
        // Fetch user entity or throw exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PolicyCreationException("User not found: " + userId));

        // Retrieve user's account and verify ownership
        Account account = accountService.getUserAccountById(request.accountId(), userId);

        // Build a new Policy entity with initialized fields
        Policy policy = Policy.builder()
                .user(user)
                .account(account)
                .policyStatus(PolicyStatus.ACTIVE) // Default to active status on creation
                .policyNumber(generatePolicyNumber()) // Generate unique policy number
                .policyType(PolicyType.fromCode(request.typeCode())) // Map code to enum
                .coverageAmount(request.coverageAmount())
                .premium(calculatePremium(request.typeCode(), request.coverageAmount())) // Calculate premium
                .currencyCode(account.getCurrencyCode()) // Match account currency
                .startDate(Instant.now()) // Start immediately
                .endDate(Instant.now().atZone(ZoneOffset.UTC).plusYears(1).toInstant()) // 1 year policy term
                .build();

        // Persist and return the created policy
        return policyRepository.save(policy);
    }

    /**
     * Generates a unique policy number prefixed with "POL".
     * Retries up to a maximum number of attempts to ensure uniqueness.
     *
     * @return Unique policy number string
     * @throws PolicyCreationException if unable to generate a unique number within attempts
     */
    private String generatePolicyNumber() {
        final int maxAttempts = 10;

        // Attempt to generate a unique policy number
        for (int i = 0; i < maxAttempts; i++) {
            String number = baseEntityService.generateEntityPublicIdentifier("POL");
            if (!policyRepository.existsByPolicyNumber(number)) {
                return number;
            }
        }

        throw new PolicyCreationException("Unable to generate unique policy number after " + maxAttempts + " attempts.");
    }

    /**
     * Calculates the insurance premium based on the policy type code and coverage amount.
     * Uses predefined base rates per policy type.
     *
     * @param accountTypeCode policy type code string
     * @param coverage       coverage amount
     * @return calculated premium amount
     */
    private double calculatePremium(String accountTypeCode, double coverage) {
        double baseRate = switch (accountTypeCode) {
            case "LOSS_PROTECTION" -> 0.01;
            case "PERFORMANCE_SHARE" -> 0.02;
            case "SUBSCRIPTION_BASED" -> 0.03;
            case "CAPITAL_GUARANTEE" -> 0.04;
            case "VOLATILITY_INSURANCE" -> 0.05;
            case "EVENT_INSURANCE" -> 0.06;
            default -> 0.0;
        };

        return coverage * baseRate;
    }
}
