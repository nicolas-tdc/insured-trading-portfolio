package com.insurancebanking.platform.dto.auth;

import java.util.List;
import java.util.UUID;

public class JwtResponse {

    // Constructors

    public JwtResponse() {}
    public JwtResponse(String token, UUID id, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    // Properties

    private String token;
    private String type = "Bearer";
    private UUID id;
    private String email;
    private List<String> roles;

    // Getters

    public String getToken() { return token; }
    public String getType() { return type; }
    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }

    // Setters

    public void setToken(String token) { this.token = token; }
    public void setType(String type) { this.type = type; }
    public void setId(UUID id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}