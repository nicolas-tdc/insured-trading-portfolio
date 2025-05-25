package com.insurancebanking.platform.service;

import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.Policy;
import com.insurancebanking.platform.model.User;
import com.insurancebanking.platform.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PolicyService {

    @Autowired
    PolicyRepository policyRepository;

    public List<Policy> getUserPolicies(User user) {
        return policyRepository.findByUser_Id(user.getId());
    }

    public Policy applyForPolicy(User user, String type, Double coverageAmount, Account account) {
        Policy policy = Policy.builder()
                .user(user)
                .policyNumber("POL" + UUID.randomUUID().toString().substring(0, 9).toUpperCase())
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