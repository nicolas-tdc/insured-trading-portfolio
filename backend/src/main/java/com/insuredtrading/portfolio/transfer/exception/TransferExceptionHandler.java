package com.insuredtrading.portfolio.transfer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insuredtrading.portfolio.core.dto.MessageResponse;

/**
 * Global exception handler for transfer-related exceptions.
 */
@RestControllerAdvice
public class TransferExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TransferExceptionHandler.class);

    /**
     * Handles validation errors during transfer operations.
     *
     * @param ex the TransferValidationException
     * @return a bad request response with the validation error message
     */
    @ExceptionHandler(TransferValidationException.class)
    public ResponseEntity<MessageResponse> handleTransferValidation(TransferValidationException ex) {
        log.warn("Transfer validation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles errors occurring during transfer creation.
     *
     * @param ex the TransferCreationException
     * @return an internal server error response with the creation error message
     */
    @ExceptionHandler(TransferCreationException.class)
    public ResponseEntity<MessageResponse> handleTransferCreation(TransferCreationException ex) {
        log.error("Transfer creation error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse(ex.getMessage()));
    }
}
