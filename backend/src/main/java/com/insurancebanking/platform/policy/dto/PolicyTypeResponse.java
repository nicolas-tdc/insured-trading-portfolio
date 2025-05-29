package com.insurancebanking.platform.policy.dto;

import java.util.UUID;

import com.insurancebanking.platform.policy.model.PolicyType;

public class PolicyTypeResponse {
    
    // Constructors

    public PolicyTypeResponse() {}

    // Properties

    UUID id;
    String name;

    // Response static builder

    public static PolicyTypeResponse from(PolicyType policyType) {
        PolicyTypeResponse response = new PolicyTypeResponse();

        response.setId(policyType.getId());
        response.setName(policyType.getName());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public String getName() { return name; }

    // Setters

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}