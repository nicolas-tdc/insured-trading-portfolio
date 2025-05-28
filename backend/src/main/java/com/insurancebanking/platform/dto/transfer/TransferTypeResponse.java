package com.insurancebanking.platform.dto.transfer;

import java.util.UUID;

import com.insurancebanking.platform.model.TransferType;

public class TransferTypeResponse {
    
    // Constructors

    public TransferTypeResponse() {}

    // Properties

    UUID id;
    String name;

    // Response static builder

    public static TransferTypeResponse from(TransferType transferType) {
        TransferTypeResponse response = new TransferTypeResponse();

        response.setId(transferType.getId());
        response.setName(transferType.getName());

        return response;
    }

    // Getters

    public UUID getId() { return id; }
    public String getName() { return name; }

    // Setters

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}