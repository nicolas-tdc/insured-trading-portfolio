package com.insurancebanking.platform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.Policy;
import com.insurancebanking.platform.model.User;
import com.insurancebanking.platform.security.UserDetailsImpl;
import com.insurancebanking.platform.service.AccountService;
import com.insurancebanking.platform.service.PolicyService;

@RestController
@RequestMapping("/api/insurance")
public class PolicyController {

    @Autowired
    PolicyService policyService;

    @Autowired
    AccountService accountService;

    @GetMapping("/policies")
    public ResponseEntity<List<Policy>> getUserPolicies(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = new User();
        user.setId(userDetails.getId());
        return ResponseEntity.ok(policyService.getUserPolicies(user));
    }

    @PostMapping("/apply-policy")
    public ResponseEntity<?> applyPolicy(
            @RequestParam String type,
            @RequestParam Double coverageAmount,
            @RequestParam String accountId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = new User();
        UUID userId = userDetails.getId();
        user.setId(userId);

        Account account = accountService.getAccountById(accountId, userId);

        Policy policy = policyService.applyForPolicy(user, type, coverageAmount, account);

        return ResponseEntity.ok(policy);
    }
}