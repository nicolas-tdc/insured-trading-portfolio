package com.insurancebanking.platform.dto.policy;

import java.time.LocalDateTime;
import java.util.UUID;

import com.insurancebanking.platform.model.Policy;

public class PolicyResponse {

    // Constructors

    public PolicyResponse() {}

    // Properties

    private UUID id;
    private UUID userId;
    private String accountNumber;
    private String policyNumber;
    private String type;
    private Double premium;
    private Double coverageAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    // Response static builder

    public static PolicyResponse from(Policy policy) {
        PolicyResponse response = new PolicyResponse();

        response.setId(policy.getId());
        response.setUserId(policy.getUser().getId());
        response.setAccountNumber(policy.getAccount().getAccountNumber());
        response.setPolicyNumber(policy.getPolicyNumber());
        response.setType(policy.getType());
        response.setPremium(policy.getPremium());
        response.setCoverageAmount(policy.getCoverageAmount());
        response.setStartDate(policy.getStartDate());
        response.setEndDate(policy.getEndDate());
        response.setStatus(policy.getStatus());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getAccountNumber() { return accountNumber; }
    public String getPolicyNumber() { return policyNumber; }
    public String getType() { return type; }
    public Double getPremium() { return premium; }
    public Double getCoverageAmount() { return coverageAmount; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getStatus() { return status; }

    // Setters

    public void setId(UUID id) { this.id = id; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
    public void setType(String type) { this.type = type; }
    public void setPremium(Double premium) { this.premium = premium; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setStatus(String status) { this.status = status; }
}