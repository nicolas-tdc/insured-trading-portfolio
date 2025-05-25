package com.insurancebanking.platform.model;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "balance", precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "currency")
    @Builder.Default
    private final String currency = "USD";

    @Column(name = "status")
    @Builder.Default
    private final String status = "active";

    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Transaction> transactions = new LinkedHashSet<>();

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Policy> policies = new LinkedHashSet<>();

    public Set<Policy> getPolicies() {
        return policies;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                "user.email='" + user.getEmail() + "'" +
                ", accountNumber='" + accountNumber + "'" +
                ", type='" + type + "'" +
                ", balance=" + balance +
                ", currency='" + currency + "'" +
                ", status='" + status + "'" +
                ", transactions.size='" + transactions.size() + "'" +
                ", policies.size='" + policies.size() + "'" +
                '}';
    }
}