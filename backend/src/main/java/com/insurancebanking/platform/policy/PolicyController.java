package com.insurancebanking.platform.policy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.account.AccountService;
import com.insurancebanking.platform.auth.AuthController;
import com.insurancebanking.platform.auth.security.UserDetailsImpl;
import com.insurancebanking.platform.core.dto.MessageResponse;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.dto.PolicyResponse;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/policy")
public class PolicyController {

    @Autowired
    PolicyService policyService;

    @Autowired
    AccountService accountService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> createPolicy(
        @RequestBody @NonNull PolicyRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String errorMessage = "Error creating policy";
        try {
            // Create policy
            Policy policy = policyService.create(request, userDetails.getId());

            // Create new policy URI
            URI location = new URI("/api/policy/" + policy.getId());

            return ResponseEntity.created(location)
                .body(PolicyResponse.from(policy));

        } catch (URISyntaxException e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> getUserList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String errorMessage = "Error getting policies";
        try {
            // Get policies responses
            List<Policy> policies = policyService.getUserPolicies(userDetails.getId());
            List<PolicyResponse> responses = policies.stream()
                .map(PolicyResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value = "/{policyId}", produces = "application/json")
    public ResponseEntity<?> getPolicy(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable @NonNull UUID policyId) {

        String errorMessage = "Error getting policy";
        try {

            // Check if policy exists
            Policy policy = policyService.getUserPolicyById(
                policyId, userDetails.getId());
            if (policy == null) {

                return ResponseEntity.badRequest()
                    .body(new MessageResponse(errorMessage + ": Policy not found"));
            }

            return ResponseEntity.ok(PolicyResponse.from(policy));

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value = "/type", produces = "application/json")
    public ResponseEntity<?> getPolicyTypes() {

        String errorMessage = "Error getting policy types";
        try {
            List<PolicyType> policyTypes = policyService.getPolicyTypes();

            return ResponseEntity.ok(policyTypes);

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}