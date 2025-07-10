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

@Service
@Transactional
@RequiredArgsConstructor
public class PolicyService {

    private final BaseEntityService baseEntityService;
    private final AccountService accountService;
    private final PolicyRepository policyRepository;
    private final UserRepository userRepository;

    public List<Policy> getUserPolicies(UUID userId) {
        return policyRepository.findByUser_IdOrderByPolicyNumberAsc(userId);
    }

    public Policy getUserPolicyById(UUID policyId, UUID userId) {
        return policyRepository.findById(policyId)
                .filter(p -> p.getUser().getId().equals(userId))
                .orElseThrow(() -> new PolicyNotFoundException(policyId));
    }

    public List<PolicyType> getPolicyTypes() {
        return List.of(PolicyType.values());
    }

    public List<String> getAccountPoliciesNumbers(UUID accountId) {
        return policyRepository.findByAccount_IdOrderByPolicyNumberAsc(accountId)
            .stream()
            .map(Policy::getPolicyNumber)
            .toList();
    }

    public Policy create(PolicyRequest request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PolicyCreationException("User not found: " + userId));

        Account account = accountService.getUserAccountById(request.accountId(), userId);

        Policy policy = Policy.builder()
                .user(user)
                .account(account)
                .policyStatus(PolicyStatus.ACTIVE)
                .policyNumber(generatePolicyNumber())
                .policyType(PolicyType.fromCode(request.typeCode()))
                .coverageAmount(request.coverageAmount())
                .premium(calculatePremium(request.typeCode(), request.coverageAmount()))
                .currencyCode(account.getCurrencyCode())
                .startDate(Instant.now())
                .endDate(Instant.now().atZone(ZoneOffset.UTC).plusYears(1).toInstant())
                .build();

        return policyRepository.save(policy);
    }

    private String generatePolicyNumber() {
        final int maxAttempts = 10;

        for (int i = 0; i < maxAttempts; i++) {
            String number = baseEntityService.generateEntityPublicIdentifier("POL");
            if (!policyRepository.existsByPolicyNumber(number)) {
                return number;
            }
        }

        throw new PolicyCreationException("Unable to generate unique policy number after " + maxAttempts + " attempts.");
    }

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
