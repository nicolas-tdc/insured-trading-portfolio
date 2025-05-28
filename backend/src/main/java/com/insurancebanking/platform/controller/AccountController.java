package com.insurancebanking.platform.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

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

import com.insurancebanking.platform.dto.account.AccountRequest;
import com.insurancebanking.platform.dto.account.AccountResponse;
import com.insurancebanking.platform.dto.common.MessageResponse;
import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.repository.AccountRepository;
import com.insurancebanking.platform.security.UserDetailsImpl;
import com.insurancebanking.platform.service.AccountService;

import io.micrometer.common.lang.NonNull;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    // Create account
    @PostMapping(value="", produces="application/json")
    public ResponseEntity<?> createAccount(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @NonNull AccountRequest request) {
        try {
            // Create account
            Account newAccount = accountService.create(request, userDetails.getId());

            // Create new account URI
            URI location = new URI("/api/account/" + newAccount.getId());

            return ResponseEntity.created(location)
                .body(AccountResponse.from(newAccount));

        } catch (URISyntaxException e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Invalid account URI:" + e.getMessage()));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error creating account:" + e.getMessage()));
        }
    }

    @GetMapping(value="", produces="application/json")
    public ResponseEntity<?> getUserAccounts(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Get user's accounts
            List<Account> accounts = accountRepository.findByUser_Id(userDetails.getId());

            // Get accounts responses
            List<AccountResponse> responses = accounts.stream()
                .map(AccountResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting user accounts: " + e.getMessage()));
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

                return ResponseEntity.badRequest()
                    .body(List.of("Invalid account id"));
            }

            return ResponseEntity.ok(AccountResponse.from(account));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting account: " + e.getMessage()));
        }
    }

    @GetMapping(value="/type", produces="application/json")
    public ResponseEntity<?> getAccountTypes() {
        try {
            return ResponseEntity.ok(accountService.getAccountTypes());

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting account types: " + e.getMessage()));
        }
    }
}