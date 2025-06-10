package com.insurancebanking.platform.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    // Properties

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}