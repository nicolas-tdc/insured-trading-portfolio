package com.insurancebanking.platform.account.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurancebanking.platform.account.dto.AccountRequest;
import com.insurancebanking.platform.account.dto.AccountResponse;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.auth.security.AuthEntryPointJwt;
import com.insurancebanking.platform.core.dto.MessageResponse;

import io.micrometer.common.lang.NonNull;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    // Create account
    @PostMapping(value="", produces="application/json")
    public ResponseEntity<?> createAccount(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @NonNull AccountRequest request) {

        try {
            // Create account
            Account account = accountService.create(request, userDetails.getId());
            // Create new account URI
            URI location = new URI("/api/account/" + account.getId());

            return ResponseEntity.created(location)
                .body(AccountResponse.from(account));

        } catch (URISyntaxException e) {
            String errorMessage = "Error creating account URI";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));

        } catch (Exception e) {
            String errorMessage = "Error creating account";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value="", produces="application/json")
    public ResponseEntity<?> getUserAccounts(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            // Get user's accounts
            List<Account> accounts = accountService.getUserAccounts(userDetails.getId());
            // Get accounts responses
            List<AccountResponse> responses = accounts.stream()
                .map(AccountResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            String errorMessage = "Error getting user accounts";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value="/{accountId}", produces="application/json")
    public ResponseEntity<?> getAccount(
        @PathVariable @NonNull UUID accountId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            // Check if account exists
            Account account = accountService.getUserAccountById(
                accountId, userDetails.getId());
            if (account == null) {
                String errorMessage = "Error getting account: Account not found";
                logger.error(errorMessage);

                return ResponseEntity.badRequest()
                    .body(new MessageResponse(errorMessage));
            }

            return ResponseEntity.ok(AccountResponse.from(account));

        } catch (Exception e) {
            String errorMessage = "Error getting account";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value="/type", produces="application/json")
    public ResponseEntity<?> getAccountTypes() {

        try {
            return ResponseEntity.ok(accountService.getAccountTypes());

        } catch (Exception e) {
            String errorMessage = "Error getting account types";
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}