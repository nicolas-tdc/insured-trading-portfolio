package com.insurancebanking.platform.account.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insurancebanking.platform.core.dto.MessageResponse;

@RestControllerAdvice
public class AccountExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(AccountExceptionHandler.class);

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<MessageResponse> handleAccountNotFound(AccountNotFoundException ex) {
        log.warn("Account not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(AccountNumberNotFoundException.class)
    public ResponseEntity<MessageResponse> handleAccountNumberNotFound(AccountNumberNotFoundException ex) {
        log.warn("Account number not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(AccountNumberGenerationException.class)
    public ResponseEntity<MessageResponse> handleAccountNumberGenerationError(AccountNumberGenerationException ex) {
        log.warn("Account number generation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(UnsupportedCurrencyException.class)
    public ResponseEntity<MessageResponse> handleUnsupportedCurrency(UnsupportedCurrencyException ex) {
        log.warn("Unsupported currency: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

}
