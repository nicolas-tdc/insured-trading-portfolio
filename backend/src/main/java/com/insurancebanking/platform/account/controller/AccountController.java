package com.insurancebanking.platform.account.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.insurancebanking.platform.account.dto.AccountRequest;
import com.insurancebanking.platform.account.dto.AccountResponse;
import com.insurancebanking.platform.account.dto.AccountTypeResponse;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.policy.service.PolicyService;

import jakarta.validation.Valid;

/**
 * AccountController
 *
 * REST controller managing user accounts in the insurance banking platform.
 * Provides authenticated endpoints to:
 * - Create a new account
 * - Retrieve user's accounts
 * - Get details of a specific account
 * - List supported account types
 */
@RestController
@RequestMapping("/api/account")
@PreAuthorize("isAuthenticated()") // Require authentication for all methods
public class AccountController {

    private final AccountService accountService; // Handles business logic related to accounts
    private final PolicyService policyService;   // Provides linked policy data for accounts

    /**
     * Constructor with dependency injection for account and policy services.
     *
     * @param accountService service for account-related operations
     * @param policyService  service to fetch policies linked to accounts
     */
    public AccountController(AccountService accountService, PolicyService policyService) {
        this.accountService = accountService;
        this.policyService = policyService;
    }

    /**
     * POST /api/account
     *
     * Creates a new account for the authenticated user.
     * Also fetches policy numbers linked to the newly created account.
     *
     * @param userDetails authenticated user's details
     * @param request     data for creating the new account
     * @return HTTP 201 response with created account and location header
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<AccountResponse> createAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AccountRequest request) {

        // Create account and generate its location URI
        Account account = accountService.create(request, userDetails.getId());
        URI location = URI.create("/api/account/" + account.getId());

        // Fetch associated policy numbers
        List<String> policies = policyService.getAccountPoliciesNumbers(account.getId());

        // Build and return account response DTO
        AccountResponse response = AccountResponse.from(account, policies);

        return ResponseEntity.created(location).body(response);
    }

    /**
     * GET /api/account
     *
     * Retrieves all accounts owned by the authenticated user,
     * each including its associated policy numbers.
     *
     * @param userDetails authenticated user's details
     * @return list of AccountResponse objects
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AccountResponse>> getUserAccounts(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Fetch user's accounts
        List<Account> accounts = accountService.getUserAccounts(userDetails.getId());

        // Map each account to AccountResponse including policies
        List<AccountResponse> responses = accounts.stream()
            .map(account -> {
                List<String> policies = policyService.getAccountPoliciesNumbers(account.getId());
                return AccountResponse.from(account, policies);
            })
            .toList();

        return ResponseEntity.ok(responses);
    }

    /**
     * GET /api/account/{accountId}
     *
     * Retrieves details of a specific account,
     * ensuring it belongs to the authenticated user.
     *
     * @param accountId   UUID of the requested account
     * @param userDetails authenticated user's details
     * @return AccountResponse with account details and linked policies
     */
    @GetMapping(value = "/{accountId}", produces = "application/json")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable UUID accountId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Fetch account and verify ownership
        Account account = accountService.getUserAccountById(accountId, userDetails.getId());

        // Fetch associated policy numbers
        List<String> policies = policyService.getAccountPoliciesNumbers(account.getId());

        // Build response DTO
        AccountResponse response = AccountResponse.from(account, policies);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/account/type
     *
     * Lists all available account types supported by the platform.
     *
     * @return list of AccountTypeResponse objects
     */
    @GetMapping(value = "/type", produces = "application/json")
    public ResponseEntity<List<AccountTypeResponse>> getAccountTypes() {

        // Fetch and map account types to response DTOs
        List<AccountTypeResponse> accountTypeResponses = accountService.getAccountTypes()
            .stream()
            .map(AccountTypeResponse::from)
            .toList();

        return ResponseEntity.ok(accountTypeResponses);
    }
}