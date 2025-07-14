package com.insurancebanking.platform.account.model;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.core.model.BaseEntity;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.transfer.model.Transfer;

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

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Account extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.PENDING;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(precision = 19, scale = 4, nullable = false)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Transfer> outgoingTransfers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "targetAccount", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Transfer> incomingTransfers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Policy> policies = new LinkedHashSet<>();

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
