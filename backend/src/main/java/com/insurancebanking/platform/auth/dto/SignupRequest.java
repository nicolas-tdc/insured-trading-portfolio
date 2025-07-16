package com.insurancebanking.platform.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user signup requests.
 *
 * @param firstName the user's first name; must be between 3 and 20 characters, non-blank
 * @param lastName the user's last name; must be between 3 and 20 characters, non-blank
 * @param email the user's email address; must be a valid email format, max 50 characters, non-blank
 * @param password the user's password; must be between 6 and 40 characters, non-blank
 */
public record SignupRequest(
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
    String lastName,

    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    @Email(message = "Email must be valid")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 40, message = "Password must be between 4 and 40 characters")
    String password
) {}
