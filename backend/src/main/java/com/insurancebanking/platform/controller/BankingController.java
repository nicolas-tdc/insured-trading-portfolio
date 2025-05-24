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

import com.insurancebanking.platform.dto.AccountDto;
import com.insurancebanking.platform.model.Account;
import com.insurancebanking.platform.model.User;
import com.insurancebanking.platform.payload.request.TransferRequest;
import com.insurancebanking.platform.payload.response.MessageResponse;
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
    public ResponseEntity<List<Account>> getUserAccounts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = new User();
        user.setId(userDetails.getId());
        return ResponseEntity.ok(accountService.getUserAccounts(user));
    }

    @PostMapping(value="/transfer", produces="application/json")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getId();

        Account fromAccount = accountService.getAccountByAccountNumber(request.getSourceAccountNumber(), userId);
        Account toAccount = accountService.getAccountByAccountNumber(request.getTargetAccountNumber(), userId);

        if (fromAccount == null || toAccount == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid account(s)"));
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            return ResponseEntity.badRequest().body(new MessageResponse("Insufficient balance"));
        }

        transactionService.recordTransfer(fromAccount, toAccount, request.getAmount(), "Transfer");

        return ResponseEntity.ok(new MessageResponse("Transfer successful"));
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
    public ResponseEntity<AccountDto> openNewAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getId();
        Account newAccount = accountService.createAccountForUserId(userId);

        return ResponseEntity.ok(AccountDto.from(newAccount));
    }
}