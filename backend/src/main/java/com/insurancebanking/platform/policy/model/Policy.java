package com.insurancebanking.platform.policy.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "policies")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    @Column(name = "policy_number", unique = true, nullable = false)
    private String policyNumber;

    @Column(name = "coverage_amount")
    private Double coverageAmount;

    @Column(name = "premium", nullable = false)
    private Double premium;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    @Builder.Default
    private String status = "active";
}