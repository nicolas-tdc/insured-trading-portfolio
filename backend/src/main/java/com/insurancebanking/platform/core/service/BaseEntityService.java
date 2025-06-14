package com.insurancebanking.platform.core.service;

import org.springframework.stereotype.Service;

@Service
public class BaseEntityService {

    public String generateEntityPublicIdentifier(String prefix) {
        int number = (int)(Math.random() * 900_000_000) + 100_000_000;

        return prefix + number;
    }
}
