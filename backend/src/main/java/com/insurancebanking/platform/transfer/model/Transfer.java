package com.insurancebanking.platform.transfer.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.core.model.BaseEntity;

@Entity
@Table(name = "transfers")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TransferStatus transferStatus = TransferStatus.PENDING;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "Transfer{" + 
                "id=" + id +
                ", transferStatus=" + transferStatus +
                ", sourceAccount" + sourceAccount.getAccountNumber() +
                ", targetAccount" + targetAccount.getAccountNumber() +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
