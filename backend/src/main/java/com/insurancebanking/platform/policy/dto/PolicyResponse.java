package com.insurancebanking.platform.policy.dto;

import java.util.UUID;

import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;

public class PolicyResponse {

    // Constructors

    public PolicyResponse() {}

    // Properties

    private UUID id;
    private String accountNumber;
    private String policyNumber;
    private PolicyType policyType;
    private Double premium;
    private Double coverageAmount;
    private String status;

    // Response static builder

    public static PolicyResponse from(Policy policy) {
        PolicyResponse response = new PolicyResponse();

        response.setId(policy.getId());
        response.setAccountNumber(policy.getAccount().getAccountNumber());
        response.setPolicyNumber(policy.getPolicyNumber());
        response.setPolicyType(policy.getPolicyType());
        response.setPremium(policy.getPremium());
        response.setCoverageAmount(policy.getCoverageAmount());
        response.setStatus(policy.getStatus());

        System.err.println(response);
        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getPolicyNumber() { return policyNumber; }
    public PolicyType getPolicyType() { return policyType; }
    public Double getPremium() { return premium; }
    public Double getCoverageAmount() { return coverageAmount; }
    public String getStatus() { return status; }

    // Setters

    public void setId(UUID value) { this.id = value; }
    public void setAccountNumber(String value) { this.accountNumber = value; }
    public void setPolicyNumber(String value) { this.policyNumber = value; }
    public void setPolicyType(PolicyType value) { this.policyType = value; }
    public void setPremium(Double value) { this.premium = value; }
    public void setCoverageAmount(Double value) { this.coverageAmount = value; }
    public void setStatus(String value) { this.status = value; }
}