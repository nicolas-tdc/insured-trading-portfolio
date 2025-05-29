package com.insurancebanking.platform.account.model;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.core.model.BaseEntity;
import com.insurancebanking.platform.currency.model.Currency;
import com.insurancebanking.platform.policy.model.Policy;
import com.insurancebanking.platform.transfer.model.Transfer;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance", precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "status")
    @Builder.Default
    private final String status = "active";

    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Transfer> transfers = new LinkedHashSet<>();

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    public Set<Policy> getPolicies() {
        return policies;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @Builder.Default
    private final Set<Policy> policies = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                "user.email='" + user.getEmail() + "'" +
                ", accountType='" + accountType + "'" +
                ", currency='" + currency + "'" +
                ", accountNumber='" + accountNumber + "'" +
                ", balance=" + balance +
                ", status='" + status + "'" +
                ", transfers.size='" + transfers.size() + "'" +
                ", policies.size='" + policies.size() + "'" +
                '}';
    }
}