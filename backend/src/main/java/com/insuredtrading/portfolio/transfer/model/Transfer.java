package com.insuredtrading.portfolio.transfer.model;

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

import com.insuredtrading.portfolio.account.model.Account;
import com.insuredtrading.portfolio.core.model.BaseEntity;

/**
 * Entity representing a money transfer between accounts.
 */
@Entity
@Table(name = "transfers")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer extends BaseEntity {

    /**
     * Source account from which the amount will be debited.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    /**
     * Target account to which the amount will be credited.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    @JoinColumn(name = "target_account_id", nullable = false)
    private Account targetAccount;

    /**
     * Current status of the transfer.
     * Defaults to PENDING when created.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TransferStatus transferStatus = TransferStatus.PENDING;

    /**
     * Unique identifier for the transfer.
     */
    @Column(name = "transfer_number", unique = true, nullable = false)
    private String transferNumber;

    /**
     * Currency code in ISO 4217 format (e.g., USD, EUR).
     */
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    /**
     * Amount to be transferred.
     */
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * Optional description or note for the transfer.
     */
    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "Transfer{" + 
                "id=" + id +
                ", transferNumber=" + transferNumber +
                ", transferStatus=" + transferStatus +
                ", sourceAccount=" + sourceAccount.getAccountNumber() +
                ", targetAccount=" + targetAccount.getAccountNumber() +
                ", currencyCode=" + currencyCode +
                ", amount=" + amount +
                ", description=" + description +
                "}";
    }
}
