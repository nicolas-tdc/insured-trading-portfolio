package com.insurancebanking.platform.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.dto.common.MessageResponse;
import com.insurancebanking.platform.dto.policy.PolicyRequest;
import com.insurancebanking.platform.dto.policy.PolicyResponse;
import com.insurancebanking.platform.model.Policy;
import com.insurancebanking.platform.security.UserDetailsImpl;
import com.insurancebanking.platform.service.AccountService;
import com.insurancebanking.platform.service.PolicyService;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    @Autowired
    PolicyService policyService;

    @Autowired
    AccountService accountService;

    @PostMapping("")
    public ResponseEntity<?> createPolicy(
        @RequestBody @NonNull PolicyRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Create policy
            Policy policy = policyService.create(request, userDetails.getId());

            // Create new policy URI
            URI location = new URI("/api/policy/" + policy.getId());

            return ResponseEntity.created(location)
                .body(PolicyResponse.from(policy));

        } catch (URISyntaxException e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Invalid policy URI:" + e.getMessage()));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error creating policy: " + e.getMessage()));
        }
    }

    @GetMapping("/user-list")
    public ResponseEntity<?> getUserList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Get policies responses
            List<Policy> policies = policyService.getUserPolicies(userDetails.getId());
            List<PolicyResponse> responses = policies.stream()
                .map(PolicyResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting policies: " + e.getMessage()));
        }
    }
}