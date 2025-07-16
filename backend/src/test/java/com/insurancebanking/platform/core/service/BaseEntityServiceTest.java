package com.insurancebanking.platform.core.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BaseEntityService}.
 * 
 * Validates the generation of public identifiers with correct format and uniqueness.
 */
public class BaseEntityServiceTest {

    private final BaseEntityService service = new BaseEntityService();

    /**
     * Verifies that the generated identifier includes the given prefix
     * followed by exactly nine digits.
     */
    @Test
    void generateEntityPublicIdentifier_shouldIncludePrefixAnd9DigitNumber() {
        String prefix = "TRF";
        String identifier = service.generateEntityPublicIdentifier(prefix);

        assertThat(identifier).startsWith(prefix);
        assertThat(identifier.substring(prefix.length())).matches("\\d{9}");
    }

    /**
     * Ensures that consecutive calls produce different identifiers,
     * confirming randomness or uniqueness.
     */
    @Test
    void generateEntityPublicIdentifier_shouldReturnDifferentValues() {
        String id1 = service.generateEntityPublicIdentifier("ACC");
        String id2 = service.generateEntityPublicIdentifier("ACC");

        assertThat(id1).isNotEqualTo(id2);
    }
}
