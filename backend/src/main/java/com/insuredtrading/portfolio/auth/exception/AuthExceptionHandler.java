package com.insuredtrading.portfolio.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insuredtrading.portfolio.core.dto.MessageResponse;

/**
 * Global exception handler for auth-related exceptions.
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthExceptionHandler.class);

    /**
     * Handles errors during sign-in.
     *
     * @param ex the BadCredentialsException
     * @return a bad request response with the error message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResponse> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Bad credentials error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles errors during sign-up.
     * 
     * @param ex the EmailAlreadyInUseException
     * @return a bad request response with the error message
     */
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<MessageResponse> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        log.warn("Email already in use: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Handles role not found errors.
     * 
     * @param ex the RoleNotFoundException
     * @return a not found response with the error message
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<MessageResponse> handleRoleNotFound(RoleNotFoundException ex) {
        log.warn("Role not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }
}
