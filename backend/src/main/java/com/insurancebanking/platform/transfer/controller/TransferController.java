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

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createTransfer(
            @RequestBody @NonNull TransferRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws URISyntaxException {

        Transfer transfer = transferService.createTransfer(request, userDetails.getId());

        URI location = new URI("/api/transfer/" + transfer.getId());
        return ResponseEntity.created(location).body(TransferResponse.from(transfer));
    }

    @PostMapping(value = "/validate-internal", produces = "application/json")
    public ResponseEntity<?> validateInternalTransferAccounts(
        @RequestBody @NonNull TransferValidateInternalRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Account targetAccount = transferService.validateInternalTransferAccounts(request, userDetails.getId());

        return ResponseEntity.ok(AccountSecureResponse.from(targetAccount));
    }

    @PostMapping(value = "/validate-external", produces = "application/json")
    public ResponseEntity<?> validateExternalTransferAccounts(
        @RequestBody @NonNull TransferValidateExternalRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Account targetAccount = transferService.validateExternalTransferAccounts(request, userDetails.getId());

        return ResponseEntity.ok(AccountSecureResponse.from(targetAccount));
    }

    @GetMapping(value = "/account/{accountId}", produces = "application/json")
    public ResponseEntity<?> getAccountTransfers(
            @PathVariable @NonNull UUID accountId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Account account = accountService.getUserAccountById(accountId, userDetails.getId());

        List<TransferResponse> responses = accountService.getAccountTransfers(account).stream()
            .map(TransferResponse::from)
            .toList();

        return ResponseEntity.ok(responses);
    }
}