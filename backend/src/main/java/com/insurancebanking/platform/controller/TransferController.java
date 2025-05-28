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

import com.insurancebanking.platform.dto.common.MessageResponse;
import com.insurancebanking.platform.dto.transfer.TransferRequest;
import com.insurancebanking.platform.dto.transfer.TransferResponse;
import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.Transfer;
import com.insurancebanking.platform.security.UserDetailsImpl;
import com.insurancebanking.platform.service.AccountService;
import com.insurancebanking.platform.service.TransferService;

import io.micrometer.common.lang.NonNull;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    @PostMapping(value="", produces="application/json")
    public ResponseEntity<?> transfer(
        @RequestBody @NonNull TransferRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Validate transfer
            String transferError = transferService.validate(request, userDetails.getId());
            if (transferError != null) {
                return ResponseEntity.badRequest()
                    .body(new MessageResponse(transferError));
            }

            // Create transfer
            Transfer newTransfer = transferService.create(request, userDetails.getId());

            // Create new transfer URI
            URI location = new URI("/api/transfer/" + newTransfer.getId());

            return ResponseEntity.created(location)
                .body(TransferResponse.from(newTransfer));

        } catch (URISyntaxException e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Invalid transfer URI:" + e.getMessage()));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error during transfer: " + e.getMessage()));
        }
    }

    @GetMapping(value="/account/{accountId}", produces="application/json")
    public ResponseEntity<?> getAccountTransfers(
        @PathVariable @NonNull UUID accountId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Check if account exists
            Account account = accountService.getUserAccountById(accountId, userDetails.getId());
            if (account == null) {
                return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid account id"));
            }

            // Get transfers responses
            List<TransferResponse> responses = transferService.getAccountTransfers(account)
                .stream()
                .map(TransferResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting account transfers: " + e.getMessage()));
        }
    }

    @GetMapping("/types")
    public ResponseEntity<?> getPolicyTypes() {
        try {
            return ResponseEntity.ok(transferService.getTransferTypes());

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new MessageResponse("Error getting policy types: " + e.getMessage()));
        }
    }
}