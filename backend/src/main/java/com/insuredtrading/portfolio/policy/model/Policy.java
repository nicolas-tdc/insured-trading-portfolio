package com.insuredtrading.portfolio.policy.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.insuredtrading.portfolio.account.model.Account;
import com.insuredtrading.portfolio.auth.model.User;
import com.insuredtrading.portfolio.core.model.BaseEntity;

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

/**
 * Policy
 *
 * Entity representing an insurance policy.
 * Extends BaseEntity to inherit common entity fields.
 * Maps to the "policies" table in the database.
 *
 * Contains associations to User and Account entities, policy metadata,
 * coverage and premium details, and policy duration.
 */
@Entity
@Table(name = "policies")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy extends BaseEntity {

    /**
     * The user who owns this policy.
     * Many policies can belong to one user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Prevent circular reference during JSON serialization
    private User user;

    /**
     * The account associated with this policy.
     * Many policies can be linked to one account.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference // Prevent circular reference during JSON serialization
    private Account account;

    /**
     * The type of this policy.
     * Stored as a string representation of the PolicyType enum.
     */
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    /**
     * The current status of the policy.
     * Defaults to PENDING if not specified.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PolicyStatus policyStatus = PolicyStatus.PENDING;

    /**
     * Unique policy number identifying this policy.
     */
    @Column(name = "policy_number", unique = true, nullable = false)
    private String policyNumber;

    /**
     * Currency code used for coverage and premium amounts.
     */
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    /**
     * The amount of coverage the policy provides.
     */
    @Column(name = "coverage_amount")
    private Double coverageAmount;

    /**
     * The premium amount to be paid for this policy.
     */
    @Column(name = "premium", nullable = false)
    private Double premium;

    /**
     * The start date and time of the policy coverage.
     */
    @Column(name = "start_date")
    private Instant startDate;

    /**
     * The end date and time when the policy coverage expires.
     */
    @Column(name = "end_date")
    private Instant endDate;

    /**
     * Custom string representation of the Policy entity,
     * including key fields for debugging and logging.
     *
     * @return String representation of the Policy
     */
    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", user=" + user +
                ", account=" + account +
                ", policyType=" + policyType +
                ", policyStatus=" + policyStatus +
                ", policyNumber=" + policyNumber +
                ", currencyCode=" + currencyCode +
                ", coverageAmount=" + coverageAmount +
                ", premium=" + premium +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                "}";
    }
}
