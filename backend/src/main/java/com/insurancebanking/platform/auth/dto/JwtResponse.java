package com.insurancebanking.platform.auth.dto;

/**
 * JWT response returned after successful authentication.
 *
 * @param token the JWT token
 * @param type the type of the token (default: Bearer)
 * @param user the authenticated user details
 */
public record JwtResponse(String token, String type, UserResponse user) {
    public JwtResponse(String token, UserResponse user) {
        this(token, "Bearer", user);
    }
}