package com.insurancebanking.platform.policy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.account.AccountService;
import com.insurancebanking.platform.auth.security.UserDetailsImpl;
import com.insurancebanking.platform.core.dto.MessageResponse;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.dto.PolicyResponse;
import com.insurancebanking.platform.policy.model.Policy;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    @Autowired
    PolicyService policyService;

    @Autowired
    AccountService accountService;

    @PostMapping(value = "", produces = "application/json")
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

    @GetMapping(value = "", produces = "application/json")
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

    @GetMapping(value = "/{policyId}", produces = "application/json")
    public ResponseEntity<?> getPolicy(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable @NonNull UUID policyId) {
        try {

            // Check if policy exists
            Policy policy = policyService.getUserPolicyById(
                policyId, userDetails.getId());
            if (policy == null) {

                return ResponseEntity.badRequest()
                    .body(List.of("Invalid account id"));
            }

            return ResponseEntity.ok(
                policyService.getUserPolicyById(policyId, userDetails.getId()));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting policy: " + e.getMessage()));
        }
    }

    @GetMapping(value = "/type", produces = "application/json")
    public ResponseEntity<?> getPolicyTypes() {
        try {
            return ResponseEntity.ok(policyService.getPolicyTypes());

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting policy types: " + e.getMessage()));
        }
    }
}