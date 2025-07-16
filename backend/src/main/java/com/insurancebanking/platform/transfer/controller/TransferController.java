package com.insurancebanking.platform.transfer.controller;

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

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.UserDetailsImpl;
import com.insurancebanking.platform.transfer.dto.TransferRequest;
import com.insurancebanking.platform.transfer.dto.TransferResponse;
import com.insurancebanking.platform.transfer.dto.TransferValidateExternalRequest;
import com.insurancebanking.platform.transfer.dto.TransferValidateInternalRequest;
import com.insurancebanking.platform.transfer.model.Transfer;
import com.insurancebanking.platform.transfer.service.TransferService;
import com.insurancebanking.platform.account.dto.AccountSecureResponse;

import io.micrometer.common.lang.NonNull;

/**
 * TransferController
 *
 * REST controller managing money transfers within the insurance banking platform.
 * Provides authenticated endpoints to:
 * - Create new transfers
 * - Validate internal transfer accounts
 * - Validate external transfer accounts
 * - Retrieve transfers associated with a specific account
 */
@RestController
@RequestMapping("/api/transfer")
@PreAuthorize("isAuthenticated()") // Require authentication for all methods
public class TransferController {

    @Autowired
    private AccountService accountService; // Service for account-related operations

    @Autowired
    private TransferService transferService; // Service handling transfer business logic

    /**
     * POST /api/transfer
     *
     * Creates a new transfer for the authenticated user.
     *
     * @param request     transfer details
     * @param userDetails authenticated user's details
     * @return HTTP 201 response with created transfer and location header
     * @throws URISyntaxException if URI creation fails
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createTransfer(
            @RequestBody @NonNull TransferRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws URISyntaxException {

        // Create the transfer using provided details and user ID
        Transfer transfer = transferService.createTransfer(request, userDetails.getId());

        // Build the URI location of the created transfer
        URI location = new URI("/api/transfer/" + transfer.getId());

        // Return HTTP 201 Created with transfer response body
        return ResponseEntity.created(location).body(TransferResponse.from(transfer));
    }

    /**
     * POST /api/transfer/validate-internal
     *
     * Validates internal transfer accounts before performing the transfer.
     *
     * @param request     internal transfer validation request data
     * @param userDetails authenticated user's details
     * @return AccountSecureResponse representing the validated target account
     */
    @PostMapping(value = "/validate-internal", produces = "application/json")
    public ResponseEntity<?> validateInternalTransferAccounts(
        @RequestBody @NonNull TransferValidateInternalRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Validate accounts involved in internal transfer for this user
        Account targetAccount = transferService.validateInternalTransferAccounts(request, userDetails.getId());

        // Return secured account data for the target account
        return ResponseEntity.ok(AccountSecureResponse.from(targetAccount));
    }

    /**
     * POST /api/transfer/validate-external
     *
     * Validates external transfer accounts before performing the transfer.
     *
     * @param request     external transfer validation request data
     * @param userDetails authenticated user's details
     * @return AccountSecureResponse representing the validated external target account
     */
    @PostMapping(value = "/validate-external", produces = "application/json")
    public ResponseEntity<?> validateExternalTransferAccounts(
        @RequestBody @NonNull TransferValidateExternalRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Validate accounts involved in external transfer for this user
        Account targetAccount = transferService.validateExternalTransferAccounts(request, userDetails.getId());

        // Return secured account data for the target external account
        return ResponseEntity.ok(AccountSecureResponse.from(targetAccount));
    }

    /**
     * GET /api/transfer/account/{accountId}
     *
     * Retrieves all transfers associated with a given account owned by the authenticated user.
     *
     * @param accountId   UUID of the account whose transfers are requested
     * @param userDetails authenticated user's details
     * @return list of TransferResponse objects representing transfers of the account
     */
    @GetMapping(value = "/account/{accountId}", produces = "application/json")
    public ResponseEntity<?> getAccountTransfers(
            @PathVariable @NonNull UUID accountId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Fetch the user's account by ID, verifying ownership
        Account account = accountService.getUserAccountById(accountId, userDetails.getId());

        // Retrieve transfers linked to the account and map to response DTOs
        List<TransferResponse> responses = accountService.getAccountTransfers(account).stream()
            .map(TransferResponse::from)
            .toList();

        // Return the list of transfers
        return ResponseEntity.ok(responses);
    }
}
