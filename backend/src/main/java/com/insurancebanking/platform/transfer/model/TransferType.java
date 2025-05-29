package com.insurancebanking.platform.transfer.model;

import com.insurancebanking.platform.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transfer_types")
public class TransferType extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "TransferType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}