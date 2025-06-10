package com.insurancebanking.platform.core.service;

public class BaseEntityService {

    public String generateEntityPublicIdentifier(String prefix) {
        int number = (int)(Math.random() * 900_000_000) + 100_000_000; // 9 digits

        return prefix + number;
    }
}
