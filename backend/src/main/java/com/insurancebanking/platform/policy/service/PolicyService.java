package com.insurancebanking.platform.policy.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;
import com.insurancebanking.platform.policy.model.PolicyStatus;
import com.insurancebanking.platform.policy.repository.PolicyRepository;

@Service
@Transactional
public class PolicyService {

    @Autowired
    private BaseEntityService baseEntityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Policy> getUserPolicies(UUID userId) {
        return policyRepository.findByUser_Id(userId);
    }

    public Policy getUserPolicyById(UUID policyId, UUID userId) {
        return policyRepository.findById(policyId)
            .filter(a -> a.getUser().getId().equals(userId))
            .orElse(null);
    }

    public List<PolicyType> getPolicyTypes() {
        return Arrays.stream(PolicyType.values()).toList();
    }

    public Policy create(PolicyRequest request, UUID userId) {
        PolicyType policyType = request.getPolicyType();
        Double coverageAmount = request.getCoverageAmount();

        // Get user and account
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Account account = accountService.getUserAccountById(request.getAccountId(), userId);

        // Create policy
        Policy policy = Policy.builder()
            .user(user)
            .policyStatus(PolicyStatus.PENDING)
            .policyNumber(generatePolicyNumber())
            .policyType(policyType)
            .coverageAmount(coverageAmount)
            .premium(calculatePremium(policyType, coverageAmount))
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusYears(1))
            .account(account)
            .build();

        return policyRepository.save(policy);
    }

    private String generatePolicyNumber() {
        int maxTries = 10;

        for (int i = 0; i < maxTries; i++) {
            String policyNumber = baseEntityService.generateEntityPublicIdentifier("POL");

            boolean exists = policyRepository.existsByPolicyNumber(policyNumber);
            if (!exists) {

                return policyNumber;
            }
        }

        throw new IllegalStateException("Unable to generate unique account number after " + maxTries + " attempts.");
    }

    private Double calculatePremium(PolicyType policyType, Double coverage) {
        double baseRate = switch (policyType) {
            case LIFE   -> 0.01;
            case HEALTH -> 0.02;
            case HOME   -> 0.015;
            case AUTO   -> 0.025;
            default -> 0.01;
        };

        return coverage * baseRate;
    }
}