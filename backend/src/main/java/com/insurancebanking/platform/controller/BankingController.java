package com.insurancebanking.platform.controller;

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

import com.insurancebanking.platform.dto.banking.AccountResponse;
import com.insurancebanking.platform.dto.banking.TransferRequest;
import com.insurancebanking.platform.dto.common.MessageResponse;
import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.User;
import com.insurancebanking.platform.security.UserDetailsImpl;
import com.insurancebanking.platform.service.AccountService;
import com.insurancebanking.platform.service.TransactionService;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/banking")
public class BankingController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @GetMapping(value="/accounts", produces="application/json")
    public ResponseEntity<List<AccountResponse>> getUserAccounts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = new User();
        user.setId(userDetails.getId());
        List<Account> accounts = accountService.getUserAccounts(user);
        List<AccountResponse> responses = accounts.stream()
            .map(AccountResponse::from)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(value="/transfer", produces="application/json")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getId();

        String sourceId = request.getSourceAccountId();
        String targetAccountNumber = request.getTargetAccountNumber();

        Account fromAccount = accountService.getAccountById(sourceId, userId);
        Account toAccount = accountService.getAccountByAccountNumber(targetAccountNumber, userId);

        ResponseEntity<MessageResponse> checkResult = transactionService.checkTransfer(fromAccount, toAccount, request.getAmount());
        if (checkResult.getStatusCode().is2xxSuccessful()) {
            transactionService.recordTransfer(fromAccount, toAccount, request.getAmount(), "Transfer");
        }

        return checkResult;
    }

    @GetMapping(value="/transactions/{accountNumber}", produces="application/json")
    public ResponseEntity<List<?>> getTransactions(@PathVariable String accountNumber,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Account account = accountService.getAccountByAccountNumber(accountNumber, userDetails.getId());
        if (account == null) {
            return ResponseEntity.badRequest().body(List.of("Invalid account"));
        }
        return ResponseEntity.ok(transactionService.getAccountTransactions(account));
    }

    @PostMapping(value="/open-account", produces="application/json")
    public ResponseEntity<AccountResponse> openNewAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getId();
        Account newAccount = accountService.createAccountForUserId(userId);

        return ResponseEntity.ok(AccountResponse.from(newAccount));
    }
}