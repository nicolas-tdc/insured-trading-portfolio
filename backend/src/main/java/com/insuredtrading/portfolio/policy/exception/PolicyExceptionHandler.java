package com.insuredtrading.portfolio.policy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insuredtrading.portfolio.core.dto.MessageResponse;

/**
 * PolicyExceptionHandler
 *
 * Global exception handler for policy-related exceptions in the application.
 * Uses Spring's @RestControllerAdvice to intercept exceptions thrown
 * in REST controllers and translate them into appropriate HTTP responses.
 */
@RestControllerAdvice
public class PolicyExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(PolicyExceptionHandler.class);

    /**
     * Handles PolicyNotFoundException by logging a warning
     * and returning a 404 Not Found response with the error message.
     *
     * @param ex the thrown PolicyNotFoundException
     * @return ResponseEntity with HTTP 404 and error message body
     */
    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<MessageResponse> handlePolicyNotFound(PolicyNotFoundException ex) {
        log.warn("Policy not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles PolicyCreationException by logging a warning
     * and returning a 400 Bad Request response with the error message.
     *
     * @param ex the thrown PolicyCreationException
     * @return ResponseEntity with HTTP 400 and error message body
     */
    @ExceptionHandler(PolicyCreationException.class)
    public ResponseEntity<MessageResponse> handlePolicyCreationError(PolicyCreationException ex) {
        log.warn("Policy creation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles PolicyTypeNotFoundException by logging a warning
     * and returning a 404 Not Found response with the error message.
     *
     * @param ex the thrown PolicyTypeNotFoundException
     * @return ResponseEntity with HTTP 404 and error message body
     */
    @ExceptionHandler(PolicyTypeNotFoundException.class)
    public ResponseEntity<MessageResponse> handlePolicyTypeNotFound(PolicyTypeNotFoundException ex) {
        log.warn("Policy type not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }
}
