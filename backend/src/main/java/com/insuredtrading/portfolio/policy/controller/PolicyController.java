package com.insuredtrading.portfolio.policy.controller;

import com.insuredtrading.portfolio.auth.model.UserDetailsImpl;
import com.insuredtrading.portfolio.policy.dto.PolicyRequest;
import com.insuredtrading.portfolio.policy.dto.PolicyResponse;
import com.insuredtrading.portfolio.policy.model.Policy;
import com.insuredtrading.portfolio.policy.dto.PolicyTypeResponse;
import com.insuredtrading.portfolio.policy.service.PolicyService;

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

/**
 * REST controller for managing insurance policies.
 * Handles policy creation, retrieval of user's policies, 
 * individual policy fetching, and retrieval of available policy types.
 */
@RestController
@RequestMapping("/api/policy")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Slf4j
public class PolicyController {

    private final PolicyService policyService;

    /**
     * Creates a new policy for the authenticated user.
     *
     * @param request the policy request data, validated
     * @param userDetails the authenticated user's details
     * @return ResponseEntity with created policy and location header
     */
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(
        @Valid @RequestBody PolicyRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Delegate policy creation to service layer with user id
        Policy policy = policyService.create(request, userDetails.getId());

        // Build URI for newly created resource location
        URI location = URI.create("/api/policy/" + policy.getId());

        // Return HTTP 201 Created with body containing the created policy DTO
        return ResponseEntity.created(location).body(PolicyResponse.from(policy));
    }

    /**
     * Retrieves all policies belonging to the authenticated user.
     *
     * @param userDetails the authenticated user's details
     * @return ResponseEntity with list of policy DTOs
     */
    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getUserPolicies(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Fetch user's policies and convert to response DTOs
        List<PolicyResponse> responses = policyService.getUserPolicies(userDetails.getId())
                                                      .stream()
                                                      .map(PolicyResponse::from)
                                                      .toList();

        // Return HTTP 200 OK with the list of policies
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a specific policy by its ID for the authenticated user.
     *
     * @param userDetails the authenticated user's details
     * @param policyId the UUID of the policy to retrieve
     * @return ResponseEntity with the policy DTO
     */
    @GetMapping("/{policyId}")
    public ResponseEntity<PolicyResponse> getPolicyById(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID policyId
    ) {
        // Fetch the specific policy for user
        Policy policy = policyService.getUserPolicyById(policyId, userDetails.getId());

        // Return HTTP 200 OK with policy DTO
        return ResponseEntity.ok(PolicyResponse.from(policy));
    }

    /**
     * Retrieves the list of available policy types.
     *
     * @return ResponseEntity with list of policy type DTOs
     */
    @GetMapping("/type")
    public ResponseEntity<List<PolicyTypeResponse>> getPolicyTypes() {
        // Fetch all policy types and convert to response DTOs
        List<PolicyTypeResponse> policyTypeResponses = policyService.getPolicyTypes()
            .stream()
            .map(PolicyTypeResponse::from)
            .toList();

        // Return HTTP 200 OK with policy types
        return ResponseEntity.ok(policyTypeResponses);
    }
}
