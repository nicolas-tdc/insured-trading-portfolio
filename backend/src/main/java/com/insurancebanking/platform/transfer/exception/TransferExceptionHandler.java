package com.insurancebanking.platform.transfer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.insurancebanking.platform.core.dto.MessageResponse;

public class TransferExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(TransferExceptionHandler.class);

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
