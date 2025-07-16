package com.insurancebanking.platform.core.exception;

import com.insurancebanking.platform.core.dto.MessageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Global exception handler for REST controllers.
 * Handles common exceptions and returns standardized HTTP responses with message bodies.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles IllegalArgumentException thrown in the application.
     * Logs a warning and returns HTTP 400 Bad Request with the exception message.
     *
     * @param ex the IllegalArgumentException instance
     * @return ResponseEntity with HTTP 400 status and error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Handled IllegalArgument: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles all other uncaught exceptions.
     * Logs the exception as an error and returns HTTP 500 Internal Server Error with generic message.
     *
     * @param ex the Exception instance
     * @return ResponseEntity with HTTP 500 status and generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGenericException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MessageResponse("An unexpected error occurred."));
    }
}
