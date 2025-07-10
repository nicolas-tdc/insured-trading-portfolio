package com.insurancebanking.platform.policy.controller;

import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.dto.PolicyResponse;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;
import com.insurancebanking.platform.policy.dto.PolicyTypeResponse;
import com.insurancebanking.platform.policy.service.PolicyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/policy")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Slf4j
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(
        @Valid @RequestBody PolicyRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Policy policy = policyService.create(request, userDetails.getId());
        URI location = URI.create("/api/policy/" + policy.getId());
        return ResponseEntity.created(location).body(PolicyResponse.from(policy));
    }

    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getUserPolicies(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<PolicyResponse> responses = policyService.getUserPolicies(userDetails.getId())
                                                      .stream()
                                                      .map(PolicyResponse::from)
                                                      .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{policyId}")
    public ResponseEntity<PolicyResponse> getPolicyById(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID policyId
    ) {
        Policy policy = policyService.getUserPolicyById(policyId, userDetails.getId());
        return ResponseEntity.ok(PolicyResponse.from(policy));
    }

    @GetMapping("/type")
    public ResponseEntity<List<PolicyTypeResponse>> getPolicyTypes() {
        List<PolicyTypeResponse> policyTypeResponses = policyService.getPolicyTypes()
            .stream()
            .map(PolicyTypeResponse::from)
            .toList();

        return ResponseEntity.ok(policyTypeResponses);
    }
}
