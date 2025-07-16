package com.insuredtrading.portfolio.auth.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.insuredtrading.portfolio.auth.model.UserDetailsImpl;

/**
 * Response DTO that holds user identity and role information.
 *
 * @param id the user's UUID
 * @param email the user's email
 * @param roles list of assigned roles
 */
public record UserResponse(UUID id, String email, List<String> roles) {

    /**
     * Factory method to convert {@link UserDetailsImpl} to {@link UserResponse}.
     *
     * @param userDetails the user details
     * @return a UserResponse
     */
    public static UserResponse from(UserDetailsImpl userDetails) {
        // Convert authorities to roles
        List<String> roles = userDetails.getAuthorities()
                                        .stream()
                                        .map(authority -> authority.getAuthority())
                                        .collect(Collectors.toList());

        return new UserResponse(userDetails.getId(), userDetails.getEmail(), roles);
    }
}
