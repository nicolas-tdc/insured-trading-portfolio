package com.insurancebanking.platform.transfer;

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

import com.insurancebanking.platform.account.AccountService;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.auth.security.UserDetailsImpl;
import com.insurancebanking.platform.core.dto.MessageResponse;
import com.insurancebanking.platform.transfer.dto.TransferRequest;
import com.insurancebanking.platform.transfer.dto.TransferResponse;
import com.insurancebanking.platform.transfer.model.Transfer;

import io.micrometer.common.lang.NonNull;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TransferController.class);

    @PostMapping(value="", produces="application/json")
    public ResponseEntity<?> createTransfer(
        @RequestBody @NonNull TransferRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String errorMessage = "Error creating transfer";
        try {
            // Validate transfer
            String transferError = transferService.validate(request, userDetails.getId());
            if (transferError != null) {
                return ResponseEntity.badRequest()
                    .body(new MessageResponse(errorMessage + ": " + transferError));
            }

            // Create transfer
            Transfer transfer = transferService.create(request, userDetails.getId());

            // Create new transfer URI
            URI location = new URI("/api/transfer/" + transfer.getId());

            return ResponseEntity.created(location)
                .body(TransferResponse.from(transfer));

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

    @GetMapping(value="/account/{accountId}", produces="application/json")
    public ResponseEntity<?> getAccountTransfers(
        @PathVariable @NonNull UUID accountId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String errorMessage = "Error getting account transfers";
        try {
            // Check if account exists
            Account account = accountService.getUserAccountById(accountId, userDetails.getId());
            if (account == null) {
                return ResponseEntity.badRequest()
                    .body(new MessageResponse(errorMessage + ": Account not found"));
            }

            // Get transfers responses
            List<TransferResponse> responses = transferService.getAccountTransfers(account)
                .stream()
                .map(TransferResponse::from)
                .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            logger.error("{}: {}", errorMessage, e.getMessage());

            return ResponseEntity.badRequest()
                .body(new MessageResponse(errorMessage));
        }
    }
}