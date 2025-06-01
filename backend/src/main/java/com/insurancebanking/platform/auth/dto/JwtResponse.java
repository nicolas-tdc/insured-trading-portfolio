package com.insurancebanking.platform.auth.dto;


public class JwtResponse {

    // Constructors

    public JwtResponse() {}
    public JwtResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    // Properties

    private String token;
    private String type = "Bearer";
    private UserResponse user;

    // Getters

    public String getToken() { return token; }
    public String getType() { return type; }
    public UserResponse getUser() { return user; }

    // Setters

    public void setToken(String token) { this.token = token; }
    public void setType(String type) { this.type = type; }
    public void setUser(UserResponse user) { this.user = user; }
}