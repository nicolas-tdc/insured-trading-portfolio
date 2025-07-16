package com.insuredtrading.portfolio.account.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insuredtrading.portfolio.core.dto.MessageResponse;

/**
 * AccountExceptionHandler
 *
 * Global exception handler for account-related exceptions.
 * Maps specific domain exceptions to appropriate HTTP responses
 * and logs relevant details for monitoring and debugging.
 */
@RestControllerAdvice
public class AccountExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(AccountExceptionHandler.class);

    /**
     * Handles AccountNotFoundException.
     * Returns HTTP 404 response with a message.
     *
     * @param ex thrown exception
     * @return response containing error message
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<MessageResponse> handleAccountNotFound(AccountNotFoundException ex) {
        log.warn("Account not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles AccountTypeNotFoundException.
     * Returns HTTP 404 response with a message.
     *
     * @param ex thrown exception
     * @return response containing error message
     */
    @ExceptionHandler(AccountTypeNotFoundException.class)
    public ResponseEntity<MessageResponse> handleAccountTypeNotFound(AccountTypeNotFoundException ex) {
        log.warn("Account type not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles AccountNumberNotFoundException.
     * Returns HTTP 404 response with a message.
     *
     * @param ex thrown exception
     * @return response containing error message
     */
    @ExceptionHandler(AccountNumberNotFoundException.class)
    public ResponseEntity<MessageResponse> handleAccountNumberNotFound(AccountNumberNotFoundException ex) {
        log.warn("Account number not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles AccountNumberGenerationException.
     * Returns HTTP 400 response with a message.
     *
     * @param ex thrown exception
     * @return response containing error message
     */
    @ExceptionHandler(AccountNumberGenerationException.class)
    public ResponseEntity<MessageResponse> handleAccountNumberGenerationError(AccountNumberGenerationException ex) {
        log.warn("Account number generation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles UnsupportedCurrencyException.
     * Returns HTTP 400 response with a message.
     *
     * @param ex thrown exception
     * @return response containing error message
     */
    @ExceptionHandler(UnsupportedCurrencyException.class)
    public ResponseEntity<MessageResponse> handleUnsupportedCurrency(UnsupportedCurrencyException ex) {
        log.warn("Unsupported currency: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }
}
