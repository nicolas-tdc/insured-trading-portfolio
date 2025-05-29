package com.insurancebanking.platform.policy;

import com.insurancebanking.platform.account.AccountService;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;
import com.insurancebanking.platform.policy.repository.PolicyRepository;
import com.insurancebanking.platform.policy.repository.PolicyTypeRepository;

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

    @Autowired
    PolicyTypeRepository policyTypeRepository;

    @Autowired
    UserRepository userRepository;

    public List<Policy> getUserPolicies(UUID userId) {
        return policyRepository.findByUser_Id(userId);
    }

    public Policy getUserPolicyById(UUID policyId, UUID userId) {
        return policyRepository.findById(policyId)
            .filter(a -> a.getUser().getId().equals(userId))
            .orElse(null);
    }

    public List<PolicyType> getPolicyTypes() {
        return policyTypeRepository.findAll();
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