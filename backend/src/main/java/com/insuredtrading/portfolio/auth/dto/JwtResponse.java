package com.insuredtrading.portfolio.auth.dto;

/**
 * JWT response returned after successful authentication.
 *
 * @param token the JWT token
 * @param type the type of the token (default: Bearer)
 * @param user the authenticated user details
 */
public record JwtResponse(String token, String type, UserResponse user) {
    /**
     * Default constructor for JWT response.
     * 
     * @param token
     * @param user
     */
    public JwtResponse(String token, UserResponse user) {
        this(token, "Bearer", user);
    }
}