package com.insuredtrading.portfolio.account.model;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import com.insuredtrading.portfolio.auth.model.User;
import com.insuredtrading.portfolio.core.model.BaseEntity;
import com.insuredtrading.portfolio.policy.model.Policy;
import com.insuredtrading.portfolio.transfer.model.Transfer;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Account
 *
 * JPA entity representing a user account within the insured trading portfolio.
 * Encapsulates account metadata, financial state, and relationships to user,
 * policies, and transfer transactions.
 */
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Account extends BaseEntity {

    /**
     * Owner of the account.
     * Many accounts can belong to one user.
     * Loaded lazily to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Type of account, e.g., SAVINGS, CHECKING.
     * Stored as string representation of the enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    /**
     * Status of the account, defaults to PENDING on creation.
     * Stored as string representation of the enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.PENDING;

    /**
     * Currency code (ISO 4217) of the account balance.
     */
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    /**
     * Unique account number assigned to this account.
     */
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    /**
     * Current monetary balance of the account.
     * Precision set for typical currency usage.
     * Defaults to zero on creation.
     */
    @Column(precision = 19, scale = 4, nullable = false)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Outgoing transfers initiated from this account.
     * Lazy loading with explicit select fetch mode.
     * Marked as JSON managed reference to handle serialization.
     * Initialized to empty set by default.
     */
    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Transfer> outgoingTransfers = new LinkedHashSet<>();

    /**
     * Incoming transfers received by this account.
     * Lazy loading with explicit select fetch mode.
     * Marked as JSON managed reference to handle serialization.
     * Initialized to empty set by default.
     */
    @OneToMany(mappedBy = "targetAccount", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Transfer> incomingTransfers = new LinkedHashSet<>();

    /**
     * Policies linked to this account.
     * Cascade operations apply to maintain referential integrity.
     * Orphan removal enabled to delete policies no longer linked.
     * Initialized to empty set by default.
     */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Policy> policies = new LinkedHashSet<>();

    /**
     * Customized string representation for logging and debugging.
     * Includes key account attributes and owner’s email if available.
     *
     * @return formatted string summarizing the account entity
     */
    @Override
    public String toString() {
        return String.format("Account{id=%s, user.email=%s, type=%s, number=%s, currency=%s, balance=%s, status=%s}",
            getId(),
            user != null ? user.getEmail() : "null",
            accountType,
            accountNumber,
            currencyCode,
            balance,
            accountStatus
        );
    }
}
