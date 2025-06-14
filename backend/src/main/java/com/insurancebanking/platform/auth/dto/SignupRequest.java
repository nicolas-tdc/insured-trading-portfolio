package com.insurancebanking.platform.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user signup containing personal and account information.
 *
 * @param firstName the user's first name
 * @param lastName the user's last name
 * @param email the user's email address
 * @param password the user's password
 */
public record SignupRequest(
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 20)
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 20)
    String lastName,

    @NotBlank(message = "Email is required")
    @Size(max = 50)
    @Email
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40)
    String password
) {}
