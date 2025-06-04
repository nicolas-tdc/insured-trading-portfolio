package com.insurancebanking.platform.policy.dto;

import java.util.UUID;

import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.policy.model.PolicyType;
import com.insurancebanking.platform.policy.model.PolicyStatus;

public class PolicyResponse {

    // Constructors

    public PolicyResponse() {}

    // Properties

    private UUID id;
    private PolicyStatus policyStatus;
    private String accountNumber;
    private String policyNumber;
    private PolicyType policyType;
    private Double premium;
    private Double coverageAmount;

    // Response static builder

    public static PolicyResponse from(Policy policy) {
        PolicyResponse response = new PolicyResponse();

        response.setId(policy.getId());
        response.setPolicyStatus(policy.getPolicyStatus());
        response.setAccountNumber(policy.getAccount().getAccountNumber());
        response.setPolicyNumber(policy.getPolicyNumber());
        response.setPolicyType(policy.getPolicyType());
        response.setPremium(policy.getPremium());
        response.setCoverageAmount(policy.getCoverageAmount());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public PolicyStatus getPolicyStatus() { return policyStatus; }
    public String getAccountNumber() { return accountNumber; }
    public String getPolicyNumber() { return policyNumber; }
    public PolicyType getPolicyType() { return policyType; }
    public Double getPremium() { return premium; }
    public Double getCoverageAmount() { return coverageAmount; }

    // Setters

    public void setId(UUID value) { this.id = value; }
    public void setPolicyStatus(PolicyStatus value) { this.policyStatus = value; }
    public void setAccountNumber(String value) { this.accountNumber = value; }
    public void setPolicyNumber(String value) { this.policyNumber = value; }
    public void setPolicyType(PolicyType value) { this.policyType = value; }
    public void setPremium(Double value) { this.premium = value; }
    public void setCoverageAmount(Double value) { this.coverageAmount = value; }
}