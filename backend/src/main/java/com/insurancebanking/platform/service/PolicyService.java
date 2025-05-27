package com.insurancebanking.platform.service;

import com.insurancebanking.platform.dto.policy.PolicyRequest;
import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.Policy;
import com.insurancebanking.platform.model.User;
import com.insurancebanking.platform.repository.PolicyRepository;
import com.insurancebanking.platform.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PolicyService {

    @Autowired
    AccountService accountService;

    @Autowired
    PolicyRepository policyRepository;

    @Autowired UserRepository userRepository;

    public List<Policy> getUserPolicies(UUID userId) {
        return policyRepository.findByUser_Id(userId);
    }

    public Policy create(PolicyRequest request, UUID userId) {
        String type = request.getType();
        Double coverageAmount = request.getCoverageAmount();

        // Get user and account
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Account account = accountService.getUserAccountById(request.getAccountId(), userId);

        // Create policy
        Policy policy = Policy.builder()
            .user(user)
            .policyNumber(UUID.randomUUID().toString().substring(0, 9).toUpperCase())
            .type(type)
            .coverageAmount(coverageAmount)
            .premium(calculatePremium(type, coverageAmount))
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusYears(1))
            .status("pending")
            .account(account)
            .build();

        return policyRepository.save(policy);
    }

    private Double calculatePremium(String type, Double coverage) {
        double baseRate = switch (type.toLowerCase()) {
            case "life" -> 0.01;
            case "auto" -> 0.02;
            case "home" -> 0.015;
            default -> 0.01;
        };

        return coverage * baseRate;
    }
}