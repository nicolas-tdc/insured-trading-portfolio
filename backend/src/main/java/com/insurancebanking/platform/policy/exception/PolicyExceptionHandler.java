package com.insurancebanking.platform.policy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insurancebanking.platform.core.dto.MessageResponse;

@RestControllerAdvice
public class PolicyExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(PolicyExceptionHandler.class);

    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<MessageResponse> handlePolicyNotFound(PolicyNotFoundException ex) {
        log.warn("Policy not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(PolicyCreationException.class)
    public ResponseEntity<MessageResponse> handlePolicyCreationError(PolicyCreationException ex) {
        log.warn("Policy creation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(PolicyTypeNotFoundException.class)
    public ResponseEntity<MessageResponse> handlePolicyTypeNotFound(PolicyTypeNotFoundException ex) {
        log.warn("Policy type not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }
}
