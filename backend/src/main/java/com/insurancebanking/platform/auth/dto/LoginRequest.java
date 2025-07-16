package com.insurancebanking.platform.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Login request DTO containing email and password credentials.
 *
 * @param email the user's email address; must be a valid email format, max 50 characters, non-blank
 * @param password the user's password; must be between 6 and 40 characters, non-blank
 */
public record LoginRequest(
    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    String email,

    @Size(min = 4, max = 40, message = "Password must be between 4 and 40 characters")
    String password
) {}
