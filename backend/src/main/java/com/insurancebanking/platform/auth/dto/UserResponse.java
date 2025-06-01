package com.insurancebanking.platform.auth.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.insurancebanking.platform.auth.security.UserDetailsImpl;

public class UserResponse {
    
    // Constructors

    public UserResponse() {}

    // Static methods

    public static UserResponse from(UserDetailsImpl userDetails) {
        UserResponse response = new UserResponse();

        response.id = userDetails.getId();
        response.email = userDetails.getEmail();
        response.roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return response;
    }

    // Properties

    private UUID id;
    private String email;
    private List<String> roles;

    // Getters

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }

    // Setters

    public void setId(UUID id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
