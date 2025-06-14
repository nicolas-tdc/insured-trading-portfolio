package com.insurancebanking.platform.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Login request DTO containing email and password credentials.
 *
 * @param email the user's email address
 * @param password the user's password
 */
public record LoginRequest(
    @NotBlank(message = "Email is required") String email,
    @NotBlank(message = "Password is required") String password
) {}
