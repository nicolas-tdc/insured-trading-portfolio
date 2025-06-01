package com.insurancebanking.platform.account;

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
import com.insurancebanking.platform.account.repository.AccountRepository;
import com.insurancebanking.platform.auth.security.AuthEntryPointJwt;
import com.insurancebanking.platform.auth.security.UserDetailsImpl;
import com.insurancebanking.platform.core.dto.MessageResponse;

import io.micrometer.common.lang.NonNull;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    // Create account
    @PostMapping(value="", produces="application/json")
    public ResponseEntity<?> createAccount(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @NonNull AccountRequest request) {

        String errorMessage = "Error creating account";
        try {
            // Create account
            Account account = accountService.create(request, userDetails.getId());

            // Create new account URI
            URI location = new URI("/api/account/" + account.getId());

            return ResponseEntity.created(location)
                .body(AccountResponse.from(account));

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

    @GetMapping(value="", produces="application/json")
    public ResponseEntity<?> getUserAccounts(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String errorMessage = "Error getting user accounts";
        try {
            // Get user's accounts
            List<Account> accounts = accountRepository.findByUser_Id(userDetails.getId());

            // Get accounts responses
            List<AccountResponse> responses = accounts.stream()
                .map(AccountResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value="/{accountId}", produces="application/json")
    public ResponseEntity<?> getAccount(
        @PathVariable @NonNull UUID accountId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String errorMessage = "Error getting account";
        try {
            // Check if account exists
            Account account = accountService.getUserAccountById(
                accountId, userDetails.getId());
            if (account == null) {
                logger.error("{}", errorMessage);

                return ResponseEntity.badRequest()
                    .body(new MessageResponse(errorMessage + ": Account not found"));
            }

            return ResponseEntity.ok(AccountResponse.from(account));

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }

    @GetMapping(value="/type", produces="application/json")
    public ResponseEntity<?> getAccountTypes() {

        String errorMessage = "Error getting account types";
        try {

            return ResponseEntity.ok(accountService.getAccountTypes());

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}