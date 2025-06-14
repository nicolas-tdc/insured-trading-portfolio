package com.insurancebanking.platform.core.exception;

import com.insurancebanking.platform.core.dto.MessageResponse;
import com.insurancebanking.platform.policy.exception.PolicyCreationException;
import com.insurancebanking.platform.policy.exception.PolicyNotFoundException;
import com.insurancebanking.platform.transfer.exception.TransferCreationException;
import com.insurancebanking.platform.transfer.exception.TransferValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Generic

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Handled IllegalArgument: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGenericException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MessageResponse("An unexpected error occurred."));
    }

    // Policy

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

    // Transfer

    @ExceptionHandler(TransferValidationException.class)
    public ResponseEntity<MessageResponse> handleTransferValidation(TransferValidationException ex) {
        log.warn("Transfer validation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(TransferCreationException.class)
    public ResponseEntity<MessageResponse> handleTransferCreation(TransferCreationException ex) {
        log.error("Transfer creation error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse(ex.getMessage()));
    }

}
