package com.insurancebanking.platform.core.service;

import org.springframework.stereotype.Service;

/**
 * BaseEntityService
 *
 * Provides common utility methods related to base entities.
 */
@Service
public class BaseEntityService {

    /**
     * Generates a pseudo-random public identifier string with a given prefix.
     * The identifier consists of the prefix concatenated with a 9-digit random number.
     *
     * @param prefix the prefix string to prepend to the generated number
     * @return generated public identifier string
     */
    public String generateEntityPublicIdentifier(String prefix) {
        // Generate a random integer between 100_000_000 and 999_999_999 (inclusive)
        int number = (int)(Math.random() * 900_000_000) + 100_000_000;

        // Concatenate prefix and number to form the identifier
        return prefix + number;
    }
}
