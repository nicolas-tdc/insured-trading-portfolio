package com.insurancebanking.platform.model;

import java.math.BigDecimal;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    @JoinColumn(name = "target_account_id", nullable = false)
    private Account targetAccount;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "balance_after", precision = 19, scale = 4)
    private BigDecimal balanceAfter;

    @Override
    public String toString() {
        return "Transaction{" + 
                "id=" + id +
                "sourceAccount" + sourceAccount.getAccountNumber() +
                "targetAccount" + targetAccount.getAccountNumber() +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", balanceAfter=" + balanceAfter +
                '}';
    }
}
