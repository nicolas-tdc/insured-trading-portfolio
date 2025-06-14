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
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AccountRequest request) {

        Account account = accountService.create(request, userDetails.getId());
        URI location = URI.create("/api/account/" + account.getId());

        return ResponseEntity.created(location)
                             .body(AccountResponse.from(account));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AccountResponse>> getUserAccounts(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<Account> accounts = accountService.getUserAccounts(userDetails.getId());
        List<AccountResponse> responses = accounts.stream()
                                                  .map(AccountResponse::from)
                                                  .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping(value = "/{accountId}", produces = "application/json")
    public ResponseEntity<?> getAccount(
        @PathVariable UUID accountId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(AccountResponse.from(
            accountService.getUserAccountById(accountId, userDetails.getId())
        ));
    }

    @GetMapping(value = "/type", produces = "application/json")
    public ResponseEntity<List<?>> getAccountTypes() {

        return ResponseEntity.ok(accountService.getAccountTypes());
    }
}
